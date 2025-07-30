
// Configuración de la API
const API_CONFIG = {
    BASE_URL: 'http://localhost:8080/api',
    ENDPOINTS: {
        CATALOGO_BUSCAR: '/plantas/catalogo/buscar',
        CATALOGO_OBTENER: '/plantas/catalogo',
        CATALOGO_POR_ID: '/plantas/catalogo',
        REGISTRAR_PLANTA: '/plantas/registrar',
        MIS_PLANTAS: '/plantas/usuario',
        VER_PLANTA: '/plantas/registro',
        EDITAR_PLANTA: '/plantas/registro',
        ELIMINAR_PLANTA: '/plantas/registro',
        ESTADISTICAS: '/plantas/estadisticas',
        HEALTH: '/plantas/health'
    }
};

// Usuario actual (en producción, debe venir de autenticación)
const USUARIO_ACTUAL = {
    id: "user_demo_001",
    nombre: "Usuario Demo",
    email: "usuario@plantcare.com"
};

// Cliente API base
class PlantCareAPI {
    static async request(endpoint, options = {}) {
        const url = `${API_CONFIG.BASE_URL}${endpoint}`;
        const defaultOptions = {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        };
        const config = { ...defaultOptions, ...options };

        try {
            const response = await fetch(url, config);
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(`HTTP ${response.status}: ${errorText}`);
            }
            return await response.json();
        } catch (error) {
            throw new Error(`Error en API (${endpoint}): ${error.message}`);
        }
    }

    static async healthCheck() {
        try {
            await this.request(API_CONFIG.ENDPOINTS.HEALTH);
            return true;
        } catch {
            return false;
        }
    }
}

// Servicios de Catálogo
class CatalogoAPI {
    static async buscarPlantas(query) {
        if (!query || query.trim().length < 2) {
            return this.obtenerTodas();
        }
        const endpoint = `${API_CONFIG.ENDPOINTS.CATALOGO_BUSCAR}?query=${encodeURIComponent(query.trim())}`;
        return await PlantCareAPI.request(endpoint);
    }

    static async obtenerTodas() {
        return await PlantCareAPI.request(API_CONFIG.ENDPOINTS.CATALOGO_OBTENER);
    }

    static async obtenerPorId(plantaId) {
        const endpoint = `${API_CONFIG.ENDPOINTS.CATALOGO_POR_ID}/${plantaId}`;
        return await PlantCareAPI.request(endpoint);
    }

    static async obtenerPorTipo(tipo) {
        const endpoint = `${API_CONFIG.ENDPOINTS.CATALOGO_OBTENER}/tipo/${tipo}`;
        return await PlantCareAPI.request(endpoint);
    }
}

// Servicios de Plantas Personales
class PlantasAPI {
    static async registrarPlanta(datosPlanta) {
        if (!datosPlanta.plantaId) throw new Error('ID de planta del catálogo es requerido');
        if (!datosPlanta.apodo || datosPlanta.apodo.trim().length < 2) throw new Error('El apodo debe tener al menos 2 caracteres');
        if (!datosPlanta.ubicacion || datosPlanta.ubicacion.trim().length < 2) throw new Error('La ubicación es requerida');

        const datos = {
            plantaId: datosPlanta.plantaId,
            apodo: datosPlanta.apodo.trim(),
            ubicacion: datosPlanta.ubicacion.trim(),
            usuarioId: USUARIO_ACTUAL.id,
            estado: datosPlanta.estado || 'SALUDABLE',
            notas: datosPlanta.notas || null,
            fotoPersonal: datosPlanta.fotoPersonal || null
        };

        const resultado = await PlantCareAPI.request(API_CONFIG.ENDPOINTS.REGISTRAR_PLANTA, {
            method: 'POST',
            body: JSON.stringify(datos)
        });

        if (!resultado.success) {
            throw new Error(resultado.message || 'Error al registrar la planta');
        }

        return resultado;
    }

    static async obtenerMisPlantas() {
        const endpoint = `${API_CONFIG.ENDPOINTS.MIS_PLANTAS}/${USUARIO_ACTUAL.id}`;
        const plantas = await PlantCareAPI.request(endpoint);
        return plantas.map(planta => ({
            ...planta,
            fechaRegistro: planta.fechaRegistro,
            ultimoCuidado: planta.fechaRegistro,
            bitacoras: planta.bitacoras || []
        }));
    }

