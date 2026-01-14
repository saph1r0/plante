
# 🌿 PlantApp - Sistema de Gestión de Plantas (Java + DDD)

## Integrantes
- Alcázar Medina, Diogo
- Esteba Feria, Sophia Alejandra
- Soto Huerta, Ángela Shirlet
- Juan de Dios Delgado, Hellen
- Velásquez Montoya, Juan José
- Ticona Pereyra, Erika Daysi

## 📌 Descripción del Proyecto
PlantApp es un sistema de gestión de plantas que permite a los usuarios:
- Registrarse y autenticarse de forma segura
- Administrar sus plantas personales
- Consultar un catálogo botánico
- Gestionar cuidados y recordatorios

El sistema fue desarrollado usando Java + Spring Boot, aplicando:
- Domain-Driven Design (DDD)
- Arquitectura Limpia
- Microservicios
- Pipeline CI/CD automatizado

El desarrollo se realizó en **IntelliJ IDEA**, siguiendo principios de arquitectura limpia.

---

## 🛠️ Estructura del Proyecto

```bash
PlanTapp/
├── mvnw, mvnw.cmd         # Wrappers para Maven (portabilidad del build)
├── pom.xml                # Descriptor del proyecto Maven (dependencias, plugins)
├── src/
│   └── main/
│       ├── java/
│       │   └── com/planta/plantapp/
│       │       ├── aplicacion/                  # Casos de uso y servicios de aplicación
│       │       │   ├── interfaces/              # Contratos para servicios
│       │       │   │   ├── IServicioAutenticacion.java
│       │       │   │   ├── IServicioBitacora.java
│       │       │   │   ├── IServicioPlanta.java
│       │       │   │   ├── IServicioRecordatorio.java
│       │       │   │   └── IServicioUsuario.java
│       │       │   └── servicios/               # Implementaciones de servicios
│       │       │       ├── ServicioAutenticacionImpl.java
│       │       │       ├── ServicioBitacoraImpl.java
│       │       │       ├── ServicioPlantaImpl.java
│       │       │       ├── ServicioRecordatorioImpl.java
│       │       │       └── ServicioUsuarioImpl.java
│       │       ├── config/                      # Configuraciones de seguridad (Spring Security)
│       │       │   └── SecurityConfig.java
│       │       ├── dominio/                     # Modelo de dominio (entidades, DTOs, fábricas)
│       │       │   ├── modelo/
│       │       │   │   ├── bitacora/            # Entidad de bitácora
│       │       │   │   ├── cuidado/             # Tipos de cuidados y tareas
│       │       │   │   ├── fabrica/             # Fábricas de creación de objetos del dominio
│       │       │   │   ├── planta/              # Entidades y DTOs de plantas
│       │       │   │   ├── recordatorio/        # Entidad Recordatorio y estados
│       │       │   │   ├── servicios/           # Servicios de dominio puro
│       │       │   │   └── usuario/             # Entidad y DTOs de usuario
│       │       │   ├── IUsuarioRepositorio.java
│       │       │   ├── IPlantaRepositorio.java
│       │       │   ├── IRecordatorioRepositorio.java
│       │       │   └── IBitacoraRepositorio.java
│       │       ├── infraestructura/             # Implementaciones de acceso a datos y persistencia
│       │       │   ├── documento/               # Documentos MongoDB (NoSQL)
│       │       │   ├── entidad/                 # Entidades JPA/Hibernate (MySQL)
│       │       │   ├── mapper/                  # Mapeadores entre entidades/dominios/DTOs
│       │       │   └── repositorio/             # Repositorios específicos
│       │       │       ├── mysql/               # Repositorios para MySQL
│       │       │       │   ├── jpa/             # Interfaces JPA Spring Data
│       │       │       │   └── ReposImpl.java   # Implementaciones personalizadas
│       │       │       └── mongodb/             # Repositorios para MongoDB
│       │       ├── presentacion/                # Controladores web (REST y MVC)
│       │       │   └── controlador/
│       │       │       ├── BitacoraController.java
│       │       │       ├── PlantaController.java
│       │       │       ├── RecordatorioController.java
│       │       │       ├── UsuarioController.java
│       │       │       └── UsuarioWebController.java
│       │       └── PlantappApplication.java     # Clase principal de arranque (Spring Boot)
│       └── resources/                           # Recursos estáticos, vistas HTML y config
│           ├── application.properties           # Configuraciones de aplicación (DB, puertos, etc.)
│           ├── static/
│           │   ├── images/                      # Catálogo visual de plantas (JPG, PNG)
│           │   └── login/
│           │       ├── css/                     # Hojas de estilo para vistas
│           │       └── js/                      # Scripts JavaScript del frontend
│           └── templates/
│               └── login/                       # Vistas Thymeleaf
│                   ├── catalogo.html
│                   ├── dashboard.html
│                   ├── home.html
│                   ├── index.html
│                   └── login.html

````
## Funcionalidades (Alto Nivel)
**🧩 Casos de Uso Principales**

- Registro e inicio de sesión
- Gestión de plantas personales
- Visualización de catálogo botánico
- Dashboard con información relevante
---
## Modelo de Dominio
📐 Entidades Principales

- Usuario
- Planta
- Cuidado
- Recordatorio
- Bitácora

## Diagrama UML de Clases
![diagrama](https://github.com/user-attachments/assets/4321524d-24e0-4a54-8735-31661deedc93)

## Prototipo
<img width="1374" height="832" alt="Captura desde 2026-01-14 09-31-40" src="https://github.com/user-attachments/assets/481daca6-5e3d-49fa-950e-29e2963211bf" />

---

## Visión General de Arquitectura

### 🧱 Enfoque Arquitectónico

El sistema **PlantApp** está diseñado siguiendo principios de **arquitectura limpia** y **Domain-Driven Design (DDD)**, asegurando una separación clara de responsabilidades y alta mantenibilidad.

**Enfoques aplicados:**
- Domain-Driven Design (DDD)
- Arquitectura Limpia
- Separación por capas
- Inversión de dependencias

| Capa            | Responsabilidad               |
| --------------- | ----------------------------- |
| Presentación    | Controladores MVC y REST      |
| Aplicación      | Casos de uso y servicios      |
| Dominio         | Entidades, reglas de negocio  |
| Infraestructura | Persistencia y acceso a datos |

### 🧠 Principios y Buenas Prácticas Aplicadas
- SOLID
- Clean Code
- Dependency Inversion
- DTOs y Repositorios
- Separación de intereses (SoC)

### 🔍 Análisis Técnico y Arquitectónico
**Evaluación Técnica**
## 📊 Evaluación Técnica

---
### 1. Prácticas de Codificación Limpia

Implementa **más de 5 prácticas de Clean Code**:

#### **Nombres Descriptivos**
```java
private static final String ATTR_LOGIN_DTO = "loginDTO";
private static final String ATTR_REGISTRO_DTO = "registroDTO";
private static final String LOGIN_VIEW = "login/login";
```

#### **Funciones Pequeñas y con Una Responsabilidad**
```java
@GetMapping("/login")
public String mostrarLogin(Model model) {
    logger.debug("Cargando formulario de login");
    model.addAttribute(ATTR_LOGIN_DTO, new UsuarioLoginDTO());
    model.addAttribute(ATTR_REGISTRO_DTO, new UsuarioRegistroDTO());
    return LOGIN_VIEW;
}
```

#### **Manejo de Errores con Logging**
```java
} catch (Exception e) {
    logger.error("Error al registrar usuario: {}", e.getMessage());
    model.addAttribute("error", "Error al registrar usuario");
    return LOGIN_VIEW;
}
```

#### **Eliminación de Código Duplicado (DRY)**
```java
private void agregarDTOsAlModelo(Model model) {
    model.addAttribute(ATTR_LOGIN_DTO, new UsuarioLoginDTO());
    model.addAttribute(ATTR_REGISTRO_DTO, new UsuarioRegistroDTO());
}
```

#### **Constantes en lugar de Magic Numbers/Strings**
```java
private static final Logger logger = LoggerFactory.getLogger(UsuarioWebController.class);
private static final String ATTR_USUARIO_NOMBRE = "usuarioNombre";
```

#### **Validación de Entrada**
```java
public void setCorreo(String correo) {
    this.correo = correo != null ? correo.trim() : null;
}
```

---

### 2. Principios SOLID
Implementa **más de 3 principios SOLID**:

#### **S - Single Responsibility Principle**
Cada clase tiene una única responsabilidad:
- `ServicioAutenticacionImpl`: Solo autenticación
- `UsuarioRepositorioImpl`: Solo persistencia de datos
- `UsuarioWebController`: Solo manejo de peticiones web

#### **O - Open/Closed Principle**
```java
public interface IServicioUsuario {
    void registrarUsuario(Usuario usuario);
    // Abierto para extensión, cerrado para modificación
}

