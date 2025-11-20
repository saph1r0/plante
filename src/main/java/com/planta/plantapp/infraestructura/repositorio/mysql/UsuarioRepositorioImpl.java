package com.planta.plantapp.infraestructura.repositorio.mysql;

import com.planta.plantapp.dominio.usuario.IUsuarioRepositorio;
import com.planta.plantapp.dominio.usuario.modelo.Usuario;
import com.planta.plantapp.infraestructura.entidad.UsuarioEntidad;
import com.planta.plantapp.infraestructura.repositorio.mysql.jpa.UsuarioJpaRepositorio;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositorioImpl implements IUsuarioRepositorio {

    private final UsuarioJpaRepositorio usuarioJpaRepositorio;

    public UsuarioRepositorioImpl(UsuarioJpaRepositorio usuarioJpaRepositorio) {
        this.usuarioJpaRepositorio = usuarioJpaRepositorio;
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        return usuarioJpaRepositorio.findById(id)
                .map(e -> new Usuario(e.getId(), e.getNombre(), e.getCorreo(), e.getContrasena()))
                .orElse(null);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioJpaRepositorio.findAll()
                .stream()
                .map(e -> new Usuario(e.getId(), e.getNombre(), e.getCorreo(), e.getContrasena()))
                .toList();
    }

    @Override
    public void guardar(Usuario usuario) {
        UsuarioEntidad entidad = new UsuarioEntidad(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getContrasena()
        );
        usuarioJpaRepositorio.save(entidad);
    }

    @Override
    public void eliminar(Long id) {
        usuarioJpaRepositorio.deleteById(id);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioJpaRepositorio.findByCorreo(correo)
                .map(e -> new Usuario(e.getId(), e.getNombre(), e.getCorreo(), e.getContrasena()));
    }

    @Override
    public Boolean existeUsuario(Long id) {
        return usuarioJpaRepositorio.existsById(id);
    }
}

