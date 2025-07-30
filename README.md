# 🌿 PlantApp - Sistema de Gestión de Plantas (Java + DDD + MongoDB)

## 📌 Descripción del Proyecto

**PlantApp** es un sistema completo de gestión de plantas implementado en **Java** siguiendo los principios de **Domain-Driven Design (DDD)** y **Arquitectura Limpia**. El sistema permite gestionar un catálogo de plantas, registrar plantas personales de usuarios, monitorear su estado y programar cuidados.

Este proyecto incluye:

- **Backend REST API** con Spring Boot y MongoDB
- **Frontend web** con HTML, CSS y JavaScript 
- **Arquitectura en capas** con separación clara de responsabilidades
- **Aplicación de principios SOLID** y prácticas de Clean Code
- **Domain-Driven Design** completo con entidades, servicios y repositorios

---

## 🎯 Propósito del Proyecto

Crear una aplicación web que permita a los usuarios:

1. **Explorar un catálogo de plantas** con información detallada
2. **Registrar sus plantas personales** con ubicación y estado
3. **Monitorear el estado** de sus plantas (saludable, necesita agua, etc.)
4. **Programar y registrar cuidados** (riego, poda, fertilización)
5. **Visualizar estadísticas** y plantas que requieren atención
6. **Gestionar un dashboard** personalizado con información relevante

---

## 🛠️ Tecnologías Utilizadas

### **Backend**
- **Java 17** - Lenguaje principal
- **Spring Boot 3.5.3** - Framework web y dependency injection
- **Spring Data MongoDB** - ODM para persistencia
- **MongoDB** - Base de datos NoSQL
- **Maven** - Gestión de dependencias

### **Frontend**
- **HTML5** - Estructura de páginas
- **CSS3** - Estilos y diseño responsivo
- **JavaScript ES6+** - Lógica de interfaz y API calls
- **Fetch API** - Comunicación con backend

### **Herramientas de Desarrollo**
- **Docker** - Contenedor para MongoDB
- **Git/GitHub** - Control de versiones
- **IntelliJ IDEA** - IDE de desarrollo
- **SonarLint** - Análisis estático de código
---

## 🗂️ Estructura del Proyecto

```bash
src/
└── main/
    ├── java/com/planta/plantapp/
    │   ├── 📁 aplicacion/              # Capa de Aplicación
    │   │   ├── interfaces/             # Contratos de servicios
    │   │   │   ├── IServicioPlanta.java
    │   │   │   ├── IServicioBitacora.java
    │   │   │   └── IServicioRecordatorio.java
    │   │   └── servicios/              # Implementaciones
    │   │       ├── ServicioPlantaImpl.java
    │   │       ├── CatalogoService.java
    │   │       └── ServicioBitacoraImpl.java
    │   │
    │   ├── 📁 dominio/                 # Capa de Dominio (Corazón DDD)
    │   │   ├── modelo/
    │   │   │   ├── planta/             # Agregado Planta
    │   │   │   │   ├── Planta.java           # Entidad raíz
    │   │   │   │   ├── RegistroPlanta.java   # Entidad
    │   │   │   │   ├── EstadoPlanta.java     # Enum/Value Object
    │   │   │   │   └── Etiqueta.java         # Value Object
    │   │   │   ├── cuidado/            # Agregado Cuidado
    │   │   │   │   ├── Cuidado.java
    │   │   │   │   ├── TipoCuidado.java
    │   │   │   │   └── TareaCuidado.java
    │   │   │   ├── fabrica/            # Factory Pattern
    │   │   │   │   ├── PlantaFabrica.java
    │   │   │   │   ├── CuidadoFabrica.java
    │   │   │   │   └── RecordatorioFabrica.java
    │   │   │   └── servicios/          # Domain Services
    │   │   │       ├── ServicioPlantaDominio.java
    │   │   │       └── ServicioBitacoraDominio.java
    │   │   └── IPlantaRepositorio.java # Repository Contract
    │   │
    │   ├── 📁 infraestructura/         # Capa de Infraestructura
    │   │   ├── repositorio/mongodb/    # Implementaciones MongoDB
    │   │   │   ├── PlantaRepositorioImpl.java
    │   │   │   ├── PlantaMongoRepository.java
    │   │   │   └── RegistroPlantaMongoRepository.java
    │   │   ├── documento/              # MongoDB Documents
    │   │   │   ├── PlantaDocumento.java
    │   │   │   └── BitacoraDocumento.java
    │   │   └── entidad/                # JPA Entities (futuro)
    │   │
    │   ├── 📁 presentacion/            # Capa de Presentación
    │   │   └── controlador/
    │   │       ├── PlantaController.java     # REST API
    │   │       ├── BitacoraController.java
    │   │       └── RecordatorioController.java
    │   │
    │   ├── 📁 config/                  # Configuraciones
    │   │   └── CorsConfig.java
    │   │
    │   └── DemoApplication.java        # Clase principal
    │
    └── resources/
        ├── application.properties      # Configuración MongoDB
        └── static/                     # Frontend Web
            ├── dashboard.html          # Dashboard principal
            ├── mis-plantas.html        # Gestión plantas personales
            ├── registro-planta.html    # Registro nueva planta
            ├── vista-planta.html       # Detalle de planta
            ├── css/plantas.css         # Estilos
            └── js/plantas-api.js       # Lógica frontend
```

