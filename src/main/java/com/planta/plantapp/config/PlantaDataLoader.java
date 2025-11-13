package com.planta.plantapp.config;

import com.planta.plantapp.dominio.modelo.cuidado.Cuidado;
import com.planta.plantapp.dominio.modelo.cuidado.TipoCuidado;
import com.planta.plantapp.dominio.modelo.planta.Etiqueta;
import com.planta.plantapp.dominio.modelo.planta.Planta;
import com.planta.plantapp.infraestructura.repositorio.mongodb.mongo.PlantaRepositorioMongoDB;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PlantaDataLoader {

    private static final Logger logger = LoggerFactory.getLogger(PlantaDataLoader.class);

    private final MongoTemplate mongoTemplate;
    private final PlantaRepositorioMongoDB plantaRepositorio;

    // ✔ Constructor Injection (Sustituye @Autowired)
    public PlantaDataLoader(MongoTemplate mongoTemplate,
                            PlantaRepositorioMongoDB plantaRepositorio) {
        this.mongoTemplate = mongoTemplate;
        this.plantaRepositorio = plantaRepositorio;
    }

    @PostConstruct
    public void inicializarPlantas() {
        try {
            long count = mongoTemplate.getCollection("plantas").countDocuments();

            if (count > 0) {
                logger.info("✅ Catálogo ya existe con {} plantas", count);
                return;
            }

            logger.info("🌱 Inicializando catálogo...");
            List<Planta> plantas = crearCatalogo40Plantas();

            for (Planta planta : plantas) {
                plantaRepositorio.guardar(planta);
            }

            logger.info("✅ Catálogo inicializado con 40 plantas");

        } catch (Exception e) {
            logger.error("❌ Error inicializando catálogo", e);
        }
    }

    private List<Planta> crearCatalogo40Plantas() {

        List<Planta> plantas = new ArrayList<>();

        plantas.add(crearPlantaConId("PLT0001", "Albahaca", "Ocimum basilicum",
                "Hierba aromática esencial en la cocina mediterránea.", "/images/albahaca.jpg",
                Arrays.asList(EtiquetaConstantes.AROMATICA, EtiquetaConstantes.MEDICINAL, EtiquetaConstantes.INTERIOR),
                Arrays.asList(
                        crearCuidado("Regar cada 2-3 días", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado("Sol directo o luz brillante", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0002", "Alocasia", "Alocasia amazonica",
                "Planta de hojas grandes en forma de flecha. 'Oreja de Elefante'.", "/images/alocasia.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.TROPICAL, EtiquetaConstantes.DECORATIVA),
                Arrays.asList(
                        crearCuidado("Regar cuando el sustrato esté semi-seco", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.TRES_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_INDIRECTA_BRILLANTE, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0003", "Aloe Vera", "Aloe barbadensis miller",
                "Suculenta medicinal con gel. Excelente para quemaduras.", "/images/aloe_vera.jpg",
                Arrays.asList(EtiquetaConstantes.SUCULENTA, EtiquetaConstantes.MEDICINAL, EtiquetaConstantes.FACIL_CUIDADO),
                Arrays.asList(
                        crearCuidado("Regar cada 15-20 días", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.QUINCE_DIAS),
                        crearCuidado("Sol directo parcial", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0004", "Anturio", "Anthurium andraeanum",
                "Planta tropical con flores en forma de corazón.", "/images/anturio.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.TROPICAL),
                Arrays.asList(
                        crearCuidado("Regar 2 veces por semana", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.TRES_DIAS),
                        crearCuidado("Luz indirecta media", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0005", "Ave del Paraíso", "Strelitzia reginae",
                "Planta espectacular con flores que parecen aves tropicales.", "/images/ave-paraiso.jpg",
                Arrays.asList(EtiquetaConstantes.EXTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.TROPICAL),
                Arrays.asList(
                        crearCuidado("Regar abundantemente en verano", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PLENO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0006", "Begonia Rex", "Begonia rex-cultorum",
                "Begonia ornamental con hojas coloridas.", "/images/begonia-rex.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.DECORATIVA, EtiquetaConstantes.COLORIDA),
                Arrays.asList(
                        crearCuidado("Regar cuando la superficie esté seca", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.TRES_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_INDIRECTA_BRILLANTE, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0007", "Cactus Barril", "Echinocactus grusonii",
                "Cactus esférico con espinas doradas.", "/images/cactus-barrel.jpg",
                Arrays.asList(EtiquetaConstantes.SUCULENTA, EtiquetaConstantes.EXTERIOR, EtiquetaConstantes.DESERTICA),
                Arrays.asList(
                        crearCuidado("Regar cada 3-4 semanas", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.VEINTICINCO_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_DIRECTO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0008", "Cactus", "Cactaceae sp.",
                "Familia diversa de plantas suculentas.", "/images/cactus.jpg",
                Arrays.asList(EtiquetaConstantes.SUCULENTA, EtiquetaConstantes.FACIL_CUIDADO, EtiquetaConstantes.INTERIOR),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_CADA_2_3_SEMANAS, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.VEINTE_DIAS),
                        crearCuidado("Sol directo parcial", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0009", "Calathea", "Calathea ornata",
                "Planta de interior con hojas decorativas.", "/images/calathea.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.TROPICAL, EtiquetaConstantes.DECORATIVA),
                Arrays.asList(
                        crearCuidado("Regar 2-3 veces por semana", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado("Luz indirecta media-baja", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0010", "Cilantro", "Coriandrum sativum",
                "Hierba aromática muy usada en cocina.", "/images/cilantro.jpg",
                Arrays.asList(EtiquetaConstantes.AROMATICA, EtiquetaConstantes.COMESTIBLE, EtiquetaConstantes.FACIL_CUIDADO),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_REGULARMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PARCIAL, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0011", "Corazón de María", "Dicentra spectabilis",
                "Flor en forma de corazón colgante. Muy ornamental.", "/images/corazon-maria.jpg",
                Arrays.asList(EtiquetaConstantes.EXTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.DECORATIVA),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_REGULARMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado("Sombra parcial", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0012", "Dracaena", "Dracaena fragrans",
                "Planta de interior alta y elegante. Purifica el aire.", "/images/dracaena.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.PURIFICADORA, EtiquetaConstantes.DECORATIVA),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_SEMANALMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.SIETE_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_INDIRECTA, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0013", "Ficus Lyrata", "Ficus lyrata",
                "Árbol de interior con hojas en forma de violín.", "/images/ficus-lyrata.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.DECORATIVA, EtiquetaConstantes.GRANDE),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_SEMANALMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.SIETE_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_BRILLANTE_INDIRECTA, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0014", "Flor de Pascua", "Euphorbia pulcherrima",
                "Brácteas rojas espectaculares, típica de Navidad.", "/images/flor-pascua.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.ESTACIONAL),
                Arrays.asList(
                        crearCuidado("Regar cuando la tierra esté seca", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.TRES_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_BRILLANTE, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0015", "Geranio", "Pelargonium sp.",
                "Flor popular de balcón, muy resistente.", "/images/geranio.jpg",
                Arrays.asList(EtiquetaConstantes.EXTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.AROMATICA),
                Arrays.asList(
                        crearCuidado("Regar regularmente sin encharcar", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_DIRECTO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0016", "Girasol", "Helianthus annuus",
                "Flor emblemática que sigue al sol.", "/images/girasol.jpg",
                Arrays.asList(EtiquetaConstantes.EXTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.COMESTIBLE),
                Arrays.asList(
                        crearCuidado("Regar abundantemente", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.UN_DIA),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PLENO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0017", "Helecho", "Nephrolepis exaltata",
                "Planta de interior que ama la humedad.", "/images/helecho.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.TROPICAL, EtiquetaConstantes.PURIFICADORA),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_FRECUENTEMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_INDIRECTA, "LUZ", FrecuenciaConstantes.DIARIO),
                        crearCuidado("Mantener humedad alta", "HUMEDAD", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0018", "Hiedra", "Hedera helix",
                "Planta trepadora que cubre muros.", "/images/hiedra.jpg",
                Arrays.asList(EtiquetaConstantes.EXTERIOR, EtiquetaConstantes.TREPADORA, EtiquetaConstantes.PURIFICADORA),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_REGULARMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.TRES_DIAS),
                        crearCuidado("Sombra parcial", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0019", "Jade", "Crassula ovata",
                "Suculenta símbolo de prosperidad.", "/images/jade.jpg",
                Arrays.asList(EtiquetaConstantes.SUCULENTA, EtiquetaConstantes.INTERIOR, EtiquetaConstantes.FACIL_CUIDADO),
                Arrays.asList(
                        crearCuidado("Regar cada 10-14 días", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOCE_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_BRILLANTE, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0020", "Kalanchoe", "Kalanchoe blossfeldiana",
                "Suculenta con flores duraderas.", "/images/kalanchoe.jpg",
                Arrays.asList(EtiquetaConstantes.SUCULENTA, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.INTERIOR),
                Arrays.asList(
                        crearCuidado("Regar cada 10 días", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DIEZ_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_BRILLANTE, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0021", "Kentia", "Howea forsteriana",
                "Palmera elegante y resistente.", "/images/kentia.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.TROPICAL, EtiquetaConstantes.GRANDE),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_SEMANALMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.SIETE_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_INDIRECTA, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0022", "Lavanda", "Lavandula angustifolia",
                "Aromática relajante.", "/images/lavanda.jpg",
                Arrays.asList(EtiquetaConstantes.AROMATICA, EtiquetaConstantes.MEDICINAL, EtiquetaConstantes.EXTERIOR),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_MODERADAMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.CUATRO_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PLENO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0023", "Manzanilla", "Matricaria chamomilla",
                "Hierba medicinal calmante.", "/images/manzanilla.jpg",
                Arrays.asList(EtiquetaConstantes.MEDICINAL, EtiquetaConstantes.AROMATICA, EtiquetaConstantes.EXTERIOR),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_REGULARMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_DIRECTO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0024", "Margarita", "Bellis perennis",
                "Flor clásica de jardín.", "/images/margarita.jpg",
                Arrays.asList(EtiquetaConstantes.EXTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.DECORATIVA),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_REGULARMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado("Sol directo a parcial", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0025", "Menta", "Mentha spicata",
                "Hierba aromática refrescante.", "/images/menta.jpg",
                Arrays.asList(EtiquetaConstantes.AROMATICA, EtiquetaConstantes.MEDICINAL, EtiquetaConstantes.COMESTIBLE),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_FRECUENTEMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.UN_DIA),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PARCIAL, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0026", "Monstera", "Monstera deliciosa",
                "Hojas perforadas icónicas.", "/images/monstera.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.TROPICAL, EtiquetaConstantes.DECORATIVA),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_SEMANALMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.SIETE_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_INDIRECTA_BRILLANTE, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0027", "Nopal", "Opuntia ficus-indica",
                "Cactus comestible muy resistente.", "/images/nopal.jpg",
                Arrays.asList(EtiquetaConstantes.SUCULENTA, EtiquetaConstantes.COMESTIBLE, EtiquetaConstantes.EXTERIOR),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_CADA_2_3_SEMANAS, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.VEINTE_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PLENO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0028", "Orégano", "Origanum vulgare",
                "Aromática muy usada en cocina.", "/images/oregano.jpg",
                Arrays.asList(EtiquetaConstantes.AROMATICA, EtiquetaConstantes.COMESTIBLE, EtiquetaConstantes.MEDICINAL),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_MODERADAMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.TRES_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PLENO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0029", "Orquídea", "Phalaenopsis sp.",
                "Flor exótica elegante.", "/images/orquidea.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.TROPICAL),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_SEMANALMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.SIETE_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_INDIRECTA_BRILLANTE, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0030", "Palmera Areca", "Dypsis lutescens",
                "Purificadora excelente.", "/images/palmera-areca.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.TROPICAL, EtiquetaConstantes.PURIFICADORA),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_FRECUENTEMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.TRES_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_INDIRECTA_BRILLANTE, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0031", "Pensamiento", "Viola tricolor",
                "Flor de invierno.", "/images/pensamiento.jpg",
                Arrays.asList(EtiquetaConstantes.EXTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.ESTACIONAL),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_REGULARMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PARCIAL, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0032", "Peperomia", "Peperomia obtusifolia",
                "Compacta y resistente.", "/images/peperomia.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.COMPACTA, EtiquetaConstantes.FACIL_CUIDADO),
                Arrays.asList(
                        crearCuidado("Regar cuando el sustrato esté seco", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.SIETE_DIAS),
                        crearCuidado("Luz indirecta media", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0033", "Perejil", "Petroselinum crispum",
                "Hierba culinaria esencial.", "/images/perejil.jpg",
                Arrays.asList(EtiquetaConstantes.AROMATICA, EtiquetaConstantes.COMESTIBLE, EtiquetaConstantes.FACIL_CUIDADO),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_REGULARMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PARCIAL, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0034", "Pilea", "Pilea peperomioides",
                "Hojas redondas tipo moneda.", "/images/pilea.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.DECORATIVA, EtiquetaConstantes.COMPACTA),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_SEMANALMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.SIETE_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_BRILLANTE_INDIRECTA, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0035", "Pothos", "Epipremnum aureum",
                "Planta muy resistente.", "/images/pothos.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.PURIFICADORA, EtiquetaConstantes.FACIL_CUIDADO),
                Arrays.asList(
                        crearCuidado("Regar cuando el sustrato esté seco", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.CINCO_DIAS),
                        crearCuidado("Luz indirecta a sombra", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0036", "Romero", "Rosmarinus officinalis",
                "Arbusto aromático.", "/images/romero.jpg",
                Arrays.asList(EtiquetaConstantes.AROMATICA, EtiquetaConstantes.MEDICINAL, EtiquetaConstantes.COMESTIBLE),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_MODERADAMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.CUATRO_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PLENO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0037", "Rosa", "Rosa sp.",
                "Clásica y elegante.", "/images/rosa.jpg",
                Arrays.asList(EtiquetaConstantes.EXTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.AROMATICA),
                Arrays.asList(
                        crearCuidado("Regar profundamente 2-3 veces por semana", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.DOS_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PLENO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0038", "Sansevieria", "Sansevieria trifasciata",
                "Extremadamente resistente.", "/images/sansevieria.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.PURIFICADORA, EtiquetaConstantes.FACIL_CUIDADO),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_CADA_2_3_SEMANAS, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.VEINTE_DIAS),
                        crearCuidado("Luz indirecta a sombra", "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0039", "Tomillo", "Thymus vulgaris",
                "Aromática mediterránea.", "/images/tomillo.jpg",
                Arrays.asList(EtiquetaConstantes.AROMATICA, EtiquetaConstantes.MEDICINAL, EtiquetaConstantes.COMESTIBLE),
                Arrays.asList(
                        crearCuidado(CuidadoDescripcionConstantes.REGAR_MODERADAMENTE, TipoCuidado.RIEGO.name(), FrecuenciaConstantes.CUATRO_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.SOL_PLENO, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        plantas.add(crearPlantaConId("PLT0040", "Violeta Africana", "Saintpaulia ionantha",
                "Compacta con flores continuas.", "/images/violeta-africana.jpg",
                Arrays.asList(EtiquetaConstantes.INTERIOR, EtiquetaConstantes.FLORIDA, EtiquetaConstantes.COMPACTA),
                Arrays.asList(
                        crearCuidado("Regar por abajo sin mojar hojas", TipoCuidado.RIEGO.name(), FrecuenciaConstantes.TRES_DIAS),
                        crearCuidado(CuidadoDescripcionConstantes.LUZ_BRILLANTE_INDIRECTA, "LUZ", FrecuenciaConstantes.DIARIO)
                )));

        return plantas;
    }

    private Planta crearPlanta(String nombreComun,
                               String nombreCientifico,
                               String descripcion,
                               String imagenURL,
                               List<String> nombresEtiquetas,
                               List<Cuidado> cuidados) {

        Planta planta = new Planta(nombreComun, nombreCientifico, descripcion, imagenURL);

        for (String nombreEtiqueta : nombresEtiquetas) {
            Etiqueta etiqueta = new Etiqueta(nombreEtiqueta, asignarColorEtiqueta(nombreEtiqueta));
            planta.agregarEtiqueta(etiqueta);
        }

        for (Cuidado cuidado : cuidados) {
            planta.agregarCuidado(cuidado);
        }

        return planta;
    }
    private Planta crearPlantaConId(String id,
                                    String nombreComun,
                                    String nombreCientifico,
                                    String descripcion,
                                    String imagenURL,
                                    List<String> etiquetas,
                                    List<Cuidado> cuidados) {

        Planta planta = crearPlanta(nombreComun, nombreCientifico, descripcion, imagenURL, etiquetas, cuidados);
        planta.setId(id);
        return planta;
    }


    private Cuidado crearCuidado(String descripcion, String tipo, String frecuencia) {

        TipoCuidado tipoCuidado = TipoCuidado.valueOf(tipo);

        Integer dias = extraerDias(frecuencia);

        return new Cuidado(tipoCuidado, descripcion, dias);
    }

    private Integer extraerDias(String frecuencia) {
        try {
            if (frecuencia.toLowerCase().contains("diario")) {
                return 1;
            }

            // ✔ regex corregido
            String numeroStr = frecuencia.replaceAll("\\D", "");

            if (!numeroStr.isEmpty()) {
                return Integer.parseInt(numeroStr);
            }

            return 7;

        } catch (Exception e) {
            return 7;
        }
    }

    private String asignarColorEtiqueta(String nombre) {
        return switch (nombre.toUpperCase()) {
            case EtiquetaConstantes.INTERIOR -> EtiquetaColorConstantes.AZUL;
            case EtiquetaConstantes.EXTERIOR -> EtiquetaColorConstantes.VERDE;
            case EtiquetaConstantes.AROMATICA -> EtiquetaColorConstantes.VIOLETA;
            case EtiquetaConstantes.MEDICINAL -> EtiquetaColorConstantes.TURQUESA;
            case EtiquetaConstantes.COMESTIBLE -> EtiquetaColorConstantes.NARANJA;
            case EtiquetaConstantes.SUCULENTA -> EtiquetaColorConstantes.ROJO;
            case EtiquetaConstantes.FLORIDA -> EtiquetaColorConstantes.ROSA;
            case EtiquetaConstantes.TROPICAL -> EtiquetaColorConstantes.VERDE_OSCURO;
            case EtiquetaConstantes.DECORATIVA -> EtiquetaColorConstantes.AMARILLO;
            case EtiquetaConstantes.PURIFICADORA -> EtiquetaColorConstantes.AZUL;
            case EtiquetaConstantes.FACIL_CUIDADO -> EtiquetaColorConstantes.VERDE;
            case EtiquetaConstantes.COMPACTA -> EtiquetaColorConstantes.NARANJA;
            case EtiquetaConstantes.TREPADORA -> EtiquetaColorConstantes.VERDE;
            case EtiquetaConstantes.COLORIDA -> EtiquetaColorConstantes.ROSA;
            case EtiquetaConstantes.ESTACIONAL -> EtiquetaColorConstantes.VIOLETA;
            case EtiquetaConstantes.GRANDE -> EtiquetaColorConstantes.VERDE_OSCURO;
            default -> EtiquetaColorConstantes.GRIS;
        };
    }
}
