// dashboard.js - Lógica para la página de usuario (dashboard.html) de la aplicación "Plantee"

// --- 1. Datos Iniciales (Completo para el dashboard) ---
const appDataDashboard = {
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
    serviceDetails: {
        1: { title: 'Consultoría de Jardinería', description: 'Asesoramiento personalizado para el cuidado de tus plantas, selección de especies y solución de problemas.' },
        2: { title: 'Diseño de Paisajes', description: 'Creación de espacios verdes estéticos y funcionales, adaptados a tus gustos y al entorno.' },
        3: { title: 'Mantenimiento de Jardines', description: 'Servicio completo de poda, riego, abonado y limpieza para que tu jardín luzca siempre impecable.' },
        4: { title: 'Control de Plagas Orgánico', description: 'Soluciones ecológicas y seguras para combatir plagas y enfermedades que afectan a tus plantas.' },
        5: { title: 'Venta de Semillas y Plantas', description: 'Amplia variedad de semillas de calidad y plantas saludables para tu hogar o jardín.' },
        6: { title: 'Talleres y Cursos', description: 'Programas educativos sobre horticultura, bonsái, suculentas y muchas otras técnicas de jardinería.' },
    },
    searchableOptions: [
        'Mi Perfil', 'Eventos', 'Foro', 'Servicios', 'Catálogo', 'Mis Registros', // Opciones para la página de usuario
        'Orquídeas', 'Suculentas', 'Helechos', 'Rosas', 'Bonsái', 'Girasoles', 'Lavanda', 'Cactus',
        'Diseño de Jardines', 'Mantenimiento de Plantas', 'Cursos de Jardinería',
        'Iniciarse en Plantas', 'Cuidado de Plantas', 'Intercambio de Plantas', 'Plantas de Interior'
    ],
    allEvents: [
        { title: 'Charla: Cuidado Básico de Suculentas', date: '2025-07-10', time: '18:00', location: 'Centro Comunitario GreenLife', description: 'Aprende todo lo necesario para mantener tus suculentas sanas y hermosas. Desde el tipo de suelo hasta la frecuencia de riego, ¡resuelve todas tus dudas con expertos locales!' },
        { title: 'Taller: Creación de Kokedamas', date: '2025-07-15', time: '10:30', location: 'Jardín Botánico Urbano', description: 'Un taller práctico donde aprenderás la técnica japonesa de las Kokedamas. Crea tu propia esfera de musgo y lleva a casa una pieza única de arte natural. Materiales incluidos.' },
        { title: 'Feria de Intercambio de Plantas', date: '2025-07-22', time: '09:00 - 14:00', location: 'Parque Central', description: '¡Trae tus esquejes, semillas o plantas en maceta para intercambiar con otros entusiastas! Una excelente oportunidad para ampliar tu colección y hacer nuevas amistades.' },
        { title: 'Webinar: Jardinería en Apartamentos', date: '2025-08-05', time: '19:00', location: 'Online (Zoom)', description: 'Descubre cómo crear un oasis verde en tu pequeño espacio. Consejos sobre plantas adecuadas, iluminación y riego para entornos urbanos.' },
        { title: 'Visita Guiada: Orquideario Nacional', date: '2025-08-18', time: '11:00', location: 'Orquideario Nacional', description: 'Recorrido por las impresionantes colecciones de orquídeas, con explicaciones de expertos sobre su historia y cuidado.' },
        { title: 'Clase de Compostaje Casero', date: '2025-09-10', time: '16:00', location: 'Huerto Urbano "La Huerta Feliz"', description: 'Aprende a transformar tus residuos orgánicos en abono rico para tus plantas, de forma sencilla y ecológica.' },
    ],
    filteredEvents: [],
    isLoggedIn: true, // Asumimos que el usuario está logueado en el dashboard
    currentCalendarMonth: new Date().getMonth(),
    currentCalendarYear: new Date().getFullYear(),
    myAddedEvents: [], // Para "Mis Registros"
    myAddedPlants: [], // Para "Mis Registros"
};

// Inicializar filteredEvents con todos los eventos al cargar.
appDataDashboard.filteredEvents = [...appDataDashboard.allEvents];