@Service
public class ServicioUsuarioImpl implements IServicioUsuario {
    // Implementación específica
}
```

#### **L - Liskov Substitution Principle**
```java
IServicioUsuario usuarioServicio; // Puede ser cualquier implementación
IServicioAutenticacion autenticacionServicio; // Intercambiable
```

#### **I - Interface Segregation Principle**
Interfaces específicas y cohesivas:
```java
public interface IServicioAutenticacion {
    Usuario autenticar(String correo, String password); // Solo autenticación
}

public interface IUsuarioRepositorio {
    Usuario obtenerPorId(String id);
    Optional<Usuario> buscarPorCorreo(String correo);
    // Solo operaciones de repositorio
}
```

#### **D - Dependency Inversion Principle**
```java
public class ServicioAutenticacionImpl implements IServicioAutenticacion {
    private final IUsuarioRepositorio usuarioRepositorio; // Depende de abstracción
    private final PasswordEncoder passwordEncoder;        // No de implementación concreta
    
    public ServicioAutenticacionImpl(IUsuarioRepositorio usuarioRepositorio,
                                   PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }
}
```

---

### 3. Domain-Driven Design

Implementa **todos los elementos DDD**:

#### **Entidades**
```java
public class Usuario {
    private Long id; // Identidad única
    private String nombre;
    private String correo;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id); // Identidad por ID
    }
}
```

#### **Objetos de Valor (Value Objects)**
```java
public class UsuarioLoginDTO {
    private String correo;
    private String contrasena;
    // Sin identidad propia, inmutable en comportamiento
}

