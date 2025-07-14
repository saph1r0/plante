        const catalogImages = [
            { src: 'AA1H7uuE.jpeg', name: 'Orquídea Phalaenopsis', date: '2024-03-15', description: 'Una hermosa orquídea con flores vibrantes, ideal para interiores luminosos. Requiere riego moderado y alta humedad ambiental.' },
            { src: 'AA1HFmux.jpeg', name: 'Echeveria Elegans', date: '2023-11-20', description: 'Suculenta de fácil cuidado, perfecta para principiantes. Necesita mucha luz solar directa y muy poco riego.' },
            { src: 'AA1HpE4h.jpeg', name: 'Helecho Espada', date: '2024-01-10', description: 'Popular por su frondoso follaje, ideal para crear un ambiente tropical. Prefiere sombra parcial y alta humedad.' },
            { src: 'bulbosas.jpg', name: 'Rosa Roja Clásica', date: '2023-07-01', description: 'La reina del jardín, con sus pétalos suaves y fragancia inconfundible. Requiere sol pleno y poda regular para florecer.' },
            { src: 'flower-g6e77477b1_1280.jpg', name: 'Bonsái Ficus', date: '2024-02-28', description: 'Un arte milenario que convierte árboles en miniatura. Este Ficus es ideal para empezar en el mundo del bonsái, resistente y adaptable.' },
            { src: 'R (1).jpeg', name: 'Girasol Gigante', date: '2023-09-05', description: 'Flores grandes y alegres que siguen el sol. Perfectos para dar un toque vibrante a tu jardín y atraer polinizadores.' },
            { src: 'R.jpeg', name: 'Lavanda Angustifolia', date: '2024-04-22', description: 'Planta aromática con hermosas flores moradas, conocida por sus propiedades relajantes y su uso en aceites esenciales.' },
            { src: 'sorta-gortenzii_643b7eb5eb1f6.jpg', name: 'Cactus San Pedro', date: '2023-10-10', description: 'Cactus columnar de crecimiento rápido, muy resistente y de bajo mantenimiento. Ideal para jardines desérticos o macetas grandes.' },
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


        const menuToggle = document.getElementById('menu-toggle');
        const sidebar = document.getElementById('sidebar');
        const mainContent = document.querySelector('main');

        menuToggle.addEventListener('click', () => {
            sidebar.classList.toggle('active');
            menuToggle.style.display = 'none'; // Ocultar el botón cuando el sidebar se abre
            document.body.classList.toggle('sidebar-active');
        });

        // Cerrar sidebar al hacer clic fuera de los botones de menú
        sidebar.addEventListener('click', (event) => {
            // Verificar si el clic NO fue en un enlace de menú
            if (!event.target.closest('.menu-btn')) {
                sidebar.classList.remove('active');
                menuToggle.style.display = 'flex'; // Mostrar el botón cuando el sidebar se cierra
                document.body.classList.remove('sidebar-active');
            }
        });

        // --- Header Search Bar ---
        const searchIcon = document.getElementById('search-icon');
        const searchBar = document.getElementById('search-bar');
        const searchInput = document.getElementById('search-input');
        const searchSuggestions = document.getElementById('search-suggestions');

        searchIcon.addEventListener('click', () => {
            searchBar.style.display = searchBar.style.display === 'block' ? 'none' : 'block';
            if (searchBar.style.display === 'block') {
                searchInput.focus();
            } else {
                searchSuggestions.style.display = 'none'; // Ocultar sugerencias al cerrar la barra
                searchInput.value = ''; // Limpiar el input al cerrar
            }
        });

        searchInput.addEventListener('input', () => {
            const query = searchInput.value.toLowerCase();
            searchSuggestions.innerHTML = ''; // Limpiar sugerencias anteriores

            if (query.length > 0) {
                const filteredSuggestions = searchableOptions.filter(option =>
                    option.toLowerCase().includes(query)
                ).slice(0, 5); // Limitar a 5 sugerencias

                if (filteredSuggestions.length > 0) {
                    filteredSuggestions.forEach(suggestion => {
                        const div = document.createElement('div');
                        div.textContent = suggestion;
                        div.addEventListener('click', () => {
                            searchInput.value = suggestion;
                            searchSuggestions.style.display = 'none';
                            // Aquí podrías añadir lógica para ir a la sección o buscar
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

        // Ocultar sugerencias si se hace clic fuera del input de búsqueda o las sugerencias
        document.addEventListener('click', (event) => {
            if (!searchBar.contains(event.target) && event.target !== searchIcon) {
                searchSuggestions.style.display = 'none';
            }
        });


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
            eventsToRender.sort((a, b) => new Date(a.date) - new Date(b.date)); // Ordenar por fecha
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

            const firstDay = new Date(year, month, 1).getDay(); // 0 (Dom) - 6 (Sáb)
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

                        // Add events to calendar cells
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

        function filterEventsByDate() {
            const startDate = dateRangeStart.value ? new Date(dateRangeStart.value) : null;
            const endDate = dateRangeEnd.value ? new Date(dateRangeEnd.value) : null;

            filteredEvents = allEvents.filter(event => {
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
                event.preventDefault(); // Prevenir salto de línea
                const message = forumInput.value.trim();
                if (message === '') return;

                if (isLoggedIn) {
                    // Si estuviera logueado, añadir mensaje al chat
                    const newMessageDiv = document.createElement('div');
                    newMessageDiv.classList.add('chat-message', 'user-message');
                    newMessageDiv.innerHTML = `<strong>Tú:</strong> ${message}`;
                    chatHistory.appendChild(newMessageDiv);
                    chatHistory.scrollTop = chatHistory.scrollHeight; // Scroll al final
                    forumInput.value = '';
                } else {
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

                // Calcular el movimiento opuesto
                const petalRect = petal.getBoundingClientRect();
                const florRect = flor.getBoundingClientRect();

                const petalX = petalRect.left + petalRect.width / 2 - florRect.left;
                const petalY = petalRect.top + petalRect.height / 2 - florRect.top;

                const centerX = florRect.width / 2;
                const centerY = florRect.height / 2;

                const offsetX = (centerX - petalX) * 0.8; // Multiplicador para ajustar el movimiento
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

        // --- Catálogo (Carrusel) ---
        const carouselTrack = document.getElementById('carousel-track');
        const photoModalOverlay = document.getElementById('photo-modal-overlay');
        const photoModalContent = document.getElementById('photo-modal-content');
        const modalImg = photoModalContent.querySelector('img');
        const modalTitle = photoModalContent.querySelector('h3');
        const modalDescription = photoModalContent.querySelector('p');

        let isDragging = false;
        let startX;
        let scrollLeft;

        // Cargar imágenes en el carrusel
        function loadCarouselImages() {
            carouselTrack.innerHTML = '';
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

                // Añadir evento click para el modal
                item.querySelector('img').addEventListener('click', (e) => {
                    const clickedIndex = parseInt(e.target.dataset.index);
                    const photo = catalogImages[clickedIndex];
                    modalImg.src = photo.src;
                    modalImg.alt = photo.name;
                    modalTitle.textContent = photo.name;
                    modalDescription.textContent = photo.description;
                    photoModalOverlay.classList.add('visible');
                });
            });
        }

        // Cargar imágenes al inicio
        loadCarouselImages();

        // Funcionalidad de arrastre
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
            const walk = (x - startX) * 2; // Factor de velocidad
            carouselTrack.scrollLeft = scrollLeft - walk;
        });

        // Cierre del modal de fotos
        photoModalOverlay.addEventListener('click', (event) => {
            // Si el click no fue dentro del contenido del modal
            if (!photoModalContent.contains(event.target)) {
                photoModalOverlay.classList.remove('visible');
            }
        });
    