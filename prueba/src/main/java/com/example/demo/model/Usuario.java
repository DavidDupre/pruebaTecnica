package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.DayOfWeek; 
import java.time.temporal.ChronoUnit;
import java.util.Set;
import com.example.demo.Role.Roles;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Roles rol;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    private int diasTrabajados;

    @Column(name = "fecha_eliminacion")
    private LocalDate fechaEliminacion;

    public Usuario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Roles getRol() { return rol; }
    public void setRol(Roles rol) { this.rol = rol; }

    public Long getIdRol() { return rol != null ? rol.getId() : null; }

    public void setCargo(Long cargo) {
    	if (this.rol == null) {
    	    this.rol = new Roles();
    	  }
    	  this.rol.setId(cargo);
    	}

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public int getDiasTrabajados() { return diasTrabajados; }
    public void setDiasTrabajados(int diasTrabajados) { this.diasTrabajados = diasTrabajados; }

    public LocalDate getFechaEliminacion() { return fechaEliminacion; }
    public void setFechaEliminacion(LocalDate fechaEliminacion) { this.fechaEliminacion = fechaEliminacion; }

    public void calcularDiasTrabajados(Set<LocalDate> festivos) {
        if (fechaIngreso != null) {
            LocalDate today = LocalDate.now();
            this.diasTrabajados = (int) ChronoUnit.DAYS.between(fechaIngreso, today);
        } else {
            this.diasTrabajados = 0;
        }
        this.diasTrabajados = contarDiasHabiles(fechaIngreso, LocalDate.now(), festivos);
    }

    private int contarDiasHabiles(LocalDate inicio, LocalDate fin, Set<LocalDate> festivos) {
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

    @PrePersist
    @PreUpdate
    public void prePersistUpdate() {
        Set<LocalDate> festivos = obtenerFestivos();
        calcularDiasTrabajados(festivos);
    }

    private Set<LocalDate> obtenerFestivos() {
        return Set.of(
            LocalDate.of(2025, 1, 1),
            LocalDate.of(2025, 1, 6),
            LocalDate.of(2025, 3, 24),
            LocalDate.of(2025, 4, 13)
        );
    }
}
