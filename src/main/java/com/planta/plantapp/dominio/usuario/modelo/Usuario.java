package com.planta.plantapp.dominio.usuario.modelo;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Modelo de dominio puro que representa un Usuario (sin lógica de negocio).
 */
public class Usuario {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}$");


    private Long id;
    private String nombre;
    private String correo;
    private String contrasena;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaUltimoAcceso;
    private List<Planta> plantas;

    public Usuario(Long id, String nombre, String correo, String contrasena) {
        validarInvariantes(nombre, correo, contrasena);
        this.id = id;
        this.nombre = nombre.trim();
        this.correo = correo.trim().toLowerCase();
        this.contrasena = contrasena.trim();
        this.activo = false;
        this.fechaCreacion = LocalDateTime.now();
    }
    private void validarInvariantes(String nombre, String correo, String contrasena) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        if (nombre.matches(".*\\d.*"))
            throw new IllegalArgumentException("El nombre no puede contener números.");

        if (correo == null || correo.trim().isEmpty())
            throw new IllegalArgumentException("El correo no puede ser nulo ni vacío.");
        if (!EMAIL_PATTERN.matcher(correo).matches())
            throw new IllegalArgumentException("El correo tiene un formato inválido.");

        if (contrasena == null || contrasena.trim().isEmpty())
            throw new IllegalArgumentException("La contraseña no puede ser nula ni vacía.");
        if (contrasena.length() < 8)
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        if (!contrasena.matches(".*\\d.*"))
            throw new IllegalArgumentException("La contraseña debe contener al menos un número.");
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

    public void activarCuenta() {
        if (activo)
            throw new IllegalStateException("La cuenta ya está activa.");
        this.activo = true;
        this.fechaUltimoAcceso = LocalDateTime.now();
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
