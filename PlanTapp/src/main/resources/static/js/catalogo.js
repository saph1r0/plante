// catalogo.js - Lógica para la página del catálogo de plantas
// Integración completa con API de plantas y MongoDB

// --- 1. Datos y Estado de la Aplicación ---
const appDataCatalogo = {
    plantasOriginales: [], // Todas las plantas desde la API
    plantasFiltradas: [], // Plantas después de aplicar filtros y búsqueda
    filtroActivo: 'all', // Filtro actual seleccionado
    terminoBusqueda: '', // Término de búsqueda actual
    vistaActual: 'grid', // 'grid' o 'list'
    cargando: false, // Estado de carga

    // Mapeo de filtros a categorías (cuando implementes en backend)
    categoriasFiltros: {
        'interior': ['interior', 'casa', 'sombra'],
        'exterior': ['exterior', 'jardin', 'sol'],
        'suculentas': ['suculenta', 'cactus', 'carnosa'],
        'flores': ['flor', 'floreciente', 'colorida']
    }
};

// --- 2. Referencias a Elementos del DOM ---
const domElementsCatalogo = {
    // Elementos principales
    menuToggle: document.getElementById('menu-toggle'),
    sidebar: document.getElementById('sidebar'),

    // Búsqueda y filtros
    searchInput: document.getElementById('plant-search-input'),
    clearSearchBtn: document.getElementById('clear-search'),
    searchSuggestions: document.getElementById('search-suggestions'),
    filterButtons: document.querySelectorAll('.filter-btn'),

    // Resultados y vista
    resultsCount: document.getElementById('results-count'),
    searchTerm: document.getElementById('search-term'),
    gridViewBtn: document.getElementById('grid-view'),
    listViewBtn: document.getElementById('list-view'),
    plantsContainer: document.getElementById('plants-container'),
    noResults: document.getElementById('no-results'),
    resetSearchBtn: document.getElementById('reset-search'),

    // Modal
    modalOverlay: document.getElementById('plant-modal-overlay'),
    modalContent: document.getElementById('plant-modal-content'),
    modalImage: document.getElementById('modal-plant-image'),
    modalName: document.getElementById('modal-plant-name'),
    modalScientific: document.getElementById('modal-plant-scientific'),
    modalDescription: document.getElementById('modal-plant-description'),
    modalCare: document.getElementById('modal-plant-care'),
    modalClose: document.querySelector('.plant-modal-close')
};

// --- 3. Funciones de API ---

/**
 * Carga todas las plantas desde la API
 */
async function cargarPlantasAPI() {
    try {
        console.log('🌱 Cargando plantas desde API...');
        appDataCatalogo.cargando = true;
        mostrarEstadoCarga(true);

        const response = await fetch('/api/plantas');

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }

        const plantas = await response.json();
        console.log(`✅ ${plantas.length} plantas cargadas desde API`);

        // Procesar y almacenar plantas
        appDataCatalogo.plantasOriginales = plantas.map(planta => ({
            id: planta.id,
            nombreComun: planta.nombreComun,
            nombreCientifico: planta.nombreCientifico,
            descripcion: planta.descripcion,
            imagenURL: planta.imagenURL || '/images/default-plant.jpg',
            cuidados: planta.cuidados || [],
            categoria: planta.categoria || 'general',
            tags: planta.tags || [],
            fechaCreacion: planta.fechaCreacion || new Date().toISOString()
        }));

        appDataCatalogo.plantasFiltradas = [...appDataCatalogo.plantasOriginales];

        // Actualizar UI
        actualizarContadorResultados();
        renderizarPlantas();

        return plantas;

    } catch (error) {
        console.error('❌ Error al cargar plantas:', error);
        mostrarErrorCarga();
        return [];
    } finally {
        appDataCatalogo.cargando = false;
        mostrarEstadoCarga(false);
    }
}

/**
 * Busca plantas por término
 */
