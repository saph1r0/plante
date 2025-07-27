// home.js - Lógica para la página de inicio (index.html) de la aplicación "Plantee"

// --- 1. Datos Iniciales (Solo los necesarios para la página de inicio) ---
const appDataHome = {
    catalogImages: [
        { src: '/images/AA1H7uuE.jpeg', name: 'Orquídea Phalaenopsis', date: '2024-03-15', description: 'Una hermosa orquídea con flores vibrantes, ideal para interiores luminosos. Requiere riego moderado y alta humedad ambiental.' },
        { src: '/images/AA1HFmux.jpeg', name: 'Echeveria Elegans', date: '2023-11-20', description: 'Suculenta de fácil cuidado, perfecta para principiantes. Necesita mucha luz solar directa y muy poco riego.' },
        { src: '/images/AA1HpE4h.jpeg', name: 'Helecho Espada', date: '2024-01-10', description: 'Popular por su frondoso follaje, ideal para crear un ambiente tropical. Prefiere sombra parcial y alta humedad.' },
        { src: '/images/bulbosas.jpg', name: 'Rosa Roja Clásica', date: '2023-07-01', description: 'La reina del jardín, con sus pétalos suaves y fragancia inconfundible. Requiere sol pleno y poda regular para florecer.' },
        { src: '/images/flower-g6e77477b1_1280.jpg', name: 'Bonsái Ficus', date: '2024-02-28', description: 'Un arte milenario que convierte árboles en miniatura. Este Ficus es ideal para empezar en el mundo del bonsái, resistente y adaptable.' },
        { src: '/images/R (1).jpeg', name: 'Girasol Gigante', date: '2023-09-05', description: 'Flores grandes y alegres que siguen el sol. Perfectos para dar un toque vibrante a tu jardín y atraer polinizadores.' },
        { src: '/images/R.jpeg', name: 'Lavanda Angustifolia', date: '2024-04-22', description: 'Planta aromática con hermosas flores moradas, conocida por sus propiedades relajantes y su uso en aceites esenciales.' },
        { src: '/images/sorta-gortenzii_643b7eb5eb1f6.jpg', name: 'Cactus San Pedro', date: '2023-10-10', description: 'Cactus columnar de crecimiento rápido, muy resistente y de bajo mantenimiento. Ideal para jardines desérticos o macetas grandes.' },
    ],
    searchableOptions: [
        'Inicio', 'Servicios', 'Catálogo', 'Contáctanos', 'Mi Cuenta',
        'Orquídeas', 'Suculentas', 'Helechos', 'Rosas', 'Bonsái', 'Girasoles', 'Lavanda', 'Cactus',
        'Consultoría de Jardinería', 'Diseño de Paisajes', 'Mantenimiento de Jardines', 'Control de Plagas Orgánico', 'Venta de Semillas y Plantas', 'Talleres y Cursos',
    ],
};

// --- 2. Referencias a Elementos del DOM ---
const domElementsHome = {
    // Sidebar y menú
    menuToggle: document.getElementById('menu-toggle'),
    sidebar: document.getElementById('sidebar'),
    mainContent: document.querySelector('main'),

    // Barra de búsqueda
    searchIcon: document.getElementById('search-icon'),
    searchBar: document.getElementById('search-bar'),
    searchInput: document.getElementById('search-input'),
    searchSuggestions: document.getElementById('search-suggestions'),

    // Catálogo (Carrusel)
    carouselTrack: document.getElementById('carousel-track'),
    carouselPrevBtn: document.getElementById('carousel-prev-btn'),
    carouselNextBtn: document.getElementById('carousel-next-btn'),
    photoModalOverlay: document.getElementById('photo-modal-overlay'),
    photoModalContent: document.getElementById('photo-modal-content'),
    modalImage: null, // Se asignará dinámicamente
    modalTitle: null, // Se asignará dinámicamente
    modalDescription: null, // Se asignará dinámicamente
    photoModalClose: null, // Se asignará dinámicamente

    // Botones de autenticación
    registerBtn: document.getElementById('register-btn'),
    loginBtn: document.getElementById('login-btn'),
};

// --- 3. Funciones de Utilidad ---

/**
 * Muestra u oculta un elemento HTML.
 * @param {HTMLElement} element - El elemento HTML a manipular.
 * @param {boolean} show - True para mostrar, false para ocultar.
 */
function toggleVisibility(element, show) {
    element.style.display = show ? 'block' : 'none';
}

/**
 * Función para desplazar a la sección correspondiente.
 * @param {string} query - El texto de la búsqueda o nombre de la sección.
 */
