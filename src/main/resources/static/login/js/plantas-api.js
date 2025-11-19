// Archivo: plantas-api.js
// Sincronizado con PlantaController y RegistroPlantaController

// Configuración de la API
const API_CONFIG = {
    BASE_URL: 'http://localhost:8080/web/plantas', 
    REGISTROS_URL: 'http://localhost:8080/web/registros', // 🆕 NUEVO
    ENDPOINTS: {
        CATALOGO_BUSCAR: '/buscar', 
        CATALOGO_OBTENER: '', 
        CATALOGO_POR_ID: '', 
        
        REGISTRAR_PLANTA: '/registros', // 🔧 CORREGIDO
        
        MIS_PLANTAS: '/registros/usuario', // 🔧 CORREGIDO
        VER_PLANTA: '/registros',
        EDITAR_PLANTA: '/registros',
        ELIMINAR_PLANTA: '/registros',
        
        ESTADISTICAS: '/estadisticas', 
        HEALTH: '/health' 
    }
};

// Usuario actual dinámico (según login)
const USUARIO_ACTUAL = (() => {
    try {
        const data = localStorage.getItem("usuarioActual");
        if (!data) {
            console.warn("⚠️ No hay usuario logueado en localStorage");
            return { id: "anonimo", nombre: "Invitado", email: "anonimo@plantcare.com" };
        }

        const user = JSON.parse(data);
        return {
            id: user.id || user.correo || "anonimo",
            nombre: user.nombre || "Invitado",
            email: user.correo || user.email || "anonimo@plantcare.com",
            rol: user.rol || "USER"
        };
    } catch (e) {
        console.error("Error leyendo usuarioActual:", e);
        return { id: "anonimo", nombre: "Invitado", email: "anonimo@plantcare.com" };
    }
})();

// Cliente API base
class PlantCareAPI {
    static async request(endpoint, options = {}) {
        const url = endpoint.startsWith('http') ? endpoint : `${API_CONFIG.BASE_URL}${endpoint}`;
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

            // 🩵 Solución: manejar cuando el cuerpo está vacío o no es JSON
            const text = await response.text();
            try {
                return text ? JSON.parse(text) : { success: true, message: "Operación completada" };
            } catch {
                return { success: true, message: text || "Operación completada" };
            }

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
    static async buscarPlantas(tipo) {
        if (!tipo || tipo.trim().length < 2) {
            return this.obtenerTodas();
        }
        const endpoint = `${API_CONFIG.ENDPOINTS.CATALOGO_BUSCAR}?tipo=${encodeURIComponent(tipo.trim())}`;
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

        console.log('📤 Enviando registro a:', `${API_CONFIG.REGISTROS_URL}`);
        console.log('📦 Datos:', datos);

        // 🔧 CORREGIDO: Usar la URL completa de registros
        const resultado = await PlantCareAPI.request(`${API_CONFIG.REGISTROS_URL}`, {
            method: 'POST',
            body: JSON.stringify(datos)
        });

        console.log('✅ Respuesta del servidor:', resultado);
        
        // 🔧 CORREGIDO: El controlador devuelve el documento directamente, no un objeto {success: true}
        return {
            success: true,
            data: resultado,
            message: 'Planta registrada exitosamente'
        };
    }

    static async obtenerMisPlantas() {
        // 🔧 CORREGIDO: Usar el endpoint correcto de registros
        const endpoint = `${API_CONFIG.REGISTROS_URL}/usuario/${USUARIO_ACTUAL.id}`;
        console.log('📥 Cargando plantas desde:', endpoint);
        
        const registros = await PlantCareAPI.request(endpoint);
        console.log('📦 Registros recibidos:', registros);
        
        // 🔧 CORREGIDO: Mapear los registros correctamente
        return registros.map(registro => ({
            id: registro.id,
            plantaId: registro.plantaId,
            apodo: registro.apodo,
            ubicacion: registro.ubicacion,
            estado: registro.estado,
            usuarioId: registro.usuarioId,
            fechaRegistro: registro.fechaRegistro,
            ultimoCuidado: registro.fechaRegistro,
            notas: registro.notas || '',
            fotoPersonal: registro.fotoPersonal || null,
            planta: registro.planta || null, // Info del catálogo si viene
            bitacoras: registro.bitacoras || []
        }));
    }

    static async obtenerPlantaPorId(registroId) {
        const endpoint = `${API_CONFIG.REGISTROS_URL}/${registroId}`;
        return await PlantCareAPI.request(endpoint);
    }

    static async actualizarPlanta(registroId, datos) {
        const endpoint = `${API_CONFIG.REGISTROS_URL}/${registroId}`;
        return await PlantCareAPI.request(endpoint, {
            method: 'PUT',
            body: JSON.stringify(datos)
        });
    }

    static async eliminarPlanta(registroId) {
        const endpoint = `${API_CONFIG.REGISTROS_URL}/${registroId}`;
        return await PlantCareAPI.request(endpoint, {
            method: 'DELETE'
        });
    }

    static async obtenerEstadisticas() {
        const endpoint = `${API_CONFIG.REGISTROS_URL}/estadisticas/${USUARIO_ACTUAL.id}`;
        console.log('📊 Cargando estadísticas desde:', endpoint);
        return await PlantCareAPI.request(endpoint);
    }


    static async obtenerPlantasQueNecesitanAtencion() {
        const endpoint = `${API_CONFIG.REGISTROS_URL}/usuario/${USUARIO_ACTUAL.id}/necesitan-atencion`;
        return await PlantCareAPI.request(endpoint);
    }

    static async registrarCuidado(registroId, tipoCuidado, notas) {
        const endpoint = `${API_CONFIG.REGISTROS_URL}/${registroId}/cuidado`;
        const datos = { tipo: tipoCuidado, notas: notas || null };

        const resultado = await PlantCareAPI.request(endpoint, {
            method: 'POST',
            body: JSON.stringify(datos)
        });

        return resultado;
    }

    static async obtenerCuidados(registroId) {
        const endpoint = `${API_CONFIG.REGISTROS_URL}/${registroId}/cuidados`;
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
                        
                        // 🎯 REDIRECCIÓN CORREGIDA: Usar la ruta del HTML
                        setTimeout(() => {
                            window.location.href = '/web/plantas/mis-plantas';
                        }, 1000); 
                        
                        return; 
                    }
                     throw new Error(resultado.message || 'Error desconocido al registrar planta');

                } catch (error) {
                    UIUtils.mostrarNotificacion('Error: ' + error.message, 'error');
                    // Restaurar botón solo en caso de error
                    const btnGuardar = document.getElementById('btn-guardar');
                    if (btnGuardar) {
                        btnGuardar.innerHTML = '<i class="fas fa-save"></i> Registrar Mi Planta';
                        btnGuardar.disabled = false;
                    }
                }
            };
        }

        // Carga automática según la página
        const pathname = window.location.pathname;
        if (pathname.includes('/web/plantas/mis-plantas')) {
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