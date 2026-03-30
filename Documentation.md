## 📚 **Documentación Técnica del Proyecto Intermodular**

### **GOSTAR - Red Social de Recetas**

**Manuel Hay Fernández**  
Desarrollo de Aplicaciones Multiplataforma (2º curso)  
Curso 2025/2026

---

## 📑 **Índice**

1. [Arquitectura del sistema](#1-arquitectura-del-sistema)
2. [API](#2-api)
3. [Base de datos](#3-base-de-datos)
4. [Aplicación Web](#4-aplicación-web)
5. [Aplicación Móvil](#5-aplicación-móvil)
6. [Aplicación de Escritorio](#6-aplicación-de-escritorio)
7. [Seguridad](#7-seguridad)
8. [Despliegue](#8-despliegue)
9. [Pruebas](#9-pruebas)
10. [Guía de uso y mantenimiento](#10-guía-de-uso-y-mantenimiento)
11. [Mejoras futuras](#11-mejoras-futuras)

---

## 1. Arquitectura del sistema

### **Esquema general**

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CLIENTES                                       │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐              │
│  │  Aplicación Web │  │ Aplicación Móvil│  │Aplicación       │              │
│  │  (PHP/HTML/CSS/JS)│ │ (Android/Java) │  │Escritorio       │              │
│  │                 │  │                 │  │(.NET WinForms)  │              │
│  └────────┬────────┘  └────────┬────────┘  └────────┬────────┘              │
│           │                    │                    │                        │
│           └────────────────────┼────────────────────┘                        │
│                                │ HTTP/JSON                                  │
└────────────────────────────────┼────────────────────────────────────────────┘
                                 ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                           API REST (Java)                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│  • JAX-RS / Jersey                                                          │
│  • Apache Tomcat 10                                                         │
│  • Autenticación mediante tokens (UUID)                                     │
└────────────────────────────────┬────────────────────────────────────────────┘
                                 │ JDBC
                                 ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         Base de Datos MySQL                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│  • MariaDB / MySQL                                                          │
│  • Tablas: usuarios, publicaciones, ingredientes, pasos, likes             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### **Tecnologías principales**

| Componente | Tecnología | Versión |
|------------|------------|---------|
| **API** | Java + JAX-RS (Jersey) | Java 11, Jersey 3.0.3 |
| **Servidor API** | Apache Tomcat | 10.0.27 |
| **Base de datos** | MySQL / MariaDB | 8.0 / 10.4 |
| **Aplicación Web** | PHP + HTML/CSS/JS | PHP 8.x |
| **Aplicación Móvil** | Android (Java) | SDK 34 (Android 14) |
| **Aplicación Escritorio** | .NET Framework (C#) | .NET Framework 4.8 |

---

## 2. API

### **Lista de endpoints**

#### **Autenticación (/auth)**

| Endpoint | Método | Descripción | Autenticación |
|----------|--------|-------------|---------------|
| `/auth/registro` | POST | Registro de nuevo usuario | No |
| `/auth/login` | POST | Inicio de sesión | No |
| `/auth/logout` | POST | Cierre de sesión | Sí (token) |
| `/auth/verificar` | GET | Verificar validez del token | Sí (token) |
| `/auth/perfil` | GET | Obtener perfil del usuario | Sí (token) |
| `/auth/perfil` | PUT | Actualizar perfil | Sí (token) |
| `/auth/avatar` | POST | Actualizar avatar (URL) | Sí (token) |
| `/auth/cambiar-password` | POST | Cambiar contraseña | Sí (token) |
| `/auth/usuario` | DELETE | Eliminar cuenta | Sí (token) |

#### **Publicaciones (/publicaciones)**

| Endpoint | Método | Descripción | Autenticación |
|----------|--------|-------------|---------------|
| `/publicaciones` | GET | Obtener todas las recetas (feed) | Sí (token) |
| `/publicaciones` | POST | Crear nueva receta | Sí (token) |
| `/publicaciones/{id}` | GET | Obtener receta por ID | Sí (token) |
| `/publicaciones/{id}` | PUT | Editar receta | Sí (token) |
| `/publicaciones/{id}` | DELETE | Eliminar receta | Sí (token) |
| `/publicaciones/{id}/like` | POST | Dar like a una receta | Sí (token) |
| `/publicaciones/{id}/like` | DELETE | Quitar like | Sí (token) |
| `/publicaciones/usuario/{usuarioId}` | GET | Recetas de un usuario | Sí (token) |

### **Autenticación**

La API utiliza **tokens de sesión** generados con UUID. El flujo es el siguiente:

1. El usuario se registra o inicia sesión
2. El servidor genera un token único y lo devuelve
3. El cliente debe incluir el token en todas las peticiones autenticadas en el header:
   ```
   Authorization: Bearer <token>
   ```

### **Ejemplos de peticiones y respuestas**

#### **Registro de usuario**

**Petición:**
```http
POST /auth/registro HTTP/1.1
Content-Type: application/json

{
    "username": "chef_manuel",
    "email": "manuel@example.com",
    "passwordHash": "miClave123",
    "nombre": "Manuel Hay",
    "bio": "Apasionado de la cocina"
}
```

**Respuesta (201 Created):**
```json
{
    "token": "550e8400-e29b-41d4-a716-446655440000",
    "usuario": {
        "id": 1,
        "username": "chef_manuel",
        "email": "manuel@example.com",
        "nombre": "Manuel Hay",
        "bio": "Apasionado de la cocina",
        "fechaRegistro": "2026-03-30T10:00:00"
    },
    "mensaje": "Registro exitoso"
}
```

#### **Crear una receta**

**Petición:**
```http
POST /publicaciones HTTP/1.1
Authorization: Bearer 550e8400-e29b-41d4-a716-446655440000
Content-Type: application/json

{
    "titulo": "Tortilla de Patatas",
    "duracion": "30 minutos",
    "imagenPrincipal": "https://ejemplo.com/tortilla.jpg",
    "descripcion": "La clásica tortilla española",
    "dificultad": "Media",
    "calorias": 350,
    "ingredientes": [
        "4 huevos",
        "500g patatas",
        "1 cebolla",
        "Aceite de oliva",
        "Sal"
    ],
    "pasos": [
        "Pelar y cortar las patatas",
        "Freír las patatas",
        "Batir los huevos",
        "Mezclar y cuajar"
    ]
}
```

**Respuesta (201 Created):**
```json
{
    "id": 1,
    "usuarioId": 1,
    "usuarioNombre": "chef_manuel",
    "titulo": "Tortilla de Patatas",
    "duracion": "30 minutos",
    "imagenPrincipal": "https://ejemplo.com/tortilla.jpg",
    "likes": 0,
    "likedByCurrentUser": false
}
```

#### **Obtener todas las recetas (feed)**

**Petición:**
```http
GET /publicaciones?page=1&limit=20 HTTP/1.1
Authorization: Bearer 550e8400-e29b-41d4-a716-446655440000
```

**Respuesta (200 OK):**
```json
[
    {
        "id": 1,
        "usuarioId": 1,
        "usuarioNombre": "chef_manuel",
        "titulo": "Tortilla de Patatas",
        "duracion": "30 minutos",
        "imagenPrincipal": "https://ejemplo.com/tortilla.jpg",
        "likes": 5,
        "likedByCurrentUser": true,
        "fechaCreacion": "2026-03-30T10:00:00"
    },
    {
        "id": 2,
        "usuarioId": 2,
        "usuarioNombre": "cocinera_ana",
        "titulo": "Gazpacho Andaluz",
        "duracion": "15 minutos",
        "imagenPrincipal": "https://ejemplo.com/gazpacho.jpg",
        "likes": 12,
        "likedByCurrentUser": false,
        "fechaCreacion": "2026-03-29T18:30:00"
    }
]
```

#### **Dar like**

**Petición:**
```http
POST /publicaciones/1/like HTTP/1.1
Authorization: Bearer 550e8400-e29b-41d4-a716-446655440000
```

**Respuesta (200 OK):**
```json
{
    "likes": 6,
    "liked": true
}
```

---

## 3. Base de datos

### **Diagrama entidad-relación**

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│    usuarios     │     │  publicaciones  │     │publicacion_     │
├─────────────────┤     ├─────────────────┤     │  ingredientes   │
│ id (PK)         │◄────│ usuario_id (FK) │     ├─────────────────┤
│ username        │     │ id (PK)         │────►│ id (PK)         │
│ email           │     │ titulo          │     │ publicacion_id  │
│ password_hash   │     │ duracion        │     │ ingrediente     │
│ nombre          │     │ imagen_principal│     └─────────────────┘
│ bio             │     │ descripcion     │
│ avatar_url      │     │ dificultad      │     ┌─────────────────┐
│ fecha_registro  │     │ calorias        │     │publicacion_pasos│
│ ultimo_acceso   │     │ likes           │     ├─────────────────┤
│ activo          │     │ fecha_creacion  │     │ id (PK)         │
└─────────────────┘     └─────────────────┘     │ publicacion_id  │
                              │                 │ paso            │
                              │                 │ orden           │
                              ▼                 └─────────────────┘
┌─────────────────┐     ┌─────────────────┐
│publicacion_likes│     │publicacion_     │
├─────────────────┤     │  imagenes       │
│ usuario_id (PK) │     ├─────────────────┤
│ publicacion_id  │     │ id (PK)         │
│ fecha           │     │ publicacion_id  │
└─────────────────┘     │ url             │
                        │ orden           │
                        └─────────────────┘
```

### **Descripción de tablas y campos**

#### **usuarios**
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Identificador único |
| username | VARCHAR(50) UNIQUE | Nombre de usuario |
| email | VARCHAR(100) UNIQUE | Correo electrónico |
| password_hash | VARCHAR(255) | Contraseña hasheada (SHA-256) |
| nombre | VARCHAR(100) | Nombre completo |
| bio | TEXT | Biografía del usuario |
| avatar_url | VARCHAR(500) | URL del avatar |
| fecha_registro | TIMESTAMP | Fecha de registro |
| ultimo_acceso | TIMESTAMP | Último inicio de sesión |
| activo | BOOLEAN | Estado de la cuenta |

#### **publicaciones**
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Identificador único |
| usuario_id | INT (FK) | Autor de la receta |
| titulo | VARCHAR(200) | Título de la receta |
| duracion | VARCHAR(50) | Tiempo de preparación |
| imagen_principal | VARCHAR(500) | URL de la imagen principal |
| descripcion | TEXT | Descripción de la receta |
| dificultad | VARCHAR(20) | Fácil, Media, Difícil |
| calorias | INT | Calorías aproximadas |
| likes | INT | Contador de likes |
| fecha_creacion | TIMESTAMP | Fecha de publicación |

#### **publicacion_ingredientes**
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Identificador único |
| publicacion_id | INT (FK) | Receta asociada |
| ingrediente | TEXT | Nombre y cantidad |

#### **publicacion_pasos**
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Identificador único |
| publicacion_id | INT (FK) | Receta asociada |
| paso | TEXT | Descripción del paso |
| orden | INT | Orden de los pasos |

#### **publicacion_imagenes**
| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Identificador único |
| publicacion_id | INT (FK) | Receta asociada |
| url | VARCHAR(500) | URL de la imagen |
| orden | INT | Orden de las imágenes |

#### **publicacion_likes**
| Campo | Tipo | Descripción |
|-------|------|-------------|
| usuario_id | INT (PK, FK) | Usuario que da like |
| publicacion_id | INT (PK, FK) | Receta que recibe like |
| fecha | TIMESTAMP | Fecha del like |

### **Reglas importantes**

1. **Integridad referencial**: Todas las claves foráneas tienen `ON DELETE CASCADE` para mantener consistencia
2. **Username y email únicos**: No puede haber dos usuarios con el mismo nombre o email
3. **Contraseñas hasheadas**: Nunca se almacenan en texto plano
4. **Tokens en memoria**: Los tokens activos se guardan en un mapa estático

---

## 4. Aplicación Web

### **Tecnología utilizada**

| Tecnología | Versión | Uso |
|------------|---------|-----|
| **PHP** | 8.x | Plantillas HTML y lógica de presentación |
| **HTML5** | - | Estructura de la página |
| **CSS3** | - | Estilos y diseño responsive |
| **JavaScript** | ES6+ | Comunicación con la API y manipulación del DOM |

### **Estructura del proyecto**

```
vista/
├── index2.php                 # Login
├── vista_registro.php         # Registro de usuarios
├── vista_principal.php        # Muro principal (feed)
├── vista_perfil.php           # Perfil de usuario
├── vista_editar_perfil.php    # Edición de perfil
├── vista_crear_publicacion.php # Crear receta
├── vista_detalle.php          # Detalle de receta
├── api_proxy.php              # Proxy para CORS (opcional)
├── styles.css                 # Estilos globales
└── assets/
    ├── imagenes/              # Imágenes del sitio
    └── iconos/                # Iconos
```

### **Conexión con la API**

La aplicación web utiliza `fetch` para realizar peticiones asíncronas a la API Java. El token se almacena en `localStorage`.

```javascript
// Ejemplo de login
async function iniciarSesion() {
    const response = await fetch('http://localhost:8080/mi-primera-api/rest/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ usernameOrEmail: email, password: pass })
    });
    
    const data = await response.json();
    if (response.ok) {
        localStorage.setItem('token', data.token);
        localStorage.setItem('usuario', JSON.stringify(data.usuario));
        window.location.href = 'vista_principal.php';
    }
}
```

---

## 5. Aplicación Móvil

### **Tecnología utilizada**

| Tecnología | Versión | Uso |
|------------|---------|-----|
| **Android SDK** | API 34 (Android 14) | Desarrollo nativo |
| **Java** | 11 | Lenguaje de programación |
| **Retrofit** | 2.9.0 | Cliente HTTP para la API |
| **Gson** | 2.10.1 | Serialización/deserialización JSON |
| **Picasso** | 2.8 | Carga de imágenes |
| **Material Design** | 1.11.0 | Componentes UI |

### **Estructura del proyecto**

```
app/src/main/java/com/example/gostar3/
├── IniciarSesion.java          # Login
├── CrearCuenta.java             # Registro
├── Muro.java                    # Actividad principal
├── DetalleRecetaActivity.java   # Detalle de receta
├── CrearRecetaActivity.java     # Crear receta
├── EditarPerfilActivity.java    # Editar perfil
├── PerfilFragment.java          # Fragment del perfil
├── InicioFragment.java          # Fragment del feed
├── BuscarFragment.java          # Fragment de búsqueda
├── NotificacionesFragment.java  # Fragment de notificaciones
├── CrearFragment.java           # Fragment para crear
├── adapter/
│   └── PublicacionAdapter.java  # Adaptador para RecyclerView
├── model/
│   ├── Usuario.java
│   ├── Publicacion.java
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   ├── RegistroRequest.java
│   └── LikeResponse.java
└── network/
    ├── ApiClient.java           # Configuración Retrofit
    └── ApiService.java          # Interfaz de endpoints
```

### **Conexión con la API**

La app utiliza **Retrofit** para la comunicación con la API. Se configura un cliente singleton que maneja las peticiones HTTP.

```java
// ApiService.java
public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
    
    @GET("publicaciones")
    Call<List<Publicacion>> getPublicaciones(@Header("Authorization") String token,
                                             @Query("page") int page,
                                             @Query("limit") int limit);
}

// Uso en el Fragment
apiService.getPublicaciones("Bearer " + token, 1, 20).enqueue(new Callback<>() {
    @Override
    public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
        publicaciones.clear();
        publicaciones.addAll(response.body());
        adapter.notifyDataSetChanged();
    }
});
```

---

## 6. Aplicación de Escritorio

### **Tecnología utilizada**

| Tecnología | Versión | Uso |
|------------|---------|-----|
| **.NET Framework** | 4.8 | Framework de desarrollo |
| **C#** | 7.0 | Lenguaje de programación |
| **Windows Forms** | - | Interfaz gráfica |
| **HttpClient** | .NET | Cliente HTTP |
| **Newtonsoft.Json** | 13.0.3 | Serialización JSON |

### **Estructura del proyecto**

```
GostarDesktop/
├── Program.cs                     # Punto de entrada
├── FormLogin.cs                   # Login
├── FormRegistro.cs                # Registro
├── FormMuro.cs                    # Muro principal
├── FormDetalle.cs                 # Detalle de receta
├── FormCrearReceta.cs             # Crear receta
├── FormEditarPerfil.cs            # Editar perfil
├── Controls/
│   └── PublicacionControl.cs      # UserControl de publicación
├── Models/
│   ├── Usuario.cs
│   ├── Publicacion.cs
│   ├── LoginRequest.cs
│   ├── LoginResponse.cs
│   ├── RegistroRequest.cs
│   └── LikeResponse.cs
├── ApiClient.cs                   # Cliente HTTP singleton
└── Properties/
    └── Settings.settings          # Almacenamiento de token
```

### **Conexión con la API**

La aplicación utiliza `HttpClient` para realizar peticiones a la API. Se implementa un patrón singleton en `ApiClient`.

```csharp
// ApiClient.cs
public class ApiClient
{
    private static readonly Lazy<ApiClient> _instance = new Lazy<ApiClient>(() => new ApiClient());
    public static ApiClient Instance => _instance.Value;
    
    private HttpClient _client;
    private string _baseUrl = "http://localhost:8080/mi-primera-api/rest/";
    
    public async Task<T> GetAsync<T>(string endpoint)
    {
        var response = await _client.GetAsync(endpoint);
        var json = await response.Content.ReadAsStringAsync();
        return JsonConvert.DeserializeObject<T>(json);
    }
    
    public async Task<T> PostAsync<T>(string endpoint, object data)
    {
        var content = new StringContent(JsonConvert.SerializeObject(data), Encoding.UTF8, "application/json");
        var response = await _client.PostAsync(endpoint, content);
        var json = await response.Content.ReadAsStringAsync();
        return JsonConvert.DeserializeObject<T>(json);
    }
}
```

---

## 7. Seguridad

### **Mecanismos de seguridad implementados**

#### **1. Tokens de sesión**
- Los tokens son generados con `UUID.randomUUID()` (128 bits)
- Cada token es único y se almacena en un mapa en memoria
- El token se envía en el header `Authorization: Bearer <token>`
- Se valida en cada petición autenticada

#### **2. Contraseñas**
- Las contraseñas se hashean con **SHA-256** antes de almacenarse
- Nunca se almacenan en texto plano
- La verificación se hace comparando hashes

#### **3. Prevención de SQL Injection**
- Se utilizan **Prepared Statements** en todas las consultas
- No se concatenan strings en las consultas SQL

```java
// Ejemplo correcto
String sql = "SELECT * FROM usuarios WHERE username = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, username);
```

#### **4. CORS (Cross-Origin Resource Sharing)**
- Se implementó un filtro CORS para permitir peticiones desde diferentes orígenes
- Configurado para desarrollo local

```java
@Provider
public class CorsFilter implements ContainerResponseFilter {
    @Override
    public void filter(...) {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    }
}
```

#### **5. Validación de datos**
- Validación en servidor de campos obligatorios
- Validación de email duplicado
- Verificación de permisos (solo el autor puede editar/eliminar)

### **Gestión de usuarios y permisos**

| Acción | Permiso requerido |
|--------|-------------------|
| Registrarse | No requiere autenticación |
| Ver publicaciones | Token válido |
| Crear publicación | Token válido |
| Editar publicación | Ser el autor |
| Eliminar publicación | Ser el autor |
| Dar like | Token válido |

### **Buenas prácticas aplicadas**

1. **Principio de mínima exposición**: Los endpoints devuelven solo la información necesaria
2. **Logs de errores**: Los errores se registran en consola para depuración
3. **Manejo de excepciones**: Try-catch para evitar caídas del servidor
4. **Cierre de recursos**: Uso de `finally` para cerrar conexiones

---

## 8. Despliegue

### **Requisitos previos**

- **Java 11** o superior
- **Apache Tomcat 10** o superior
- **MySQL / MariaDB** instalado y funcionando
- **PHP 8.x** con servidor web (Apache o similar)
- **Android Studio** (para compilar la app móvil)
- **Visual Studio** (para compilar la app de escritorio)

### **Ejecutar la API**

1. **Crear la base de datos**:
```sql
CREATE DATABASE red_social;
USE red_social;
-- Las tablas se crearán automáticamente al iniciar la API
```

2. **Configurar conexión** en `DatabaseConfig.java`:
```java
config.setJdbcUrl("jdbc:mysql://localhost:3306/red_social");
config.setUsername("root");
config.setPassword("");
```

3. **Compilar el proyecto**:
```bash
mvn clean package
```

4. **Desplegar en Tomcat**:
- Copiar el archivo `target/mi-primera-api.war` a la carpeta `webapps` de Tomcat
- Iniciar Tomcat

5. **Verificar**:
```
http://localhost:8080/mi-primera-api/rest/auth/verificar
```

### **Ejecutar la aplicación web**

1. **Colocar los archivos PHP** en el directorio del servidor web (ej: `htdocs` de XAMPP)
2. **Asegurar que la API esté corriendo** en `localhost:8080`
3. **Acceder a** `http://localhost/vista/index2.php`

### **Ejecutar la aplicación móvil**

1. **Abrir el proyecto en Android Studio**
2. **Configurar la URL de la API** en `ApiClient.java`:
```java
private static final String BASE_URL = "http://10.0.2.2:8080/mi-primera-api/rest/";
```
3. **Compilar y ejecutar** en emulador o dispositivo real

### **Ejecutar la aplicación de escritorio**

1. **Abrir la solución en Visual Studio**
2. **Configurar la URL de la API** en `ApiClient.cs`:
```csharp
private string _baseUrl = "http://localhost:8080/mi-primera-api/rest/";
```
3. **Compilar y ejecutar** (F5)

---

## 9. Pruebas

### **Pruebas unitarias realizadas**

| Módulo | Prueba | Resultado |
|--------|--------|-----------|
| AuthService | Registro con datos válidos | ✅ OK |
| AuthService | Registro con email duplicado | ❌ 409 Conflict |
| AuthService | Login con credenciales correctas | ✅ 200 OK |
| AuthService | Login con contraseña incorrecta | ❌ 401 Unauthorized |
| PublicacionService | Crear receta con token válido | ✅ 201 Created |
| PublicacionService | Crear receta sin token | ❌ 401 Unauthorized |
| PublicacionService | Obtener recetas del feed | ✅ 200 OK |
| PublicacionService | Dar like a receta | ✅ 200 OK |
| PublicacionService | Dar like repetido | ❌ 400 Bad Request |
| PublicacionService | Eliminar receta como autor | ✅ 200 OK |
| PublicacionService | Eliminar receta no autor | ❌ 403 Forbidden |

### **Pruebas de integración**

| Escenario | Descripción | Resultado |
|-----------|-------------|-----------|
| Flujo completo web | Registro → Login → Crear receta → Like → Perfil | ✅ OK |
| Flujo completo móvil | Registro → Login → Ver feed → Dar like | ✅ OK |
| Flujo completo escritorio | Login → Crear receta → Ver detalle → Eliminar | ✅ OK |
| Persistencia de sesión | Cerrar y abrir app, token válido | ✅ OK |
| CORS | Peticiones desde web a API | ✅ OK |

---

## 10. Guía de uso y mantenimiento

### **Ejecución en local**

1. **Iniciar MySQL**:
```bash
# Windows
net start MySQL
# Linux/Mac
sudo systemctl start mysql
```

2. **Iniciar Tomcat**:
```bash
# Navegar a la carpeta bin de Tomcat
./catalina.sh start  # Linux/Mac
catalina.bat start   # Windows
```

3. **Iniciar servidor web** (XAMPP, WAMP, etc.)

4. **Acceder a la aplicación**:
- Web: `http://localhost/vista/index2.php`
- Móvil: Ejecutar desde Android Studio
- Escritorio: Ejecutar desde Visual Studio

### **Añadir funcionalidades**

Para añadir nuevas funcionalidades a la API:

1. **Crear el endpoint** en el servicio correspondiente
2. **Añadir el método en ApiService** (para móvil y escritorio)
3. **Actualizar las vistas** con el nuevo endpoint

### **Problemas frecuentes**

| Problema | Causa | Solución |
|----------|-------|----------|
| `CLEARTEXT communication not permitted` | Android 9+ no permite HTTP | Añadir `android:usesCleartextTraffic="true"` en manifest |
| CORS error | Diferente origen web-API | Implementar filtro CORS en Java |
| `Connection refused` | API no corriendo | Verificar que Tomcat está iniciado |
| `404 Not Found` | Endpoint incorrecto | Verificar URL y Paths |
| Token inválido | Token expirado o no guardado | Volver a hacer login |

---

## 11. Mejoras futuras

### **Mejoras previstas**

| Área | Mejora | Prioridad |
|------|--------|-----------|
| **Seguridad** | Implementar JWT en lugar de tokens en memoria | Alta |
| **API** | Añadir paginación y filtros de búsqueda | Media |
| **Web** | Implementar lazy loading en el feed | Media |
| **Móvil** | Añadir notificaciones push | Baja |

### **Nuevas funcionalidades**

1. **Comentarios en recetas**: Sistema de comentarios anidados
2. **Seguir usuarios**: Feed personalizado con recetas de usuarios seguidos
3. **Valoraciones con estrellas**: Sistema de puntuación de recetas
4. **Categorías**: Filtrado por tipo de receta (Desayuno, Comida, Postre)
5. **Subida de imágenes**: En lugar de URLs, subir archivos al servidor
6. **Recetas favoritas**: Guardar recetas en favoritos
7. **Exportar recetas**: Generar PDF con la receta

### **Optimizaciones posibles**

1. **Caché de imágenes**: Implementar caché local para imágenes
2. **Paginación infinita**: Scroll infinito en el feed
3. **Índices en BD**: Optimizar consultas frecuentes
4. **Conexión pooling**: Mejorar gestión de conexiones a BD

---

## 📝 **Conclusión**

GOSTAR es una plataforma completa de red social de recetas que demuestra la integración de tres tipos de aplicaciones (web, móvil y escritorio) con una API REST centralizada. El proyecto aplica los conocimientos adquiridos en el ciclo de Desarrollo de Aplicaciones Multiplataforma, incluyendo:

- Desarrollo de API REST con Java y JAX-RS
- Persistencia de datos con MySQL
- Consumo de APIs desde diferentes plataformas
- Patrones de diseño y buenas prácticas
- Seguridad en aplicaciones web

---

**Manuel Hay Fernández**  
Desarrollo de Aplicaciones Multiplataforma (2º curso)  
Curso 2025/2026
