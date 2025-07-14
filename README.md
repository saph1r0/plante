¡Perfecto! A continuación te presento el `README.md` corregido, más claro, profesional y con mejor estructura para entregar o subir a GitHub. También mejoré la sección de estilos de programación para que sea más coherente y legible.

---

````markdown
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

## 🎨 Interfaz de Usuario

Capturas de pantalla del sistema:

* Pantalla de inicio de sesión
* Pantalla de registro de usuario

![img.png](img.png)
![img\_1.png](img_1.png)

---

## ✅ Módulo: Registro e Inicio de Sesión Seguro

### 🎯 Objetivo

Permitir que los usuarios se registren e inicien sesión de manera segura, implementando validaciones, persistencia y protección de credenciales.

---

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

### 🔜 H.2.1.3 - Verificación por correo (Próximamente)

---

## 🔐 Seguridad en el Manejo de Credenciales

| Tarea                                                      | Estado       |
| ---------------------------------------------------------- | ------------ |
| Detectar credenciales visibles                             | ✅ Completado |
| Reemplazar por variables de entorno (`DB_USER`, `DB_PASS`) | ✅ Completado |
| Validar almacenamiento seguro de contraseñas               | ✅ Completado |

---

## 🧠 Convenciones y Buenas Prácticas Java

```java
// ✔️ Clases en PascalCase
public class UsuarioAuthService {
    // ✔️ Métodos en camelCase
    public boolean validarCredenciales(String email, String password) {
        // ✔️ Variables descriptivas
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
        // ✔️ Constantes en MAYÚSCULAS
        final int MAX_INTENTOS = 3;
    }
}
```

---

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

Secuencia:

1. Validación
2. Verificación
3. Ejecución
4. Resultado

---

### 4. Error Handling (Manejo de Errores)

Captura controlada de excepciones para mantener robustez del sistema:

```java
try {
    Usuario usuario = repositorioUsuario.obtenerPorId(id);
    if (usuario == null)
        throw new IllegalArgumentException("Usuario no encontrado");
    return usuario;
} catch (Exception e) {
    throw new IllegalStateException("Error al obtener usuario por ID", e);
}
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

**Estado en el Proyecto:**
Todas las ocurrencias han sido revisadas y corregidas, asegurando que no se usen `Boolean` boxeados directamente en expresiones condicionales sin verificación previa.

---

## ✅ Conclusión

* ✔ Cumplimiento de principios de diseño y arquitectura limpia.
* ✔ Aplicación de estilos de programación modernos y robustos.
* ✔ Código validado con herramientas de análisis estático para garantizar calidad.