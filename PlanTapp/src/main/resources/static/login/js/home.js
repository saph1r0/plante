// home.js - Lógica para la página de inicio (index.html) de la aplicación "Plantee"
// ¡NUEVO! Integración con API de plantas

// --- 1. Datos Iniciales ---
const appDataHome = {
    // Datos estáticos originales como fallback
    catalogImages: [
        { src: '/images/AA1H7uuE.jpeg', name: 'Orquídea Phalaenopsis', date: '2024-03-15', description: 'Una hermosa orquídea con flores vibrantes, ideal para interiores luminosos. Requiere riego moderado y alta humedad ambiental.' },
        { src: '/images/AA1HFmux.jpeg', name: 'Echeveria Elegans', date: '2023-11-20', description: 'Suculenta de fácil cuidado, perfecta para principiantes. Necesita mucha luz solar directa y muy poco riego.' },
        { src: '/images/AA1HpE4h.jpeg', name: 'Helecho Espada', date: '2024-01-10', description: 'Popular por su frondoso follaje, ideal para crear un ambiente tropical. Prefiere sombra parcial y alta humedad.' },
        { src: '/images/bulbosas.jpg', name: 'Rosa Roja Clásica', date: '2023-07-01', description: 'La reina del jardín, con sus pétalos suaves y fragancia inconfundible. Requiere sol pleno y poda regular para florecer.' },
        { src: '/images/flower-g6e77477b1_1280.jpg', name: 'Bonsái Ficus', date: '2024-02-28', description: 'Un arte milenario que convierte árboles en miniatura. Este Ficus es ideal para empezar en el mundo del bonsái, resistente y adaptable.' },
    ],
    searchableOptions: [
        'Inicio', 'Servicios', 'Catálogo', 'Contáctanos', 'Mi Cuenta',
        'Orquídeas', 'Suculentas', 'Helechos', 'Rosas', 'Bonsái', 'Girasoles', 'Lavanda', 'Cactus',
        'Consultoría de Jardinería', 'Diseño de Paisajes', 'Mantenimiento de Jardines', 'Control de Plagas Orgánico', 'Venta de Semillas y Plantas', 'Talleres y Cursos',
    ],
    //Datos de plantas desde API
    plantasReales: [],
    plantasFiltradas: []
};

// --- 2. Referencias a Elementos del DOM---
const domElementsHome = {
    menuToggle: document.getElementById('menu-toggle'),
    sidebar: document.getElementById('sidebar'),
    mainContent: document.querySelector('main'),
    searchIcon: document.getElementById('search-icon'),
    searchBar: document.getElementById('search-bar'),
    searchInput: document.getElementById('search-input'),
    searchSuggestions: document.getElementById('search-suggestions'),
    carouselTrack: document.getElementById('carousel-track'),
    carouselPrevBtn: document.getElementById('carousel-prev-btn'),
    carouselNextBtn: document.getElementById('carousel-next-btn'),
    photoModalOverlay: document.getElementById('photo-modal-overlay'),
    photoModalContent: document.getElementById('photo-modal-content'),
    modalImage: null,
    modalTitle: null,
    modalDescription: null,
    photoModalClose: null,
    registerBtn: document.getElementById('register-btn'),
    loginBtn: document.getElementById('login-btn'),
};

// --- 3.Funciones para API de Plantas ---

/**
 * Carga las plantas desde la API del backend
 */
async function cargarPlantasDesdeAPI() {
    try {
        console.log('🌱 Cargando plantas desde API...');

        const response = await fetch('/web/plantas');

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }

        const plantas = await response.json();
        console.log('✅ Plantas cargadas:', plantas.length);

        // Convertir plantas a formato compatible con el carrusel
        appDataHome.plantasReales = plantas.map(planta => ({
            id: planta.id,
            src: planta.imagenURL || '/login/images/default-plant.jpg',
            name: planta.nombreComun,
            scientificName: planta.nombreCientifico,
            description: planta.descripcion,
            date: new Date().toISOString().split('T')[0], // Fecha actual como placeholder
            cuidados: planta.cuidados || []
        }));

        appDataHome.plantasFiltradas = [...appDataHome.plantasReales];

        // Actualizar opciones de búsqueda con nombres de plantas
        const nombresPlanta = plantas.map(p => p.nombreComun);
        appDataHome.searchableOptions = [
            ...appDataHome.searchableOptions,
            ...nombresPlanta
        ];

        // Re-renderizar carrusel con plantas reales
        loadCarouselImagesHome();

        return plantas;

    } catch (error) {
        console.error('❌ Error al cargar plantas:', error);
        // Usar datos estáticos como fallback
        console.log('📋 Usando datos estáticos como fallback');
        return appDataHome.catalogImages;
    }
}

