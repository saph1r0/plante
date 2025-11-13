package com.planta.plantapp.aplicacion.interfaces;

import com.planta.plantapp.dominio.usuario.modelo.Usuario;
public interface IServicioAutenticacion {
    Usuario autenticar(String correo, String password);
}
