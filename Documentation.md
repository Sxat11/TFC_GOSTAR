## 📚 **Technical Documentation – Intermodular Project**

### **GOSTAR – Social Network for Recipes**

**Manuel Hay Fernández**  
Multiplatform Application Development (2nd year)  
Academic Year 2025/2026
Colexio Vivas

---

## 📑 **Table of Contents**

1. [System Architecture](#1-system-architecture)
2. [API](#2-api)
3. [Database](#3-database)
4. [Web Application](#4-web-application)
5. [Mobile Application](#5-mobile-application)
6. [Desktop Application](#6-desktop-application)
7. [Security](#7-security)
8. [Deployment](#8-deployment)
9. [Testing](#9-testing)
10. [User Guide & Maintenance](#10-user-guide--maintenance)
11. [Future Improvements](#11-future-improvements)

---

## 1. System Architecture

### **General Overview**

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CLIENTS                                        │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐              │
│  │   Web App       │  │   Mobile App    │  │   Desktop App   │              │
│  │ (PHP/HTML/CSS/JS)│ │ (Android/Java) │  │ (.NET WinForms) │              │
│  └────────┬────────┘  └────────┬────────┘  └────────┬────────┘              │
│           │                    │                    │                        │
│           └────────────────────┼────────────────────┘                        │
│                                │ HTTP/JSON                                  │
└────────────────────────────────┼────────────────────────────────────────────┘
                                 ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                           REST API (Java)                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│  • JAX-RS / Jersey                                                          │
│  • Apache Tomcat 10                                                         │
│  • Token-based authentication (UUID)                                        │
└────────────────────────────────┬────────────────────────────────────────────┘
                                 │ JDBC
                                 ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         MySQL Database                                      │
├─────────────────────────────────────────────────────────────────────────────┤
│  • MariaDB / MySQL                                                          │
│  • Tables: users, recipes, ingredients, steps, likes                       │
└─────────────────────────────────────────────────────────────────────────────┘
```

### **Technologies Used**

| Component | Technology | Version |
|-----------|------------|---------|
| **API** | Java + JAX-RS (Jersey) | Java 11, Jersey 3.0.3 |
| **API Server** | Apache Tomcat | 10.0.27 |
| **Database** | MySQL / MariaDB | 8.0 / 10.4 |
| **Web App** | PHP + HTML/CSS/JS | PHP 8.x |
| **Mobile App** | Android (Java) | SDK 34 (Android 14) |
| **Desktop App** | .NET Framework (C#) | .NET Framework 4.8 |

---

## 2. API

### **Endpoints**

#### **Authentication (/auth)**

| Endpoint | Method | Description | Auth Required |
|----------|--------|-------------|---------------|
| `/auth/registro` | POST | User registration | No |
| `/auth/login` | POST | User login | No |
| `/auth/logout` | POST | Logout | Yes (token) |
| `/auth/verificar` | GET | Verify token validity | Yes (token) |
| `/auth/perfil` | GET | Get user profile | Yes (token) |
| `/auth/perfil` | PUT | Update profile | Yes (token) |
| `/auth/avatar` | POST | Update avatar URL | Yes (token) |
| `/auth/cambiar-password` | POST | Change password | Yes (token) |
| `/auth/usuario` | DELETE | Delete account | Yes (token) |

#### **Recipes (/publicaciones)**

| Endpoint | Method | Description | Auth Required |
|----------|--------|-------------|---------------|
| `/publicaciones` | GET | Get all recipes (feed) | Yes (token) |
| `/publicaciones` | POST | Create new recipe | Yes (token) |
| `/publicaciones/{id}` | GET | Get recipe by ID | Yes (token) |
| `/publicaciones/{id}` | PUT | Edit recipe | Yes (token) |
| `/publicaciones/{id}` | DELETE | Delete recipe | Yes (token) |
| `/publicaciones/{id}/like` | POST | Like a recipe | Yes (token) |
| `/publicaciones/{id}/like` | DELETE | Unlike a recipe | Yes (token) |
| `/publicaciones/usuario/{usuarioId}` | GET | Get user's recipes | Yes (token) |

### **Authentication**

The API uses **session tokens** generated with UUID. The flow is:

1. User registers or logs in
2. Server generates a unique token and returns it
3. Client must include the token in all authenticated requests in the header:
   ```
   Authorization: Bearer <token>
   ```

### **Request & Response Examples**

#### **User Registration**

**Request:**
```http
POST /auth/registro HTTP/1.1
Content-Type: application/json

{
    "username": "chef_manuel",
    "email": "manuel@example.com",
    "passwordHash": "myPassword123",
    "nombre": "Manuel Hay",
    "bio": "Passionate about cooking"
}
```

**Response (201 Created):**
```json
{
    "token": "550e8400-e29b-41d4-a716-446655440000",
    "usuario": {
        "id": 1,
        "username": "chef_manuel",
        "email": "manuel@example.com",
        "nombre": "Manuel Hay",
        "bio": "Passionate about cooking",
        "fechaRegistro": "2026-03-30T10:00:00"
    },
    "mensaje": "Registration successful"
}
```

#### **Create a Recipe**

**Request:**
```http
POST /publicaciones HTTP/1.1
Authorization: Bearer 550e8400-e29b-41d4-a716-446655440000
Content-Type: application/json

{
    "titulo": "Spanish Omelette",
    "duracion": "30 minutes",
    "imagenPrincipal": "https://example.com/omelette.jpg",
    "descripcion": "The classic Spanish omelette",
    "dificultad": "Medium",
    "calorias": 350,
    "ingredientes": [
        "4 eggs",
        "500g potatoes",
        "1 onion",
        "Olive oil",
        "Salt"
    ],
    "pasos": [
        "Peel and slice the potatoes",
        "Fry the potatoes in olive oil",
        "Beat the eggs",
        "Mix and cook the omelette"
    ]
}
```

**Response (201 Created):**
```json
{
    "id": 1,
    "usuarioId": 1,
    "usuarioNombre": "chef_manuel",
    "titulo": "Spanish Omelette",
    "duracion": "30 minutes",
    "imagenPrincipal": "https://example.com/omelette.jpg",
    "likes": 0,
    "likedByCurrentUser": false
}
```

#### **Get All Recipes (Feed)**

**Request:**
```http
GET /publicaciones?page=1&limit=20 HTTP/1.1
Authorization: Bearer 550e8400-e29b-41d4-a716-446655440000
```

**Response (200 OK):**
```json
[
    {
        "id": 1,
        "usuarioId": 1,
        "usuarioNombre": "chef_manuel",
        "titulo": "Spanish Omelette",
        "duracion": "30 minutes",
        "imagenPrincipal": "https://example.com/omelette.jpg",
        "likes": 5,
        "likedByCurrentUser": true,
        "fechaCreacion": "2026-03-30T10:00:00"
    }
]
```

#### **Like a Recipe**

**Request:**
```http
POST /publicaciones/1/like HTTP/1.1
Authorization: Bearer 550e8400-e29b-41d4-a716-446655440000
```

**Response (200 OK):**
```json
{
    "likes": 6,
    "liked": true
}
```

---

## 3. Database

### **Entity-Relationship Diagram**

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│     users       │     │    recipes      │     │recipe_ingredients│
├─────────────────┤     ├─────────────────┤     ├─────────────────┤
│ id (PK)         │◄────│ user_id (FK)    │     │ id (PK)         │
│ username        │     │ id (PK)         │────►│ recipe_id (FK)  │
│ email           │     │ title           │     │ ingredient      │
│ password_hash   │     │ duration        │     └─────────────────┘
│ name            │     │ main_image      │
│ bio             │     │ description     │     ┌─────────────────┐
│ avatar_url      │     │ difficulty      │     │   recipe_steps  │
│ registration_date│     │ calories        │     ├─────────────────┤
│ last_access     │     │ likes           │     │ id (PK)         │
│ active          │     │ creation_date   │     │ recipe_id (FK)  │
└─────────────────┘     └─────────────────┘     │ step            │
                              │                 │ order           │
                              │                 └─────────────────┘
                              ▼
┌─────────────────┐     ┌─────────────────┐
│   recipe_likes  │     │recipe_additional│
├─────────────────┤     │    images       │
│ user_id (PK)    │     ├─────────────────┤
│ recipe_id (PK)  │     │ id (PK)         │
│ date            │     │ recipe_id (FK)  │
└─────────────────┘     │ url             │
                        │ order           │
                        └─────────────────┘
```

### **Table Descriptions**

#### **users**
| Field | Type | Description |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Unique identifier |
| username | VARCHAR(50) UNIQUE | Username |
| email | VARCHAR(100) UNIQUE | Email address |
| password_hash | VARCHAR(255) | Hashed password (SHA-256) |
| name | VARCHAR(100) | Full name |
| bio | TEXT | User biography |
| avatar_url | VARCHAR(500) | Avatar image URL |
| registration_date | TIMESTAMP | Registration date |
| last_access | TIMESTAMP | Last login time |
| active | BOOLEAN | Account status |

#### **recipes**
| Field | Type | Description |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Unique identifier |
| user_id | INT (FK) | Recipe author |
| title | VARCHAR(200) | Recipe title |
| duration | VARCHAR(50) | Preparation time |
| main_image | VARCHAR(500) | Main image URL |
| description | TEXT | Recipe description |
| difficulty | VARCHAR(20) | Easy, Medium, Hard |
| calories | INT | Approximate calories |
| likes | INT | Like counter |
| creation_date | TIMESTAMP | Publication date |

#### **recipe_ingredients**
| Field | Type | Description |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Unique identifier |
| recipe_id | INT (FK) | Associated recipe |
| ingredient | TEXT | Ingredient name and quantity |

#### **recipe_steps**
| Field | Type | Description |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Unique identifier |
| recipe_id | INT (FK) | Associated recipe |
| step | TEXT | Step description |
| order | INT | Step order |

#### **recipe_additional_images**
| Field | Type | Description |
|-------|------|-------------|
| id | INT (PK, AUTO_INCREMENT) | Unique identifier |
| recipe_id | INT (FK) | Associated recipe |
| url | VARCHAR(500) | Image URL |
| order | INT | Image order |

#### **recipe_likes**
| Field | Type | Description |
|-------|------|-------------|
| user_id | INT (PK, FK) | User who liked |
| recipe_id | INT (PK, FK) | Recipe that received like |
| date | TIMESTAMP | Like timestamp |

### **Important Rules**

1. **Referential integrity**: All foreign keys use `ON DELETE CASCADE` to maintain consistency
2. **Unique constraints**: Username and email must be unique
3. **Password hashing**: Passwords are never stored in plain text
4. **Tokens in memory**: Active tokens are stored in a static map

---

## 4. Web Application

### **Technologies Used**

| Technology | Version | Purpose |
|------------|---------|---------|
| **PHP** | 8.x | HTML templates and presentation logic |
| **HTML5** | - | Page structure |
| **CSS3** | - | Styles and responsive design |
| **JavaScript** | ES6+ | API communication and DOM manipulation |

### **Project Structure**

```
web/
├── index2.php                 # Login page
├── registro.php               # User registration
├── principal.php              # Main feed
├── perfil.php                 # User profile
├── editar_perfil.php          # Edit profile
├── crear_publicacion.php      # Create recipe
├── detalle.php                # Recipe details
├── api_proxy.php              # CORS proxy (optional)
├── styles.css                 # Global styles
└── assets/
    ├── images/                # Site images
    └── icons/                 # Icons
```

### **API Integration**

The web app uses `fetch` to make asynchronous requests to the Java API. Tokens are stored in `localStorage`.

```javascript
// Login example
async function login() {
    const response = await fetch('http://localhost:8080/mi-primera-api/rest/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ usernameOrEmail: email, password: pass })
    });
    
    const data = await response.json();
    if (response.ok) {
        localStorage.setItem('token', data.token);
        localStorage.setItem('user', JSON.stringify(data.usuario));
        window.location.href = 'principal.php';
    }
}
```

---

## 5. Mobile Application

### **Technologies Used**

| Technology | Version | Purpose |
|------------|---------|---------|
| **Android SDK** | API 34 (Android 14) | Native development |
| **Java** | 11 | Programming language |
| **Retrofit** | 2.9.0 | HTTP client for API |
| **Gson** | 2.10.1 | JSON serialization/deserialization |
| **Picasso** | 2.8 | Image loading |
| **Material Design** | 1.11.0 | UI components |

### **Project Structure**

```
app/src/main/java/com/example/gostar/
├── LoginActivity.java              # Login
├── RegisterActivity.java           # Registration
├── MainActivity.java               # Main activity
├── RecipeDetailActivity.java       # Recipe details
├── CreateRecipeActivity.java       # Create recipe
├── EditProfileActivity.java        # Edit profile
├── ProfileFragment.java            # Profile fragment
├── FeedFragment.java               # Feed fragment
├── SearchFragment.java             # Search fragment
├── adapter/
│   └── RecipeAdapter.java          # RecyclerView adapter
├── model/
│   ├── User.java
│   ├── Recipe.java
│   ├── LoginRequest.java
│   ├── LoginResponse.java
│   ├── RegisterRequest.java
│   └── LikeResponse.java
└── network/
    ├── ApiClient.java              # Retrofit configuration
    └── ApiService.java             # Endpoint interface
```

### **API Integration**

The app uses **Retrofit** for API communication. A singleton client handles HTTP requests.

```java
// ApiService.java
public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
    
    @GET("publicaciones")
    Call<List<Recipe>> getRecipes(@Header("Authorization") String token,
                                  @Query("page") int page,
                                  @Query("limit") int limit);
}

// Usage in Fragment
apiService.getRecipes("Bearer " + token, 1, 20).enqueue(new Callback<>() {
    @Override
    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        recipes.clear();
        recipes.addAll(response.body());
        adapter.notifyDataSetChanged();
    }
});
```

---

## 6. Desktop Application

### **Technologies Used**

| Technology | Version | Purpose |
|------------|---------|---------|
| **.NET Framework** | 4.8 | Development framework |
| **C#** | 7.0 | Programming language |
| **Windows Forms** | - | GUI framework |
| **HttpClient** | .NET | HTTP client |
| **Newtonsoft.Json** | 13.0.3 | JSON serialization |

### **Project Structure**

```
GostarDesktop/
├── Program.cs                     # Entry point
├── LoginForm.cs                   # Login
├── RegisterForm.cs                # Registration
├── MainForm.cs                    # Main feed
├── RecipeDetailForm.cs            # Recipe details
├── CreateRecipeForm.cs            # Create recipe
├── EditProfileForm.cs             # Edit profile
├── Controls/
│   └── RecipeControl.cs           # Reusable recipe control
├── Models/
│   ├── User.cs
│   ├── Recipe.cs
│   ├── LoginRequest.cs
│   ├── LoginResponse.cs
│   ├── RegisterRequest.cs
│   └── LikeResponse.cs
├── ApiClient.cs                   # HTTP client singleton
└── Properties/
    └── Settings.settings          # Token storage
```

### **API Integration**

The application uses `HttpClient` for API requests with a singleton pattern.

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

## 7. Security

### **Security Mechanisms Implemented**

#### **1. Session Tokens**
- Tokens generated with `UUID.randomUUID()` (128 bits)
- Each token is unique and stored in a memory map
- Token sent in `Authorization: Bearer <token>` header
- Validated on every authenticated request

#### **2. Password Security**
- Passwords hashed with **SHA-256** before storage
- Never stored in plain text
- Verification performed by comparing hashes

#### **3. SQL Injection Prevention**
- **Prepared Statements** used for all queries
- No string concatenation in SQL queries

```java
// Correct example
String sql = "SELECT * FROM users WHERE username = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, username);
```

#### **4. CORS (Cross-Origin Resource Sharing)**
- CORS filter implemented to allow requests from different origins
- Configured for local development

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

#### **5. Input Validation**
- Server-side validation of required fields
- Duplicate email validation
- Permission verification (only author can edit/delete)

### **User Permissions**

| Action | Permission Required |
|--------|---------------------|
| Register | No authentication |
| View recipes | Valid token |
| Create recipe | Valid token |
| Edit recipe | Be the author |
| Delete recipe | Be the author |
| Like recipe | Valid token |

### **Best Practices Applied**

1. **Principle of Least Exposure**: Endpoints return only necessary information
2. **Error Logging**: Errors are logged to console for debugging
3. **Exception Handling**: Try-catch blocks to prevent server crashes
4. **Resource Cleanup**: Using `finally` blocks to close connections

---

## 8. Deployment

### **Prerequisites**

- **Java 11** or higher
- **Apache Tomcat 10** or higher
- **MySQL / MariaDB** installed and running
- **PHP 8.x** with web server (Apache or similar)
- **Android Studio** (to compile mobile app)
- **Visual Studio** (to compile desktop app)

### **Running the API**

1. **Create the database**:
```sql
CREATE DATABASE gostar;
USE gostar;
-- Tables are created automatically when the API starts
```

2. **Configure connection** in `DatabaseConfig.java`:
```java
config.setJdbcUrl("jdbc:mysql://localhost:3306/gostar");
config.setUsername("root");
config.setPassword("");
```

3. **Compile the project**:
```bash
mvn clean package
```

4. **Deploy to Tomcat**:
- Copy `target/gostar-api.war` to Tomcat's `webapps` folder
- Start Tomcat

5. **Verify**:
```
http://localhost:8080/gostar-api/rest/auth/verificar
```

### **Running the Web Application**

1. **Place PHP files** in web server directory (e.g., `htdocs` of XAMPP)
2. **Ensure API is running** on `localhost:8080`
3. **Access** `http://localhost/gostar-web/index2.php`

### **Running the Mobile Application**

1. **Open the project in Android Studio**
2. **Configure API URL** in `ApiClient.java`:
```java
private static final String BASE_URL = "http://10.0.2.2:8080/gostar-api/rest/";
```
3. **Build and run** on emulator or physical device

### **Running the Desktop Application**

1. **Open the solution in Visual Studio**
2. **Configure API URL** in `ApiClient.cs`:
```csharp
private string _baseUrl = "http://localhost:8080/gostar-api/rest/";
```
3. **Build and run** (F5)

---

## 9. Testing

### **Unit Tests Conducted**

| Module | Test | Result |
|--------|------|--------|
| AuthService | Registration with valid data | ✅ OK |
| AuthService | Registration with duplicate email | ❌ 409 Conflict |
| AuthService | Login with correct credentials | ✅ 200 OK |
| AuthService | Login with incorrect password | ❌ 401 Unauthorized |
| RecipeService | Create recipe with valid token | ✅ 201 Created |
| RecipeService | Create recipe without token | ❌ 401 Unauthorized |
| RecipeService | Get feed recipes | ✅ 200 OK |
| RecipeService | Like a recipe | ✅ 200 OK |
| RecipeService | Duplicate like | ❌ 400 Bad Request |
| RecipeService | Delete own recipe | ✅ 200 OK |
| RecipeService | Delete someone else's recipe | ❌ 403 Forbidden |

### **Integration Tests**

| Scenario | Description | Result |
|----------|-------------|--------|
| Full web flow | Register → Login → Create recipe → Like → Profile | ✅ OK |
| Full mobile flow | Register → Login → View feed → Like | ✅ OK |
| Full desktop flow | Login → Create recipe → View details → Delete | ✅ OK |
| Session persistence | Close and reopen app, token valid | ✅ OK |
| CORS | Requests from web to API | ✅ OK |

---

## 10. User Guide & Maintenance

### **Local Execution**

1. **Start MySQL**:
```bash
# Windows
net start MySQL
# Linux/Mac
sudo systemctl start mysql
```

2. **Start Tomcat**:
```bash
# Navigate to Tomcat's bin folder
./catalina.sh start  # Linux/Mac
catalina.bat start   # Windows
```

3. **Start web server** (XAMPP, WAMP, etc.)

4. **Access the applications**:
- Web: `http://localhost/gostar-web/index2.php`
- Mobile: Run from Android Studio
- Desktop: Run from Visual Studio

### **Adding New Features**

To add new features to the API:

1. **Create the endpoint** in the corresponding service class
2. **Add the method to ApiService** (for mobile and desktop)
3. **Update the views** with the new endpoint

### **Common Issues**

| Problem | Cause | Solution |
|---------|-------|----------|
| `CLEARTEXT communication not permitted` | Android 9+ blocks HTTP | Add `android:usesCleartextTraffic="true"` to manifest |
| CORS error | Different web-API origin | Implement CORS filter in Java |
| `Connection refused` | API not running | Verify Tomcat is started |
| `404 Not Found` | Incorrect endpoint | Check URL and Path annotations |
| Invalid token | Expired or missing token | Re-login |

---

## 11. Future Improvements

### **Planned Improvements**

| Area | Improvement | Priority |
|------|------------|----------|
| **Security** | Implement JWT instead of memory tokens | High |
| **API** | Add pagination and search filters | Medium |
| **Web** | Implement lazy loading for feed | Medium |
| **Mobile** | Add push notifications | Low |

### **New Features**

1. **Comments on recipes**: Nested comment system
2. **Follow users**: Personalized feed with followed users' recipes
3. **Star ratings**: Recipe rating system
4. **Categories**: Filter by recipe type (Breakfast, Lunch, Dessert)
5. **Image upload**: Upload files instead of URLs
6. **Favorite recipes**: Save recipes to favorites
7. **Export recipes**: Generate PDF of recipe

### **Possible Optimizations**

1. **Image caching**: Implement local image cache
2. **Infinite scrolling**: Scroll to load more recipes
3. **Database indexes**: Optimize frequent queries
4. **Connection pooling**: Improve database connection management

---

## 📝 **Conclusion**

GOSTAR is a complete recipe social network platform demonstrating the integration of three types of applications (web, mobile, desktop) with a centralized REST API. The project applies knowledge acquired in the Multiplatform Application Development program, including:

- REST API development with Java and JAX-RS
- Data persistence with MySQL
- API consumption from different platforms
- Design patterns and best practices
- Security in web applications

---

**Manuel Hay Fernández**  
Multiplatform Application Development (2nd year)  
Academic Year 2025/2026
Colexio Vivas