/**
 * Busca plantas por nombre
 */
function buscarPlantas(query) {
    if (!query || query.length < 2) {
        appDataHome.plantasFiltradas = [...appDataHome.plantasReales];
    } else {
        appDataHome.plantasFiltradas = appDataHome.plantasReales.filter(planta =>
            planta.name.toLowerCase().includes(query.toLowerCase()) ||
            planta.scientificName.toLowerCase().includes(query.toLowerCase())
        );
    }

    console.log(`🔍 Búsqueda "${query}": ${appDataHome.plantasFiltradas.length} resultados`);
    loadCarouselImagesHome();
}

/**
 * Genera descripción detallada de la planta incluyendo cuidados
 */
function generarDescripcionCompleta(planta) {
    let descripcion = planta.description || 'Información de la planta no disponible.';

    if (planta.cuidados && planta.cuidados.length > 0) {
        descripcion += '\n\n🌿 Cuidados principales:\n';
        planta.cuidados.slice(0, 3).forEach(cuidado => {
            const tipo = cuidado.tipo || 'Cuidado';
            const desc = cuidado.descripcion || 'Sin descripción';
            const frecuencia = cuidado.frecuenciaDias ? `cada ${cuidado.frecuenciaDias} días` : '';
            descripcion += `• ${tipo}: ${desc} ${frecuencia}\n`;
        });
    }

    return descripcion;
}

// --- 4. Funciones de Utilidad ---

function toggleVisibility(element, show) {
    element.style.display = show ? 'block' : 'none';
}

function scrollToSection(query) {
    let targetId = '';
    switch(query.toLowerCase()) {
        case 'inicio': targetId = 'inicio'; break;
        case 'servicios': targetId = 'servicios'; break;
        case 'catálogo': targetId = 'catalogo'; break;
        case 'contáctanos': targetId = 'contactanos'; break;
        case 'mi cuenta': window.location.href = 'dashboard.html'; return;
        default:
            // Buscar en plantas reales primero
            let foundSection = false;
            for (const item of appDataHome.plantasReales) {
                if (item.name.toLowerCase().includes(query.toLowerCase())) {
                    targetId = 'catalogo';
                    foundSection = true;
                    // También filtrar las plantas
                    buscarPlantas(query);
                    break;
                }
            }
            if (!foundSection) {
                // Fallback a datos estáticos
                for (const item of appDataHome.catalogImages) {
                    if (item.name.toLowerCase().includes(query.toLowerCase())) {
                        targetId = 'catalogo';
                        foundSection = true;
                        break;
                    }
                }
            }
            if (!foundSection) {
                alert(`No se encontró una sección o elemento relevante para "${query}" en esta página. Intenta en "Mi Cuenta".`);
                return;
            }
            break;
    }

    if (targetId) {
        const targetElement = document.getElementById(targetId);
        if (targetElement) {
            window.scrollTo({
                top: targetElement.offsetTop - 80,
                behavior: 'smooth'
            });
        }
    }
}

// --- 5. Inicialización de Componentes ---

function initializeSidebarHome() {
    if (!domElementsHome.menuToggle || !domElementsHome.sidebar) {
        console.error('Elementos del sidebar no encontrados');
        return;
    }

    domElementsHome.menuToggle.addEventListener('click', () => {
        domElementsHome.sidebar.classList.toggle('active');
        domElementsHome.menuToggle.classList.toggle('hidden');
        document.body.classList.toggle('sidebar-active');
    });

    domElementsHome.sidebar.addEventListener('click', (event) => {
        if (event.target.closest('.nav-link')) {
            setTimeout(() => {
                domElementsHome.sidebar.classList.remove('active');
                domElementsHome.menuToggle.classList.remove('hidden');
                document.body.classList.remove('sidebar-active');
            }, 100);
        } else if (!domElementsHome.sidebar.contains(event.target)) {
            domElementsHome.sidebar.classList.remove('active');
            domElementsHome.menuToggle.classList.remove('hidden');
            document.body.classList.remove('sidebar-active');
        }
    });
}

/**
 *Inicializa la funcionalidad de la barra de búsqueda con plantas reales
 */
