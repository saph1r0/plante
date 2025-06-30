package dominio.modelo.planta;

import dominio.usuario.modelo.Usuario;
import dominio.modelo.bitacora.Bitacora;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class RegistroPlanta extends Usuario {

    /**
     * Default constructor
     */
    public RegistroPlanta() {
    }

    /**
     * 
     */
    public int id;

    /**
     * 
     */
    public string apodo;

    /**
     * 
     */
    public Date fechaRegistro;

    /**
     * 
     */
    public EstadoPlanta estado;




    /**
     * 
     */
    public Set<Bitacora> BitacoraId;





}