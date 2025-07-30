let catalogImages = [
            { src: 'https://via.placeholder.com/400x300/C8E6C9/2F6C35?text=Orquidea', name: 'Orquídea Phalaenopsis', date: '2024-03-15', description: 'Una hermosa orquídea con flores vibrantes, ideal para interiores luminosos. Requiere riego moderado y alta humedad ambiental.' },
            { src: 'https://via.placeholder.com/400x300/D4EDDA/2F6C35?text=Echeveria', name: 'Echeveria Elegans', date: '2023-11-20', description: 'Suculenta de fácil cuidado, perfecta para principiantes. Necesita mucha luz solar directa y muy poco riego.' },
            { src: 'https://via.placeholder.com/400x300/E6F0E6/2F6C35?text=Helecho', name: 'Helecho Espada', date: '2024-01-10', description: 'Popular por su frondoso follaje, ideal para crear un ambiente tropical. Prefiere sombra parcial y alta humedad.' },
            { src: 'https://via.placeholder.com/400x300/F5EFE6/2F6C35?text=Rosa', name: 'Rosa Roja Clásica', date: '2023-07-01', description: 'La reina del jardín, con sus pétalos suaves y fragancia inconfundible. Requiere sol pleno y poda regular para florecer.' },
            { src: 'https://via.placeholder.com/400x300/FDF9F3/2F6C35?text=Bonsai', name: 'Bonsái Ficus', date: '2024-02-28', description: 'Un arte milenario que convierte árboles en miniatura. Este Ficus es ideal para empezar en el mundo del bonsái, resistente y adaptable.' },
            { src: 'https://via.placeholder.com/400x300/C8E6C9/6FA878?text=Girasol', name: 'Girasol Gigante', date: '2023-09-05', description: 'Flores grandes y alegres que siguen el sol. Perfectos para dar un toque vibrante a tu jardín y atraer polinizadores.' },
            { src: 'https://via.placeholder.com/400x300/D4EDDA/6FA878?text=Lavanda', name: 'Lavanda Angustifolia', date: '2024-04-22', description: 'Planta aromática con hermosas flores moradas, conocida por sus propiedades relajantes y su uso en aceites esenciales.' },
            { src: 'https://via.placeholder.com/400x300/E6F0E6/6FA878?text=Cactus', name: 'Cactus San Pedro', date: '2023-10-10', description: 'Cactus columnar de crecimiento rápido, muy resistente y de bajo mantenimiento. Ideal para jardines desérticos o macetas grandes.' },
        ];

        const serviceDetails = {
            1: { title: 'Consultoría de Jardinería', description: 'Asesoramiento personalizado para el cuidado de tus plantas, selección de especies y solución de problemas.' },
            2: { title: 'Diseño de Paisajes', description: 'Creación de espacios verdes estéticos y funcionales, adaptados a tus gustos y al entorno.' },
            3: { title: 'Mantenimiento de Jardines', description: 'Servicio completo de poda, riego, abonado y limpieza para que tu jardín luzca siempre impecable.' },
            4: { title: 'Control de Plagas Orgánico', description: 'Soluciones ecológicas y seguras para combatir plagas y enfermedades que afectan a tus plantas.' },
            5: { title: 'Venta de Semillas y Plantas', description: 'Amplia variedad de semillas de calidad y plantas saludables para tu hogar o jardín.' },
            6: { title: 'Talleres y Cursos', description: 'Programas educativos sobre horticultura, bonsái, suculentas y muchas otras técnicas de jardinería.' },
        };

        const searchableOptions = [
            'Inicio', 'Eventos', 'Foro', 'Servicios', 'Catálogo', 'Contáctanos',
            'Orquídeas', 'Suculentas', 'Helechos', 'Rosas', 'Bonsái', 'Girasoles', 'Lavanda', 'Cactus',
            'Diseño de Jardines', 'Mantenimiento de Plantas', 'Cursos de Jardinería',
            'Iniciarse en Plantas', 'Cuidado de Plantas', 'Intercambio de Plantas', 'Plantas de Interior'
        ];

        let allEvents = [
            { title: 'Charla: Cuidado Básico de Suculentas', date: '2025-07-10', time: '18:00', location: 'Centro Comunitario GreenLife', description: 'Aprende todo lo necesario para mantener tus suculentas sanas y hermosas. Desde el tipo de suelo hasta la frecuencia de riego, ¡resuelve todas tus dudas con expertos locales!' },
            { title: 'Taller: Creación de Kokedamas', date: '2025-07-15', time: '10:30', location: 'Jardín Botánico Urbano', description: 'Un taller práctico donde aprenderás la técnica japonesa de las Kokedamas. Crea tu propia esfera de musgo y lleva a casa una pieza única de arte natural. Materiales incluidos.' },
            { title: 'Feria de Intercambio de Plantas', date: '2025-07-22', time: '09:00 - 14:00', location: 'Parque Central', description: '¡Trae tus esquejes, semillas o plantas en maceta para intercambiar con otros entusiastas! Una excelente oportunidad para ampliar tu colección y hacer nuevas amistades.' },
            { title: 'Webinar: Jardinería en Apartamentos', date: '2025-08-05', time: '19:00', location: 'Online (Zoom)', description: 'Descubre cómo crear un oasis verde en tu pequeño espacio. Consejos sobre plantas adecuadas, iluminación y riego para entornos urbanos.' },
            { title: 'Visita Guiada: Orquideario Nacional', date: '2025-08-18', time: '11:00', location: 'Orquideario Nacional', description: 'Recorrido por las impresionantes colecciones de orquídeas, con explicaciones de expertos sobre su historia y cuidado.' },
            { title: 'Clase de Compostaje Casero', date: '2025-09-10', time: '16:00', location: 'Huerto Urbano "La Huerta Feliz"', description: 'Aprende a transformar tus residuos orgánicos en abono rico para tus plantas, de forma sencilla y ecológica.' },
        ];

        let filteredEvents = [...allEvents];


        // --- Cookbook: Encapsulación de funcionalidades UI ---
        function initializeSidebar() {
            const menuToggle = document.getElementById('menu-toggle');
            const sidebar = document.getElementById('sidebar');

            menuToggle.addEventListener('click', () => {
                sidebar.classList.toggle('active');
                menuToggle.style.display = 'none';
                document.body.classList.toggle('sidebar-active');
            });

            sidebar.addEventListener('click', (event) => {
                if (!event.target.closest('.menu-btn')) {
                    sidebar.classList.remove('active');
                    menuToggle.style.display = 'flex';
                    document.body.classList.remove('sidebar-active');
                }
            });
        }

        function initializeSearchBar() {
            const searchIcon = document.getElementById('search-icon');
            const searchBar = document.getElementById('search-bar');
            const searchInput = document.getElementById('search-input');
            const searchSuggestions = document.getElementById('search-suggestions');

            searchIcon.addEventListener('click', () => {
                searchBar.style.display = searchBar.style.display === 'block' ? 'none' : 'block';
                if (searchBar.style.display === 'block') {
                    searchInput.focus();
                } else {
                    searchSuggestions.style.display = 'none';
                    searchInput.value = '';
                }
            });

            searchInput.addEventListener('input', () => {
                const query = searchInput.value.toLowerCase();
                searchSuggestions.innerHTML = '';

                if (query.length > 0) {
                    const filteredSuggestions = searchableOptions.filter(option =>
                        option.toLowerCase().includes(query)
                    ).slice(0, 5);

                    if (filteredSuggestions.length > 0) {
                        filteredSuggestions.forEach(suggestion => {
                            const div = document.createElement('div');
                            div.textContent = suggestion;
                            div.addEventListener('click', () => {
                                searchInput.value = suggestion;
                                searchSuggestions.style.display = 'none';
                            });
                            searchSuggestions.appendChild(div);
                        });
                        searchSuggestions.style.display = 'block';
                    } else {
                        searchSuggestions.style.display = 'none';
                    }
                } else {
                    searchSuggestions.style.display = 'none';
                }
            });

            document.addEventListener('click', (event) => {
                if (!searchBar.contains(event.target) && event.target !== searchIcon) {
                    searchSuggestions.style.display = 'none';
                }
            });
        }

        const vistaLista = document.getElementById('vista-lista');
        const vistaCalendario = document.getElementById('vista-calendario');
        const currentMonthYear = document.getElementById('current-month-year');
        const calendarBody = document.getElementById('calendar-body');
        const btnVistaLista = document.getElementById('btn-vista-lista');
        const btnVistaCalendario = document.getElementById('btn-vista-calendario');
        const dateRangeStart = document.getElementById('date-range-start');
        const dateRangeEnd = document.getElementById('date-range-end');

        let currentMonth = new Date().getMonth();
        let currentYear = new Date().getFullYear();

        function renderEventsList(eventsToRender) {
            vistaLista.innerHTML = '';
            if (eventsToRender.length === 0) {
                vistaLista.innerHTML = '<p style="text-align: center; margin-top: 20px;">No hay eventos disponibles para las fechas seleccionadas.</p>';
                return;
            }
            // Pipeline: Etapa de Ordenamiento
            eventsToRender.sort((a, b) => new Date(a.date) - new Date(b.date));
            eventsToRender.forEach(event => {
                const eventItem = document.createElement('div');
                eventItem.classList.add('event-item');
                eventItem.innerHTML = `
                    <h3>${event.title}</h3>
                    <p class="event-meta">Fecha: ${new Date(event.date).toLocaleDateString('es-ES', { year: 'numeric', month: 'long', day: 'numeric' })} | Hora: ${event.time} | Lugar: ${event.location}</p>
                    <p>${event.description}</p>
                `;
                vistaLista.appendChild(eventItem);
            });
        }

        function renderCalendar(month, year, eventsToRender) {
            calendarBody.innerHTML = '';
            currentMonthYear.textContent = new Date(year, month).toLocaleDateString('es-ES', { month: 'long', year: 'numeric' });

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
                calendarBody.appendChild(row);
                if (date > daysInMonth) break;
            }
        }

        function mostrarVista(vista, button) {
            vistaLista.classList.add('hidden');
            vistaCalendario.classList.add('hidden');
            btnVistaLista.classList.remove('active');
            btnVistaCalendario.classList.remove('active');

            document.getElementById('vista-' + vista).classList.remove('hidden');
            button.classList.add('active');

            if (vista === 'calendario') {
                renderCalendar(currentMonth, currentYear, filteredEvents);
            }
            filterEventsByDate();
        }

        // Pipeline: Etapa de Filtrado de Eventos
        function getFilteredEvents(startDate, endDate) {
            return allEvents.filter(event => {
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
        }

        function filterEventsByDate() {
            const startDate = dateRangeStart.value ? new Date(dateRangeStart.value) : null;
            const endDate = dateRangeEnd.value ? new Date(dateRangeEnd.value) : null;

            filteredEvents = getFilteredEvents(startDate, endDate); // Uso de la función de pipeline

            if (!vistaLista.classList.contains('hidden')) {
                renderEventsList(filteredEvents);
            }
            if (!vistaCalendario.classList.contains('hidden')) {
                renderCalendar(currentMonth, currentYear, filteredEvents);
            }
        }

        // --- Foro ---
        const forumInput = document.getElementById('forum-input');
        const chatHistory = document.getElementById('chat-history');
        const loginMessage = document.getElementById('login-message');
        let isLoggedIn = false; // Simulación de estado de login

        forumInput.addEventListener('keydown', (event) => {
            if (event.key === 'Enter') {
                event.preventDefault();
                const message = forumInput.value.trim();
                if (message === '') return;

                if (isLoggedIn) {
                    const newMessageDiv = document.createElement('div');
                    newMessageDiv.classList.add('chat-message', 'user-message');
                    newMessageDiv.innerHTML = `<strong>Tú:</strong> ${message}`;
                    chatHistory.appendChild(newMessageDiv);
                    chatHistory.scrollTop = chatHistory.scrollHeight;
                    forumInput.value = '';
                } else {
                    // Error/Exception Handling: Mensaje de usuario si no está logueado
                    loginMessage.classList.add('show');
                    setTimeout(() => {
                        loginMessage.classList.remove('show');
                    }, 3000);
                }
            }
        });

        const flor = document.querySelector('.flor');
        const petalos = document.querySelectorAll('.petalo');
        const florCenterBtn = document.getElementById('flor-center-btn');
        const fullServiceInfo = document.getElementById('full-service-info');

        florCenterBtn.addEventListener('click', () => {
            flor.style.transform = 'translate(0, 0)';
            flor.classList.remove('petal-selected');
            petalos.forEach(p => p.classList.remove('selected'));
            fullServiceInfo.style.display = 'none';
        });

        petalos.forEach(petal => {
            petal.addEventListener('click', () => {
                const serviceId = petal.dataset.serviceId;
                const detail = serviceDetails[serviceId];

                const petalRect = petal.getBoundingClientRect();
                const florRect = flor.getBoundingClientRect();

                const petalX = petalRect.left + petalRect.width / 2 - florRect.left;
                const petalY = petalRect.top + petalRect.height / 2 - florRect.top;

                const centerX = florRect.width / 2;
                const centerY = florRect.height / 2;

                const offsetX = (centerX - petalX) * 0.8;
                const offsetY = (centerY - petalY) * 0.8;

                flor.style.transform = `translate(${offsetX}px, ${offsetY}px)`;
                flor.classList.add('petal-selected');

                petalos.forEach(p => p.classList.remove('selected'));
                petal.classList.add('selected');

                fullServiceInfo.querySelector('h3').textContent = detail.title;
                fullServiceInfo.querySelector('p').textContent = detail.description;
                fullServiceInfo.style.display = 'block';
            });
        });

        // --- Catálogo (Carrusel e Incorporación de Añadir Planta) ---
        const carouselTrack = document.getElementById('carousel-track');
        const carouselPrevBtn = document.getElementById('carousel-prev-btn');
        const carouselNextBtn = document.getElementById('carousel-next-btn');
        const photoModalOverlay = document.getElementById('photo-modal-overlay');
        const photoModalContent = document.getElementById('photo-modal-content');
        const modalImg = photoModalContent.querySelector('img');
        const modalTitle = photoModalContent.querySelector('h3');
        const modalDescription = photoModalContent.querySelector('p');
        const photoModalClose = document.getElementById('photo-modal-close');

        let isDragging = false;
        let startX;
        let scrollLeft;

        function loadCarouselImages() {
            carouselTrack.innerHTML = '';
            // Persistent-Tables: Iteración sobre la "tabla" catalogImages
            catalogImages.forEach((image, index) => {
                const item = document.createElement('div');
                item.classList.add('carousel-item');
                item.innerHTML = `
                    <img src="${image.src}" alt="${image.name}" data-index="${index}">
                    <div class="image-info">
                        <strong>${image.name}</strong>
                        <span>${image.date}</span>
                    </div>
                `;
                carouselTrack.appendChild(item);
            });
            addCarouselImageClickListeners();
        }

        function addCarouselImageClickListeners() {
            document.querySelectorAll('.carousel-item img').forEach(img => {
                img.removeEventListener('click', openPhotoModal);
                img.addEventListener('click', openPhotoModal);
            });
        }

        function openPhotoModal(e) {
            const clickedIndex = parseInt(e.target.dataset.index);
            // Persistent-Tables: Acceso a un "registro" específico por índice
            const photo = catalogImages[clickedIndex];
            modalImg.src = photo.src;
            modalImg.alt = photo.name;
            modalTitle.textContent = photo.name;
            modalDescription.textContent = photo.description;
            photoModalOverlay.classList.add('visible');
        }

        photoModalOverlay.addEventListener('click', (event) => {
            if (event.target === photoModalOverlay || event.target === photoModalClose) {
                photoModalOverlay.classList.remove('visible');
            }
        });
        photoModalClose.addEventListener('click', () => {
            photoModalOverlay.classList.remove('visible');
        });


        carouselTrack.addEventListener('mousedown', (e) => {
            isDragging = true;
            carouselTrack.classList.add('dragging');
            startX = e.pageX - carouselTrack.offsetLeft;
            scrollLeft = carouselTrack.scrollLeft;
        });

        carouselTrack.addEventListener('mouseleave', () => {
            isDragging = false;
            carouselTrack.classList.remove('dragging');
        });

        carouselTrack.addEventListener('mouseup', () => {
            isDragging = false;
            carouselTrack.classList.remove('dragging');
        });

        carouselTrack.addEventListener('mousemove', (e) => {
            if (!isDragging) return;
            e.preventDefault();
            const x = e.pageX - carouselTrack.offsetLeft;
            const walk = (x - startX) * 1.5;
            carouselTrack.scrollLeft = scrollLeft - walk;
        });

        carouselPrevBtn.addEventListener('click', () => {
            carouselTrack.scrollBy({
                left: -carouselTrack.offsetWidth / 2,
                behavior: 'smooth'
            });
        });

        carouselNextBtn.addEventListener('click', () => {
            carouselTrack.scrollBy({
                left: carouselTrack.offsetWidth / 2,
                behavior: 'smooth'
            });
        });

        // Add Plant Form
        const addPlantForm = document.getElementById('add-plant-form');
        const plantFormMessage = document.getElementById('plant-form-message');
        const myPlantsGrid = document.getElementById('my-plants-grid');

        // Cookbook: Función utilitaria para mensajes de formulario
        function showFormMessage(element, message, type) {
            element.textContent = message;
            element.className = 'form-message ' + type;
            element.style.display = 'block';
            setTimeout(() => {
                element.style.display = 'none';
            }, 3000);
        }

        addPlantForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const name = document.getElementById('plant-name').value;
            const date = document.getElementById('plant-date').value;
            const description = document.getElementById('plant-description').value;
            const imageFile = document.getElementById('plant-image').files[0];

            // Error/Exception Handling: Validación de campos del formulario
            if (!name || !date || !description || !imageFile) {
                showFormMessage(plantFormMessage, 'Por favor, completa todos los campos y sube una imagen.', 'error');
                return;
            }

            // Error/Exception Handling: Validación de archivo de imagen
            const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];
            const maxSizeMB = 2;

            if (!allowedTypes.includes(imageFile.type)) {
                showFormMessage(plantFormMessage, 'Tipo de archivo no permitido. Solo se aceptan JPG, PNG, GIF.', 'error');
                return;
            }

            if (imageFile.size > maxSizeMB * 1024 * 1024) {
                showFormMessage(plantFormMessage, `La imagen es demasiado grande. Máximo ${maxSizeMB}MB.`, 'error');
                return;
            }

            try {
                // Persistent-Tables: Simular la adición de un nuevo "registro" a la tabla catalogImages
                const imageUrl = URL.createObjectURL(imageFile);

                const newPlant = { src: imageUrl, name, date, description };
                catalogImages.push(newPlant); // Añadir al array del catálogo
                loadCarouselImages(); // Recargar el carrusel para mostrar la nueva planta
                addPlantForm.reset();
                showFormMessage(plantFormMessage, 'Planta añadida al catálogo (demostración)!', 'success');
                addPlantToMyRecords(newPlant); // Añadir a "Mis Registros"
            } catch (error) {
                // Error/Exception Handling: Captura de errores inesperados durante el procesamiento de la imagen
                console.error("Error al añadir planta:", error);
                showFormMessage(plantFormMessage, 'Ocurrió un error al añadir la planta. Intenta de nuevo.', 'error');
            }
        });

        function addPlantToMyRecords(plant) {
            const plantCard = document.createElement('div');
            plantCard.classList.add('carousel-item');
            plantCard.style.marginRight = '0';
            plantCard.style.marginBottom = '15px';
            plantCard.innerHTML = `
                <img src="${plant.src}" alt="${plant.name}">
                <div class="image-info">
                    <strong>${plant.name}</strong>
                    <span>${plant.date}</span>
                </div>
            `;
            myPlantsGrid.appendChild(plantCard);
        }


        // Inicializaciones de componentes al cargar la página
        loadCarouselImages();
        initializeSidebar(); // Cookbook: Inicialización de la barra lateral
        initializeSearchBar(); // Cookbook: Inicialización de la barra de búsqueda