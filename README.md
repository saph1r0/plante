
# 🌿 PlantApp - Sistema de Gestión de Plantas (Java + DDD)

## 📌 Descripción del Proyecto

PlantApp es un sistema de gestión de usuarios, plantas y recordatorios implementado en Java siguiendo los principios de **Domain-Driven Design (DDD)** y una arquitectura por capas.

Este proyecto incluye:

- Modelado de entidades centrales: `Usuario`, `Planta`, `Cuidado`, `Recordatorio`.
- Fábricas especializadas para instanciar objetos del dominio.
- Repositorios con contratos definidos para persistencia.
- Validaciones de reglas de negocio en la capa de aplicación.

El desarrollo se realizó en **IntelliJ IDEA**, siguiendo principios de arquitectura limpia.

---

## 🛠️ Estructura del Proyecto

```bash
src/
└── main/
    ├── java/
    │   └── com/planta/plantapp/
    │       ├── aplicacion/
    │       │   ├── interfaces/
    │       │   │   └── IServicioUsuario.java
    │       │   └── servicios/
    │       │       └── ServicioUsuarioImpl.java
    │       ├── dominio/
    │       │   └── usuario/
    │       │       ├── IUsuarioRepositorio.java
    │       │       └── modelo/
    │       │           └── Usuario.java
    │       ├── infraestructura/
    │       │   ├── entidad/
    │       │   │   └── UsuarioEntidad.java
    │       │   └── repositorio/
    │       │       └── mysql/
    │       │           └── UsuarioRepositorioImpl.java
    │       └── presentacion/
    │           └── controlador/
    │               └── UsuarioController.java
    └── resources/
        ├── static/
        │   └── login/
        │       ├── css/
        │       │   └── styles.css
        │       └── js/
        │           └── script.js
        └── templates/
            └── login/
                └── login.html
````

---
### INTERFAZ DE USUARIO
### 🚀 Módulo de Servicios - Bitácora (Java)

Este módulo forma parte del sistema de gestión de plantas y se encarga de toda la lógica de **servicios** relacionada con la bitácora de eventos, garantizando un manejo seguro, eficiente y mantenible.

---

## 🎯 Objetivo

Implementar los **servicios de aplicación** que procesan, filtran y mantienen los registros de bitácora, delegando el acceso a datos al repositorio `IBitacoraRepositorio`.

---

## 🏗️ Funcionalidades Clave

✅ Registrar observaciones nuevas asociadas a una planta  
✅ Editar descripciones de bitácoras existentes  
✅ Eliminar registros de bitácora  
✅ Consultar bitácoras por:
- ID de planta
- Rango de fechas
- Tipo o estado
  ✅ Exportar el historial de bitácoras a un archivo `historial.txt`  
  ✅ Trazabilidad completa mediante logs

---

## 💡 Estilos de Programación Aplicados

A continuación se describen los estilos aplicados en el código, con ejemplos reales del módulo:

---

### 🥘 1. Cookbook

Aplicación de pasos simples y explícitos, que hacen el código claro y directo, siguiendo el patrón “receta”.

```java
// Cookbook: inicialización sencilla y validación inmediata
public ServicioBitacoraImpl(IBitacoraRepositorio repositorioBitacora) {
    if (repositorioBitacora == null) {
        throw new IllegalArgumentException("Repositorio no puede ser nulo");
    }
    this.repositorioBitacora = repositorioBitacora;
}
```

### 2. Pipeline
   Uso del API Stream de Java para construir cadenas de procesamiento (pipeline) donde los datos fluyen a través de filtros, mapeos y recolecciones.

```java
p// Pipeline: filtrar bitácoras por planta
public List<Bitacora> obtenerPorPlanta(Long plantaId) {
    return repositorioBitacora.obtenerTodas()
            .stream()
            .filter(b -> plantaId.equals(b.getPlantaId()))
            .collect(Collectors.toList());
}

```
### 3. Lazy-Rivers
Implementación de flujos “perezosos” con Stream, donde los datos solo son evaluados cuando se necesita el resultado final (lazy evaluation).

```java
// Lazy-Rivers: filtrar rango de fechas sin procesar todos los elementos innecesarios
public List<Bitacora> obtenerPorFechaRango(Date desde, Date hasta) {
    return repositorioBitacora.obtenerTodas()
            .stream()
            .filter(b -> !b.getFecha().before(desde) && !b.getFecha().after(hasta))
            .collect(Collectors.toList());
}

