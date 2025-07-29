package com.planta.plantapp.dominio.usuario.modelo;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Modelo de dominio puro que representa un Usuario (sin lógica de negocio).
 */
public class Usuario {

    private Long id;
    private String nombre;
    private String correo;
    private String contrasena;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimoAcceso;
    private List<Planta> plantas;

    public Usuario(Long id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }
    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena; // Hash

    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public boolean isActivo() {
        return activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDateTime getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public List<Planta> getPlantas() {
        return plantas;
    }

    // Setters
    public void setNombreCompleto(String nombreCompleto) {
        this.nombre = nombreCompleto;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }

    public void setPlantas(List<Planta> plantas) {
        this.plantas = plantas;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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