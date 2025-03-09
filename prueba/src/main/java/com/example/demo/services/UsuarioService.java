package com.example.demo.services;

import com.example.demo.model.Usuario;
import com.example.demo.Repo.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseEntity<String> editUsuario(Long id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Usuario editado con éxito.");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            Usuario usuario = usuarioRepository.findById(id).get();
            usuario.setFechaEliminacion(LocalDate.now());
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Usuario marcado como eliminado con éxito.");
        }
        return ResponseEntity.notFound().build();
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
}
