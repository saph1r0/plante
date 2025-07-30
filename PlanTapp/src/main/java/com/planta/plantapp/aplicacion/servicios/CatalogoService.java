package com.planta.plantapp.aplicacion.servicios;

import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.dominio.modelo.planta.Etiqueta;
import com.planta.plantapp.dominio.modelo.fabrica.PlantaFabrica;
import com.planta.plantapp.dominio.modelo.fabrica.CuidadoFabrica;
import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.IPlantaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.text.MessageFormat;

/**
 * Servicio para inicializar el catálogo de plantas usando las fábricas del dominio.
 * Aplicando prácticas de Clean Code y convenciones Java
 */
@Service
public class CatalogoService {

    // ========================================
    // CONSTANTES - Evitar literales duplicados
    // ========================================
    private static final String TIPO_INTERIOR = "interior";
    private static final String TIPO_EXTERIOR = "exterior";
    private static final String SOL_DIRECTO = "sol_directo";
    private static final String SOL_INDIRECTO = "sol_indirecto";
    private static final String AROMATICA = "aromatica";

    // Logger en lugar de System.err/System.out
    private static final Logger logger = Logger.getLogger(CatalogoService.class.getName());

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
                logger.info("Inicializando catálogo de plantas...");
                crearCatalogo();
                logger.info("Catálogo creado con 10 plantas");
            } else {
                logger.log(Level.INFO, "Catálogo ya existe con {0} plantas", plantasExistentes.size());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error inicializando catálogo", e);
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
                    Arrays.asList(TIPO_EXTERIOR, "flor", SOL_DIRECTO, "ornamental"),
                    "cat_001",
                    TIPO_EXTERIOR
            );
            if (rosa != null) {
                agregarCuidadosSeguro(rosa,
                        new int[]{3, 14, 30},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.PODA}
                );
                plantaRepositorio.guardar(rosa);
            }

            // 2. LAVANDA
            Planta lavanda = crearPlantaSegura(
                    "Lavanda",
                    "Lavandula angustifolia",
                    "Planta aromática mediterránea. Perfecta para relajación y uso culinario.",
                    "lavanda.jpg",
                    Arrays.asList(TIPO_EXTERIOR, AROMATICA, SOL_DIRECTO, "medicinal"),
                    "cat_002",
                    TIPO_EXTERIOR
            );
            if (lavanda != null) {
                agregarCuidadosSeguro(lavanda,
                        new int[]{7, 21, 90},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.PODA}
                );
                plantaRepositorio.guardar(lavanda);
            }

            // 3. POTHOS
            Planta pothos = crearPlantaSegura(
                    "Pothos",
                    "Epipremnum aureum",
                    "Planta de interior muy resistente. Ideal para principiantes en jardinería.",
                    "pothos.jpg",
                    Arrays.asList(TIPO_INTERIOR, SOL_INDIRECTO, "colgante", "facil_cuidado"),
                    "cat_003",
                    TIPO_INTERIOR
            );
            if (pothos != null) {
                agregarCuidadosSeguro(pothos,
                        new int[]{5, 30, 7},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(pothos);
            }

            // 4. ALBAHACA
            Planta albahaca = crearPlantaSegura(
                    "Albahaca",
                    "Ocimum basilicum",
                    "Hierba aromática esencial para la cocina. Fácil de cultivar en macetas.",
                    "albahaca.jpg",
                    Arrays.asList(TIPO_INTERIOR, AROMATICA, "culinaria", "anual"),
                    "cat_004",
                    TIPO_INTERIOR
            );
            if (albahaca != null) {
                agregarCuidadosSeguro(albahaca,
                        new int[]{2, 14, 15},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.PODA}
                );
                plantaRepositorio.guardar(albahaca);
            }

            // 5. SUCULENTA JADE
            Planta jade = crearPlantaSegura(
                    "Planta de Jade",
                    "Crassula ovata",
                    "Suculenta robusta que simboliza prosperidad. Muy poco mantenimiento.",
                    "jade.jpg",
                    Arrays.asList(TIPO_INTERIOR, "suculenta", SOL_INDIRECTO, "decorativa"),
                    "cat_005",
                    TIPO_INTERIOR
            );
            if (jade != null) {
                agregarCuidadosSeguro(jade,
                        new int[]{14, 60, 30},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(jade);
            }

            // 6. GIRASOL
            Planta girasol = crearPlantaSegura(
                    "Girasol",
                    "Helianthus annuus",
                    "Flor emblemática que sigue al sol. Alegra cualquier jardín con su presencia.",
                    "girasol.jpg",
                    Arrays.asList(TIPO_EXTERIOR, "flor", SOL_DIRECTO, "grande", "anual"),
                    "cat_006",
                    TIPO_EXTERIOR
            );
            if (girasol != null) {
                agregarCuidadosSeguro(girasol,
                        new int[]{2, 7, 21},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.ROTACION}
                );
                plantaRepositorio.guardar(girasol);
            }

            // 7. FICUS
            Planta ficus = crearPlantaSegura(
                    "Ficus",
                    "Ficus benjamina",
                    "Árbol de interior elegante. Purifica el aire y aporta elegancia a espacios modernos.",
                    "ficus.jpg",
                    Arrays.asList(TIPO_INTERIOR, SOL_INDIRECTO, "arbol", "purificador"),
                    "cat_007",
                    TIPO_INTERIOR
            );
            if (ficus != null) {
                agregarCuidadosSeguro(ficus,
                        new int[]{7, 21, 14},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(ficus);
            }

            // 8. ROMERO
            Planta romero = crearPlantaSegura(
                    "Romero",
                    "Rosmarinus officinalis",
                    "Hierba mediterránea aromática. Excelente para cocinar y como repelente natural.",
                    "romero.jpg",
                    Arrays.asList(TIPO_EXTERIOR, AROMATICA, "culinaria", "perenne", "medicinal"),
                    "cat_008",
                    TIPO_EXTERIOR
            );
            if (romero != null) {
                agregarCuidadosSeguro(romero,
                        new int[]{5, 30, 60},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.PODA}
                );
                plantaRepositorio.guardar(romero);
            }

            // 9. MONSTERA
            Planta monstera = crearPlantaSegura(
                    "Monstera Deliciosa",
                    "Monstera deliciosa",
                    "Planta tropical de interior con hojas perforadas únicas. Muy popular en decoración.",
                    "monstera.jpg",
                    Arrays.asList(TIPO_INTERIOR, "tropical", SOL_INDIRECTO, "grande", "decorativa"),
                    "cat_009",
                    TIPO_INTERIOR
            );
            if (monstera != null) {
                agregarCuidadosSeguro(monstera,
                        new int[]{7, 21, 14},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(monstera);
            }

            // 10. VIOLETA AFRICANA
            Planta violeta = crearPlantaSegura(
                    "Violeta Africana",
                    "Saintpaulia ionantha",
                    "Planta de interior con flores delicadas y coloridas. Ideal para espacios con poca luz.",
                    "violeta_africana.jpg",
                    Arrays.asList(TIPO_INTERIOR, "flor", "sombra", "compacta"),
                    "cat_010",
                    TIPO_INTERIOR
            );
            if (violeta != null) {
                agregarCuidadosSeguro(violeta,
                        new int[]{5, 30, 14},
                        new TipoCuidado[]{TipoCuidado.RIEGO, TipoCuidado.FERTILIZACION, TipoCuidado.LIMPIEZA}
                );
                plantaRepositorio.guardar(violeta);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error creando catálogo", e);
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
            logger.log(Level.WARNING, "Error creando planta " + nombreComun, e);
            return null;
        }
    }

    /**
     * Método auxiliar para agregar cuidados de forma segura
     * ✅ VERSIÓN CORREGIDA - Compatible con constructor de Cuidado
     */
    private void agregarCuidadosSeguro(Planta planta, int[] frecuencias, TipoCuidado[] tipos) {
        try {
            for (int i = 0; i < tipos.length && i < frecuencias.length; i++) {
                // ✅ Usar el constructor correcto: (TipoCuidado, String, Integer)
                Cuidado cuidado = cuidadoFabrica.crearCuidado(
                        tipos[i],                    // TipoCuidado
                        planta,                      // Planta (se convertirá a String internamente)
                        Double.valueOf(frecuencias[i]), // Cantidad
                        tipos[i].getDescripcion()    // Notas
                );
                if (cuidado != null) {
                    planta.agregarCuidado(cuidado);
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error agregando cuidados a " + planta.getNombreComun(), e);
        }
    }

    /**
     * Método público para obtener estadísticas del catálogo
     */
    public String getEstadisticasCatalogo() {
        try {
            List<Planta> todas = plantaRepositorio.listarTodas();
            long interiores = todas.stream().filter(p -> TIPO_INTERIOR.equals(p.getTipo())).count();
            long exteriores = todas.stream().filter(p -> TIPO_EXTERIOR.equals(p.getTipo())).count();

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
            logger.log(Level.WARNING, "Error buscando plantas", e);
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
            logger.log(Level.WARNING, "Error obteniendo por tipo", e);
            return Arrays.asList(); // Lista vacía en caso de error
        }
    }
}