---

## 🎨 Interfaz de Usuario

### **Capturas de Pantalla del Sistema:**

**Dashboard Principal**
- Vista general con estadísticas
- Plantas que requieren atención
  <img width="713" height="653" alt="image" src="https://github.com/user-attachments/assets/57ee229b-fb00-444f-8a28-643a0c5125ef" />

**Mis Plantas**
- Lista de plantas personales del usuario
- Estados actuales y últimos cuidados
- Opciones de edición y eliminación
- <img width="721" height="578" alt="image" src="https://github.com/user-attachments/assets/fe3dc0a9-f4ed-4a91-9854-8e037ce15320" />


**Registro de Nueva Planta**
- Selección desde catálogo
- Personalización con apodo y ubicación
- Configuración de estado inicial
  <img width="723" height="645" alt="image" src="https://github.com/user-attachments/assets/aff3b9be-1f2a-4b15-8118-e741d7eb22eb" />
<img width="713" height="653" alt="image" src="https://github.com/user-attachments/assets/0c33adab-f8fb-4174-8652-f72b3670e491" />

---

## ✅ Funcionalidades Implementadas

### 🟢 **Gestión de Catálogo**

| Funcionalidad | Estado | Descripción |
|---------------|--------|-------------|
| Listar plantas del catálogo | ✅ | Obtener todas las plantas disponibles |
| Buscar plantas por nombre | ✅ | Filtro de búsqueda en tiempo real |
| Filtrar por tipo (interior/exterior) | ✅ | Categorización de plantas |
| Ver detalles de planta | ✅ | Información completa de cada especie |
| Inicialización automática | ✅ | Carga de datos de prueba al inicio |

### 🟢 **Módulo: Gestión de Plantas Personales**

| Funcionalidad | Estado | Descripción |
|---------------|--------|-------------|
| Registrar planta personal | ✅ | Agregar planta desde catálogo |
| Listar mis plantas | ✅ | Ver plantas del usuario |
| Editar información de planta | ✅ | Modificar apodo, ubicación, estado |
| Eliminar planta | ✅ | Remover planta del sistema |
| Cambiar estado de planta | ✅ | Actualizar condición actual |

### 🟢 **Módulo: Dashboard y Reportes**

| Funcionalidad | Estado | Descripción |
|---------------|--------|-------------|
| Dashboard interactivo | ✅ | Vista general del sistema |
| Estadísticas por usuario | ✅ | Contadores y métricas |
| API REST completa | ✅ | Endpoints para todas las operaciones |

---

## 🛠️ Estructura del Proyecto

```
src/main/java/com/planta/plantapp/
├── aplicacion/          # Capa de Aplicación
│   ├── interfaces/      # Contratos de servicios
│   └── servicios/       # Lógica de casos de uso
├── dominio/            # Capa de Dominio
│   └── modelo/         # Entidades y objetos de valor
├── infraestructura/    # Capa de Infraestructura
│   ├── documento/      # Documentos MongoDB
│   └── repositorio/    # Implementaciones de persistencia
└── presentacion/       # Capa de Presentación
    └── controlador/    # Controllers REST
```
---

## 📊 Análisis Técnico del Proyecto

### 1. Estilos de Programación 

#### **Orientado a Objetos**
```java
public class Planta {
    private String id;
    private String nombreComun;
    private String nombreCientifico;
    
    public Planta(String nombreComun, String nombreCientifico) {
        this.nombreComun = nombreComun;
        this.nombreCientifico = nombreCientifico;
    }
}
```

#### **Pipeline (Funcional)**
```java
return plantaMongoRepo.findAll()
    .stream()
    .filter(p -> p.getTipo().equalsIgnoreCase(tipo))
    .map(this::convertirADominio)
    .collect(Collectors.toList());
```

#### **Cookbook (Funciones con estado compartido)**
```java
@Service
public class CatalogoService {
    private List<Planta> catalogo; // Estado compartido
    
    public void inicializarCatalogo() { /* función modifica estado */ }
    public List<Planta> obtenerCatalogo() { /* función lee estado */ }
}
```