    static async obtenerPlantaPorId(registroId) {
        const endpoint = `${API_CONFIG.ENDPOINTS.VER_PLANTA}/${registroId}`;
        return await PlantCareAPI.request(endpoint);
    }

    static async actualizarPlanta(registroId, datos) {
        const endpoint = `${API_CONFIG.ENDPOINTS.EDITAR_PLANTA}/${registroId}`;
        return await PlantCareAPI.request(endpoint, {
            method: 'PUT',
            body: JSON.stringify(datos)
        });
    }

    static async eliminarPlanta(registroId) {
        const endpoint = `${API_CONFIG.ENDPOINTS.ELIMINAR_PLANTA}/${registroId}`;
        return await PlantCareAPI.request(endpoint, {
            method: 'DELETE'
        });
    }

    static async obtenerEstadisticas() {
        const endpoint = `${API_CONFIG.ENDPOINTS.ESTADISTICAS}/${USUARIO_ACTUAL.id}`;
        return await PlantCareAPI.request(endpoint);
    }

    static async obtenerPlantasQueNecesitanAtencion() {
        const endpoint = `${API_CONFIG.ENDPOINTS.MIS_PLANTAS}/${USUARIO_ACTUAL.id}/necesitan-atencion`;
        return await PlantCareAPI.request(endpoint);
    }

    static async registrarCuidado(registroId, tipoCuidado, notas) {
        const endpoint = `${API_CONFIG.ENDPOINTS.VER_PLANTA}/${registroId}/cuidado`;
        const datos = { tipo: tipoCuidado, notas: notas || null };

        const resultado = await PlantCareAPI.request(endpoint, {
            method: 'POST',
            body: JSON.stringify(datos)
        });

        return resultado;
    }

    static async obtenerCuidados(registroId) {
        const endpoint = `${API_CONFIG.ENDPOINTS.VER_PLANTA}/${registroId}/cuidados`;
        return await PlantCareAPI.request(endpoint);
    }
}

// Utilidades para UI
class UIUtils {
    static mostrarNotificacion(mensaje, tipo = 'success') {
        const notification = document.getElementById('notification');
        if (notification) {
            notification.textContent = mensaje;
            notification.className = `notification ${tipo} show`;
            setTimeout(() => {
                notification.classList.remove('show');
            }, 4000);
        } else {
            if (tipo === 'error') alert('Error: ' + mensaje);
        }
    }

    static mostrarCargando(elemento, mensaje = 'Cargando...') {
        if (elemento) {
            elemento.innerHTML = `
                <div class="loading text-center" style="padding: 2rem;">
                    <i class="fas fa-spinner fa-spin" style="font-size: 2rem; color: var(--color-green-medium);"></i>
                    <p style="margin-top: 1rem; color: var(--color-gray-text);">${mensaje}</p>
                </div>
            `;
        }
    }

    static ocultarCargando() {
        document.querySelectorAll('.loading').forEach(el => {
            if (el.parentNode) el.parentNode.removeChild(el);
        });
    }

    static mostrarError(elemento, mensaje) {
        if (elemento) {
            elemento.innerHTML = `
                <div class="error-state text-center" style="padding: 2rem;">
                    <i class="fas fa-exclamation-triangle" style="font-size: 3rem; color: #dc3545; margin-bottom: 1rem;"></i>
                    <h3 style="color: #dc3545; margin-bottom: 1rem;">Error</h3>
                    <p style="color: var(--color-gray-text);">${mensaje}</p>
                    <button class="btn btn-secondary" onclick="window.location.reload()">
                        <i class="fas fa-refresh"></i> Recargar página
                    </button>
                </div>
            `;
        }
    }
}

// Gestión de conexión al backend
class BackendManager {
    static async verificarConexion() {
        try {
            return await PlantCareAPI.healthCheck();
        } catch {
            return false;
        }
    }

    static async inicializar() {
        const disponible = await this.verificarConexion();
        if (!disponible) {
            UIUtils.mostrarNotificacion(
                'No se puede conectar con el servidor. Verifique que el backend esté ejecutándose.',
                'error'
            );
        }
        return disponible;
    }
}