// --- 2. Referencias a Elementos del DOM ---
const domElementsDashboard = {
    // Sidebar y menú
    menuToggle: document.getElementById('menu-toggle'),
    sidebar: document.getElementById('sidebar'),
    mainContent: document.querySelector('main'),

    // Barra de búsqueda
    searchIcon: document.getElementById('search-icon'),
    searchBar: document.getElementById('search-bar'),
    searchInput: document.getElementById('search-input'),
    searchSuggestions: document.getElementById('search-suggestions'),

    // Eventos
    eventListView: document.getElementById('vista-lista'),
    eventCalendarView: document.getElementById('vista-calendario'),
    currentMonthYearDisplay: document.getElementById('current-month-year'),
    calendarBody: document.getElementById('calendar-body'),
    btnListView: document.getElementById('btn-vista-lista'),
    btnCalendarView: document.getElementById('btn-vista-calendario'),
    dateRangeStartInput: document.getElementById('date-range-start'),
    dateRangeEndInput: document.getElementById('date-range-end'),
    addEventForm: document.getElementById('add-event-form'),
    eventFormMessage: document.getElementById('event-form-message'),
    myEventsList: document.getElementById('my-events-list'),

    // Foro
    forumInput: document.getElementById('forum-input'),
    chatHistory: document.getElementById('chat-history'),
    loginMessage: document.getElementById('login-message'),

    // Servicios (Flor interactiva)
    flowerContainer: document.querySelector('.flor'),
    petals: document.querySelectorAll('.petalo'),
    flowerCenterButton: document.getElementById('flor-center-btn'),
    fullServiceInfoPanel: document.getElementById('full-service-info'),

    // Catálogo (Carrusel)
    carouselTrack: document.getElementById('carousel-track'),
    carouselPrevBtn: document.getElementById('carousel-prev-btn'),
    carouselNextBtn: document.getElementById('carousel-next-btn'),
    photoModalOverlay: document.getElementById('photo-modal-overlay'),
    photoModalContent: document.getElementById('photo-modal-content'),
    modalImage: null,
    modalTitle: null,
    modalDescription: null,
    photoModalClose: null,

    // Añadir Planta y Mis Registros
    addPlantForm: document.getElementById('add-plant-form'),
    plantFormMessage: document.getElementById('plant-form-message'),
    myPlantsGrid: document.getElementById('my-plants-grid'),

    // Botón de cerrar sesión
    logoutBtn: document.getElementById('logout-btn'),
};

// Asignar referencias a elementos dentro del modal de fotos una vez que photoModalContent esté disponible.
domElementsDashboard.modalImage = domElementsDashboard.photoModalContent.querySelector('img');
domElementsDashboard.modalTitle = domElementsDashboard.photoModalContent.querySelector('h3');
domElementsDashboard.modalDescription = domElementsDashboard.photoModalContent.querySelector('p');
domElementsDashboard.photoModalClose = domElementsDashboard.photoModalContent.querySelector('.photo-modal-close');


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
 * Muestra un mensaje temporal en un elemento del formulario.
 * @param {HTMLElement} element - El elemento donde se mostrará el mensaje.
 * @param {string} message - El texto del mensaje.
 * @param {string} type - Clase CSS para el tipo de mensaje (ej. 'success', 'error').
 */
function showFormMessage(element, message, type) {
    element.textContent = message;
    element.className = `form-message ${type}`;
    toggleVisibility(element, true);

    setTimeout(() => {
        toggleVisibility(element, false);
    }, 3000);
}

/**
 * Función para desplazar a la sección correspondiente.
 * @param {string} query - El texto de la búsqueda o nombre de la sección.
 */
