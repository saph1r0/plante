package com.planta.plantapp.dominio.modelo.planta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "registros_plantas")
public class RegistroPlanta {

    @Id
    private String id;
    
    private String apodo;
    
    @Field("fecha_registro")
    private LocalDateTime fechaRegistro;
    
    private EstadoPlanta estado;
    
    @Field("usuario_id")
    private String usuarioId;
    
    @DBRef
    private Planta planta;
    
    private String ubicacion;
    
    @Field("foto_personal")
    private String fotoPersonal;
    
    private String notas;
    
    // ✅ NUEVO: Sistema de cuidados embebido
    private List<CuidadoRegistro> cuidados = new ArrayList<>();
    
    @Field("ultimo_cuidado")
    private LocalDateTime ultimoCuidado;

    // Constructores
    public RegistroPlanta() {
        this.fechaRegistro = LocalDateTime.now();
        this.estado = EstadoPlanta.SALUDABLE;
        this.cuidados = new ArrayList<>();
        this.ultimoCuidado = LocalDateTime.now(); // Fecha de registro como primer "cuidado"
    }

    public RegistroPlanta(String apodo, String usuarioId, Planta planta, String ubicacion) {
        this();
        this.apodo = Objects.requireNonNull(apodo, "apodo no puede ser null");
        this.usuarioId = Objects.requireNonNull(usuarioId, "usuarioId no puede ser null");
        this.planta = Objects.requireNonNull(planta, "planta no puede ser null");
        this.ubicacion = ubicacion;
    }

    // ✅ NUEVOS MÉTODOS PARA CUIDADOS
    public void agregarCuidado(String tipo, String notas) {
        CuidadoRegistro nuevoCuidado = new CuidadoRegistro(tipo, notas);
        this.cuidados.add(nuevoCuidado);
        this.ultimoCuidado = nuevoCuidado.getFecha();
        
        // Actualizar estado según el cuidado
        if ("riego".equalsIgnoreCase(tipo) && this.estado == EstadoPlanta.NECESITA_AGUA) {
            this.estado = EstadoPlanta.SALUDABLE;
        }
    }
    
    public List<CuidadoRegistro> getCuidadosRecientes(int limite) {
        return cuidados.stream()
                .sorted((a, b) -> b.getFecha().compareTo(a.getFecha()))
                .limit(limite)
                .toList();
    }
    
    public int getTotalCuidados() {
        return cuidados.size();
    }
    
    public long getDiasSinCuidado() {
        if (ultimoCuidado == null) {
            return java.time.Duration.between(fechaRegistro, LocalDateTime.now()).toDays();
        }
        return java.time.Duration.between(ultimoCuidado, LocalDateTime.now()).toDays();
    }

    // Métodos de dominio existentes
    public void cambiarEstado(EstadoPlanta nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public boolean necesitaAtencion() {
        return estado != null && estado.requiereCuidadoInmediato();
    }

    public long getDiasDesdeRegistro() {
        return java.time.Duration.between(fechaRegistro, LocalDateTime.now()).toDays();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getApodo() { return apodo; }
    public void setApodo(String apodo) { this.apodo = apodo; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public EstadoPlanta getEstado() { return estado; }
    public void setEstado(EstadoPlanta estado) { this.estado = estado; }
    
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    
    public Planta getPlanta() { return planta; }
    public void setPlanta(Planta planta) { this.planta = planta; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    public String getFotoPersonal() { return fotoPersonal; }
    public void setFotoPersonal(String fotoPersonal) { this.fotoPersonal = fotoPersonal; }
    
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    
    // ✅ NUEVOS GETTERS/SETTERS PARA CUIDADOS
    public List<CuidadoRegistro> getCuidados() { return new ArrayList<>(cuidados); }
    public void setCuidados(List<CuidadoRegistro> cuidados) { 
        this.cuidados = cuidados != null ? new ArrayList<>(cuidados) : new ArrayList<>(); 
    }
    
    public LocalDateTime getUltimoCuidado() { return ultimoCuidado; }
    public void setUltimoCuidado(LocalDateTime ultimoCuidado) { this.ultimoCuidado = ultimoCuidado; }

    // Métodos de conveniencia para el frontend
    public String getNombrePlanta() {
        return planta != null ? planta.getNombreComun() : "Planta desconocida";
    }

    public String getNombreCientifico() {
        return planta != null ? planta.getNombreCientifico() : "No especificado";
    }

    public String getTipoPlanta() {
        return planta != null ? planta.getTipo() : "No especificado";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistroPlanta)) return false;
        RegistroPlanta that = (RegistroPlanta) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RegistroPlanta{id='" + id + "', apodo='" + apodo + "', estado=" + estado + 
               ", totalCuidados=" + getTotalCuidados() + '}';
    }

    // ✅ CLASE INTERNA PARA CUIDADOS
    public static class CuidadoRegistro {
        private String tipo;
        private String notas;
        private LocalDateTime fecha;
        
        public CuidadoRegistro() {
            this.fecha = LocalDateTime.now();
        }
        
        public CuidadoRegistro(String tipo, String notas) {
            this();
            this.tipo = tipo;
            this.notas = notas;
        }
        
        // Getters y Setters
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }
        
        public String getNotas() { return notas; }
        public void setNotas(String notas) { this.notas = notas; }
        
        public LocalDateTime getFecha() { return fecha; }
        public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
        
        @Override
        public String toString() {
            return "CuidadoRegistro{tipo='" + tipo + "', fecha=" + fecha + '}';
        }
    }
}