function buscarPlantas(termino) {
    appDataCatalogo.terminoBusqueda = termino.toLowerCase().trim();
    aplicarFiltros();
}

/**
 * Aplica filtros de categoría
 */
function aplicarFiltroPorCategoria(categoria) {
    appDataCatalogo.filtroActivo = categoria;
    aplicarFiltros();
}

/**
 * Aplica todos los filtros activos
 */
function aplicarFiltros() {
    let plantasFiltradas = [...appDataCatalogo.plantasOriginales];

    // Filtro por búsqueda
    if (appDataCatalogo.terminoBusqueda) {
        plantasFiltradas = plantasFiltradas.filter(planta =>
            planta.nombreComun.toLowerCase().includes(appDataCatalogo.terminoBusqueda) ||
            planta.nombreCientifico.toLowerCase().includes(appDataCatalogo.terminoBusqueda) ||
            planta.descripcion.toLowerCase().includes(appDataCatalogo.terminoBusqueda)
        );
    }

    // Filtro por categoría (cuando implementes en backend)
    if (appDataCatalogo.filtroActivo !== 'all') {
        plantasFiltradas = plantasFiltradas.filter(planta => {
            // Por ahora, filtro básico por descripción/tags
            // Cuando tengas categorías en el backend, cambia esto por: planta.categoria === appDataCatalogo.filtroActivo
            const textoCompleto = `${planta.descripcion} ${planta.tags.join(' ')}`.toLowerCase();
            const categorias = appDataCatalogo.categoriasFiltros[appDataCatalogo.filtroActivo] || [];
            return categorias.some(cat => textoCompleto.includes(cat));
        });
    }

    appDataCatalogo.plantasFiltradas = plantasFiltradas;

    // Actualizar UI
    actualizarContadorResultados();
    actualizarTerminoBusqueda();
    renderizarPlantas();

    console.log(`🔍 Filtros aplicados: ${plantasFiltradas.length} plantas encontradas`);
}

// --- 4. Funciones de Renderizado ---

/**
 * Renderiza todas las plantas en el grid
 */
function renderizarPlantas() {
    if (!domElementsCatalogo.plantsContainer) return;

    const container = domElementsCatalogo.plantsContainer;

    // Limpiar contenedor
    container.innerHTML = '';

    // Mostrar/ocultar mensaje de sin resultados
    if (appDataCatalogo.plantasFiltradas.length === 0) {
        domElementsCatalogo.noResults.style.display = 'block';
        return;
    } else {
        domElementsCatalogo.noResults.style.display = 'none';
    }

    // Crear tarjetas de plantas
    appDataCatalogo.plantasFiltradas.forEach((planta, index) => {
        const plantCard = crearTarjetaPlanta(planta, index);
        container.appendChild(plantCard);
    });

    console.log(`🎨 ${appDataCatalogo.plantasFiltradas.length} plantas renderizadas`);
}

/**
 * Crea una tarjeta individual de planta
 */
