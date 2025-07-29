package com.planta.demo.dominio.modelo.planta;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.planta.demo.dominio.modelo.cuidado.Cuidado;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "plantas_catalogo")
public class Planta {

    @Id
    private String id;
    
    @Field("nombre_comun")
    private String nombreComun;
    
    @Field("nombre_cientifico")
    private String nombreCientifico;
    
    private String descripcion;
    
    @Field("imagen_url")
    private String imagenURL;
    
    private String tipo;
    
    private List<Etiqueta> etiquetas = new ArrayList<>();
    
    @Field("cuidados_recomendados")
    private List<Cuidado> cuidadosRecomendados = new ArrayList<>();

    // Constructores
    public Planta() {
        this.etiquetas = new ArrayList<>();
        this.cuidadosRecomendados = new ArrayList<>();
    }

    public Planta(String nombreComun, String nombreCientifico, String descripcion, String imagenURL) {
        this();
        if (nombreComun == null || nombreComun.isBlank())
            throw new IllegalArgumentException("El nombre común no puede ser nulo o vacío.");
        if (nombreCientifico == null || nombreCientifico.isBlank())
            throw new IllegalArgumentException("El nombre científico no puede ser nulo o vacío.");

        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
        this.descripcion = descripcion;
        this.imagenURL = imagenURL;
    }

    // Métodos de dominio
    public void agregarCuidado(Cuidado cuidado) {
        if (cuidado != null && !this.cuidadosRecomendados.contains(cuidado)) {
            this.cuidadosRecomendados.add(cuidado);
        }
    }

    public void agregarEtiqueta(Etiqueta etiqueta) {
        if (etiqueta != null && !this.etiquetas.contains(etiqueta)) {
            this.etiquetas.add(etiqueta);
        }
    }

    public String getFrecuenciaRiegoTexto() {
        return cuidadosRecomendados.stream()
                .filter(c -> c.getTipo().esRiego())
                .findFirst()
                .map(c -> {
                    int dias = c.getFrecuenciaDias();
                    if (dias == 1) return "Diario";
                    if (dias == 7) return "Semanal";
                    return "Cada " + dias + " días";
                })
                .orElse("No especificado");
    }

    // Getters y Setters completos
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombreComun() { return nombreComun; }
    public void setNombreComun(String nombreComun) { this.nombreComun = nombreComun; }
    
    public String getNombreCientifico() { return nombreCientifico; }
    public void setNombreCientifico(String nombreCientifico) { this.nombreCientifico = nombreCientifico; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getImagenURL() { return imagenURL; }
    public void setImagenURL(String imagenURL) { this.imagenURL = imagenURL; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public List<Etiqueta> getEtiquetas() { return new ArrayList<>(etiquetas); }
    public void setEtiquetas(List<Etiqueta> etiquetas) { 
        this.etiquetas = etiquetas != null ? new ArrayList<>(etiquetas) : new ArrayList<>(); 
    }
    
    public List<Cuidado> getCuidadosRecomendados() { return new ArrayList<>(cuidadosRecomendados); }
    public void setCuidadosRecomendados(List<Cuidado> cuidadosRecomendados) { 
        this.cuidadosRecomendados = cuidadosRecomendados != null ? new ArrayList<>(cuidadosRecomendados) : new ArrayList<>(); 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Planta)) return false;
        Planta planta = (Planta) o;
        return Objects.equals(id, planta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Planta{id='" + id + "', nombreComun='" + nombreComun + "', tipo='" + tipo + "'}";
    }
}
