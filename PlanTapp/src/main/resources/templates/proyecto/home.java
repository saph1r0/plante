// home.js - Lógica para la página de inicio (index.html) de la aplicación "Plantee"

// --- 1. Datos Iniciales (Solo los necesarios para la página de inicio) ---
const appDataHome = {
    catalogImages: [
        { src: 'AA1H7uuE.jpeg', name: 'Orquídea Phalaenopsis', date: '2024-03-15', description: 'Una hermosa orquídea con flores vibrantes, ideal para interiores luminosos. Requiere riego moderado y alta humedad ambiental.' },
        { src: 'AA1HFmux.jpeg', name: 'Echeveria Elegans', date: '2023-11-20', description: 'Suculenta de fácil cuidado, perfecta para principiantes. Necesita mucha luz solar directa y muy poco riego.' },
        { src: 'AA1HpE4h.jpeg', name: 'Helecho Espada', date: '2024-01-10', description: 'Popular por su frondoso follaje, ideal para crear un ambiente tropical. Prefiere sombra parcial y alta humedad.' },
        { src: 'bulbosas.jpg', name: 'Rosa Roja Clásica', date: '2023-07-01', description: 'La reina del jardín, con sus pétalos suaves y fragancia inconfundible. Requiere sol pleno y poda regular para florecer.' },
        { src: 'flower-g6e77477b1_1280.jpg', name: 'Bonsái Ficus', date: '2024-02-28', description: 'Un arte milenario que convierte árboles en miniatura. Este Ficus es ideal para empezar en el mundo del bonsái, resistente y adaptable.' },
        { src: 'R (1).jpeg', name: 'Girasol Gigante', date: '2023-09-05', description: 'Flores grandes y alegres que siguen el sol. Perfectos para dar un toque vibrante a tu jardín y atraer polinizadores.' },
        { src: 'R.jpeg', name: 'Lavanda Angustifolia', date: '2024-04-22', description: 'Planta aromática con hermosas flores moradas, conocida por sus propiedades relajantes y su uso en aceites esenciales.' },
        { src: 'sorta-gortenzii_643b7eb5eb1f6.jpg', name: 'Cactus San Pedro', date: '2023-10-10', description: 'Cactus columnar de crecimiento rápido, muy resistente y de bajo mantenimiento. Ideal para jardines desérticos o macetas grandes.' },
    ],
    searchableOptions: [
        'Inicio', 'Servicios', 'Catálogo', 'Contáctanos', 'Mi Cuenta', // Opciones para la página de inicio
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

// Asignar referencias a elementos dentro del modal de fotos una vez que photoModalContent esté disponible.
domElementsHome.modalImage = domElementsHome.photoModalContent.querySelector('img');
domElementsHome.modalTitle = domElementsHome.photoModalContent.querySelector('h3');
domElementsHome.modalDescription = domElementsHome.photoModalContent.querySelector('p');
domElementsHome.photoModalClose = domElementsHome.photoModalContent.querySelector('.photo-modal-close');


// --- 3. Funciones de Utilidad ---

/**
 * Muestra u oculta un elemento HTML.
 * @param {HTMLElement} element - El elemento HTML a manipular.
 * @param {boolean} show - True para mostrar, false para ocultar.
 */
function toggleVisibility(element, show) {
    element.style.display = show ? 'block' : 'none';
    // Para elementos con transiciones, se pueden usar clases como 'hidden'
    // element.classList.toggle('hidden', !show);
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
        case 'mi cuenta': window.location.href = 'dashboard.html'; return; // Redirigir a dashboard
        default:
            // Si no es una sección principal, se podría buscar en el catálogo (solo en home)
            let foundSection = false;
            for (const item of appDataHome.catalogImages) {
                if (item.name.toLowerCase().includes(query.toLowerCase())) {
                    targetId = 'catalogo';
                    foundSection = true;
                    break;
                }
            }
            if (!foundSection) {
                // En la página de inicio, no hay eventos ni foro directo para buscar
                // Se podría añadir un mensaje o redirigir al dashboard si la búsqueda es más compleja.
                alert(`No se encontró una sección o elemento relevante para "${query}" en esta página. Intenta en "Mi Cuenta".`);
                return;
            }
            break;
    }

    if (targetId) {
        const targetElement = document.getElementById(targetId);
        if (targetElement) {
            window.scrollTo({
                top: targetElement.offsetTop - 80, // Ajustar por la altura del header fijo
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
    domElementsHome.menuToggle.addEventListener('click', () => {
        domElementsHome.sidebar.classList.toggle('active');
        domElementsHome.menuToggle.classList.toggle('hidden'); // Ocultar el botón
        document.body.classList.toggle('sidebar-active');
    });

    // Cerrar sidebar al hacer clic en un enlace de menú o fuera de la barra lateral.
    domElementsHome.sidebar.addEventListener('click', (event) => {
        if (event.target.closest('.nav-link')) {
            // Si es un enlace de menú, cerrar el sidebar después de un pequeño retraso para la navegación
            setTimeout(() => {
                domElementsHome.sidebar.classList.remove('active');
                domElementsHome.menuToggle.classList.remove('hidden');
                document.body.classList.remove('sidebar-active');
            }, 100);
        } else if (!domElementsHome.sidebar.contains(event.target)) {
            // Si se hace clic fuera del sidebar, cerrarlo
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
    domElementsHome.searchIcon.addEventListener('click', () => {
        const isSearchBarVisible = domElementsHome.searchBar.classList.contains('active');
        domElementsHome.searchBar.classList.toggle('active'); // Alternar visibilidad con clase

        if (!isSearchBarVisible) {
            domElementsHome.searchInput.focus();
        } else {
            toggleVisibility(domElementsHome.searchSuggestions, false); // Ocultar sugerencias
            domElementsHome.searchInput.value = ''; // Limpiar el input
        }
    });

    domElementsHome.searchInput.addEventListener('input', () => {
        const query = domElementsHome.searchInput.value.toLowerCase();
        domElementsHome.searchSuggestions.innerHTML = ''; // Limpiar sugerencias anteriores

        if (query.length > 0) {
            const filteredSuggestions = appDataHome.searchableOptions.filter(option =>
                option.toLowerCase().includes(query)
            ).slice(0, 5); // Limitar a 5 sugerencias

            if (filteredSuggestions.length > 0) {
                filteredSuggestions.forEach(suggestion => {
                    const div = document.createElement('div');
                    div.textContent = suggestion;
                    div.addEventListener('click', () => {
                        domElementsHome.searchInput.value = suggestion;
                        toggleVisibility(domElementsHome.searchSuggestions, false);
                        scrollToSection(suggestion); // Usar la función de desplazamiento
                        domElementsHome.searchBar.classList.remove('active'); // Ocultar barra al seleccionar
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

    // Ocultar sugerencias si se hace clic fuera del input de búsqueda o las sugerencias
    document.addEventListener('click', (event) => {
        if (!domElementsHome.searchBar.contains(event.target) && event.target !== domElementsHome.searchIcon) {
            toggleVisibility(domElementsHome.searchSuggestions, false);
            domElementsHome.searchBar.classList.remove('active'); // Ocultar barra también
            domElementsHome.searchInput.value = ''; // Limpiar input
        }
    });
}

/**
 * Carga y renderiza las imágenes del catálogo en el carrusel para la página de inicio.
 * Solo muestra el carrusel, sin interactividad de modal de detalle.
 */
function loadCarouselImagesHome() {
    domElementsHome.carouselTrack.innerHTML = ''; // Limpiar contenido anterior

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

        // Añadir evento click para abrir el modal de fotos (solo visualización)
        item.querySelector('img').addEventListener('click', (e) => {
            const clickedIndex = parseInt(e.target.dataset.index);
            const photo = appDataHome.catalogImages[clickedIndex];
            domElementsHome.modalImage.src = photo.src;
            domElementsHome.modalImage.alt = photo.name;
            domElementsHome.modalTitle.textContent = photo.name;
            domElementsHome.modalDescription.textContent = photo.description;
            domElementsHome.photoModalOverlay.classList.add('visible');
        });
    });
}

/**
 * Inicializa la funcionalidad de arrastre del carrusel.
 */
function initializeCarouselDragHome() {
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
        e.preventDefault(); // Prevenir selección de texto al arrastrar
        const x = e.pageX - domElementsHome.carouselTrack.offsetLeft;
        const walk = (x - startX) * 2; // Factor de velocidad para el arrastre
        domElementsHome.carouselTrack.scrollLeft = scrollLeft - walk;
    });
}

/**
 * Inicializa el cierre del modal de fotos al hacer clic fuera de su contenido o en el botón de cierre.
 */
function initializePhotoModalCloseHome() {
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
    domElementsHome.registerBtn.addEventListener('click', () => {
        alert('La funcionalidad de registro será implementada en la página de usuario.');
        // En una aplicación real, aquí se redirigiría a una página de registro o se abriría un modal.
    });

    domElementsHome.loginBtn.addEventListener('click', () => {
        window.location.href = 'dashboard.html'; // Redirigir a la página de usuario
    });
}


// --- 5. Inicialización de la Aplicación (Punto de Entrada) ---
document.addEventListener('DOMContentLoaded', () => {
    initializeSidebarHome();
    initializeSearchBarHome();
    loadCarouselImagesHome();
    initializeCarouselDragHome();
    initializePhotoModalCloseHome();
    initializeAuthButtonsHome();
});