// Integración con formularios y páginas
document.addEventListener('DOMContentLoaded', async () => {
    const backendDisponible = await BackendManager.inicializar();

    if (backendDisponible) {
        const formRegistro = document.getElementById('form-registro');
        if (formRegistro) {
            formRegistro.onsubmit = async (event) => {
                event.preventDefault();
                const btn = document.getElementById('btn-guardar');
                btn.disabled = true;
                btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Guardando...';

                try {
                    if (!window.plantaConfirmada) {
                        throw new Error('Debe seleccionar una planta del catálogo');
                    }

                    const datosPlanta = {
                        plantaId: window.plantaConfirmada.id,
                        apodo: document.getElementById('apodo').value.trim(),
                        ubicacion: document.getElementById('ubicacion').value.trim(),
                        estado: document.getElementById('estado-inicial').value,
                        notas: document.getElementById('notas').value.trim() || null,
                        fotoPersonal: document.getElementById('foto-url').value.trim() || null
                    };

                    const resultado = await PlantasAPI.registrarPlanta(datosPlanta);

                    if (resultado.success) {
                        UIUtils.mostrarNotificacion('¡Planta registrada exitosamente!', 'success');
                        setTimeout(() => {
                            window.location.href = 'mis-plantas.html';
                        }, 1500);
                    }
                } catch (error) {
                    UIUtils.mostrarNotificacion('Error: ' + error.message, 'error');
                } finally {
                    btn.disabled = false;
                    btn.innerHTML = '<i class="fas fa-save"></i> Registrar Mi Planta';
                }
            };
        }

        // Carga automática según la página
        const pathname = window.location.pathname;
        if (pathname.includes('mis-plantas.html')) {
            setTimeout(() => window.cargarPlantasDesdeBackend(), 500);
        }
        if (pathname.includes('dashboard.html')) {
            setTimeout(() => window.cargarEstadisticasDesdeBackend(), 500);
        }
    }
});

// Funciones globales para uso en páginas
window.cargarPlantasDesdeBackend = async function() {
    const container = document.getElementById('lista-plantas');
    if (container) UIUtils.mostrarCargando(container);

    try {
        const plantas = await PlantasAPI.obtenerMisPlantas();
        if (window.plantasOriginales !== undefined) {
            window.plantasOriginales = plantas;
            window.plantasFiltradas = [...plantas];
        }
        UIUtils.ocultarCargando();
        if (window.mostrarPlantas) window.mostrarPlantas();
        return plantas;
    } catch (error) {
        UIUtils.mostrarError(container, 'Error cargando plantas: ' + error.message);
        return [];
    }
};

window.cargarEstadisticasDesdeBackend = async function() {
    try {
        const estadisticas = await PlantasAPI.obtenerEstadisticas();
        const elementos = {
            'total-plantas': estadisticas.totalPlantas || 0,
            'plantas-saludables': estadisticas.plantasSaludables || 0,
            'plantas-cuidado': estadisticas.plantasNecesitanAtencion || 0,
            'cuidados-hoy': Math.floor(Math.random() * 3) + 1
        };

        Object.entries(elementos).forEach(([id, valor]) => {
            const el = document.getElementById(id);
            if (el) {
                el.textContent = valor;
                el.style.animation = 'none';
                setTimeout(() => el.style.animation = 'pulse 0.5s ease-in-out', 10);
            }
        });
    } catch (error) {
        UIUtils.mostrarNotificacion('Error cargando estadísticas: ' + error.message, 'error');
    }
};

window.guardarCuidadoConBackend = async function(plantaId, tipo, notas) {
    try {
        const resultado = await PlantasAPI.registrarCuidado(plantaId, tipo, notas);
        if (resultado.success) {
            UIUtils.mostrarNotificacion(`¡${tipo.charAt(0).toUpperCase() + tipo.slice(1)} registrado!`, 'success');
            return true;
        }
        throw new Error(resultado.message);
    } catch (error) {
        UIUtils.mostrarNotificacion('Error al guardar cuidado: ' + error.message, 'error');
        return false;
    }
};

// Exportación de módulos
Object.assign(window, {
    API_CONFIG,
    USUARIO_ACTUAL,
    PlantCareAPI,
    CatalogoAPI,
    PlantasAPI,
    UIUtils,
    BackendManager,
    plantasAPI: PlantasAPI,
    catalogoAPI: CatalogoAPI
});
