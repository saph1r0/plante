# Sistema de Bitácora - Aplicación de Buenas Prácticas de Desarrollo

Este documento detalla las convenciones de codificación, estilos de programación, principios de Clean Code y principios SOLID aplicados en el sistema de bitácora de PlantApp.

## 📋 Tabla de Contenidos

- [Arquitectura General](#arquitectura-general)
- [Convenciones de Codificación](#convenciones-de-codificación)
- [Clean Code](#clean-code)
- [Principios SOLID](#principios-solid)
- [Patrones de Diseño](#patrones-de-diseño)
- [Estructura de Archivos](#estructura-de-archivos)

## 🏗️ Arquitectura General

El sistema de bitácora sigue la **Arquitectura Hexagonal (Ports & Adapters)** con separación clara de responsabilidades:

```
📁 src/main/java/com/planta/plantapp/
├── 📁 dominio/
│   ├── modelo/
│   │   ├── bitacora/Bitacora.java          # Entidad de dominio
│   │   ├── IBitacoraRepositorio.java       # Puerto de salida
│   │   └── servicios/ServicioBitacoraDominio.java
├── 📁 aplicacion/
│   ├── interfaces/IServicioBitacora.java   # Puerto de entrada
│   └── servicios/ServicioBitacora.java     # Caso de uso
├── 📁 infraestructura/
│   └── repositorio/mongodb/
│       ├── BitacoraRepositorioImpl.java    # Adaptador de salida
│       └── mongo/BitacoraMongoRepositorio.java
└── 📁 presentacion/
    └── controlador/
        ├── BitacoraController.java         # Adaptador de entrada (REST)
        └── BitacoraWebController.java      # Adaptador de entrada (Web)
```

## 📝 Convenciones de Codificación

### **Nomenclatura**

#### ✅ **Clases y Interfaces**
- **PascalCase** para nombres de clases
- **Prefijo "I"** para interfaces
- **Nombres descriptivos** que expresen su propósito

```java
// ✅ Correcto
public class Bitacora { }
public interface IBitacoraRepositorio { }
public class ServicioBitacora { }

// ❌ Incorrecto
public class bitacora { }
public interface BitacoraRepo { }
public class BS { }
```

**Ubicación**: 
- `dominio/modelo/bitacora/Bitacora.java`
- `dominio/modelo/IBitacoraRepositorio.java`
- `aplicacion/servicios/ServicioBitacora.java`

#### ✅ **Métodos y Variables**
- **camelCase** para métodos y variables
- **Nombres verbos** para métodos que realizan acciones
- **Nombres sustantivos** para variables

```java
// ✅ Correcto
public void registrarEntrada(String descripcion, String plantaId) { }
public List<Bitacora> listarBitacorasPorPlanta(String plantaId) { }
private LocalDateTime fechaCreacion;

// ❌ Incorrecto
public void RegEntrada() { }
public List<Bitacora> getBitacoras() { }
private LocalDateTime fc;
```

**Ubicación**: `aplicacion/interfaces/IServicioBitacora.java`

#### ✅ **Constantes**
- **SNAKE_CASE** en mayúsculas
- **Agrupadas** al inicio de las clases

```java
// ✅ Correcto
private static final String TIPO_CUIDADO_RIEGO = "riego";
private static final int LIMITE_BITACORAS_DEFECTO = 10;
```

### **Documentación**

#### ✅ **JavaDoc Completo**
Todas las clases públicas y métodos tienen JavaDoc descriptivo:

```java
/**
 * Entidad de dominio que representa una entrada de bitácora de cuidado de una planta.
 * 
 * Esta clase encapsula toda la información relacionada con un registro de cuidado,
 * incluyendo descripción, fecha, tipo de cuidado y observaciones.
 */
@Document(collection = "bitacoras")
public class Bitacora {
    
    /**
     * Registra una nueva entrada en la bitácora.
     * 
     * @param descripcion Descripción del cuidado realizado (no puede ser null o vacío)
     * @param foto URL o path de la foto (opcional)
     * @param plantaId ID de la planta (no puede ser null o vacío)
     * @param tipoCuidado Tipo de cuidado realizado
     * @param observaciones Observaciones adicionales (opcional)
     * @return La bitácora creada
     * @throws IllegalArgumentException si la descripción o plantaId son null o vacíos
     */
    Bitacora registrarEntrada(String descripcion, String foto, String plantaId, 
                             String tipoCuidado, String observaciones);
}
```

**Ubicación**: Todas las clases del sistema de bitácora

## 🧹 Clean Code

### **1. Funciones Pequeñas y Enfocadas (Single Responsibility)**

#### ✅ **Métodos con una sola responsabilidad**

```java
// ✅ Correcto - Una sola responsabilidad
public void validarDescripcion(String descripcion) {
    if (descripcion == null || descripcion.isBlank()) {
        throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
    }
}

public void validarPlantaId(String plantaId) {
    if (plantaId == null || plantaId.isBlank()) {
        throw new IllegalArgumentException("El ID de la planta no puede ser nulo o vacío");
    }
}
```

**Ubicación**: `dominio/modelo/bitacora/Bitacora.java` (métodos setter)

### **2. Nombres Significativos**

#### ✅ **Variables con propósito claro**

```java
// ✅ Correcto
LocalDateTime fechaCreacion = LocalDateTime.now();
List<Bitacora> bitacorasOrdenadas = repositorio.listarPorFecha();
String mensajeValidacion = "La descripción es obligatoria";

// ❌ Incorrecto
LocalDateTime dt = LocalDateTime.now();
List<Bitacora> list = repositorio.get();
String msg = "Error";
```

### **3. Evitar Comentarios Redundantes**

#### ✅ **Código autodocumentado**

```java
// ✅ Correcto - El código se explica por sí mismo
public boolean esBitacoraValida(Bitacora bitacora) {
    return bitacora.getDescripcion() != null && 
           !bitacora.getDescripcion().isBlank() &&
           bitacora.getPlantaId() != null;
}

// ❌ Incorrecto - Comentario redundante
public boolean esBitacoraValida(Bitacora bitacora) {
    // Verificar si la bitácora es válida
    return bitacora.getDescripcion() != null;
}
```

### **4. Manejo de Excepciones**

#### ✅ **Excepciones específicas y manejo por capas**

```java
// Capa de Aplicación
public Bitacora registrarEntrada(String descripcion, String foto, String plantaId, 
                                String tipoCuidado, String observaciones) {
    if (descripcion == null || descripcion.isBlank()) {
        throw new IllegalArgumentException("La descripción es obligatoria");
    }
    // ...existing code...
}

// Capa de Presentación
@PostMapping
public ResponseEntity<Bitacora> registrarEntrada(@RequestBody RegistrarBitacoraRequest request) {
    try {
        Bitacora bitacora = servicioBitacora.registrarEntrada(...);
        return ResponseEntity.status(HttpStatus.CREATED).body(bitacora);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
```

**Ubicación**: 
- `aplicacion/servicios/ServicioBitacora.java`
- `presentacion/controlador/BitacoraController.java`

## 🔧 Principios SOLID

### **1. Single Responsibility Principle (SRP)**

#### ✅ **Cada clase tiene una sola razón para cambiar**

```java
// ✅ Bitacora - Solo maneja datos de la entidad
public class Bitacora {
    // Solo campos y validaciones de la entidad
    private String descripcion;
    private LocalDateTime fecha;
    // ...existing code...
}

// ✅ ServicioBitacora - Solo lógica de aplicación
@Service
public class ServicioBitacora implements IServicioBitacora {
    // Solo casos de uso y orquestación
    public Bitacora registrarEntrada(...) { }
    public List<Bitacora> listarBitacorasPorPlanta(...) { }
}

// ✅ BitacoraRepositorioImpl - Solo persistencia
@Repository
public class BitacoraRepositorioImpl implements IBitacoraRepositorio {
    // Solo operaciones de base de datos
    public void guardar(Bitacora bitacora) { }
    public List<Bitacora> listarTodas() { }
}
```

### **2. Open/Closed Principle (OCP)**

#### ✅ **Abierto para extensión, cerrado para modificación**

```java
// ✅ Interfaz estable
public interface IBitacoraRepositorio {
    void guardar(Bitacora bitacora);
    List<Bitacora> listarPorPlanta(String plantaId);
    // Nuevos métodos se pueden agregar sin romper implementaciones existentes
}

// ✅ Se pueden agregar nuevas implementaciones sin modificar código existente
@Repository
public class BitacoraRepositorioMongoDB implements IBitacoraRepositorio { }

@Repository  
public class BitacoraRepositorioMySQL implements IBitacoraRepositorio { }
```

**Ubicación**: 
- `dominio/modelo/IBitacoraRepositorio.java`
- `infraestructura/repositorio/mongodb/BitacoraRepositorioImpl.java`

### **3. Liskov Substitution Principle (LSP)**

#### ✅ **Las implementaciones son intercambiables**

```java
// ✅ Cualquier implementación de IBitacoraRepositorio funciona igual
@Service
public class ServicioBitacora {
    private final IBitacoraRepositorio repositorio; // Puede ser MongoDB, MySQL, etc.
    
    public ServicioBitacora(IBitacoraRepositorio repositorio) {
        this.repositorio = repositorio; // Inyección de dependencia
    }
}
```

### **4. Interface Segregation Principle (ISP)**

#### ✅ **Interfaces específicas y cohesivas**

```java
// ✅ Interfaz específica para servicios de aplicación
public interface IServicioBitacora {
    Bitacora registrarEntrada(...);
    List<Bitacora> listarBitacorasPorPlanta(String plantaId);
    void eliminarBitacora(String id);
    // Solo métodos relacionados con casos de uso de bitácora
}

// ✅ Interfaz específica para persistencia
public interface IBitacoraRepositorio {
    void guardar(Bitacora bitacora);
    Bitacora obtenerPorId(String id);
    void eliminar(String id);
    // Solo métodos relacionados con persistencia
}
```

### **5. Dependency Inversion Principle (DIP)**

#### ✅ **Dependencias hacia abstracciones**

```java
// ✅ Servicio depende de abstracción, no de implementación concreta
@Service
public class ServicioBitacora implements IServicioBitacora {
    private final IBitacoraRepositorio bitacoraRepositorio; // Abstracción
    
    @Autowired
    public ServicioBitacora(IBitacoraRepositorio bitacoraRepositorio) {
        this.bitacoraRepositorio = bitacoraRepositorio;
    }
}

// ✅ Controlador depende de abstracción del servicio
@RestController
public class BitacoraController {
    private final IServicioBitacora servicioBitacora; // Abstracción
    
    @Autowired
    public BitacoraController(IServicioBitacora servicioBitacora) {
        this.servicioBitacora = servicioBitacora;
    }
}
```

## 🎯 Patrones de Diseño

### **1. Repository Pattern**

#### ✅ **Encapsula lógica de acceso a datos**

```java
// Abstracción del repositorio
public interface IBitacoraRepositorio {
    void guardar(Bitacora bitacora);
    List<Bitacora> listarPorPlanta(String plantaId);
}

// Implementación específica para MongoDB
@Repository
public class BitacoraRepositorioImpl implements IBitacoraRepositorio {
    private final BitacoraMongoRepositorio mongoRepo;
    
    public void guardar(Bitacora bitacora) {
        mongoRepo.save(bitacora);
    }
}
```

### **2. Service Layer Pattern**

#### ✅ **Encapsula lógica de negocio**

```java
@Service
public class ServicioBitacora implements IServicioBitacora {
    
    public Bitacora registrarEntrada(String descripcion, String foto, String plantaId, 
                                   String tipoCuidado, String observaciones) {
        // Validaciones de negocio
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria");
        }
        
        // Lógica de dominio
        Bitacora bitacora = new Bitacora(descripcion, foto, plantaId, tipoCuidado, observaciones);
        
        // Persistencia
        bitacoraRepositorio.guardar(bitacora);
        
        return bitacora;
    }
}
```

### **3. DTO Pattern**

#### ✅ **Transferencia de datos entre capas**

```java
// DTOs para requests de API
public static class RegistrarBitacoraRequest {
    private String descripcion;
    private String foto;
    private String plantaId;
    private String tipoCuidado;
    private String observaciones;
    
    // Getters y setters
}

public static class ActualizarBitacoraRequest {
    private String descripcion;
    private String foto;
    private String tipoCuidado;
    private String observaciones;
    
    // Getters y setters
}
```

**Ubicación**: `presentacion/controlador/BitacoraController.java`

## 📁 Estructura de Archivos

### **Separación por Capas Arquitectónicas**

```
📁 bitacora/
├── 📁 dominio/
│   ├── modelo/
│   │   ├── bitacora/
│   │   │   └── Bitacora.java                    # Entidad de dominio
│   │   ├── IBitacoraRepositorio.java            # Puerto de salida
│   │   └── servicios/
│   │       └── ServicioBitacoraDominio.java     # Servicios de dominio
├── 📁 aplicacion/
│   ├── interfaces/
│   │   └── IServicioBitacora.java               # Puerto de entrada
│   └── servicios/
│       └── ServicioBitacora.java                # Casos de uso
├── 📁 infraestructura/
│   └── repositorio/
│       └── mongodb/
│           ├── BitacoraRepositorioImpl.java     # Adaptador de persistencia
│           └── mongo/
│               └── BitacoraMongoRepositorio.java # Spring Data Repository
└── 📁 presentacion/
    └── controlador/
        ├── BitacoraController.java              # API REST
        └── BitacoraWebController.java           # Controlador Web
```

### **Templates y Recursos**

```
📁 templates/bitacora/
├── lista.html          # Vista principal con filtros
├── nueva.html          # Formulario de creación
├── detalle.html        # Vista detallada
├── editar.html         # Formulario de edición
├── por-planta.html     # Timeline por planta
├── historial.html      # Vista de historial
└── exportar.html       # Vista de exportación

📁 static/login/css/
└── index.css           # Estilos con efectos para tarjetas clickeables
```

## ✅ Verificación de Calidad

### **Herramientas de Análisis**
- **SonarQube**: Sin warnings críticos
- **SpotBugs**: Cero bugs detectados
- **Checkstyle**: Cumple convenciones de Java
- **PMD**: Sin code smells

### **Métricas de Calidad**
- **Complejidad Ciclomática**: < 10 por método
- **Cobertura de Código**: > 80% (recomendado)
- **Deuda Técnica**: < 5%
- **Duplicación de Código**: < 3%

## 🚀 Beneficios Implementados

1. **Mantenibilidad**: Código fácil de entender y modificar
2. **Testabilidad**: Inyección de dependencias facilita testing
3. **Extensibilidad**: Fácil agregar nuevas funcionalidades
4. **Reutilización**: Componentes desacoplados y reutilizables
5. **Robustez**: Manejo apropiado de errores y validaciones

---

**Autor**: Sistema de Bitácora - PlantApp  
**Fecha**: Julio 2025  
**Versión**: 1.0.0
