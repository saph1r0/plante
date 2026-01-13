

// Archivo: plantas-api.js sincronizado con RegistroPlantaController y Facade
const API_CONFIG = {
    // URL base que incluye /api para coincidir con @RequestMapping("/api/registros")
    BASE_URL: 'http://localhost:8080/api',
    REGISTROS_URL: 'http://localhost:8080/api/registros',
    CATALOGO_URL: 'http://localhost:8080/web/plantas' // Mantenemos web/plantas si ese controller no ha cambiado a /api
};

// Usuario dinámico desde localStorage (conectado a user-service)
const USUARIO_ACTUAL = (() => {
    try {
        const data = localStorage.getItem("usuarioActual");
        if (!data) return { id: "anonimo" };
        const user = JSON.parse(data);
        // Priorizamos el ID que viene del microservicio de usuarios
        return { id: user.id || user.correo || "anonimo" };
    } catch (e) {
        return { id: "anonimo" };
    }
})();

class PlantCareAPI {
    static async request(url, options = {}) {
        const token = localStorage.getItem('token');

        const defaultHeaders = {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };

        if (token) {
            defaultHeaders['Authorization'] = `Bearer ${token}`;
        }

        const config = {
            ...options,
            headers: {
                ...defaultHeaders,
                ...(options.headers || {})
            }
        };

        try {
            const response = await fetch(url, config);

            if (response.status === 401 || response.status === 403) {
                console.error("No autorizado (token inválido o ausente)");
                throw new Error("No autorizado");
            }

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`Error ${response.status}: ${errorText}`);
            }

            const text = await response.text();
            return text ? JSON.parse(text) : { success: true };
        } catch (error) {
            console.error(`Error en conexión API:`, error);
            throw error;
        }
    }
}

class CatalogoAPI {
    // Sincronizado con la lógica de búsqueda de plantas generales
    static async buscarPlantas(termino) {
        const url = `${API_CONFIG.CATALOGO_URL}/buscar?tipo=${encodeURIComponent(termino)}`;
        return await PlantCareAPI.request(url);
    }

    static async obtenerPorId(id) {
        return await PlantCareAPI.request(`${API_CONFIG.CATALOGO_URL}/${id}`);
    }
}

class PlantasAPI {
    /**
     * Sincronizado con RegistroPlantaController @PostMapping
     * Envía RegistroPlantaRequestDTO y el Controller extrae el usuario del SecurityContext
     */
    static async registrarPlanta(datosPlanta) {
        return await PlantCareAPI.request(API_CONFIG.REGISTROS_URL, {
            method: 'POST',
            body: JSON.stringify({
                plantaId: datosPlanta.plantaId,
                apodo: datosPlanta.apodo,
                ubicacion: datosPlanta.ubicacion,
                estado: datosPlanta.estado,
                fotoPersonal: datosPlanta.fotoPersonal,
                notas: datosPlanta.notas
            })
        });
    }

    /**
     * Sincronizado con @GetMapping("/mis-plantas")
     */
    static async obtenerMisPlantas() {
        const url = `${API_CONFIG.REGISTROS_URL}/mis-plantas`;
        return await PlantCareAPI.request(url);
    }

    /**
     * Sincronizado con @GetMapping("/{id}")
     */
    static async obtenerPlantaPorId(id) {
        return await PlantCareAPI.request(`${API_CONFIG.REGISTROS_URL}/${id}`);
    }

    /**
     * Sincronizado con @PutMapping("/{id}") y RegistroPlantaFacade.actualizar
     */
    static async actualizarPlanta(id, datos) {
        return await PlantCareAPI.request(`${API_CONFIG.REGISTROS_URL}/${id}`, {
            method: 'PUT',
            body: JSON.stringify(datos)
        });
    }

    /**
     * Sincronizado con @DeleteMapping("/{id}")
     */
    static async eliminarPlanta(id) {
        return await PlantCareAPI.request(`${API_CONFIG.REGISTROS_URL}/${id}`, {
            method: 'DELETE'
        });
    }

    /**
     * Sincronizado con @GetMapping("/estadisticas")
     */
    static async obtenerEstadisticas() {
        return await PlantCareAPI.request(`${API_CONFIG.REGISTROS_URL}/estadisticas`);
    }

    /**
     * IMPORTANTE: Como indicaste que el registro de cuidados irá a un futuro microservicio,
     * este método ahora es un placeholder que puedes conectar luego.
     */
    static async registrarCuidado(registroId, tipo, notas) {
        console.warn("La funcionalidad de cuidados se migrará a CuidadosService.");
        // Por ahora podrías usar el PutMapping de actualización para cambiar el estado si es necesario
        return { success: false, message: "Servicio de cuidados en migración." };
    }
}

// Exportación global para los HTML
window.CatalogoAPI = CatalogoAPI;
window.PlantasAPI = PlantasAPI;
window.UIUtils = {
    mostrarNotificacion: (msg, tipo) => {
        const notification = document.getElementById('notification');
        if (notification) {
            notification.textContent = msg;
            notification.className = `notification ${tipo} show`;
            setTimeout(() => notification.classList.remove('show'), 3000);
        } else {
            alert(msg);
        }
    }
};