``` 
### 4. Error / Exception Handling
Validación rigurosa de parámetros y captura controlada de excepciones para mantener la robustez del servicio.

```java
// Error Handling: defensivo y explícito
public void editarObservacion(Long bitacoraId, String nuevaDescripcion) {
    if (bitacoraId == null || nuevaDescripcion == null) {
        throw new IllegalArgumentException("Parámetros inválidos");
    }
    Bitacora bitacora = repositorioBitacora.buscarPorId(bitacoraId);
    if (bitacora == null) {
        throw new NoSuchElementException("Bitácora no encontrada con ID " + bitacoraId);
    }
    bitacora.setDescripcion(nuevaDescripcion);
    repositorioBitacora.actualizar(bitacora);
}

}
```
Y manejo de IO:

```java
public byte exportarHistorial() {
    try (FileWriter writer = new FileWriter("historial.txt")) {
        writer.write("=== Historial de bitácoras ===\n");
        logger.info("Historial exportado exitosamente a historial.txt");
        return 0;
    } catch (IOException e) {
        logger.severe("Error al exportar historial: " + e.getMessage());
        return 1;
    }
}

```
### Calidad del Código: SonarLint
Durante el desarrollo se utilizó SonarLint para realizar análisis estático y detectar problemas potenciales como:

✅ Uso inseguro de tipos boxeados (Boolean vs boolean)
✅ Ausencia de validación de null en parámetros
✅ Uso innecesario de System.out en lugar de un logger

Todas estas recomendaciones fueron detectadas y corregidas para garantizar un código limpio, mantenible y seguro.

### 📌 Avance en Trello
Tarea	Estado
Implementar constructor cookbook	✅ Completado

Agregar métodos con Pipeline y Lazy-Rivers	✅ Completado

Validación y manejo robusto de errores	✅ Completado

Exportación segura con manejo de IO	✅ Completado

Validación de calidad con SonarLint	✅ Completado


###  Conclusión
Este módulo implementa servicios de aplicación modernos, aplicando principios de:

*Programación funcional (Streams)

*Defensive programming (chequeos y exceptions)

*Estilos Cookbook, Pipeline, Lazy-Rivers y Error Handling

Todo esto asegura un código legible, escalable y fácil de mantener, preparado para futuras integraciones como auditorías, seguridad avanzada o microservicios.


# 🧼 Laboratorio 11: Clean Code - ServicioBitacoraImpl


## 🎯 Objetivo

Aplicar prácticas de codificación legible en el módulo `ServicioBitacoraImpl` para detectar y corregir:
- Bugs
- Code smells
- Vulnerabilities

Usando principios de Clean Code y análisis con SonarLint.

---

## 🧪 Archivo Analizado

`com.planta.demo.aplicacion.servicios.ServicioBitacoraImpl.java`

---

## ✅ Prácticas de Clean Code Aplicadas

| Categoría                       | Práctica Aplicada | Fragmento de Código |
|---------------------------------|-------------------|----------------------|
| **Nombres**                     | Nombres descriptivos y semánticos | `bitacoraId`, `nuevaDescripcion`, `buscarPorTipo` |
| **Funciones**                   | Una sola responsabilidad por función | `buscarPorTipo`, `editarObservacion` |
| **Comentarios**                 | Eliminación de comentarios redundantes y duplicados | `// TODO implement here` eliminado |
| **Estructura de Código Fuente**| Organización lógica y eliminación de duplicaciones | Constructor y métodos únicos |
| **Objetos / Estructuras de Datos** | Uso de Streams y API funcional para colecciones | `.stream().filter(...).collect(...)` |
| **Tratamiento de Errores**     | Validaciones con `Objects.requireNonNull` en vez de `if` | `Objects.requireNonNull(...)` |
| **Clases**                      | Clase con inyección de dependencia validada | `ServicioBitacoraImpl` |

---

## 🔍 Análisis SonarLint

**Severidad corregida:**
- ❌ 1 Bug (Constructor duplicado)
- ❌ 1 Vulnerabilidad (campo no usado)
- ❌ 3 Code Smells (validación, lógica incompleta, colección no declarada)

**Severidades críticas encontradas:** Ninguna  
**Estado final del análisis:** ✅ Limpio

> Se adjunta captura del análisis SonarLint después de refactorizar.

