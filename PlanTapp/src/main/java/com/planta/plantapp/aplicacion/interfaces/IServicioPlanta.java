package com.planta.plantapp.aplicacion.interfaces;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;

import java.util.List;

/**
 * Interfaz de servicio para operaciones de plantas.
 * Define el contrato para la gestión de plantas en el sistema.
 */
public interface IServicioPlanta {

    /**
     * Obtiene todas las plantas registradas en el sistema.
     * @return Lista de plantas disponibles
     */
    List<Planta> obtenerTodas();

    /**
     * Busca una planta específica por su identificador.
     * @param id Identificador único de la planta
     * @return Planta encontrada o null si no existe
     */
    Planta obtenerPorId(Long id);

    /**
     * Registra una nueva planta en el sistema.
     * @param planta Datos de la planta a registrar
     * @return true si el registro fue exitoso, false en caso contrario
     */
    boolean guardar(Planta planta);

    /**
     * Remueve una planta del sistema.
     * @param id Identificador de la planta a eliminar
     */
    void eliminar(Long id);

    /**
     * Asocia un cuidado específico a una planta.
     * @param plantaId Identificador de la planta
     * @param tipo Tipo de cuidado a aplicar
     * @param frecuenciaDias Periodicidad del cuidado en días
     * @param notas Observaciones adicionales
     */
    void agregarCuidado(Long plantaId, TipoCuidado tipo, Integer frecuenciaDias, String notas);

    /**
     * Localiza plantas por categoría o especie.
     * @param tipo Criterio de búsqueda
     * @return Lista de plantas que coinciden
     */
    List<Planta> buscarPorTipo(String tipo);

    /**
     * Obtiene las plantas asociadas a un usuario específico.
     * @param usuarioId Identificador del propietario
     * @return Lista de plantas del usuario
     */
    List<Planta> listarPorUsuario(Long usuarioId);

    /**
     * Registra una nueva planta siguiendo el proceso completo de validación.
     * @param nombre Nombre común de la planta
     * @param tipo Categoría o especie
     * @param usuarioId Identificador del propietario
     * @return true si el registro fue exitoso
     */
    boolean registrarNuevaPlanta(String nombre, String tipo, String usuarioId);

    /**
     * Obtiene los errores de validación del último proceso ejecutado.
     * @return Lista de mensajes de error
     */
    List<String> obtenerUltimosErrores();

    /**
     * Cuenta plantas por estado específico.
     * @param estado Estado a contabilizar
     * @return Número de plantas en el estado dado
     */
    int contarPorEstado(String estado);
}

