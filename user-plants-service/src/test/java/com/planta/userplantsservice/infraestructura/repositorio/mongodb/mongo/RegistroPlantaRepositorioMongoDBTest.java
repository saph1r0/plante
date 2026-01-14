package com.planta.userplantsservice.infraestructura.repositorio.mongodb.mongo;

import com.planta.userplantsservice.dominio.modelo.planta.EstadoPlanta;
import com.planta.userplantsservice.infraestructura.documento.RegistroPlantaDocumento;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroPlantaRepositorioMongoDBTest {

    @Mock
    private RegistroPlantaRepository repository;

    @InjectMocks
    private RegistroPlantaRepositorioMongoDB adaptador;

    @Test
    void guardar_DeberiaLlamarAlMetodoSaveDelRepositorio() {
        RegistroPlantaDocumento doc = new RegistroPlantaDocumento("u1", "p1", "Rosa", "Sala", EstadoPlanta.SALUDABLE);
        when(repository.save(doc)).thenReturn(doc);

        adaptador.guardar(doc);

        verify(repository, times(1)).save(doc);
    }

    @Test
    void obtenerPorId_DeberiaRetornarDocumento_CuandoExiste() {
        RegistroPlantaDocumento doc = new RegistroPlantaDocumento();
        doc.setId("123");
        when(repository.findById("123")).thenReturn(Optional.of(doc));

        RegistroPlantaDocumento resultado = adaptador.obtenerPorId("123");

        assertNotNull(resultado);
        assertEquals("123", resultado.getId());
    }
    @Test
    void eliminar_DeberiaLlamarAlDeleteByIdDelRepositorio() {
        String idAEliminar = "reg-999";
        doNothing().when(repository).deleteById(idAEliminar);

        adaptador.eliminar(idAEliminar);

        verify(repository, times(1)).deleteById(idAEliminar);
    }

    @Test
    void listarPorUsuario_DeberiaLlamarAlMetodoDelRepositorio() {
        String userId = "user-123";
        adaptador.listarPorUsuario(userId);
        verify(repository).findByUsuarioId(userId);
    }
}