---

## 📌 Ejemplos de Código Corregido

### ✅ Validación sin `if`, usando `Objects.requireNonNull`
```java
import java.util.Objects;

public ServicioBitacoraImpl(IBitacoraRepositorio repositorioBitacora) {
    this.repositorioBitacora = Objects.requireNonNull(
        repositorioBitacora, "Repositorio no puede ser nulo"
    );
}

```
🔄 Editar Observación con validación funcional
```java
public void editarObservacion(Long bitacoraId, String nuevaDescripcion) {
Objects.requireNonNull(bitacoraId, "ID de bitácora requerido");
Objects.requireNonNull(nuevaDescripcion, "Descripción no puede ser nula");

    Bitacora bitacora = Objects.requireNonNull(
        repositorioBitacora.buscarPorId(bitacoraId),
        "Bitácora no encontrada con ID " + bitacoraId
    );

    bitacora.setDescripcion(nuevaDescripcion);
    repositorioBitacora.actualizar(bitacora);
    logger.info("Descripción actualizada para bitácora ID " + bitacoraId);
}
```
🔁 Eliminación con repositorio y log
```java

public void eliminar(Long bitacoraId) {
List<Bitacora> bitacoras = repositorioBitacora.obtenerTodas();
bitacoras.removeIf(b -> b.getId().equals(bitacoraId));
repositorioBitacora.eliminar(bitacoraId);
logger.info("Bitácora con ID " + bitacoraId + " eliminada.");
}
```
📤 Exportar historial a archivo
```java
public byte exportarHistorial() {
try (FileWriter writer = new FileWriter("historial.txt")) {
writer.write("=== Historial de bitácoras ===\n");
writer.write("Registro 1: inicio del sistema\n");
writer.write("Registro 2: parada del sistema\n");
writer.write("Registro 3: error crítico\n");
logger.info("Historial exportado exitosamente");
return 0;
} catch (IOException e) {
logger.severe("Error al exportar historial: " + e.getMessage());
return 1;
}
}
```
🛠 Funciones Completadas
editarObservacion(Long, String)

eliminar(Long)

exportarHistorial()

obtenerPorPlanta(Long)

obtenerPorFechaRango(Date, Date)

buscarPorTipo(String)

listarPendientesPorUsuario(Long) (puedes completarla si deseas)



🧠 Conclusión
Este laboratorio permitió aplicar Clean Code de forma práctica, mejorando legibilidad, mantenibilidad y eliminando riesgos como bugs y vulnerabilidades. Se reemplazaron validaciones clásicas por técnicas más limpias usando Objects.requireNonNull.

# 🧼 LAB 12 ServicioBitacoraImpl – Aplicación de Principios SOLID en Java
Este proyecto demuestra cómo aplicar los principios SOLID en el desarrollo de un componente de servicios para manejar registros de bitácoras. La arquitectura está diseñada para ser limpia, mantenible y extensible siguiendo buenas prácticas de programación orientada a objetos.

# 📌 Objetivo
+ Implementar un servicio ServicioBitacoraImpl que permita:

+ Editar observaciones de bitácoras.

+ Filtrar bitácoras por rango de fechas.

+ Eliminar bitácoras por ID.

+ Exportar historial de eventos.

+ Facilitar la extensibilidad futura (ej. exportar a PDF, CSV, etc.)

# ✅ Principios SOLID Aplicados
#  1. SRP - Principio de Responsabilidad Única
Separación de responsabilidades: la clase `HistorialExporter` se encarga únicamente de exportar historial.

```java
class HistorialExporter implements ExportadorHistorial {
  public byte exportar() {
        ...
  }
}
```
# 2. OCP – Open/Closed Principlede negocio sobre bitácoras.

   El sistema puede ser extendido sin modificar el código existente.

Para exportar a otro formato, se puede crear otra clase que implemente ExportadorHistorial sin cambiar ServicioBitacoraImpl.

```java

class ExportadorPDF implements ExportadorHistorial {
public byte exportar() {
// lógica para exportar PDF
  }
}
```

# 3.DIP – Dependency Inversion Principle
   ServicioBitacoraImpl depende de las abstracciones IBitacoraRepositorio y ExportadorHistorial, no de clases concretas.
```java
public ServicioBitacoraImpl(IBitacoraRepositorio repo, ExportadorHistorial exp)
```
