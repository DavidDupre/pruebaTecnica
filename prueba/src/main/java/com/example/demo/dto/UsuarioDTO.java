package com.example.demo.dto;

import com.example.demo.model.Usuario;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String cargo;
    private String fechaIngreso;
    private int diasTrabajados;

    public UsuarioDTO(Usuario usuario, int diasTrabajados) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        
        if (usuario.getRol() != null) {
            this.cargo = usuario.getRol().getNombreCargo();
        } else {
            this.cargo = "Cargo no disponible";
        }
        
        if (usuario.getFechaIngreso() != null) {
            this.fechaIngreso = usuario.getFechaIngreso().toString();
        } else {
            this.fechaIngreso = "Fecha no disponible";
        }
        
        this.diasTrabajados = diasTrabajados;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getCargo() { return cargo; }
    public String getFechaIngreso() { return fechaIngreso; }
    public int getDiasTrabajados() { return diasTrabajados; }
}