public class UsuarioRegistroDTO {
    private String nombre;
    private String correo;
    private String contrasena;
}
```

#### **Servicios de Dominio**
```java
@Service
public class ServicioAutenticacionImpl implements IServicioAutenticacion {
    @Override
    public Usuario autenticar(String correo, String password) {
        // Lógica de negocio compleja que no pertenece a una entidad
    }
}
```

#### **Repositorios**
```java
public interface IUsuarioRepositorio {
    Usuario obtenerPorId(String id);
    Optional<Usuario> buscarPorCorreo(String correo);
    void guardar(Usuario usuario);
    void eliminar(String id);
}

@Repository
public class UsuarioRepositorioImpl implements IUsuarioRepositorio {
    // Implementación específica de persistencia
}
```

#### **Módulos (Packages)**
```
com.planta.plantapp.dominio.usuario.modelo     // Entidades
com.planta.plantapp.aplicacion.servicios       // Servicios de aplicación
com.planta.plantapp.infraestructura.repositorio // Repositorios
com.planta.plantapp.presentacion.controlador   // Controladores
```

#### **Fábricas (Implícitas)**
```java
// Constructor actúa como factory method
public Usuario(String nombre, String correo, String contrasena) {
    this.nombre = nombre;
    this.correo = correo;
    this.contrasena = contrasena;
}
```

---

### 4. Estilos/Patrones de Arquitectura 

Implementa **Arquitectura en Capas con Repositorio**:

#### **Capa de Presentación**
```java
@Controller
@RequestMapping("/web")
public class UsuarioWebController {
    // Maneja peticiones HTTP, vistas Thymeleaf
}

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    // API REST para servicios externos
}
```

#### **Capa de Aplicación**
```java
@Service
public class ServicioUsuarioImpl implements IServicioUsuario {
    // Orquesta casos de uso y lógica de aplicación
}

@Service
public class ServicioAutenticacionImpl implements IServicioAutenticacion {
    // Casos de uso específicos de autenticación
}
```

#### **Capa de Dominio**
```java
public class Usuario {
    // Entidad de dominio pura
}

