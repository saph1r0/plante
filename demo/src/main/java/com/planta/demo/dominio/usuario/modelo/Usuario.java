package com.planta.demo.dominio.usuario.modelo;

import com.planta.demo.dominio.modelo.planta.Planta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Modelo de dominio puro que representa un Usuario.
 * VERSIÓN CORREGIDA con métodos faltantes
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

    // Constructores
    public Usuario(String nombreUsuario, String email, String password) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor vacío para JPA/serialización
    public Usuario() {
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    // ========================================
    // GETTERS Y SETTERS CORREGIDOS
    // ========================================

    public Long getId() { 
        return id; 
    }
    
    // ARREGLO: setId que acepta tanto Long como String
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public void setId(String id) { 
        this.id = id != null ? Long.parseLong(id) : null; 
    }

    public String getNombreUsuario() { 
        return nombreUsuario; 
    }
    
    public void setNombreUsuario(String nombreUsuario) { 
        this.nombreUsuario = nombreUsuario; 
    }

    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }

    // ARREGLO: Agregar getCorreo() que era lo que faltaba
    public String getCorreo() { 
        return email; 
    }
    
    public void setCorreo(String correo) { 
        this.email = correo; 
    }

    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() { 
        return nombreCompleto; 
    }
    
    public void setNombreCompleto(String nombreCompleto) { 
        this.nombreCompleto = nombreCompleto; 
    }

    public boolean isActivo() { 
        return activo; 
    }
    
    public void setActivo(boolean activo) { 
        this.activo = activo; 
    }

    public LocalDateTime getFechaCreacion() { 
        return fechaCreacion; 
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) { 
        this.fechaCreacion = fechaCreacion; 
    }

    public LocalDateTime getFechaUltimoAcceso() { 
        return fechaUltimoAcceso; 
    }
    
    public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) { 
        this.fechaUltimoAcceso = fechaUltimoAcceso; 
    }

    public List<Planta> getPlantas() { 
        return plantas; 
    }
    
    public void setPlantas(List<Planta> plantas) { 
        this.plantas = plantas; 
    }

    // ========================================
    // MÉTODOS DE UTILIDAD
    // ========================================

    /**
     * Actualiza la fecha de último acceso a ahora
     */
    public void actualizarUltimoAcceso() {
        this.fechaUltimoAcceso = LocalDateTime.now();
    }

    /**
     * Verifica si el usuario está activo
     */
    public boolean estaActivo() {
        return this.activo;
    }

    /**
     * Obtiene el nombre de display (nombreCompleto o nombreUsuario)
     */
    public String getNombreDisplay() {
        return nombreCompleto != null && !nombreCompleto.trim().isEmpty() 
            ? nombreCompleto 
            : nombreUsuario;
    }

    // ========================================
    // EQUALS, HASHCODE Y TOSTRING
    // ========================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) && 
               Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", activo=" + activo +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
