package com.planta.plantapp.dominio.modelo.bitacora;

import com.planta.plantapp.dominio.modelo.planta.Planta;

import java.util.Date;
import java.util.Objects;

/**
 * Entidad de dominio que representa una entrada de bitácora de cuidado de una planta.
 * Refactorizada aplicando técnicas de clean code y principios SOLID.
 */
public class Bitacora {

    private final String id;  // Campo final para inmutabilidad parcial
    private Date fecha;
    private String descripcion;
    private String foto;
    private Planta planta;

    // ========== CONSTRUCTORES ==========

    /**
     * Constructor principal ahora privado para uso con factory methods.
     * Refactoring aplicado: Encapsulate Constructor with Factory Method.
     */
    private Bitacora(String id, String descripcion, String foto, Planta planta) {
        this.id = id;
        this.fecha = new Date();  // Inicialización automática

        // Validación de parámetros - Refactoring: Add Parameter Validation
        this.descripcion = Objects.requireNonNull(descripcion, "La descripción no puede ser nula");
        this.foto = Objects.requireNonNull(foto, "La foto no puede ser nula");
        this.planta = Objects.requireNonNull(planta, "La planta no puede ser nula");

        // Validación de strings vacíos
        if (descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
        if (foto.isBlank()) {
            throw new IllegalArgumentException("La foto no puede estar vacía");
        }
    }

    /**
     * Constructor vacío preservado para compatibilidad con frameworks (JPA, Jackson, etc.).
     * Necesario para serialización y frameworks de persistencia.
     */
    public Bitacora() {
        this.id = null;  // ID será asignado posteriormente por setter o framework
    }

    // ========== FACTORY METHODS ==========

    /**
     * Factory method para crear una nueva bitácora.
     * Refactoring aplicado: Replace Constructor with Factory Method.
     * @param descripcion Descripción de la observación
     * @param foto URL o ruta de la foto
     * @param planta Planta asociada
     * @return Nueva instancia de Bitacora
     */
    public static Bitacora crearNueva(String descripcion, String foto, Planta planta) {
        String idGenerado = generarIdUnico();
        return new Bitacora(idGenerado, descripcion, foto, planta);
    }

    /**
     * Factory method para reconstruir una bitácora existente (desde base de datos).
     * @param id Identificador único
     * @param fecha Fecha de la observación
     * @param descripcion Descripción de la observación
     * @param foto URL o ruta de la foto
     * @param planta Planta asociada
     * @return Instancia de Bitacora reconstruida
     */
    public static Bitacora reconstruir(String id, Date fecha, String descripcion, String foto, Planta planta) {
        Bitacora bitacora = new Bitacora(id, descripcion, foto, planta);
        bitacora.setFecha(fecha);
        return bitacora;
    }

    /**
     * Genera un identificador único para la bitácora.
     * @return String con ID único
     */
    private static String generarIdUnico() {
        return java.util.UUID.randomUUID().toString();
    }

    // ========== GETTERS Y SETTERS ==========

    /**
     * @return Identificador único de la bitácora
     */
    public String getId() {
        return id;
    }

    // Nota: No hay setId() porque el campo id es final

    /**
     * @return Fecha de la observación
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de la observación.
     * @param fecha Nueva fecha (no puede ser nula)
     */
    public void setFecha(Date fecha) {
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser nula");
    }

    /**
     * @return Descripción de la observación
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la observación.
     * @param descripcion Nueva descripción (no puede ser nula o vacía)
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = Objects.requireNonNull(descripcion, "La descripción no puede ser nula");
        if (descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede estar vacía");
        }
    }

    /**
     * @return URL o ruta de la foto
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Establece la URL o ruta de la foto.
     * @param foto Nueva foto (no puede ser nula o vacía)
     */
    public void setFoto(String foto) {
        this.foto = Objects.requireNonNull(foto, "La foto no puede ser nula");
        if (foto.isBlank()) {
            throw new IllegalArgumentException("La foto no puede estar vacía");
        }
    }

    /**
     * @return Planta asociada a la bitácora
     */
    public Planta getPlanta() {
        return planta;
    }

    /**
     * Establece la planta asociada.
     * @param planta Nueva planta (no puede ser nula)
     */
    public void setPlanta(Planta planta) {
        this.planta = Objects.requireNonNull(planta, "La planta no puede ser nula");
    }

    // ========== BUSINESS METHODS ==========

    /**
     * Verifica si la bitácora pertenece a una planta específica.
     * Refactoring: Extract Business Logic.
     * @param plantaId Identificador de la planta a verificar
     * @return true si la bitácora pertenece a la planta, false en caso contrario
     */
    public boolean perteneceAPlanta(String plantaId) {
        return planta != null && planta.getId() != null && planta.getId().equals(plantaId);
    }

    /**
     * Obtiene un resumen corto de la bitácora para visualización.
     * Refactoring: Add Business Method.
     * @return Resumen formateado de la bitácora
     */
    public String obtenerResumen() {
        return String.format("%s: %s",
                formatoFechaCorta(),
                descripcion.length() > 30 ? descripcion.substring(0, 27) + "..." : descripcion);
    }

    /**
     * Formatea la fecha en formato corto (dd/mm).
     * Refactoring: Extract Method.
     * @return Fecha formateada o "Sin fecha"
     */
    private String formatoFechaCorta() {
        if (fecha == null) return "Sin fecha";
        return String.format("%td/%tm", fecha, fecha);
    }

    // ========== OVERRIDES ==========

    /**
     * Representación en string de la bitácora.
     * Refactoring aplicado: Extract Method (múltiples métodos extraídos).
     * @return String formateado con los datos de la bitácora
     */
    @Override
    public String toString() {
        return formatToString();
    }

    /**
     * Método extraído para formatear el string de la bitácora.
     * Refactoring: Extract Method.
     * @return String formateado
     */
    private String formatToString() {
        return String.format("Bitacora{id='%s', fecha=%s, descripcion='%s', planta=%s}",
                id != null ? id : "N/A",
                formatFecha(fecha),
                descripcion != null ? descripcion : "N/A",
                obtenerNombrePlanta());
    }

    /**
     * Método extraído para formatear la fecha.
     * Refactoring: Extract Method.
     * @param fecha Fecha a formatear
     * @return String de la fecha o "N/A"
     */
    private String formatFecha(Date fecha) {
        return fecha != null ? fecha.toString() : "N/A";
    }

    /**
     * Método extraído para obtener el nombre de la planta.
     * Refactoring: Extract Method.
     * @return Nombre de la planta o "N/A"
     */
    private String obtenerNombrePlanta() {
        return planta != null ? planta.getNombreComun() : "N/A";
    }

    /**
     * Compara esta bitácora con otro objeto.
     * @param o Objeto a comparar
     * @return true si son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bitacora other = (Bitacora) o;
        return Objects.equals(id, other.id);
    }

    /**
     * @return Hash code basado en el ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}