public interface IUsuarioRepositorio {
    // Contrato del dominio para persistencia
}
```

#### **Capa de Infraestructura/Repositorio**
```java
@Repository
public class UsuarioRepositorioImpl implements IUsuarioRepositorio {
    private final UsuarioJpaRepositorio usuarioJpaRepositorio;
    // Implementación específica con JPA/Hibernate
}
```

---

## 🏗️ Patrones de Diseño Identificados

### **Repository Pattern**
```java
public interface IUsuarioRepositorio {
    Optional<Usuario> buscarPorCorreo(String correo);
}

@Repository
public class UsuarioRepositorioImpl implements IUsuarioRepositorio {
    // Encapsula lógica de acceso a datos
}
```

### **Dependency Injection**
```java
public ServicioAutenticacionImpl(IUsuarioRepositorio usuarioRepositorio,
                               PasswordEncoder passwordEncoder) {
    this.usuarioRepositorio = usuarioRepositorio;
    this.passwordEncoder = passwordEncoder;
}
```

### **Data Transfer Object (DTO)**
```java
public class UsuarioLoginDTO {
    private String correo;
    private String contrasena;
    // Transfiere datos entre capas
}
```

### **MVC (Model-View-Controller)**
```java
@Controller // Controlador
public class UsuarioWebController {
    public String mostrarLogin(Model model) { // Modelo
        return "login/login"; // Vista
    }
}
```


---

### 📦 Diagrama de Capas / Paquetes

```text
presentacion  →  aplicacion  →  dominio  →  infraestructura
```

## Módulos y Servicios REST (OpenAPI / Swagger)
### 🔹 Módulo: Usuario
Propósito: Gestión de autenticación y registro
| Método | Endpoint           | Descripción       |
| ------ | ------------------ | ----------------- |
| POST   | /api/auth/register | Registrar usuario |
| POST   | /api/auth/login    | Autenticación     |

### 🔹 Módulo: Plantas
Propósito: Gestión de plantas personales
| Método | Endpoint          | Descripción      |
| ------ | ----------------- | ---------------- |
| GET    | /api/plantas      | Listar plantas   |
| POST   | /api/plantas      | Registrar planta |
| PUT    | /api/plantas/{id} | Actualizar       |
| DELETE | /api/plantas/{id} | Eliminar         |


 **Gestión de Plantas Personales**

| Funcionalidad | Estado | Descripción |
|---------------|--------|-------------|
| Registrar planta personal | ✅ | Agregar planta desde catálogo |
| Listar mis plantas | ✅ | Ver plantas del usuario |
| Editar información de planta | ✅ | Modificar apodo, ubicación, estado |
| Eliminar planta | ✅ | Remover planta del sistema |
| Cambiar estado de planta | ✅ | Actualizar condición actual |

### 🔹 Módulo: Catálogo
Propósito: Consulta de plantas botánicas
| Método | Endpoint              |
| ------ | --------------------- |
| GET    | /web/plantas/catalogo |

### 🟢 Módulo: Dashboard y Reportes

| Funcionalidad | Estado | Descripción |
|---------------|--------|-------------|
| Dashboard interactivo | ✅ | Vista general del sistema |
| Estadísticas por usuario | ✅ | Contadores y métricas |
| API REST completa | ✅ | Endpoints para todas las operaciones |


## 🔐 Seguridad en el Manejo de Credenciales

| Tarea                                                      | Estado       |
| ---------------------------------------------------------- | ------------ |
| Detectar credenciales visibles                             | ✅ Completado |
| Reemplazar por variables de entorno (`DB_USER`, `DB_PASS`) | ✅ Completado |
| Validar almacenamiento seguro de contraseñas               | ✅ Completado |

## 📝 Historias de Usuario Cubiertas

### 🟢 H.2.1.1 - Validar Datos del Formulario

| Tarea                                                     | Estado       |
| --------------------------------------------------------- | ------------ |
| Diseñar formulario con campos: nombre, correo, contraseña | ✅ Completado |
| Validar formato de correo                                 | ✅ Completado |
| Validar seguridad de contraseña                           | ✅ Completado |
| Mostrar mensajes de error al usuario                      | ✅ Completado |

### 🟢 H.2.1.2 - Guardar Usuario y Encriptar Contraseña

| Tarea                               | Estado       |
| ----------------------------------- | ------------ |
| Configurar conexión a MySQL         | ✅ Completado |
| Almacenar datos en tabla `usuarios` | ✅ Completado |
| Evitar contraseñas en texto plano   | ✅ Completado |

## Pipeline CI/CD (Jenkins)
**Etapas del Pipeline**
- Checkout del repositorio
- Clean & Build (multi-módulo)
- Pruebas unitarias
- Levantamiento de microservicios
- Pruebas funcionales
- Análisis SonarQube
- Análisis de seguridad OWASP ZAP
<img width="1837" height="971" alt="Captura desde 2026-01-14 14-00-56" src="https://github.com/user-attachments/assets/d1376d3f-b98e-4918-a953-c2c4ec6d2e55" />

-

```java
pipeline {
    agent any

    tools {
        maven 'MAVEN'   // Nombre del Maven instalado en Jenkins
        jdk 'JAVA'      // Nombre del JDK en Jenkins
    }

    environment {
        SONARQUBE_TOKEN = credentials('sonarqube-local') // Token SonarQube
        CI = 'true'
        BASE_URL = 'http://localhost:8080'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'sophia-erika-hellen-pruebas',
                    credentialsId: 'github-tokens',
                    url: 'https://github.com/saph1r0/plante.git'
            }
        }

        stage('Clean Workspace') {
            steps {
                echo 'Limpiando workspace'
                bat 'mvn clean -B'
            }
        }

        stage('Build All Modules') {
            steps {
                echo 'Construyendo todos los módulos (sin tests)'
                bat 'mvn install -B -DskipTests=true'
            }
        }

        stage('Run Unit Tests (Surefire)') {
            steps {
                echo 'Ejecutando tests unitarios'
                bat 'mvn test -B'
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('Run Functional/Integration Tests (Failsafe)') {
            steps {
                echo 'Ejecutando tests funcionales Selenium'

                // Levanta microservicios en background
                bat 'start /b java -jar user-service/target/user-service.jar --server.port=8082'
                bat 'start /b java -jar user-plants-service/target/user-plants-service.jar --server.port=8081'
                bat 'start /b java -jar plantapp/target/plantapp.jar --server.port=8080'

                echo 'Esperando que los servicios estén listos'
                sleep 25 // espera a que se levanten los servicios

                // Ejecuta tests funcionales/integración
                bat 'mvn verify -B -Dtest=*FunctionalTest'
                junit '**/target/failsafe-reports/*.xml'
            }
        }

        stage('JaCoCo Coverage Report') {
            steps {
                echo 'Generando reporte de cobertura JaCoCo'
                bat 'mvn jacoco:report'
                archiveArtifacts artifacts: '**/target/site/jacoco/index.html', fingerprint: true
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube-local') {
                    bat """
                    mvn sonar:sonar ^
                      -Dsonar.projectKey=Plantapp ^
                      -Dsonar.projectName=Plantapp ^
                      -Dsonar.host.url=http://localhost:9000 ^
                      -Dsonar.login=%SONARQUBE_TOKEN%
                    """
                }
            }
        }

        stage('OWASP ZAP Security Scan') {
            steps {
                echo 'Ejecutando análisis de seguridad OWASP ZAP'
                bat """
                cd /d "C:\\Program Files\\ZAP\\Zed Attack Proxy"

                zap.bat ^
                  -cmd ^
                  -quickurl http://localhost:8080/web/login ^
                  -quickout "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\plante-ci-cd\\zap-plantapp.html"

                zap.bat ^
                  -cmd ^
                  -quickurl http://localhost:8081/api/auth/login ^
                  -quickout "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\plante-ci-cd\\zap-user-plants-service.html"

                zap.bat ^
                  -cmd ^
                  -quickurl http://localhost:8082/api/auth/login ^
                  -quickout "C:\\ProgramData\\Jenkins\\.jenkins\\workspace\\plante-ci-cd\\zap-user-service.html"
                """
            }
        }

    }

    post {
        always {
            echo 'Pipeline finalizado'
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
        success {
            echo 'Pipeline CI/CD ejecutado correctamente'
        }
        failure {
            echo 'Pipeline falló'
        }
    }
}
```
### Pipeline sin testers
![jenkins](https://github.com/user-attachments/assets/6e9aab03-0525-4702-8a34-4115bf53e25f)
![jenkins2](https://github.com/user-attachments/assets/c8c97892-594a-41b9-af52-1804a8a8e55d)


## Variables de Entorno (Jenkins)
```text
CI=true
BASE_URL=http://localhost:8080
```
✔ Headless automático

✔ URLs no hardcodeadas

## Gestión de Issues

- Uso de GitHub Issues
- Clasificación:
  - Bug
  - Enhancement
  - Technical Debt
- Asignación por integrante
<img width="327" height="734" alt="Captura desde 2026-01-14 10-38-41" src="https://github.com/user-attachments/assets/6689f96e-e93e-42d0-b762-dec1f344255a" />


## Gestión de Entrega (Despliegue)
- Jenkins automatiza:
  - Build
  - Test
  - Análisis
- Artefactos versionados (.jar)
- Despliegue local controlado

## Evidencias Visuales (GUI)
✔ Login
<img width="1787" height="971" alt="Captura desde 2026-01-14 10-40-34" src="https://github.com/user-attachments/assets/548f0b45-7366-4965-9e04-69518983b80a" />

✔ Registro
<img width="1787" height="971" alt="Captura desde 2026-01-14 10-40-54" src="https://github.com/user-attachments/assets/cc87d877-c1fd-483d-931c-62d94319675e" />

✔ Dashboard Principal
<img width="1787" height="971" alt="Captura desde 2026-01-14 10-41-35" src="https://github.com/user-attachments/assets/baf56e1b-71a5-4b20-af1b-94658a3ea13f" />

✔ Dashboard Personal
<img width="1787" height="971" alt="Captura desde 2026-01-14 10-41-49" src="https://github.com/user-attachments/assets/c43c9871-9fa9-4069-8d85-f3ece6bae882" />

✔ Catálogo
<img width="1787" height="971" alt="Captura desde 2026-01-14 10-42-09" src="https://github.com/user-attachments/assets/79e2e349-c8fb-45ca-a841-e09b7429bebf" />


## 💡 Estilos de Programación Aplicados

### 1. Trinity (Entrada → Procesamiento → Salida)


Separación clara entre:

* **Entrada**: Datos recibidos desde `@RequestParam` en el `Controller`.
* **Procesamiento**: Lógica delegada a la capa de aplicación (`usuarioServicio`).
* **Salida**: Respuesta al cliente (mensaje textual o estado HTTP).

```java
@PostMapping("/login")
public String autenticarUsuario(@RequestParam String email,
                                @RequestParam String contrasena) {
    Usuario usuario = usuarioServicio.autenticarUsuario(email, contrasena);
    return (usuario != null) ? "Inicio de sesión exitoso" : "Credenciales incorrectas";
}
```

---

### 2. Things (Modelo Rico del Dominio)

El objeto `Usuario` representa una entidad del dominio con identidad, atributos y relaciones:

```java
public class Usuario {
    private Long id;
    private String nombre;
    private String correo;
    private List<Planta> plantas;
}
```
```java
@Document(collection = "registros_plantas")
public class RegistroPlanta {
    @Id
    private String id; // Identidad única
    private String apodo;
    private EstadoPlanta estado;
}
}
```
---

### 3. Pipeline (Procesamiento Paso a Paso)

Estilo aplicado en métodos como `registrarUsuario`, donde los datos fluyen en una secuencia:

**Ejemplo:**

```java
public void registrarUsuario(Usuario usuario) {
    if (usuario == null || usuario.getCorreo() == null)
        throw new IllegalArgumentException("Datos inválidos");

    Optional<Usuario> existente = repositorioUsuario.buscarPorCorreo(usuario.getCorreo());
    if (existente.isPresent())
        throw new IllegalArgumentException("Correo ya registrado");

    repositorioUsuario.guardar(usuario);
}
```
```java
return plantaMongoRepo.findAll()
    .stream()
    .filter(p -> p.getTipo().equalsIgnoreCase(tipo))
    .map(this::convertirADominio)
    .collect(Collectors.toList());