function crearTarjetaPlanta(planta, index) {
    const card = document.createElement('div');
    card.className = 'plant-card';
    card.style.animationDelay = `${index * 0.1}s`;

    // Limitar descripción
    const descripcionCorta = planta.descripcion.length > 120
        ? planta.descripcion.substring(0, 120) + '...'
        : planta.descripcion;

    card.innerHTML = `
        <div class="plant-card-image">
            <img src="${planta.imagenURL}"
                 alt="${planta.nombreComun}"
                 onerror="this.src='/images/default-plant.jpg'">
        </div>
        <div class="plant-card-content">
            <div class="plant-card-header">
                <h3 class="plant-card-name">${planta.nombreComun}</h3>
                <p class="plant-card-scientific">${planta.nombreCientifico}</p>
            </div>
            <p class="plant-card-description">${descripcionCorta}</p>
            ${planta.tags.length > 0 ? `
                <div class="plant-card-tags">
                    ${planta.tags.slice(0, 3).map(tag => `
                        <span class="plant-tag">${tag}</span>
                    `).join('')}
                </div>
            ` : ''}
            <div class="plant-card-footer">
                <div class="plant-card-actions">
                    <button class="plant-action-btn" title="Ver detalles" aria-label="Ver detalles de ${planta.nombreComun}">
                        <i class="fas fa-eye"></i>
                    </button>
                    <button class="plant-action-btn" title="Agregar a favoritos" aria-label="Agregar ${planta.nombreComun} a favoritos">
                        <i class="fas fa-heart"></i>
                    </button>
                    <button class="plant-action-btn" title="Compartir" aria-label="Compartir ${planta.nombreComun}">
                        <i class="fas fa-share"></i>
                    </button>
                </div>
            </div>
        </div>
    `;

    // Event listener para abrir modal
    card.addEventListener('click', (e) => {
        // Solo abrir modal si no se hizo clic en botones de acción
        if (!e.target.closest('.plant-action-btn')) {
            mostrarModalPlanta(planta);
        }
    });

    // Event listeners para botones de acción
    const actionButtons = card.querySelectorAll('.plant-action-btn');
    actionButtons[0].addEventListener('click', (e) => {
        e.stopPropagation();
        mostrarModalPlanta(planta);
    });
    actionButtons[1].addEventListener('click', (e) => {
        e.stopPropagation();
        toggleFavorito(planta);
    });
    actionButtons[2].addEventListener('click', (e) => {
        e.stopPropagation();
        compartirPlanta(planta);
    });

    return card;
}

/**
 * Muestra el modal con detalles de la planta
 */
function mostrarModalPlanta(planta) {
    if (!domElementsCatalogo.modalOverlay) return;

    // Actualizar contenido del modal
    domElementsCatalogo.modalImage.src = planta.imagenURL;
    domElementsCatalogo.modalImage.alt = planta.nombreComun;
    domElementsCatalogo.modalName.textContent = planta.nombreComun;
    domElementsCatalogo.modalScientific.textContent = planta.nombreCientifico;
    domElementsCatalogo.modalDescription.textContent = planta.descripcion;

    // Renderizar cuidados
    renderizarCuidadosModal(planta.cuidados);

    // Mostrar modal
    domElementsCatalogo.modalOverlay.classList.add('show');
    document.body.style.overflow = 'hidden';

    console.log(`👁️ Modal abierto para: ${planta.nombreComun}`);
}

/**
 * Renderiza los cuidados en el modal
 */
function renderizarCuidadosModal(cuidados) {
    if (!domElementsCatalogo.modalCare || !cuidados.length) {
        domElementsCatalogo.modalCare.innerHTML = '<p>No hay información de cuidados disponible.</p>';
        return;
    }

    const iconosPorTipo = {
        'riego': 'fas fa-tint',
        'luz': 'fas fa-sun',
        'temperatura': 'fas fa-thermometer-half',
        'humedad': 'fas fa-cloud',
        'fertilizante': 'fas fa-seedling',
        'poda': 'fas fa-cut',
        'sustrato': 'fas fa-mountain',
        'default': 'fas fa-leaf'
    };

    domElementsCatalogo.modalCare.innerHTML = cuidados.map(cuidado => `
        <div class="care-item">
            <i class="care-icon ${iconosPorTipo[cuidado.tipo?.toLowerCase()] || iconosPorTipo.default}"></i>
            <div class="care-content">
                <div class="care-title">${cuidado.tipo || 'Cuidado'}</div>
                <div class="care-description">
                    ${cuidado.descripcion}
                    ${cuidado.frecuenciaDias ? `<br><strong>Frecuencia:</strong> cada ${cuidado.frecuenciaDias} días` : ''}
                </div>
            </div>
        </div>
    `).join('');
}

/**
 * Cierra el modal
 */
function cerrarModal() {
    domElementsCatalogo.modalOverlay.classList.remove('show');
    document.body.style.overflow = '';
}