function initializeSearchBarHome() {
    if (!domElementsHome.searchIcon || !domElementsHome.searchBar) {
        console.error('Elementos de búsqueda no encontrados');
        return;
    }

    domElementsHome.searchIcon.addEventListener('click', () => {
        const isSearchBarVisible = domElementsHome.searchBar.classList.contains('active');
        domElementsHome.searchBar.classList.toggle('active');

        if (!isSearchBarVisible) {
            domElementsHome.searchInput.focus();
        } else {
            toggleVisibility(domElementsHome.searchSuggestions, false);
            domElementsHome.searchInput.value = '';
            // Resetear filtros
            appDataHome.plantasFiltradas = [...appDataHome.plantasReales];
            loadCarouselImagesHome();
        }
    });

    domElementsHome.searchInput.addEventListener('input', () => {
        const query = domElementsHome.searchInput.value.toLowerCase();
        domElementsHome.searchSuggestions.innerHTML = '';

        if (query.length > 0) {
            // Buscar en plantas reales + opciones estáticas
            const todasLasOpciones = [
                ...appDataHome.searchableOptions,
                ...appDataHome.plantasReales.map(p => p.name),
                ...appDataHome.plantasReales.map(p => p.scientificName)
            ];

            const filteredSuggestions = [...new Set(todasLasOpciones)]
                .filter(option => option.toLowerCase().includes(query))
                .slice(0, 5);

            if (filteredSuggestions.length > 0) {
                filteredSuggestions.forEach(suggestion => {
                    const div = document.createElement('div');
                    div.textContent = suggestion;
                    div.addEventListener('click', () => {
                        domElementsHome.searchInput.value = suggestion;
                        toggleVisibility(domElementsHome.searchSuggestions, false);

                        // Si es una planta, buscar y mostrar
                        const esPlanta = appDataHome.plantasReales.some(p =>
                            p.name.toLowerCase() === suggestion.toLowerCase() ||
                            p.scientificName.toLowerCase() === suggestion.toLowerCase()
                        );

                        if (esPlanta) {
                            buscarPlantas(suggestion);
                        }

                        scrollToSection(suggestion);
                        domElementsHome.searchBar.classList.remove('active');
                    });
                    domElementsHome.searchSuggestions.appendChild(div);
                });
                toggleVisibility(domElementsHome.searchSuggestions, true);
            } else {
                toggleVisibility(domElementsHome.searchSuggestions, false);
            }

            // Buscar plantas en tiempo real
            buscarPlantas(query);
        } else {
            toggleVisibility(domElementsHome.searchSuggestions, false);
            // Resetear filtros
            appDataHome.plantasFiltradas = [...appDataHome.plantasReales];
            loadCarouselImagesHome();
        }
    });

    document.addEventListener('click', (event) => {
        if (!domElementsHome.searchBar.contains(event.target) && event.target !== domElementsHome.searchIcon) {
            toggleVisibility(domElementsHome.searchSuggestions, false);
            domElementsHome.searchBar.classList.remove('active');
            domElementsHome.searchInput.value = '';
            // Resetear filtros
            appDataHome.plantasFiltradas = [...appDataHome.plantasReales];
            loadCarouselImagesHome();
        }
    });
}

/**
 * Carga y renderiza las plantas (reales o estáticas) en el carrusel
 */
function loadCarouselImagesHome() {
    if (!domElementsHome.carouselTrack) {
        console.error('Elemento carouselTrack no encontrado');
        return;
    }

    domElementsHome.carouselTrack.innerHTML = '';

    // Usar plantas filtradas si existen, sino usar datos estáticos
    const datosAMostrar = appDataHome.plantasFiltradas.length > 0
        ? appDataHome.plantasFiltradas
        : appDataHome.catalogImages;

    datosAMostrar.forEach((item, index) => {
        const carouselItem = document.createElement('div');
        carouselItem.classList.add('carousel-item');

        // Template para plantas reales vs estáticas
        const esPlantaReal = item.id !== undefined;

        carouselItem.innerHTML = `
            <img src="${item.src}"
                 alt="${item.name}"
                 data-index="${index}"
                 data-es-planta-real="${esPlantaReal}">
            <div class="image-info">
                <strong>${item.name}</strong>
                ${esPlantaReal ?
                    `<em>${item.scientificName}</em>` :
                    `<span>${item.date}</span>`
                }
            </div>
        `;

        domElementsHome.carouselTrack.appendChild(carouselItem);

        const imgElement = carouselItem.querySelector('img');
        if (imgElement) {
            imgElement.addEventListener('click', (e) => {
                const clickedIndex = parseInt(e.target.dataset.index);
                const esPlantaReal = e.target.dataset.esPlantaReal === 'true';

                const item = esPlantaReal ?
                    appDataHome.plantasFiltradas[clickedIndex] :
                    appDataHome.catalogImages[clickedIndex];

                if (domElementsHome.photoModalOverlay && domElementsHome.modalImage) {
                    domElementsHome.modalImage.src = item.src;
                    domElementsHome.modalImage.alt = item.name;

                    // Título con nombre científico si es planta real
                    if (esPlantaReal) {
                        domElementsHome.modalTitle.innerHTML = `
                            ${item.name}
                            <br><em style="font-size: 0.8em; color: #666;">${item.scientificName}</em>
                        `;
                        domElementsHome.modalDescription.textContent = generarDescripcionCompleta(item);
                    } else {
                        domElementsHome.modalTitle.textContent = item.name;
                        domElementsHome.modalDescription.textContent = item.description;
                    }

                    domElementsHome.photoModalOverlay.classList.add('visible');
                }
            });
        }
    });

    console.log(`🎠 Carrusel actualizado con ${datosAMostrar.length} elementos`);
}