function scrollToSectionDashboard(query) {
    let targetId = '';
    switch(query.toLowerCase()) {
        case 'mi perfil': targetId = 'mi-perfil'; break;
        case 'eventos': targetId = 'eventos'; break;
        case 'foro': targetId = 'foro'; break;
        case 'servicios': targetId = 'servicios'; break;
        case 'catálogo': targetId = 'catalogo'; break;
        case 'mis registros': targetId = 'mis-registros'; break;
        case 'ir a inicio': window.location.href = 'index.html'; return; // Redirigir a index.html
        default:
            // Lógica de búsqueda más compleja dentro del dashboard
            let foundSection = false;
            for (const item of appDataDashboard.catalogImages) {
                if (item.name.toLowerCase().includes(query.toLowerCase())) {
                    targetId = 'catalogo';
                    foundSection = true;
                    break;
                }
            }
            if (!foundSection) {
                 for (const event of appDataDashboard.allEvents) {
                    if (event.title.toLowerCase().includes(query.toLowerCase()) || event.description.toLowerCase().includes(query.toLowerCase())) {
                        targetId = 'eventos';
                        foundSection = true;
                        break;
                    }
                }
            }
            if (!foundSection) {
                alert(`No se encontró una sección o elemento relevante para "${query}".`);
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
function initializeSidebarDashboard() {
    domElementsDashboard.menuToggle.addEventListener('click', () => {
        domElementsDashboard.sidebar.classList.toggle('active');
        domElementsDashboard.menuToggle.classList.toggle('hidden');
        document.body.classList.toggle('sidebar-active');
    });

    domElementsDashboard.sidebar.addEventListener('click', (event) => {
        if (event.target.closest('.nav-link')) {
            setTimeout(() => {
                domElementsDashboard.sidebar.classList.remove('active');
                domElementsDashboard.menuToggle.classList.remove('hidden');
                document.body.classList.remove('sidebar-active');
            }, 100);
        } else if (!domElementsDashboard.sidebar.contains(event.target)) {
            domElementsDashboard.sidebar.classList.remove('active');
            domElementsDashboard.menuToggle.classList.remove('hidden');
            document.body.classList.remove('sidebar-active');
        }
    });
}

/**
 * Inicializa la funcionalidad de la barra de búsqueda.
 */
function initializeSearchBarDashboard() {
    domElementsDashboard.searchIcon.addEventListener('click', () => {
        const isSearchBarVisible = domElementsDashboard.searchBar.classList.contains('active');
        domElementsDashboard.searchBar.classList.toggle('active');

        if (!isSearchBarVisible) {
            domElementsDashboard.searchInput.focus();
        } else {
            toggleVisibility(domElementsDashboard.searchSuggestions, false);
            domElementsDashboard.searchInput.value = '';
        }
    });

    domElementsDashboard.searchInput.addEventListener('input', () => {
        const query = domElementsDashboard.searchInput.value.toLowerCase();
        domElementsDashboard.searchSuggestions.innerHTML = '';

        if (query.length > 0) {
            const filteredSuggestions = appDataDashboard.searchableOptions.filter(option =>
                option.toLowerCase().includes(query)
            ).slice(0, 5);

            if (filteredSuggestions.length > 0) {
                filteredSuggestions.forEach(suggestion => {
                    const div = document.createElement('div');
                    div.textContent = suggestion;
                    div.addEventListener('click', () => {
                        domElementsDashboard.searchInput.value = suggestion;
                        toggleVisibility(domElementsDashboard.searchSuggestions, false);
                        scrollToSectionDashboard(suggestion);
                        domElementsDashboard.searchBar.classList.remove('active');
                    });
                    domElementsDashboard.searchSuggestions.appendChild(div);
                });
                toggleVisibility(domElementsDashboard.searchSuggestions, true);
            } else {
                toggleVisibility(domElementsDashboard.searchSuggestions, false);
            }
        } else {
            toggleVisibility(domElementsDashboard.searchSuggestions, false);
        }
    });

    document.addEventListener('click', (event) => {
        if (!domElementsDashboard.searchBar.contains(event.target) && event.target !== domElementsDashboard.searchIcon) {
            toggleVisibility(domElementsDashboard.searchSuggestions, false);
            domElementsDashboard.searchBar.classList.remove('active');
            domElementsDashboard.searchInput.value = '';
        }
    });
}

/**
 * Renderiza la lista de eventos en la vista de lista.
 * @param {Array<Object>} eventsToRender - Array de objetos de evento a renderizar.
 */
function renderEventListDashboard(eventsToRender) {
    domElementsDashboard.eventListView.innerHTML = '';
    if (eventsToRender.length === 0) {
        domElementsDashboard.eventListView.innerHTML = '<p style="text-align: center; margin-top: 20px;">No hay eventos disponibles para las fechas seleccionadas.</p>';
        return;
    }
    eventsToRender.sort((a, b) => new Date(a.date) - new Date(b.date));
    eventsToRender.forEach(event => {
        const eventItem = document.createElement('div');
        eventItem.classList.add('event-item');
        eventItem.innerHTML = `
            <h3>${event.title}</h3>
            <p class="event-meta">Fecha: ${new Date(event.date).toLocaleDateString('es-ES', { year: 'numeric', month: 'long', day: 'numeric' })} | Hora: ${event.time} | Lugar: ${event.location}</p>
            <p>${event.description}</p>
        `;
        domElementsDashboard.eventListView.appendChild(eventItem);
    });
}

/**
 * Renderiza el calendario de eventos para un mes y año dados.
 * @param {number} month - El mes (0-11).
 * @param {number} year - El año.
 * @param {Array<Object>} eventsToRender - Array de objetos de evento a mostrar en el calendario.
 */
function renderCalendarDashboard(month, year, eventsToRender) {
    domElementsDashboard.calendarBody.innerHTML = '';
    domElementsDashboard.currentMonthYearDisplay.textContent = new Date(year, month).toLocaleDateString('es-ES', { month: 'long', year: 'numeric' });

    const firstDay = new Date(year, month, 1).getDay();
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const today = new Date();
    const todayDate = today.getDate();
    const todayMonth = today.getMonth();
    const todayYear = today.getFullYear();

    let date = 1;
    for (let i = 0; i < 6; i++) {
        const row = document.createElement('tr');
        for (let j = 0; j < 7; j++) {
            const cell = document.createElement('td');
            if (i === 0 && j < firstDay) {
                cell.textContent = '';
            } else if (date > daysInMonth) {
                cell.textContent = '';
            } else {
                cell.innerHTML = `<strong>${date}</strong>`;
                const currentCellDate = new Date(year, month, date);

                if (date === todayDate && month === todayMonth && year === todayYear) {
                    cell.classList.add('today');
                }

                const eventsOnThisDay = eventsToRender.filter(event =>
                    new Date(event.date).toDateString() === currentCellDate.toDateString()
                );
                if (eventsOnThisDay.length > 0) {
                    cell.classList.add('has-event');
                    eventsOnThisDay.forEach(event => {
                        const eventBadge = document.createElement('span');
                        eventBadge.classList.add('event-badge');
                        eventBadge.textContent = event.title;
                        cell.appendChild(eventBadge);
                    });
                }
                date++;
            }
            row.appendChild(cell);
        }
        domElementsDashboard.calendarBody.appendChild(row);
        if (date > daysInMonth) break;
    }
}

/**
 * Cambia la vista de eventos entre lista y calendario.
 * @param {string} viewType - 'lista' o 'calendario'.
 * @param {HTMLElement} activeButton - El botón que se activa.
 */
function switchEventViewDashboard(viewType, activeButton) {
    domElementsDashboard.eventListView.classList.add('hidden');
    domElementsDashboard.eventCalendarView.classList.add('hidden');
    domElementsDashboard.btnListView.classList.remove('active');
    domElementsDashboard.btnCalendarView.classList.remove('active');

    document.getElementById(`vista-${viewType}`).classList.remove('hidden');
    activeButton.classList.add('active');

    if (viewType === 'calendario') {
        renderCalendarDashboard(appDataDashboard.currentCalendarMonth, appDataDashboard.currentCalendarYear, appDataDashboard.filteredEvents);
    } else {
        renderEventListDashboard(appDataDashboard.filteredEvents);
    }
}

/**
 * Filtra los eventos por rango de fechas y actualiza la vista.
 */
function filterAndRenderEventsDashboard() {
    const startDate = domElementsDashboard.dateRangeStartInput.value ? new Date(domElementsDashboard.dateRangeStartInput.value) : null;
    const endDate = domElementsDashboard.dateRangeEndInput.value ? new Date(domElementsDashboard.dateRangeEndInput.value) : null;

    appDataDashboard.filteredEvents = appDataDashboard.allEvents.filter(event => {
        const eventDate = new Date(event.date);
        let isWithinRange = true;

        if (startDate && eventDate < startDate) {
            isWithinRange = false;
        }
        if (endDate && eventDate > new Date(endDate.setDate(endDate.getDate() + 1))) {
            isWithinRange = false;
        }
        return isWithinRange;
    });

    if (!domElementsDashboard.eventListView.classList.contains('hidden')) {
        renderEventListDashboard(appDataDashboard.filteredEvents);
    }
    if (!domElementsDashboard.eventCalendarView.classList.contains('hidden')) {
        renderCalendarDashboard(appDataDashboard.currentCalendarMonth, appDataDashboard.currentCalendarYear, appDataDashboard.filteredEvents);
    }
}

/**
 * Añade un evento a la lista de "Mis Registros".
 * @param {Object} event - El objeto evento a añadir.
 */
function addEventToMyRecords(event) {
    const listItem = document.createElement('li');
    listItem.innerHTML = `
        <strong>${event.title}</strong><br>
        ${new Date(event.date).toLocaleDateString('es-ES')} - ${event.location}
    `;
    domElementsDashboard.myEventsList.appendChild(listItem);
}

/**
 * Inicializa la funcionalidad del foro.
 */
function initializeForumDashboard() {
    domElementsDashboard.forumInput.addEventListener('keydown', (event) => {
        if (event.key === 'Enter' && !event.shiftKey) {
            event.preventDefault();
            const message = domElementsDashboard.forumInput.value.trim();

            if (message === '') {
                return;
            }

            if (appDataDashboard.isLoggedIn) {
                const newMessageDiv = document.createElement('div');
                newMessageDiv.classList.add('chat-message', 'user-message');
                newMessageDiv.innerHTML = `<strong>Tú:</strong> ${message}`;
                domElementsDashboard.chatHistory.appendChild(newMessageDiv);
                domElementsDashboard.chatHistory.scrollTop = domElementsDashboard.chatHistory.scrollHeight;
                domElementsDashboard.forumInput.value = '';
            } else {
                domElementsDashboard.loginMessage.classList.add('show');
                setTimeout(() => {
                    domElementsDashboard.loginMessage.classList.remove('show');
                }, 3000);
            }
        }
    });
}

/**
 * Inicializa la funcionalidad de la flor de servicios.
 */
function initializeServicesFlowerDashboard() {
    domElementsDashboard.flowerCenterButton.addEventListener('click', () => {
        domElementsDashboard.flowerContainer.style.transform = 'translate(0, 0) scale(1)';
        domElementsDashboard.flowerContainer.classList.remove('petal-selected');
        domElementsDashboard.petals.forEach(p => p.classList.remove('selected'));
        toggleVisibility(domElementsDashboard.fullServiceInfoPanel, false);
    });

    domElementsDashboard.petals.forEach(petal => {
        petal.addEventListener('click', () => {
            const serviceId = petal.dataset.serviceId;
            const detail = appDataDashboard.serviceDetails[serviceId];

            domElementsDashboard.petals.forEach(p => p.classList.remove('selected'));
            petal.classList.add('selected');

            const florRect = domElementsDashboard.flowerContainer.getBoundingClientRect();
            const petalRect = petal.getBoundingClientRect();

            const deltaX = (petalRect.left + petalRect.width / 2) - (florRect.left + florRect.width / 2);
            const deltaY = (petalRect.top + petalRect.height / 2) - (florRect.top + florRect.height / 2);

            const moveFactorX = -deltaX / (window.innerWidth > 768 ? 1.5 : 1.2);
            const moveFactorY = -deltaY / (window.innerWidth > 768 ? 1.5 : 1.2);

            domElementsDashboard.flowerContainer.style.transform = `translate(${moveFactorX}px, ${moveFactorY}px) scale(0.9)`;
            domElementsDashboard.flowerContainer.classList.add('petal-selected');

            domElementsDashboard.fullServiceInfoPanel.querySelector('h3').textContent = detail.title;
            domElementsDashboard.fullServiceInfoPanel.querySelector('p').textContent = detail.description;
            toggleVisibility(domElementsDashboard.fullServiceInfoPanel, true);
        });
    });
}

/**
 * Carga y renderiza las imágenes del catálogo en el carrusel.
 */
function loadCarouselImagesDashboard() {
    domElementsDashboard.carouselTrack.innerHTML = '';
    appDataDashboard.catalogImages.forEach((image, index) => {
        const item = document.createElement('div');
        item.classList.add('carousel-item');
        item.innerHTML = `
            <img src="${image.src}" alt="${image.name}" data-index="${index}">
            <div class="image-info">
                <strong>${image.name}</strong>
                <span>${image.date}</span>
            </div>
        `;
        domElementsDashboard.carouselTrack.appendChild(item);
    });
    addCarouselImageClickListenersDashboard();
}

/**
 * Añade listeners de clic a las imágenes del carrusel para abrir el modal.
 */
function addCarouselImageClickListenersDashboard() {
    document.querySelectorAll('.carousel-item img').forEach(img => {
        img.removeEventListener('click', openPhotoModalDashboard);
        img.addEventListener('click', openPhotoModalDashboard);
    });
}

/**
 * Abre el modal de fotos con la información de la imagen clicada.
 * @param {Event} e - El evento de clic.
 */
function openPhotoModalDashboard(e) {
    const clickedIndex = parseInt(e.target.dataset.index);
    const photo = appDataDashboard.catalogImages[clickedIndex];
    domElementsDashboard.modalImage.src = photo.src;
    domElementsDashboard.modalImage.alt = photo.name;
    domElementsDashboard.modalTitle.textContent = photo.name;
    domElementsDashboard.modalDescription.textContent = photo.description;
    domElementsDashboard.photoModalOverlay.classList.add('visible');
}

/**
 * Inicializa la funcionalidad de arrastre del carrusel.
 */
function initializeCarouselDragDashboard() {
    let isDragging = false;
    let startX;
    let scrollLeft;

    domElementsDashboard.carouselTrack.addEventListener('mousedown', (e) => {
        isDragging = true;
        domElementsDashboard.carouselTrack.classList.add('dragging');
        startX = e.pageX - domElementsDashboard.carouselTrack.offsetLeft;
        scrollLeft = domElementsDashboard.carouselTrack.scrollLeft;
    });

    domElementsDashboard.carouselTrack.addEventListener('mouseleave', () => {
        isDragging = false;
        domElementsDashboard.carouselTrack.classList.remove('dragging');
    });

    domElementsDashboard.carouselTrack.addEventListener('mouseup', () => {
        isDragging = false;
        domElementsDashboard.carouselTrack.classList.remove('dragging');
    });

    domElementsDashboard.carouselTrack.addEventListener('mousemove', (e) => {
        if (!isDragging) return;
        e.preventDefault();
        const x = e.pageX - domElementsDashboard.carouselTrack.offsetLeft;
        const walk = (x - startX) * 1.5;
        domElementsDashboard.carouselTrack.scrollLeft = scrollLeft - walk;
    });
}

/**
 * Inicializa la navegación con botones del carrusel.
 */
function initializeCarouselNavButtonsDashboard() {
    domElementsDashboard.carouselPrevBtn.addEventListener('click', () => {
        domElementsDashboard.carouselTrack.scrollBy({
            left: -domElementsDashboard.carouselTrack.offsetWidth / 2,
            behavior: 'smooth'
        });
    });

    domElementsDashboard.carouselNextBtn.addEventListener('click', () => {
        domElementsDashboard.carouselTrack.scrollBy({
            left: domElementsDashboard.carouselTrack.offsetWidth / 2,
            behavior: 'smooth'
        });
    });
}

/**
 * Inicializa el cierre del modal de fotos.
 */
function initializePhotoModalCloseDashboard() {
    domElementsDashboard.photoModalOverlay.addEventListener('click', (event) => {
        if (event.target === domElementsDashboard.photoModalOverlay || event.target === domElementsDashboard.photoModalClose) {
            domElementsDashboard.photoModalOverlay.classList.remove('visible');
        }
    });
    domElementsDashboard.photoModalClose.addEventListener('click', () => {
        domElementsDashboard.photoModalOverlay.classList.remove('visible');
    });
}

/**
 * Añade una planta a la sección "Mis Registros".
 * @param {Object} plant - El objeto planta a añadir.
 */
function addPlantToMyRecords(plant) {
    const plantCard = document.createElement('div');
    plantCard.classList.add('carousel-item'); // Reutilizar el estilo del carrusel
    plantCard.style.marginRight = '0'; // Eliminar margen extra para la grid
    plantCard.style.marginBottom = '15px'; // Espacio entre elementos en la grid
    plantCard.innerHTML = `
        <img src="${plant.src}" alt="${plant.name}">
        <div class="image-info">
            <strong>${plant.name}</strong>
            <span>${plant.date}</span>
        </div>
    `;
    domElementsDashboard.myPlantsGrid.appendChild(plantCard);
}

/**
 * Inicializa la lógica de los botones de autenticación para el dashboard.
 */
function initializeAuthButtonsDashboard() {
    domElementsDashboard.logoutBtn.addEventListener('click', () => {
        appDataDashboard.isLoggedIn = false; // Simular cierre de sesión
        alert('Has cerrado sesión. Redirigiendo a la página de inicio.');
        window.location.href = 'index.html'; // Redirigir a la página de inicio
    });
}

// --- 5. Inicialización de la Aplicación (Punto de Entrada) ---
document.addEventListener('DOMContentLoaded', () => {
    initializeSidebarDashboard();
    initializeSearchBarDashboard();
    initializeForumDashboard();
    initializeServicesFlowerDashboard();
    loadCarouselImagesDashboard();
    initializeCarouselDragDashboard();
    initializeCarouselNavButtonsDashboard();
    initializePhotoModalCloseDashboard();
    initializeAuthButtonsDashboard();

    // Event listeners para los botones de vista de eventos
    domElementsDashboard.btnListView.addEventListener('click', () => switchEventViewDashboard('lista', domElementsDashboard.btnListView));
    domElementsDashboard.btnCalendarView.addEventListener('click', () => switchEventViewDashboard('calendario', domElementsDashboard.btnCalendarView));

    // Event listeners para los filtros de fecha de eventos
    domElementsDashboard.dateRangeStartInput.addEventListener('change', filterAndRenderEventsDashboard);
    domElementsDashboard.dateRangeEndInput.addEventListener('change', filterAndRenderEventsDashboard);
    document.getElementById('btn-apply-filter').addEventListener('click', filterAndRenderEventsDashboard);

    // Event listener para el formulario de añadir evento
    domElementsDashboard.addEventForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const title = document.getElementById('event-title').value;
        const date = document.getElementById('event-date').value;
        const time = document.getElementById('event-time').value;
        const location = document.getElementById('event-location').value;
        const description = document.getElementById('event-description').value;

        if (title && date && time && location && description) {
            const newEvent = { title, date, time, location, description };
            appDataDashboard.allEvents.push(newEvent); // Añadir al array principal de eventos
            appDataDashboard.myAddedEvents.push(newEvent); // Añadir a los eventos del usuario
            filterAndRenderEventsDashboard(); // Re-filtrar y renderizar
            domElementsDashboard.addEventForm.reset();
            showFormMessage(domElementsDashboard.eventFormMessage, 'Evento añadido con éxito!', 'success');
            addEventToMyRecords(newEvent); // Añadir a "Mis Registros"
        } else {
            showFormMessage(domElementsDashboard.eventFormMessage, 'Por favor, completa todos los campos.', 'error');
        }
    });

    // Event listener para el formulario de añadir planta
    domElementsDashboard.addPlantForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const name = document.getElementById('plant-name').value;
        const date = document.getElementById('plant-date').value;
        const description = document.getElementById('plant-description').value;
        const imageFile = document.getElementById('plant-image').files[0];

        if (name && date && description && imageFile) {
            const imageUrl = URL.createObjectURL(imageFile); // Simular URL de imagen

            const newPlant = { src: imageUrl, name, date, description };
            appDataDashboard.catalogImages.push(newPlant); // Añadir al array del catálogo
            appDataDashboard.myAddedPlants.push(newPlant); // Añadir a las plantas del usuario
            loadCarouselImagesDashboard(); // Recargar el carrusel para mostrar la nueva planta
            domElementsDashboard.addPlantForm.reset();
            showFormMessage(domElementsDashboard.plantFormMessage, 'Planta añadida al catálogo (demostración)!', 'success');
            addPlantToMyRecords(newPlant); // Añadir a "Mis Registros"
        } else {
            showFormMessage(domElementsDashboard.plantFormMessage, 'Por favor, completa todos los campos y sube una imagen.', 'error');
        }
    });

    // Renderizar la vista de lista de eventos por defecto al cargar el dashboard
    renderEventListDashboard(appDataDashboard.filteredEvents);

    // Si la URL tiene un hash, desplazar a esa sección
    if (window.location.hash) {
        const targetId = window.location.hash.substring(1); // Eliminar el '#'
        const targetElement = document.getElementById(targetId);
        if (targetElement) {
            setTimeout(() => { // Pequeño retraso para que el layout se asiente
                window.scrollTo({
                    top: targetElement.offsetTop - 80, // Ajustar por la altura del header fijo
                    behavior: 'smooth'
                });
            }, 100);
        }
    }
});
