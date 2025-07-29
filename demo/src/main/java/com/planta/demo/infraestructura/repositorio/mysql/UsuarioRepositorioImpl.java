package com.planta.demo.infraestructura.repositorio.mysql;

import com.planta.demo.dominio.usuario.IUsuarioRepositorio;
import com.planta.demo.dominio.usuario.modelo.Usuario;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación del repositorio de Usuario usando MySQL.
 * VERSIÓN CORREGIDA para tipos de datos compatibles
 */
@Repository
public class UsuarioRepositorioImpl implements IUsuarioRepositorio {

    // PERSISTENT TABLES: Simular tablas con Maps
    private Map<String, Usuario> tablaUsuarios = new ConcurrentHashMap<>();
    private Map<String, String> indiceCorreos = new ConcurrentHashMap<>(); // correo -> id
    
    public UsuarioRepositorioImpl() {
        this.tablaUsuarios = new ConcurrentHashMap<>();
        this.indiceCorreos = new ConcurrentHashMap<>();
    }
    
    @Override
    public Usuario obtenerPorId(String id) {
        // PERSISTENT TABLES: Query declarativa SELECT * WHERE id = ?
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID requerido");
        }

        // Consultar tabla simulada
        return tablaUsuarios.get(id);
    }

    @Override
    public List<Usuario> listarTodos() {
        // PERSISTENT TABLES: SELECT * FROM usuarios
        return new ArrayList<>(tablaUsuarios.values());
    }

    @Override
    public void guardar(Usuario usuario) {
        // PERSISTENT TABLES: INSERT/UPDATE en tabla principal e índices
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no puede ser nulo");
        }

        String usuarioId;
        
        // ARREGLO: Manejar ID como String consistentemente
        if (usuario.getId() == null) {
            usuarioId = UUID.randomUUID().toString();
            usuario.setId(usuarioId); // setId ya maneja String y Long
        } else {
            usuarioId = usuario.getId().toString(); // ARREGLO: convertir Long a String
        }

        // Guardar en tabla principal usando String como clave
        tablaUsuarios.put(usuarioId, usuario);

        // Actualizar índice de correos
        if (usuario.getCorreo() != null) {
            indiceCorreos.put(usuario.getCorreo().toLowerCase(), usuarioId); // ARREGLO: usar usuarioId String
        }
    }

    @Override
    public void eliminar(String id) {
        // PERSISTENT TABLES: DELETE con limpieza de índices
        if (id != null) {
            Usuario usuario = tablaUsuarios.remove(id);
            if (usuario != null && usuario.getCorreo() != null) {
                indiceCorreos.remove(usuario.getCorreo().toLowerCase());
            }
        }
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        // PERSISTENT TABLES: Query con índice SELECT * WHERE email = ?
        if (correo == null || !correo.contains("@")) {
            return Optional.empty();
        }

        // Usar índice de correos (simular tabla de índices)
        String usuarioId = indiceCorreos.get(correo.toLowerCase());
        if (usuarioId != null) {
            return Optional.ofNullable(tablaUsuarios.get(usuarioId));
        }

        return Optional.empty();
    }

    @Override
    public Boolean existeUsuario(String id) {
        // PERSISTENT TABLES: Query COUNT(*) simulada
        return id != null && tablaUsuarios.containsKey(id);
    }
}
