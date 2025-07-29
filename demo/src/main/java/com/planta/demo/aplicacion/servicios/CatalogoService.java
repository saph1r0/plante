package com.planta.demo.aplicacion.servicios;

import com.planta.demo.dominio.modelo.planta.Planta;
import com.planta.demo.dominio.modelo.planta.Etiqueta;
import com.planta.demo.dominio.modelo.fabrica.PlantaFabrica;
import com.planta.demo.dominio.modelo.fabrica.CuidadoFabrica;
import com.planta.demo.dominio.modelo.cuidado.Cuidado;
import com.planta.demo.dominio.modelo.cuidado.TipoCuidado;
import com.planta.demo.dominio.modelo.IPlantaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;

/**
 * Servicio para inicializar el catálogo de plantas usando las fábricas del dominio.
 */
@Service
public class CatalogoService {

    @Autowired
    private IPlantaRepositorio plantaRepositorio;

    private final PlantaFabrica plantaFabrica = new PlantaFabrica();
    private final CuidadoFabrica cuidadoFabrica = new CuidadoFabrica();

    /**
     * Inicializa el catálogo al arrancar la aplicación
     */
    @PostConstruct
    public void inicializarCatalogo() {
        try {
            // Verificar si ya existe catálogo
            List<Planta> plantasExistentes = plantaRepositorio.listarTodas();
            
            if (plantasExistentes.isEmpty()) {
                System.out.println("Inicializando catálogo de plantas...");
                crearCatalogo();
                System.out.println("Catálogo creado con 10 plantas");
            } else {
                System.out.println("Catálogo ya existe con " + plantasExistentes.size() + " plantas");
            }
        } catch (Exception e) {
            System.err.println("Error inicializando catálogo: " + e.getMessage());
        }
    }