#### **Things (Objetos con identidad)**
```java
@Document(collection = "registros_plantas")
public class RegistroPlanta {
    @Id
    private String id; // Identidad única
    private String apodo;
    private EstadoPlanta estado;
}
```

### 2. Clean Code

#### **Nombres Descriptivos**
```java
private static final String SEPARADOR_VISUAL = "========================================";
private static final Logger logger = Logger.getLogger(DemoApplication.class.getName());
```

#### **Funciones Pequeñas**
```java
@GetMapping("/catalogo")
public ResponseEntity<List<Planta>> obtenerCatalogoCompleto() {
    try {
        List<Planta> catalogo = servicioPlanta.obtenerTodas();
        return ResponseEntity.ok(catalogo);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
```

#### **Manejo de Errores**
```java
try {
    plantaRepositorio.guardar(planta);
    logger.log(Level.INFO, "Planta guardada exitosamente: {0}", planta.getNombreComun());
    return true;
} catch (Exception e) {
    logger.log(Level.SEVERE, "Error guardando planta", e);
    return false;
}
```

#### **DRY (Don't Repeat Yourself)**
```java
private void validarDatosPlanta(String plantaId, String apodo, String ubicacion) {
    if (plantaId == null || plantaId.trim().isEmpty()) {
        throw new IllegalArgumentException("ID de planta es requerido");
    }
    // Validaciones centralizadas
}
```

### 3. Principios SOLID 

#### **S - Single Responsibility**
```java
@Service
public class CatalogoService {
    // Solo responsable del catálogo de plantas
}

@Repository  
public class PlantaRepositorioImpl {
    // Solo responsable de persistencia
}
```

#### **O - Open/Closed**
```java
public interface IPlantaRepositorio {
    List<Planta> listarTodas();
    // Abierto para extensión, cerrado para modificación
}
```

#### **D - Dependency Inversion**
```java
@Service
public class ServicioPlantaImpl {
    private final IPlantaRepositorio plantaRepositorio; // Depende de abstracción
    
    public ServicioPlantaImpl(IPlantaRepositorio plantaRepositorio) {
        this.plantaRepositorio = plantaRepositorio;
    }
}
```

### 4. Domain-Driven Design 

#### **Entidades**
```java
public class Planta {
    private String id; // Identidad
    private String nombreComun;
    private String descripcion;
}
```

#### **Objetos de Valor**
```java
public enum EstadoPlanta {
    SALUDABLE, NECESITA_AGUA, FLORECIENDO, MARCHITA
}

public enum TipoCuidado {
    RIEGO, PODA, FERTILIZACION, TRANSPLANTE
}
```

#### **Repositorios**
```java
public interface IPlantaRepositorio {
    List<Planta> listarTodas();
    Planta obtenerPorId(String id);
    void guardar(Planta planta);
}
```

#### **Servicios de Dominio**
```java
@Service
public class ServicioPlantaImpl {
    public boolean guardar(Planta planta) {
        // Lógica de negocio compleja
    }
}
```

#### **Módulos**
```
dominio/modelo/planta/     # Agregado Planta
dominio/modelo/cuidado/    # Agregado Cuidado  
dominio/modelo/bitacora/   # Agregado Bitácora
```

### 5. Arquitectura en Capas 

#### **Presentación**
```java
@RestController
@RequestMapping("/plantas")
public class PlantaController {
    // Maneja HTTP requests/responses
}
```

#### **Aplicación**
```java
@Service
public class ServicioPlantaImpl {
    // Orquesta casos de uso
}
```

#### **Dominio**
```java
public class Planta {
    // Entidades y lógica de negocio pura
}
```

#### **Infraestructura**
```java
@Repository
public class PlantaRepositorioImpl {
    // Implementación con MongoDB
}
```

---

## 🚀 Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/plantas/catalogo` | Obtener catálogo completo |
| GET | `/plantas/catalogo/buscar?query=X` | Buscar plantas |
| POST | `/plantas/registrar` | Registrar planta personal |
| GET | `/plantas/usuario/{id}` | Plantas de usuario |
| GET | `/plantas/health` | Estado del servicio |

---

## 🔧 Instalación y Ejecución

```bash
# Clonar repositorio
git clone [repo-url]

# Iniciar MongoDB (Docker)
docker run -d -p 27017:27017 --name plantcare-mongodb mongo:latest

# Compilar y ejecutar
mvn clean package -DskipTests
java -jar target/plantapp-0.0.1-SNAPSHOT.jar

# Acceder a la aplicación
http://localhost:8080/api/dashboard.html
```