// --- 5. Funciones de UI ---

/**
 * Actualiza el contador de resultados
 */
function actualizarContadorResultados() {
    if (!domElementsCatalogo.resultsCount) return;

    const total = appDataCatalogo.plantasFiltradas.length;
    const icono = '<i class="fas fa-leaf"></i>';

    let texto = '';
    if (appDataCatalogo.cargando) {
        texto = `${icono} Cargando plantas...`;
    } else if (total === 0) {
        texto = `${icono} No se encontraron plantas`;
    } else if (total === 1) {
        texto = `${icono} 1 planta encontrada`;
    } else {
        texto = `${icono} ${total} plantas encontradas`;
    }

    domElementsCatalogo.resultsCount.innerHTML = texto;
}

/**
 * Actualiza el término de búsqueda mostrado
 */
function actualizarTerminoBusqueda() {
    if (!domElementsCatalogo.searchTerm) return;

    if (appDataCatalogo.terminoBusqueda) {
        domElementsCatalogo.searchTerm.textContent = appDataCatalogo.terminoBusqueda;
        domElementsCatalogo.searchTerm.style.display = 'inline';
    } else {
        domElementsCatalogo.searchTerm.style.display = 'none';
    }
}

/**
 * Muestra/oculta el estado de carga
 */
function mostrarEstadoCarga(mostrar) {
    if (!domElementsCatalogo.plantsContainer) return;

    if (mostrar) {
        domElementsCatalogo.plantsContainer.innerHTML = `
            <div class="loading-container">
                <div class="loading-spinner">
                    <i class="fas fa-spinner fa-spin"></i>
                </div>
                <p>Cargando plantas del catálogo...</p>
            </div>
        `;
    }
}

/**
 * Muestra error de carga
 */
function mostrarErrorCarga() {
    if (!domElementsCatalogo.plantsContainer) return;

    domElementsCatalogo.plantsContainer.innerHTML = `
        <div class="loading-container">
            <div class="loading-spinner">
                <i class="fas fa-exclamation-triangle" style="color: #e74c3c;"></i>
            </div>
            <p>Error al cargar las plantas. Por favor, intenta recargar la página.</p>
            <button class="btn btn-primary" onclick="location.reload()">
                <i class="fas fa-refresh"></i> Recargar
            </button>
        </div>
    `;
}

/**
 * Cambia entre vista de grid y lista
 */
function cambiarVista(vista) {
    appDataCatalogo.vistaActual = vista;

    // Actualizar botones
    domElementsCatalogo.gridViewBtn.classList.toggle('active', vista === 'grid');
    domElementsCatalogo.listViewBtn.classList.toggle('active', vista === 'list');

    // Actualizar contenedor
    domElementsCatalogo.plantsContainer.classList.toggle('list-view', vista === 'list');

    console.log(`👁️ Vista cambiada a: ${vista}`);
}

/**
 * Genera sugerencias de búsqueda
 */
function generarSugerencias(termino) {
    if (!termino || termino.length < 2) {
        domElementsCatalogo.searchSuggestions.classList.remove('show');
        return;
    }

    const sugerencias = [];
    const terminoLower = termino.toLowerCase();

    // Buscar en nombres de plantas
    appDataCatalogo.plantasOriginales.forEach(planta => {
        if (planta.nombreComun.toLowerCase().includes(terminoLower)) {
            sugerencias.push({
                texto: planta.nombreComun,
                tipo: 'planta',
                icono: 'fas fa-leaf'
            });
        }
        if (planta.nombreCientifico.toLowerCase().includes(terminoLower)) {
            sugerencias.push({
                texto: planta.nombreCientifico,
                tipo: 'cientifico',
                icono: 'fas fa-microscope'
            });
        }
    });

    // Limitar sugerencias y eliminar duplicados
    const sugerenciasUnicas = [...new Map(sugerencias.map(s => [s.texto, s])).values()].slice(0, 5);

    if (sugerenciasUnicas.length > 0) {
        renderizarSugerencias(sugerenciasUnicas);
    } else {
        domElementsCatalogo.searchSuggestions.classList.remove('show');
    }
}

