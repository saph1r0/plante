package com.planta.demo.dominio.modelo.planta;

import com.planta.demo.dominio.modelo.bitacora.Bitacora;
import com.planta.demo.dominio.usuario.modelo.Usuario;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Modelo del dominio que representa el registro de una planta por parte de un usuario.
 * Es parte del dominio del problema.
 */
public class RegistroPlanta {

    private int id;
    private String apodo;
    private Date fechaRegistro;
    private EstadoPlanta estado;

    /**
     * Usuario que registró la planta (composición).
     */
    private Usuario usuario;
    /** Registro histórico (bitácoras) de eventos de la planta. */
    private final Set<Bitacora> bitacoras;

    public RegistroPlanta(String apodo, Usuario usuario) {
        this.apodo = Objects.requireNonNull(apodo, "apodo no puede ser null");
        this.usuario = Objects.requireNonNull(usuario, "usuario no puede ser null");
        this.fechaRegistro = new Date();
        this.estado = EstadoPlanta.SALUDABLE; // por defecto
        this.bitacoras = new HashSet<>();
    }

    // Métodos de dominio
    public void cambiarEstado(EstadoPlanta nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void agregarBitacora(Bitacora bitacora) {
        if (this.bitacoras == null) {
            this.bitacoras = new HashSet<>();
        }
        this.bitacoras.add(bitacora);
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public EstadoPlanta getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlanta estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Bitacora> getBitacoras() {
        return bitacoras;
    }

    public void setBitacoras(Set<Bitacora> bitacoras) {
        this.bitacoras = bitacoras;
    }

    // Equals y hashCode por ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistroPlanta)) return false;
        RegistroPlanta that = (RegistroPlanta) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