// Resto de funciones...
function initializeCarouselDragHome() {
    if (!domElementsHome.carouselTrack) {
        console.error('CarouselTrack no encontrado para drag');
        return;
    }

    let isDragging = false;
    let startX;
    let scrollLeft;

    domElementsHome.carouselTrack.addEventListener('mousedown', (e) => {
        isDragging = true;
        domElementsHome.carouselTrack.classList.add('dragging');
        startX = e.pageX - domElementsHome.carouselTrack.offsetLeft;
        scrollLeft = domElementsHome.carouselTrack.scrollLeft;
    });

    domElementsHome.carouselTrack.addEventListener('mouseleave', () => {
        isDragging = false;
        domElementsHome.carouselTrack.classList.remove('dragging');
    });

    domElementsHome.carouselTrack.addEventListener('mouseup', () => {
        isDragging = false;
        domElementsHome.carouselTrack.classList.remove('dragging');
    });

    domElementsHome.carouselTrack.addEventListener('mousemove', (e) => {
        if (!isDragging) return;
        e.preventDefault();
        const x = e.pageX - domElementsHome.carouselTrack.offsetLeft;
        const walk = (x - startX) * 2;
        domElementsHome.carouselTrack.scrollLeft = scrollLeft - walk;
    });
}

function initializePhotoModalCloseHome() {
    if (!domElementsHome.photoModalOverlay || !domElementsHome.photoModalContent) {
        console.error('Elementos del modal de fotos no encontrados');
        return;
    }

    domElementsHome.modalImage = domElementsHome.photoModalContent.querySelector('img');
    domElementsHome.modalTitle = domElementsHome.photoModalContent.querySelector('h3');
    domElementsHome.modalDescription = domElementsHome.photoModalContent.querySelector('p');
    domElementsHome.photoModalClose = domElementsHome.photoModalContent.querySelector('.photo-modal-close');

    if (!domElementsHome.photoModalClose) {
        console.error('Botón de cierre del modal no encontrado');
        return;
    }

    domElementsHome.photoModalOverlay.addEventListener('click', (event) => {
        if (event.target === domElementsHome.photoModalOverlay || event.target === domElementsHome.photoModalClose) {
            domElementsHome.photoModalOverlay.classList.remove('visible');
        }
    });

    domElementsHome.photoModalClose.addEventListener('click', () => {
        domElementsHome.photoModalOverlay.classList.remove('visible');
    });
}

function initializeAuthButtonsHome() {
    //if (domElementsHome.registerBtn) {
    //    domElementsHome.registerBtn.addEventListener('click', () => {
    //        alert('La funcionalidad de registro será implementada en la página de usuario.');
    //    });
    //}

    if (domElementsHome.loginBtn) {
        domElementsHome.loginBtn.addEventListener('click', () => {
            window.location.href = 'dashboard.html';
        });
    }
}

// --- 6. ¡ACTUALIZADA! Inicialización de la Aplicación ---
document.addEventListener('DOMContentLoaded', async () => {
    console.log('🌱 Inicializando aplicación home con plantas reales...');

    // Inicializar componentes básicos primero
    initializeSidebarHome();
    initializeSearchBarHome();
    initializeCarouselDragHome();
    initializePhotoModalCloseHome();
    initializeAuthButtonsHome();

    // Cargar plantas estáticas como placeholder inicial
    loadCarouselImagesHome();

    //Cargar plantas reales desde API
    await cargarPlantasDesdeAPI();

    console.log('✅ Aplicación home inicializada correctamente');
});