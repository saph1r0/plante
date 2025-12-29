package com.planta.userservice.aplicacion.interfaces;

import com.planta.userservice.dominio.modelo.Usuario;

public interface IServicioAutenticacion {
    Usuario autenticar(String correo, String contrasena);
}