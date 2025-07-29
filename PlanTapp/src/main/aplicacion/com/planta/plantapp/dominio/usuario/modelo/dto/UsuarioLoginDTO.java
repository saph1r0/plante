package com.planta.plantapp.dominio.usuario.modelo.dto;

public class UsuarioLoginDTO {

    private String correo;
    private String contrasena;

    public UsuarioLoginDTO() {
    }

    public UsuarioLoginDTO(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo != null ? correo.trim() : null;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena != null ? contrasena.trim() : null;
    }
}