/**
 * Renderiza las sugerencias de búsqueda
 */
function renderizarSugerencias(sugerencias) {
    domElementsCatalogo.searchSuggestions.innerHTML = sugerencias.map(sugerencia => `
        <div class="suggestion-item" data-texto="${sugerencia.texto}">
            <i class="suggestion-icon ${sugerencia.icono}"></i>
            <span>${sugerencia.texto}</span>
        </div>
    `).join('');

    // Event listeners para sugerencias
    domElementsCatalogo.searchSuggestions.querySelectorAll('.suggestion-item').forEach(item => {
        item.addEventListener('click', () => {
            const texto = item.dataset.texto;
            domElementsCatalogo.searchInput.value = texto;
            buscarPlantas(texto);
            domElementsCatalogo.searchSuggestions.classList.remove('show');
        });
    });

    domElementsCatalogo.searchSuggestions.classList.add('show');
}

// --- 6. Funciones de Acciones ---

/**
 * Toggle favorito (placeholder)
 */
function toggleFavorito(planta) {
    console.log(`❤️ Toggle favorito para: ${planta.nombreComun}`);
    // Implementar lógica de favoritos aquí
    alert(`${planta.nombreComun} agregada a favoritos (funcionalidad pendiente)`);
}

/**
 * Compartir planta
 */
function compartirPlanta(planta) {
    if (navigator.share) {
        navigator.share({
            title: planta.nombreComun,
            text: planta.descripcion,
            url: window.location.href
        });
    } else {
        // Fallback: copiar al portapapeles
        const url = `${window.location.origin}${window.location.pathname}?planta=${planta.id}`;
        navigator.clipboard.writeText(url).then(() => {
            alert('Enlace copiado al portapapeles');
        });
    }
    console.log(`📤 Compartiendo: ${planta.nombreComun}`);
}

/**
 * Resetea todos los filtros y búsqueda
 */
function resetearFiltros() {
    appDataCatalogo.terminoBusqueda = '';
    appDataCatalogo.filtroActivo = 'all';

    domElementsCatalogo.searchInput.value = '';
    domElementsCatalogo.clearSearchBtn.style.display = 'none';

    // Resetear botones de filtro
    domElementsCatalogo.filterButtons.forEach(btn => {
        btn.classList.toggle('active', btn.dataset.filter === 'all');
    });

    aplicarFiltros();
    console.log('🔄 Filtros reseteados');
}

// --- 7. Inicializadores de Eventos ---

/**
 * Inicializa la barra de búsqueda
 */
function inicializarBusqueda() {
    if (!domElementsCatalogo.searchInput) return;

    // Input de búsqueda
    domElementsCatalogo.searchInput.addEventListener('input', (e) => {
        const valor = e.target.value;

        // Mostrar/ocultar botón de limpiar
        domElementsCatalogo.clearSearchBtn.style.display = valor ? 'block' : 'none';

        // Generar sugerencias
        generarSugerencias(valor);

        // Buscar con debounce
        clearTimeout(domElementsCatalogo.searchInput.timeoutId);
        domElementsCatalogo.searchInput.timeoutId = setTimeout(() => {
            buscarPlantas(valor);
        }, 300);
    });

    // Botón limpiar búsqueda
    if (domElementsCatalogo.clearSearchBtn) {
        domElementsCatalogo.clearSearchBtn.addEventListener('click', () => {
            domElementsCatalogo.searchInput.value = '';
            domElementsCatalogo.clearSearchBtn.style.display = 'none';
            domElementsCatalogo.searchSuggestions.classList.remove('show');
            buscarPlantas('');
        });
    }

    // Cerrar sugerencias al hacer clic fuera
    document.addEventListener('click', (e) => {
        if (!e.target.closest('.main-search')) {
            domElementsCatalogo.searchSuggestions.classList.remove('show');
        }
    });
}

