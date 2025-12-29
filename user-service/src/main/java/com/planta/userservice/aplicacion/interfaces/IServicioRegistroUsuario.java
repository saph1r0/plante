package com.planta.userservice.aplicacion.interfaces;

import com.planta.userservice.dominio.modelo.Usuario;

public interface IServicioRegistroUsuario {
    Usuario registrar(String nombre, String correo, String contrasena);
}
