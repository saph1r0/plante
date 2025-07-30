package com.planta.plantapp.presentacion.controlador;

import com.planta.plantapp.aplicacion.interfaces.IServicioBitacora;
import com.planta.plantapp.dominio.modelo.bitacora.Bitacora;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador web para manejar las vistas HTML de la bitácora.
 */
@Controller
@RequestMapping("/bitacora")
public class BitacoraWebController {

    private final IServicioBitacora servicioBitacora;

    @Autowired
    public BitacoraWebController(IServicioBitacora servicioBitacora) {
        this.servicioBitacora = servicioBitacora;
    }

    /**
     * Muestra la lista de todas las bitácoras.
     */
    @GetMapping
    public String listarBitacoras(Model model) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.listarTodasLasBitacoras();
            model.addAttribute("bitacoras", bitacoras);
            return "bitacora/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar las bitácoras: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Muestra las bitácoras de una planta específica.
     */
    @GetMapping("/planta/{plantaId}")
    public String listarBitacorasPorPlanta(@PathVariable String plantaId, Model model) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.listarBitacorasPorPlanta(plantaId);
            model.addAttribute("bitacoras", bitacoras);
            model.addAttribute("plantaId", plantaId);
            return "bitacora/por-planta";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar las bitácoras de la planta: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Muestra el formulario para crear una nueva entrada en la bitácora.
     */
    @GetMapping("/nueva")
    public String mostrarFormularioNueva(@RequestParam(required = false) String plantaId, Model model) {
        model.addAttribute("bitacora", new Bitacora());
        if (plantaId != null) {
            model.addAttribute("plantaId", plantaId);
        }
        return "bitacora/nueva";
    }

    /**
     * Procesa el formulario para crear una nueva entrada en la bitácora.
     */
    @PostMapping("/nueva")
    public String crearBitacora(@RequestParam String descripcion,
                               @RequestParam(required = false) String foto,
                               @RequestParam String plantaId,
                               @RequestParam(required = false) String tipoCuidado,
                               @RequestParam(required = false) String observaciones,
                               RedirectAttributes redirectAttributes) {
        try {
            servicioBitacora.registrarEntrada(descripcion, foto, plantaId, tipoCuidado, observaciones);
            redirectAttributes.addFlashAttribute("mensaje", "Entrada de bitácora registrada exitosamente");
            return "redirect:/bitacora/planta/" + plantaId;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Error en los datos: " + e.getMessage());
            return "redirect:/bitacora/nueva?plantaId=" + plantaId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la entrada: " + e.getMessage());
            return "redirect:/bitacora/nueva?plantaId=" + plantaId;
        }
    }

    /**
     * Muestra los detalles de una bitácora específica.
     */
    @GetMapping("/{id}")
    public String verDetalle(@PathVariable String id, Model model) {
        try {
            Bitacora bitacora = servicioBitacora.obtenerBitacora(id);
            model.addAttribute("bitacora", bitacora);
            return "bitacora/detalle";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Bitácora no encontrada");
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar la bitácora: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Muestra el formulario para editar una bitácora.
     */
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable String id, Model model) {
        try {
            Bitacora bitacora = servicioBitacora.obtenerBitacora(id);
            model.addAttribute("bitacora", bitacora);
            return "bitacora/editar";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Bitácora no encontrada");
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar la bitácora: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Procesa el formulario para actualizar una bitácora.
     */
    @PostMapping("/{id}/editar")
    public String actualizarBitacora(@PathVariable String id,
                                   @RequestParam String descripcion,
                                   @RequestParam(required = false) String foto,
                                   @RequestParam(required = false) String tipoCuidado,
                                   @RequestParam(required = false) String observaciones,
                                   RedirectAttributes redirectAttributes) {
        try {
            Bitacora bitacora = servicioBitacora.actualizarBitacora(id, descripcion, foto, tipoCuidado, observaciones);
            redirectAttributes.addFlashAttribute("mensaje", "Bitácora actualizada exitosamente");
            return "redirect:/bitacora/" + id;
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Error en los datos: " + e.getMessage());
            return "redirect:/bitacora/" + id + "/editar";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Bitácora no encontrada");
            return "redirect:/bitacora";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la bitácora: " + e.getMessage());
            return "redirect:/bitacora/" + id + "/editar";
        }
    }

    /**
     * Elimina una bitácora.
     */
    @PostMapping("/{id}/eliminar")
    public String eliminarBitacora(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            Bitacora bitacora = servicioBitacora.obtenerBitacora(id);
            String plantaId = bitacora.getPlantaId();
            servicioBitacora.eliminarBitacora(id);
            redirectAttributes.addFlashAttribute("mensaje", "Bitácora eliminada exitosamente");
            return "redirect:/bitacora/planta/" + plantaId;
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Bitácora no encontrada");
            return "redirect:/bitacora";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la bitácora: " + e.getMessage());
            return "redirect:/bitacora";
        }
    }

    /**
     * Muestra el historial reciente de una planta.
     */
    @GetMapping("/planta/{plantaId}/historial")
    public String mostrarHistorialReciente(@PathVariable String plantaId,
                                         @RequestParam(defaultValue = "10") int limite,
                                         Model model) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.obtenerHistorialReciente(plantaId, limite);
            long totalCuidados = servicioBitacora.obtenerTotalCuidados(plantaId);

            model.addAttribute("bitacoras", bitacoras);
            model.addAttribute("plantaId", plantaId);
            model.addAttribute("totalCuidados", totalCuidados);
            model.addAttribute("limite", limite);

            return "bitacora/historial";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar el historial: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Exporta las bitácoras de una planta (vista de impresión).
     */
    @GetMapping("/planta/{plantaId}/exportar")
    public String exportarBitacoras(@PathVariable String plantaId, Model model) {
        try {
            List<Bitacora> bitacoras = servicioBitacora.exportarBitacorasPlanta(plantaId);
            model.addAttribute("bitacoras", bitacoras);
            model.addAttribute("plantaId", plantaId);
            model.addAttribute("fechaExportacion", LocalDateTime.now());
            return "bitacora/exportar";
        } catch (Exception e) {
            model.addAttribute("error", "Error al exportar las bitácoras: " + e.getMessage());
            return "error";
        }
    }
}