    /**
     * Crea el catálogo completo con 10 plantas variadas
     */
    private void crearCatalogo() {
        try {
            
            // 1. ROSA ROJA
            Planta rosa = crearPlantaSegura(
                "Rosa Roja",
                "Rosa gallica",
                "Hermosa rosa ornamental con fragancia intensa. Ideal para jardines y cortes florales.",
                "rosa_roja.jpg",
                Arrays.asList("exterior", "fragante", "sol_directo", "decorativa"),
                "cat_001",
                "exterior"
            );
            if (rosa != null) {
                agregarCuidadosSeguro(rosa, 
                    new int[]{7, 21, 60}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.PODA}
                );
                plantaRepositorio.guardar(rosa);
            }

            // 2. SUCULENTA ECHEVERIA
            Planta echeveria = crearPlantaSegura(
                "Echeveria",
                "Echeveria elegans",
                "Suculenta perfecta para principiantes. Resistente y de bajo mantenimiento.",
                "echeveria.jpg",
                Arrays.asList("interior", "suculenta", "sol_indirecto", "facil_cuidado"),
                "cat_002",
                "interior"
            );
            if (echeveria != null) {
                agregarCuidadosSeguro(echeveria,
                    new int[]{14, 30}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(echeveria);
            }

            // 3. HELECHO BOSTON
            Planta helecho = crearPlantaSegura(
                "Helecho Boston",
                "Nephrolepis exaltata",
                "Helecho elegante que aporta frescura tropical a cualquier espacio interior.",
                "helecho_boston.jpg",
                Arrays.asList("interior", "tropical", "sombra", "humedad_alta"),
                "cat_003",
                "interior"
            );
            if (helecho != null) {
                agregarCuidadosSeguro(helecho,
                    new int[]{3, 7}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(helecho);
            }

            // 4. CACTUS SAN PEDRO
            Planta cactus = crearPlantaSegura(
                "Cactus San Pedro",
                "Echinopsis pachanoi",
                "Cactus columnar de crecimiento rápido. Muy resistente y decorativo.",
                "cactus_san_pedro.jpg",
                Arrays.asList("exterior", "cactus", "sol_directo", "resistente"),
                "cat_004",
                "exterior"
            );
            if (cactus != null) {
                agregarCuidadosSeguro(cactus,
                    new int[]{21, 60}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(cactus);
            }

            // 5. LAVANDA
            Planta lavanda = crearPlantaSegura(
                "Lavanda",
                "Lavandula angustifolia",
                "Planta aromática con hermosas flores moradas. Ideal para relajación y aceites esenciales.",
                "lavanda.jpg",
                Arrays.asList("exterior", "aromatica", "sol_directo", "medicinal"),
                "cat_005",
                "exterior"
            );
            if (lavanda != null) {
                agregarCuidadosSeguro(lavanda,
                    new int[]{7, 14, 90}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.PODA}
                );
                plantaRepositorio.guardar(lavanda);
            }

            // 6. POTHOS
            Planta pothos = crearPlantaSegura(
                "Pothos",
                "Epipremnum aureum",
                "Planta trepadora ideal para interiores. Purifica el aire y es muy fácil de cuidar.",
                "pothos.jpg",
                Arrays.asList("interior", "trepadora", "sol_indirecto", "purificadora"),
                "cat_006",
                "interior"
            );
            if (pothos != null) {
                agregarCuidadosSeguro(pothos,
                    new int[]{5, 21, 14}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(pothos);
            }

            // 7. GIRASOL
            Planta girasol = crearPlantaSegura(
                "Girasol",
                "Helianthus annuus",
                "Flor alegre que sigue el sol. Perfecta para jardines familiares y atraer polinizadores.",
                "girasol.jpg",
                Arrays.asList("exterior", "anual", "sol_directo", "grande"),
                "cat_007",
                "exterior"
            );
            if (girasol != null) {
                agregarCuidadosSeguro(girasol,
                    new int[]{2, 14}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION}
                );
                plantaRepositorio.guardar(girasol);
            }

            // 8. MONSTERA
            Planta monstera = crearPlantaSegura(
                "Monstera Deliciosa",
                "Monstera deliciosa",
                "Planta tropical con hojas grandes y perforadas. Muy popular en decoración interior.",
                "monstera.jpg",
                Arrays.asList("interior", "tropical", "luz_brillante", "grande"),
                "cat_008",
                "interior"
            );
            if (monstera != null) {
                agregarCuidadosSeguro(monstera,
                    new int[]{7, 21, 7}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(monstera);
            }

            // 9. ALBAHACA
            Planta albahaca = crearPlantaSegura(
                "Albahaca",
                "Ocimum basilicum",
                "Hierba aromática esencial en la cocina. Fácil de cultivar en macetas.",
                "albahaca.jpg",
                Arrays.asList("exterior", "aromatica", "comestible", "sol_directo"),
                "cat_009",
                "exterior"
            );
            if (albahaca != null) {
                agregarCuidadosSeguro(albahaca,
                    new int[]{3, 15, 7}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.PODA}
                );
                plantaRepositorio.guardar(albahaca);
            }

            // 10. VIOLETA AFRICANA
            Planta violeta = crearPlantaSegura(
                "Violeta Africana",
                "Saintpaulia ionantha",
                "Planta de interior con flores delicadas y coloridas. Ideal para espacios con poca luz.",
                "violeta_africana.jpg",
                Arrays.asList("interior", "flor", "sombra", "compacta"),
                "cat_010",
                "interior"
            );
            if (violeta != null) {
                agregarCuidadosSeguro(violeta,
                    new int[]{5, 30, 14}, 
                    new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(violeta);
            }

        } catch (Exception e) {
            System.err.println("Error creando catálogo: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para crear planta de forma segura
     */
    private Planta crearPlantaSegura(String nombreComun, String nombreCientifico, String descripcion, 
                                    String imagenURL, List<String> etiquetas, String id, String tipo) {
        try {
            Planta planta = plantaFabrica.crearPlanta(nombreComun, nombreCientifico, descripcion, imagenURL, etiquetas);
            planta.setId(id);
            planta.setTipo(tipo);
            return planta;
        } catch (Exception e) {
            System.err.println("Error creando planta " + nombreComun + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Método auxiliar para agregar cuidados de forma segura
     */
    private void agregarCuidadosSeguro(Planta planta, int[] frecuencias, TipoCuidado[] tipos) {
        try {
            for (int i = 0; i < tipos.length && i < frecuencias.length; i++) {
                Cuidado cuidado = cuidadoFabrica.crearCuidado(
                    tipos[i].name(),
                    tipos[i].getDescripcion(),
                    frecuencias[i]
                );
                if (cuidado != null) {
                    planta.agregarCuidado(cuidado);
                }
            }
        } catch (Exception e) {
            System.err.println("Error agregando cuidados a " + planta.getNombreComun() + ": " + e.getMessage());
        }
    }

    /**
     * Método público para obtener estadísticas del catálogo
     */
    public String getEstadisticasCatalogo() {
        try {
            List<Planta> todas = plantaRepositorio.listarTodas();
            long interiores = todas.stream().filter(p -> "interior".equals(p.getTipo())).count();
            long exteriores = todas.stream().filter(p -> "exterior".equals(p.getTipo())).count();
            
            return String.format("Catálogo: %d plantas total (%d interior, %d exterior)", 
                todas.size(), interiores, exteriores);
        } catch (Exception e) {
            return "Error obteniendo estadísticas: " + e.getMessage();
        }
    }

    /**
     * Método para buscar plantas por consulta
     */
    public List<Planta> buscarPlantas(String consulta) {
        try {
            if (consulta == null || consulta.trim().isEmpty()) {
                return plantaRepositorio.listarTodas();
            }
            return plantaRepositorio.buscarPorNombre(consulta.trim());
        } catch (Exception e) {
            System.err.println("Error buscando plantas: " + e.getMessage());
            return Arrays.asList(); // Lista vacía en caso de error
        }
    }

    /**
     * Método para obtener plantas por tipo
     */
    public List<Planta> obtenerPorTipo(String tipo) {
        try {
            if (tipo == null || tipo.trim().isEmpty()) {
                return plantaRepositorio.listarTodas();
            }
            return plantaRepositorio.buscarPorTipo(tipo);
        } catch (Exception e) {
            System.err.println("Error obteniendo por tipo: " + e.getMessage());
            return Arrays.asList(); // Lista vacía en caso de error
        }
    }
}
