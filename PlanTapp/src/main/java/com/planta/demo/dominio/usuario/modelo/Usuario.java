package com.planta.demo.dominio.usuario.modelo;

import com.planta.demo.dominio.modelo.planta.Planta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Modelo de dominio puro que representa un Usuario (sin lógica de negocio).
 */
public class Usuario {

    private Long id;
    private String nombreUsuario;
    private String email;
    private String password;
    private String nombreCompleto;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimoAcceso;
    private List<Planta> plantas;

    public Usuario(String nombreUsuario, String email, String password) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getNombreCompleto() { return nombreCompleto; }
    public boolean isActivo() { return activo; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaUltimoAcceso() { return fechaUltimoAcceso; }
    public List<Planta> getPlantas() { return plantas; }

    // Setters
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) { this.fechaUltimoAcceso = fechaUltimoAcceso; }
    public void setPlantas(List<Planta> plantas) { this.plantas = plantas; }
    public void setPassword(String password) {
        this.password = password;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
