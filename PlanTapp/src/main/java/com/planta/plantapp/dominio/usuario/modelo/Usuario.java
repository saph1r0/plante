package com.planta.plantapp.dominio.usuario.modelo;

import com.planta.plantapp.dominio.modelo.planta.Planta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Modelo de dominio puro que representa un Usuario.
 * Aplicando prácticas de Clean Code y eliminando duplicación
 *
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

    // ========================================
    // CONSTRUCTORES
    // ========================================

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
    // GETTERS Y SETTERS
    // ========================================

    public Long getId() {
        return id;
    }

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

    /**
     * ✅ CORRECCIÓN: getCorreo() ahora tiene implementación diferente
     * Devuelve el email en formato normalizado (lowercase) para comparaciones
     * Esto es útil para búsquedas case-insensitive y validaciones
     */
    public String getCorreo() {
        return email != null ? email.toLowerCase().trim() : null;
    }

    /**

     * Aplica transformaciones de limpieza y formato estándar
     */
    public void setCorreo(String correo) {
        this.email = correo != null ? correo.toLowerCase().trim() : null;
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

    /**
     * Valida si el email tiene formato válido
     */
    public boolean tieneEmailValido() {
        return email != null &&
                email.contains("@") &&
                email.contains(".") &&
                email.length() > 5;
    }

    /**
     * Obtiene las iniciales del usuario
     */
    public String getIniciales() {
        if (nombreCompleto != null && !nombreCompleto.trim().isEmpty()) {
            String[] partes = nombreCompleto.trim().split("\\s+");
            StringBuilder iniciales = new StringBuilder();
            for (String parte : partes) {
                if (!parte.isEmpty()) {
                    iniciales.append(parte.charAt(0));
                }
            }
            return iniciales.toString().toUpperCase();
        } else if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            return String.valueOf(nombreUsuario.charAt(0)).toUpperCase();
        }
        return "U";
    }

    /**
     * Verifica si la contraseña cumple criterios mínimos
     */
    public boolean tienePasswordSegura() {
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }

    // ========================================
    // EQUALS, HASHCODE Y TOSTRING
    // ========================================

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