function scrollToSection(query) {
    let targetId = '';
    switch(query.toLowerCase()) {
        case 'inicio': targetId = 'inicio'; break;
        case 'servicios': targetId = 'servicios'; break;
        case 'catálogo': targetId = 'catalogo'; break;
        case 'contáctanos': targetId = 'contactanos'; break;
        case 'mi cuenta': window.location.href = 'dashboard.html'; return;
        default:
            let foundSection = false;
            for (const item of appDataHome.catalogImages) {
                if (item.name.toLowerCase().includes(query.toLowerCase())) {
                    targetId = 'catalogo';
                    foundSection = true;
                    break;
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

// --- 4. Inicialización de Componentes ---

/**
 * Inicializa la funcionalidad de la barra lateral.
 */
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
 * Inicializa la funcionalidad de la barra de búsqueda.
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
        }
    });

    domElementsHome.searchInput.addEventListener('input', () => {
        const query = domElementsHome.searchInput.value.toLowerCase();
        domElementsHome.searchSuggestions.innerHTML = '';

        if (query.length > 0) {
            const filteredSuggestions = appDataHome.searchableOptions.filter(option =>
                option.toLowerCase().includes(query)
            ).slice(0, 5);

            if (filteredSuggestions.length > 0) {
                filteredSuggestions.forEach(suggestion => {
                    const div = document.createElement('div');
                    div.textContent = suggestion;
                    div.addEventListener('click', () => {
                        domElementsHome.searchInput.value = suggestion;
                        toggleVisibility(domElementsHome.searchSuggestions, false);
                        scrollToSection(suggestion);
                        domElementsHome.searchBar.classList.remove('active');
                    });
                    domElementsHome.searchSuggestions.appendChild(div);
                });
                toggleVisibility(domElementsHome.searchSuggestions, true);
            } else {
                toggleVisibility(domElementsHome.searchSuggestions, false);
            }
        } else {
            toggleVisibility(domElementsHome.searchSuggestions, false);
        }
    });

    document.addEventListener('click', (event) => {
        if (!domElementsHome.searchBar.contains(event.target) && event.target !== domElementsHome.searchIcon) {
            toggleVisibility(domElementsHome.searchSuggestions, false);
            domElementsHome.searchBar.classList.remove('active');
            domElementsHome.searchInput.value = '';
        }
    });
}

/**
 * Carga y renderiza las imágenes del catálogo en el carrusel para la página de inicio.
 */
function loadCarouselImagesHome() {
    if (!domElementsHome.carouselTrack) {
        console.error('Elemento carouselTrack no encontrado');
        return;
    }

    domElementsHome.carouselTrack.innerHTML = '';

    appDataHome.catalogImages.forEach((image, index) => {
        const item = document.createElement('div');
        item.classList.add('carousel-item');
        item.innerHTML = `
            <img src="${image.src}" alt="${image.name}" data-index="${index}">
            <div class="image-info">
                <strong>${image.name}</strong>
                <span>${image.date}</span>
            </div>
        `;
        domElementsHome.carouselTrack.appendChild(item);

        const imgElement = item.querySelector('img');
        if (imgElement) {
            imgElement.addEventListener('click', (e) => {
                const clickedIndex = parseInt(e.target.dataset.index);
                const photo = appDataHome.catalogImages[clickedIndex];

                if (domElementsHome.photoModalOverlay && domElementsHome.modalImage) {
                    domElementsHome.modalImage.src = photo.src;
                    domElementsHome.modalImage.alt = photo.name;
                    domElementsHome.modalTitle.textContent = photo.name;
                    domElementsHome.modalDescription.textContent = photo.description;
                    domElementsHome.photoModalOverlay.classList.add('visible');
                }
            });
        }
    });
}

/**
 * Inicializa la funcionalidad de arrastre del carrusel.
 */
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

/**
 * Inicializa el cierre del modal de fotos.
 */
function initializePhotoModalCloseHome() {
    if (!domElementsHome.photoModalOverlay || !domElementsHome.photoModalContent) {
        console.error('Elementos del modal de fotos no encontrados');
        return;
    }

    // Asignar referencias a elementos dentro del modal de fotos
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

/**
 * Inicializa la lógica de redirección de botones de autenticación.
 */
function initializeAuthButtonsHome() {
    if (domElementsHome.registerBtn) {
        domElementsHome.registerBtn.addEventListener('click', () => {
            alert('La funcionalidad de registro será implementada en la página de usuario.');
        });
    }

    if (domElementsHome.loginBtn) {
        domElementsHome.loginBtn.addEventListener('click', () => {
            window.location.href = 'dashboard.html';
        });
    }
}

// --- 5. Inicialización de la Aplicación (Punto de Entrada) ---
document.addEventListener('DOMContentLoaded', () => {
    console.log('Inicializando aplicación home...');

    initializeSidebarHome();
    initializeSearchBarHome();
    loadCarouselImagesHome();
    initializeCarouselDragHome();
    initializePhotoModalCloseHome();
    initializeAuthButtonsHome();

    console.log('Aplicación home inicializada correctamente');
});