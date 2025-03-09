package com.example.demo.Controller;

import com.example.demo.model.Usuario;
import com.example.demo.Repo.UsuarioRepository;
import com.example.demo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.ContractController.*;
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import com.example.demo.Role.Roles;
import com.example.demo.Repo.RolesRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private RolesRepository rolesRepository;
    
    private RestTemplate restTemplate;
    
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        Set<LocalDate> festivos = obtenerFestivos();
        usuario.calcularDiasTrabajados(festivos);
        Usuario nuevoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }


    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findByFechaEliminacionIsNull();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/calcular-dias")
    public ResponseEntity<Integer> calcularDiasHabiles(@RequestBody FechasRequest request) {
        LocalDate fechaInicio = LocalDate.parse(request.getFechaInicio());
        LocalDate fechaFin = LocalDate.parse(request.getFechaFin());
        int diasHabiles = contarDiasHabiles(fechaInicio, fechaFin);
        return ResponseEntity.ok(diasHabiles);
    }

    private int contarDiasHabiles(LocalDate inicio, LocalDate fin) {
        Set<LocalDate> festivos = obtenerFestivos();
        int diasHabiles = 0;

        while (!inicio.isAfter(fin)) {
            if (inicio.getDayOfWeek() != DayOfWeek.SATURDAY && 
                inicio.getDayOfWeek() != DayOfWeek.SUNDAY &&
                !festivos.contains(inicio)) {
                diasHabiles++;
            }
            inicio = inicio.plusDays(1);
        }
        return diasHabiles;
    }

    private Set<LocalDate> obtenerFestivos() {
        try {
            String url = "https://api-colombia.com/api/v1/holiday/year/2025";
            List<?> respuesta = restTemplate.getForObject(url, List.class);

            Set<LocalDate> festivos = new HashSet<>();
            if (respuesta != null) {
                for (Object item : respuesta) {
                    String fecha = item.toString().split("date=")[1].split(",")[0];
                    festivos.add(LocalDate.parse(fecha));
                }
            }
            return festivos;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashSet<>();
        }
    }
    
    @GetMapping("/contrato/{id}")
    public ResponseEntity<byte[]> downloadContrato(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        ContractController contractController = new ContractController();
        byte[] contratoBytes = contractController.generateContract(usuario, "path/a/la/firma/imagen.png");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contrato_" + usuario.getId() + ".pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");

        return new ResponseEntity<>(contratoBytes, headers, HttpStatus.OK);
    }
    
    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.editUsuario(id, usuario);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Usuario> eliminarUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setFechaEliminacion(LocalDate.now());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }


    @GetMapping("/busquedaid/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    
    @PostMapping("/roles")
    public ResponseEntity<List<Roles>> getRoles() {
        List<Roles> roles = rolesRepository.findAll();
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    static class FechasRequest {
        private String fechaInicio;
        private String fechaFin;

        public String getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(String fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public String getFechaFin() {
            return fechaFin;
        }

        public void setFechaFin(String fechaFin) {
            this.fechaFin = fechaFin;
        }
    }
}
