package com.planta.demo.dominio.modelo.planta;

import com.planta.demo.dominio.usuario.modelo.Usuario;
import com.planta.demo.dominio.modelo.bitacora.Bitacora;

import java.util.Date;
import java.util.Set;

public class RegistroPlanta extends Usuario {

    public RegistroPlanta() {
    }

    public int id;
    public String apodo;
    public Date fechaRegistro;
    public EstadoPlanta estado;

    public Set<Bitacora> bitacoras; // nombre en minúscula
}
