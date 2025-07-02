#WACeL-Java
# Sistema de Gestión de Plantaciones

Este proyecto implementa un **sistema completo para la gestión de plantaciones**, con funcionalidades para registrar plantas, usuarios, ubicaciones, sensores y eventos relacionados con el riego, fertilización y control ambiental.

---

##  Arquitectura del Sistema

El sistema está construido bajo una arquitectura **multicapa**, con:

- **Vista / Controladores (View/Controller):** Interfaces REST y (opcionalmente) páginas Thymeleaf para interactuar con el usuario.
- **Servicios (Service Layer):** Lógica de negocio que maneja las operaciones y reglas.
- **Dominio (Domain):** Entidades como Plantas, Sensores, Usuarios, Eventos y Ubicaciones.
- **Persistencia (Persistence):** Repositorios JPA y Spring Data para conectar con **MongoDB** y **MySQL**.

---

##  Tecnologías utilizadas

- Java + Spring Boot
- Spring Data JPA & Hibernate
- MongoDB
- MySQL
- Maven
- (Opcional) Thymeleaf para vistas web
- Lombok (para simplificar entidades)

---
##  Instalación y ejecución

1. **Clona el proyecto:**

    ```bash
    git clone https://github.com/tu-usuario/sistema-plantaciones.git
    cd sistema-plantaciones
    ```

2. **Configura tu base de datos:**

   - Crea una base de datos **MySQL** (por ejemplo `plantacionesdb`) y configura usuario y contraseña.
   - (Opcional) Configura tu **MongoDB** (por defecto usa `localhost:27017`).

3. **Edita `application.properties`:**

    ```properties
    spring.datasource.url=https://github.com/saph1r0/plante.git
    spring.datasource.username=root

    ```

4. **Compila y ejecuta con Maven desde VS Code o terminal:**

    ```bash
    ./mvnw spring-boot:run
    ```

5. Accede a la aplicación en:

    ```
    http://localhost:8080/
    ```

---

## Módulos principales

- **🌱 Plantas:** Registro y control de especies, ubicación, riego y fertilización.
- **📍 Ubicaciones:** Gestión de zonas de cultivo y mapeo geográfico.
- **💧 Sensores:** Lectura de temperatura, humedad y luz asociadas a plantas/zona.
- **📅 Eventos:** Registro de riegos, fertilizaciones y podas con recordatorios.
- **👤 Usuarios:** Roles, autenticación y permisos.

---

##  Estructura del proyecto
src/
└── main/
├── java/com/miempresa/plantaciones/
│ ├── controller/
│ ├── service/
│ ├── domain/
│ ├── repository/
│ └── config/
└── resources/
├── application.properties
├── static/
└── templates/

---

##  Estado del proyecto

🚀 Proyecto en desarrollo. Se encuentra en la fase de construcción de APIs REST y persistencia.

---

##  Contribuciones

¡Pull requests y sugerencias son bienvenidas!  
No dudes en abrir un issue para reportar errores o proponer mejoras.

---

## Reference

---

