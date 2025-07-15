
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