/**
 * Inicializa los filtros
 */
function inicializarFiltros() {
    domElementsCatalogo.filterButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            // Actualizar estado visual
            domElementsCatalogo.filterButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');

            // Aplicar filtro
            const filtro = btn.dataset.filter;
            aplicarFiltroPorCategoria(filtro);
        });
    });
}

/**
 * Inicializa los controles de vista
 */
function inicializarVistas() {
    if (domElementsCatalogo.gridViewBtn) {
        domElementsCatalogo.gridViewBtn.addEventListener('click', () => {
            cambiarVista('grid');
        });
    }

    if (domElementsCatalogo.listViewBtn) {
        domElementsCatalogo.listViewBtn.addEventListener('click', () => {
            cambiarVista('list');
        });
    }
}

/**
 * Inicializa el modal
 */
function inicializarModal() {
    if (!domElementsCatalogo.modalOverlay) return;

    // Cerrar modal
    if (domElementsCatalogo.modalClose) {
        domElementsCatalogo.modalClose.addEventListener('click', cerrarModal);
    }

    // Cerrar al hacer clic en overlay
    domElementsCatalogo.modalOverlay.addEventListener('click', (e) => {
        if (e.target === domElementsCatalogo.modalOverlay) {
            cerrarModal();
        }
    });

    // Cerrar con ESC
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && domElementsCatalogo.modalOverlay.classList.contains('show')) {
            cerrarModal();
        }
    });
}

/**
 * Inicializa el sidebar (reutilizado del home)
 */
function inicializarSidebar() {
    if (!domElementsCatalogo.menuToggle || !domElementsCatalogo.sidebar) return;

    domElementsCatalogo.menuToggle.addEventListener('click', () => {
        domElementsCatalogo.sidebar.classList.toggle('active');
        domElementsCatalogo.menuToggle.classList.toggle('hidden');
        document.body.classList.toggle('sidebar-active');
    });

    // Cerrar sidebar al hacer clic fuera
    document.addEventListener('click', (e) => {
        if (!domElementsCatalogo.sidebar.contains(e.target) &&
            !domElementsCatalogo.menuToggle.contains(e.target) &&
            domElementsCatalogo.sidebar.classList.contains('active')) {
            domElementsCatalogo.sidebar.classList.remove('active');
            domElementsCatalogo.menuToggle.classList.remove('hidden');
            document.body.classList.remove('sidebar-active');
        }
    });
}

/**
 * Inicializa botón de reset
 */
function inicializarReset() {
    if (domElementsCatalogo.resetSearchBtn) {
        domElementsCatalogo.resetSearchBtn.addEventListener('click', resetearFiltros);
    }
}

// --- 8. Inicialización Principal ---

/**
 * Inicializa toda la aplicación del catálogo
 */
async function inicializarCatalogo() {
    console.log('🌱 Inicializando catálogo de plantas...');

    try {
        // Inicializar componentes de UI
        inicializarSidebar();
        inicializarBusqueda();
        inicializarFiltros();
        inicializarVistas();
        inicializarModal();
        inicializarReset();

        // Cargar plantas desde API
        await cargarPlantasAPI();

        // Verificar si hay un parámetro de planta en la URL (para enlaces directos)
        const urlParams = new URLSearchParams(window.location.search);
        const plantaId = urlParams.get('planta');
        if (plantaId) {
            const planta = appDataCatalogo.plantasOriginales.find(p => p.id === plantaId);
            if (planta) {
                setTimeout(() => mostrarModalPlanta(planta), 500);
            }
        }

        console.log('✅ Catálogo inicializado correctamente');

    } catch (error) {
        console.error('❌ Error al inicializar catálogo:', error);
        mostrarErrorCarga();
    }
}

// --- 9. Evento de Inicialización ---
document.addEventListener('DOMContentLoaded', inicializarCatalogo);