
---

# 🌿 PlantApp - Sistema de Gestión de Plantas (Java + DDD)

## 📌 Descripción del Proyecto

Sistema de dominio para la gestión de plantas, cuidados y recordatorios implementado en Java bajo los principios de **Domain-Driven Design (DDD)**. Se ha trabajado con:

* Modelado de entidades centrales (Planta, Cuidado, Recordatorio)
* Fábricas especializadas para crear objetos de dominio
* Repositorios con contratos definidos
* Validación de reglas de negocio

El desarrollo se realizó utilizando **IntelliJ IDEA** como entorno principal. Durante la implementación, se llevó a cabo una **reestructuración completa del proyecto** para alinearlo con los estándares de arquitectura limpia (DDD), separando adecuadamente las capas de aplicación, dominio, infraestructura y presentación.

---

## 🛠️ Estructura del Código

```bash
|src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── planta/
│   │           └── plantapp/
│   │               ├── aplicacion/
│   │               │   ├── interfaces/
│   │               │   │   └── IServicioUsuario.java
│   │               │   └── servicios/
│   │               │       └── ServicioUsuarioImpl.java
│   │               ├── dominio/
│   │               │   └── usuario/
│   │               │       ├── IUsuarioRepositorio.java
│   │               │       └── modelo/
│   │               │           └── Usuario.java
│   │               ├── infraestructura/
│   │               │   ├── entidad/
│   │               │   │   └── UsuarioEntidad.java
│   │               │   └── repositorio/
│   │               │       └── mysql/
│   │               │           ├── jpa/
│   │               │           │   └── UsuarioJpaRepositorio.java
│   │               │           └── UsuarioRepositorioImpl.java
│   │               └── presentacion/
│   │                   └── controlador/
│   │                       └── UsuarioController.java
│   └── resources/
│       ├── static/
│       │   └── login/
│       │       ├── css/
│       │       │   └── styles.css
│       │       └── js/
│       │           └── script.js
│       └── templates/
│           └── login/
│               └── login.html
```

---

## 🎨 Interfaz de Usuario

Capturas de la interfaz implementada:

* Pantalla de inicio de sesión
* Pantalla de registro de usuario

![img.png](img.png)
![img\_1.png](img_1.png)

---

## ✅ Feature: Registro e Inicio de Sesión Seguro - PlantApp

### 🎯 Objetivo

Permitir que los usuarios se registren e inicien sesión de forma segura, aplicando validación, persistencia y protección de contraseñas.

---

## 📝 Historias de Usuario Cubiertas

### 🟢 H.2.1.1 - Validar Datos de Entrada en el Formulario

| Tarea                                                                           | Estado       |
| ------------------------------------------------------------------------------- | ------------ |
| Diseñar formulario con campos: nombre, correo electrónico, contraseña           | ✅ Completado |
| Validar formato de correo electrónico                                           | ✅ Completado |
| Validar contraseña segura (mínimo 8 caracteres, al menos una letra y un número) | ✅ Completado |
| Mostrar mensajes de error claros y amigables al usuario                         | ✅ Completado |

---

### 🟢 H.2.1.2 - Guardar Datos en la Base de Datos y Encriptar Contraseña

| Tarea                                                    | Estado       |
| -------------------------------------------------------- | ------------ |
| Conexión establecida con la base de datos (MySQL)        | ✅ Completado |
| Almacenar usuario de forma segura en la tabla `usuarios` | ✅ Completado |
| Asegurar que nunca se guarden contraseñas en texto plano | ✅ Completado |

---

### 🔜 H.2.1.3 Verificación de cuenta por correo electrónico (Próximamente)

---

## 🔐 Seguridad en el Manejo de Credenciales (Secret Leaks)

| Tarea                                                                   | Estado       |
| ----------------------------------------------------------------------- | ------------ |
| Detectar credenciales visibles en `application.properties`              | ✅ Completado |
| Reemplazar credenciales por variables de entorno (`DB_USER`, `DB_PASS`) | ✅ Completado |
| Validar que las contraseñas se almacenan encriptadas (no reversibles)   | ✅ Completado |

---

## 💡  Aplicación de Convenciones de Codificación (Java)

#### 📌 **Nomenclatura y Estructura**
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

// ✔️ Interfaces sin prefijo 'I' (opcional)
public interface UsuarioRepository {
    Usuario buscarPorEmail(String email);
}
```

#### 📌 **Documentación**
```java
/**
 * Autentica un usuario en el sistema
 * @param email Correo electrónico del usuario
 * @param password Contraseña en texto plano
 * @return Usuario autenticado
 * @throws AuthException Si las credenciales son inválidas
 * @see Usuario
 */
public Usuario autenticar(String email, String password) throws AuthException {
    ...
}
```


✅ **Implementación cumple con todos los requisitos de calidad y convenciones**  
🔗 **Incluye:** Evidencias de código, métricas de mejora y prácticas documentadas