```

Uso del **API Stream de Java** para construir cadenas de procesamiento donde los datos fluyen a través de filtros, mapeos y transformaciones.

```java
// Pipeline: transformaciones encadenadas sin estado compartido
public List<PlantaResumenDTO> obtenerTodas() {
    return plantaRepositorio.listarTodos()
        .stream()
        .filter(planta -> planta != null && planta.getEstado() != null)
        .filter(planta -> !"ELIMINADA".equals(planta.getEstado()))
        .map(this::transformarAResumen)
        .sorted((p1, p2) -> p1.getNombre().compareTo(p2.getNombre()))
        .limit(100)
        .collect(Collectors.toList());
}
```

Secuencia:

1. Validación
2. Verificación
3. Ejecución
4. Resultado

---

### 4. Error Handling (Manejo de Errores)

Captura controlada de excepciones para mantener robustez del sistema:

```java
@Override
    public boolean actualizarPerfil(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) return false;

        try {
            repositorioUsuario.guardar(usuario);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
```

### 5.Cookbook

Aplicación de **procedimientos secuenciales** que modifican estado compartido.

```java
// Cookbook: Estado compartido modificado por procedimientos
private List<String> erroresValidacion = new ArrayList<>();
private Planta plantaEnProceso;
private boolean procesoCompletado = false;

public boolean registrarNuevaPlanta(String nombre, String tipo, String usuarioId) {
    // Paso 1: Limpiar estado previo
    limpiarEstadoProceso();
    
    // Paso 2: Validar datos de entrada
    validarDatosEntrada(nombre, tipo, usuarioId);
    
    // Paso 3: Normalizar y preparar datos
    prepararDatosPlanta(nombre, tipo);
    
    // Paso 4: Ejecutar guardado si todo está correcto
    if (erroresValidacion.isEmpty()) {
        ejecutarGuardado(usuarioId);
    }
    
    return procesoCompletado;
}
```
```java
@Service
public class CatalogoService {
    private List<Planta> catalogo; // Estado compartido
    
    public void inicializarCatalogo() { /* función modifica estado */ }
    public List<Planta> obtenerCatalogo() { /* función lee estado */ }
}
```
###  6.Persistent Tables

Implementación de **estructura tabular** con queries declarativas, simulando el comportamiento de una base de datos relacional.

```java
// Persistent Tables: simulación de tablas relacionales
private final Map<String, Usuario> tablaUsuarios;
private final Map<String, String> indiceCorreos; // correo -> id

@Override
public Optional<Usuario> buscarPorCorreo(String correo) {
    // Query declarativa: SELECT * FROM usuarios WHERE email = ?
    if (correo == null || !correo.contains("@")) {
        return Optional.empty();
    }
    
    String usuarioId = indiceCorreos.get(correo.toLowerCase());
    if (usuarioId != null) {
        return Optional.ofNullable(tablaUsuarios.get(usuarioId));
    }
    
    return Optional.empty();
}
```


#### **Programación Declarativa (Spring Annotations)**
```java
@Service
@Repository
@Controller
@RequestMapping("/web")
```

Prácticas aplicadas:

* Validación previa con `IllegalArgumentException`
* Manejo de fallos inesperados con `try-catch`
* Propagación controlada con `IllegalStateException`


## 🔍 Análisis Estático SonarLint/SonarQube

Durante el desarrollo, se utilizaron herramientas como **SonarLint** para realizar análisis estático y mejorar la calidad del código. A continuación, se documenta una de las recomendaciones aplicadas:

### 🟡 Recomendación: Evitar uso directo de `Boolean` boxeado en expresiones booleanas

**Mensaje de SonarLint:**

> Avoid using boxed "Boolean" types directly in boolean expressions.

**Descripción:**
El uso de objetos `Boolean` en condiciones puede lanzar una excepción `NullPointerException` si el valor es `null`. La conversión automática (unboxing) falla silenciosamente. Es mejor verificar explícitamente si el valor no es `null`.

**❌ Código no conforme:**

```java
Boolean activo = usuario.getActivo();
if (activo) {  // ❌ Posible NullPointerException
    ...
}
```

**✅ Código corregido:**

```java
Boolean activo = usuario.getActivo();
if (Boolean.TRUE.equals(activo)) {
    ...
}
```

## 🚀 Fortalezas del Proyecto

1. **Arquitectura Sólida**: Separación clara de responsabilidades en capas
2. **Seguridad**: Implementación correcta de BCrypt para contraseñas
3. **Logging**: Sistema robusto de trazabilidad con SLF4J
4. **Validación**: Manejo apropiado de errores y validaciones
5. **Testabilidad**: Alto desacoplamiento facilita testing
6. **Escalabilidad**: Estructura preparada para crecimiento
