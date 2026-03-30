<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR - Página Principal</title>

    <style>
        @media (min-width: 768px) {
            .auth-section {
                align-items: center;    

            }
        }
        header {
        
           
        }

        
        .navbar {
            background: white;
            padding: 15px 0;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
        }

        .container {
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 0 20px;
        }

        .nav-group {
            display: flex;
            gap: 25px;
            align-items: center;
        }

        .nav-link {
            display: flex;
            align-items: center;
            gap: 10px;
            text-decoration: none;
            color: #333;
            font-family: sans-serif;
            font-weight: 600;
        }

        .icon {
            height: 30px;
            width: auto;
        }

        .nav-link:hover {
            color: rgb(41, 131, 0);
        }

        /* ===== NUEVO: GRID DE 3 COLUMNAS ===== */
        .publicaciones-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            /* 3 columnas */
            gap: 20px;
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }

        /* Tarjetas de recetas (versión compacta) */
        .post-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s, box-shadow 0.3s;
            cursor: pointer;
            height: 280px;
            /* Altura fija para uniformidad */
            position: relative;
        }

        .post-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
        }

        /* Imagen de fondo que ocupa toda la tarjeta */
        .post-card-image {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            z-index: 1;
        }

        /* Overlay oscuro para mejorar legibilidad del texto */
        .post-card-overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(to bottom, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.7));
            z-index: 2;
        }

        /* Contenido sobre la imagen */
        .post-card-content {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            padding: 20px;
            color: white;
            z-index: 3;
        }

        .post-card-title {
            font-size: 20px;
            font-weight: bold;
            margin-bottom: 8px;
          
        }

        .post-card-meta {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-bottom: 12px;
            font-size: 14px;
           
        }

        .post-card-author {
            display: flex;
            align-items: center;
            gap: 6px;
        }

        .post-card-avatar {
            width: 24px;
            height: 24px;
            border-radius: 50%;
            background: rgb(41, 131, 0);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            font-weight: bold;
            color: white;
        }

        .post-card-likes {
            display: flex;
            align-items: center;
            gap: 4px;
        }

        .post-card-likes img {
            width: 18px;
            height: 18px;
            filter: brightness(0) invert(1);
            /* Corazón blanco */
        }

        .post-card-duration {
            position: absolute;
            top: 15px;
            right: 15px;
           
            color: white;
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 12px;
            z-index: 3;
        }

        /* Loading y estados vacíos (igual que antes) */
        .loading {
            text-align: center;
            padding: 40px;
            
        }

        .no-recetas {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 8px;
            border: 1px solid #dbdbdb;
            max-width: 500px;
            margin: 30px auto;
        }

        .no-recetas button {
            margin-top: 20px;
            padding: 12px 24px;
            background: rgb(41, 131, 0);
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        .loader {
            border: 4px solid #f3f3f3;
            border-top: 4px solid rgb(41, 131, 0);
            border-radius: 50%;
            width: 40px;
            height: 40px;
            animation: spin 1s linear infinite;
            margin: 40px auto;
        }

        .titulo-gostar {
            font-family: 'Poppins', sans-serif;
            font-size: 48px;
            font-weight: 800;
            text-align: center;
            margin-top: 40px;

            background: linear-gradient(90deg, #2E7D32, #43A047, #00C853);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;

            letter-spacing: 2px;
        }

        @keyframes spin {
            0% {
                transform: rotate(0deg);
            }

            100% {
                transform: rotate(360deg);
            }
        }

        .welcome-section {
            text-align: center;
            margin: 20px 0;
        }

        .welcome-section h2 {
            color: rgb(41, 131, 0);
            margin-bottom: 10px;
        }

        .welcome-section p {
            color: #666;
        }

        /* Responsive: tablet 2 columnas, móvil 1 columna */
        @media (max-width: 900px) {
            .publicaciones-grid {
                grid-template-columns: repeat(2, 1fr);
            }
        }

        @media (max-width: 600px) {
            .publicaciones-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>

<body>
    <script>
        // Verificar si hay token al cargar la página
        document.addEventListener('DOMContentLoaded', function() {
            const token = localStorage.getItem('token');
            const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');

            // Si no hay token, redirigir al login
            if (!token) {
                window.location.href = '../index.php';
                return;
            }

            // Mostrar nombre de usuario en la interfaz
            actualizarInterfazUsuario(usuario);

            // Verificar que el token sigue siendo válido
            verificarToken(token);

            // Cargar publicaciones
            cargarPublicaciones();
        });

        function actualizarInterfazUsuario(usuario) {
            // Actualizar el nombre en el menú "Tú"
            const usernameDisplay = document.getElementById('username-display');
            if (usernameDisplay) {
                usernameDisplay.textContent = usuario.nombre || usuario.username || 'Perfil';
            }
        }

        async function verificarToken(token) {
            try {
                const response = await fetch('http://localhost:8080/mi-primera-api/rest/auth/verificar', {
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                const data = await response.json();

                if (!data.valido) {
                    console.warn('Token inválido, redirigiendo a login');
                    localStorage.removeItem('token');
                    localStorage.removeItem('usuario');
                    window.location.href = '../index.php';
                } else if (data.usuario) {
                    // Actualizar datos del usuario si es necesario
                    localStorage.setItem('usuario', JSON.stringify(data.usuario));
                    actualizarInterfazUsuario(data.usuario);
                }
            } catch (error) {
                console.error('Error verificando token:', error);
            }
        }

        // Función para logout
        function logout() {
            const token = localStorage.getItem('token');

            if (token) {
                fetch('http://localhost:8080/mi-primera-api/rest/auth/logout', {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                }).finally(() => {
                    localStorage.removeItem('token');
                    localStorage.removeItem('usuario');
                    window.location.href = '../index.php';
                });
            } else {
                window.location.href = '../index.php';
            }
        }

        // Función para cargar publicaciones
        async function cargarPublicaciones() {
            const token = localStorage.getItem('token');
            const container = document.getElementById('publicaciones-grid');

            try {
                const response = await fetch('http://localhost:8080/mi-primera-api/rest/publicaciones?page=1&limit=20', {
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                if (!response.ok) {
                    throw new Error('Error al cargar publicaciones');
                }

                const publicaciones = await response.json();
                mostrarPublicaciones(publicaciones);

            } catch (error) {
                console.error('Error:', error);
                container.innerHTML = `
                    <div class="no-recetas">
                        <p>Error al cargar las recetas. Intenta de nuevo más tarde.</p>
                    </div>
                `;
            }
        }

        function mostrarPublicaciones(publicaciones) {
            const container = document.getElementById('publicaciones-grid');

            if (!publicaciones || publicaciones.length === 0) {
                container.innerHTML = `
                    <div class="no-recetas">
                        <h3>¡No hay recetas todavía!</h3>
                        <p>Sé el primero en compartir una receta</p>
                        <button onclick="window.location.href='vista_crear_publicacion.php'">
                            Crear primera receta
                        </button>
                    </div>
                `;
                return;
            }

            let html = '';

            publicaciones.forEach(pub => {
                // Obtener inicial para avatar
                const inicial = (pub.usuarioNombre || 'U').charAt(0).toUpperCase();

                html += `
                    <div class="post-card" onclick="verDetalle(${pub.id})">
                        <img src="${pub.imagenPrincipal}" alt="${pub.titulo}" class="post-card-image">
                        <div class="post-card-overlay"></div>
                        
                        <div class="post-card-duration"> ${pub.duracion}</div>
                        
                        <div class="post-card-content">
                            <h3 class="post-card-title">${pub.titulo}</h3>
                            
                            <div class="post-card-meta">
                                <div class="post-card-author">
                                    <div class="post-card-avatar">${inicial}</div>
                                    <span>${pub.usuarioNombre || 'Usuario'}</span>
                                </div>
                                
                                <div class="post-card-likes">
                                  
                                    <span>${pub.likes || 0}</span>
                                </div>
                            </div>
                            
                            <button class="action-btn" onclick="darLike(${pub.id}, event)" style="background: none; border: none; color: white; cursor: pointer; padding: 5px 10px; border-radius: 20px; background: rgba(255,255,255,0.2); margin-top: 10px;">
                                 Dar like
                            </button>
                        </div>
                    </div>
                `;
            });

            container.innerHTML = html;
        }

        async function darLike(publicacionId, event) {
            event.stopPropagation(); // Evitar que se active el clic en la tarjeta

            const token = localStorage.getItem('token');
            const boton = event.currentTarget;

            try {
                const response = await fetch(`http://localhost:8080/mi-primera-api/rest/publicaciones/${publicacionId}/like`, {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    // Actualizar el contador en la tarjeta
                    const likeSpan = boton.closest('.post-card').querySelector('.post-card-likes span');
                    if (likeSpan) {
                        likeSpan.textContent = data.likes;
                    }

                    // Feedback visual rápido
                    boton.style.background = '#ff4444';
                    setTimeout(() => {
                        boton.style.background = 'rgba(255,255,255,0.2)';
                    }, 200);
                }
            } catch (error) {
                console.error('Error al dar like:', error);
            }
        }

        function verDetalle(publicacionId) {
            window.location.href = `vista_detalle.php?id=${publicacionId}`;
        }
    </script>

    <header>
        <nav class="navbar">
            <div class="container">
                <a href="vista_principal.php" class="logo-main">
                    <img src="../GostarLogoLargo.png" alt="GOSTAR Logo" height="60px">
                </a>

                <div class="nav-group">
                    <a href="#" class="nav-link">
                        <img src="../favoritos.png" alt="Favoritos" class="icon">
                        <span>Favoritos</span>
                    </a>
                    <a href="vista_perfil.php" class="nav-link">
                        <img src="../perfil.png" alt="Tú" class="icon">
                        <span id="username-display">Tú</span>
                    </a>
                    <button type="button" onclick="logout()" style="background: none; border: none; cursor: pointer; padding: 8px 12px; color: #ff4444; font-weight: 600;">
                        Cerrar sesión
                    </button>
                </div>
            </div>
        </nav>

        <nav class="navbar2">
            <div class="nav-group">
                <a href="/index.php" class="nav-link">Todas las categorias</a>
                <a href="#" class="nav-link">Desayuno</a>
                <a href="#" class="nav-link">Comida</a>
                <a href="#" class="nav-link">Cena</a>
                <a href="#" class="nav-link">Postre</a>
                <a href="#" class="nav-link">Vegeteriano</a>
                <a href="#" class="nav-link">Vegano</a>
                <a href="#" class="nav-link">Sin gluten</a>
                <a href="#" class="nav-link">Sin lactosa</a>
                <a href="#" class="nav-link">Asiatico</a>
                <button onclick="window.location.href='vista_crear_publicacion.php'"
                    style="background: rgb(41, 131, 0);; color: white; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; margin-left: 15px;">
                    Nueva Receta
                </button>
            </div>
        </nav>
    </header>

    <div class="container2" style="background-image: url('../fondo.png'); background-size: cover; background-position: center; min-height: 80vh;">
    <div class="welcome-section">
        <h1 class="titulo-gostar">Bienvenido a GOSTAR</h1>
        <p>¿Qué vamos a hacer hoy?</p>
    </div>

    <!-- Contenedor para publicaciones en GRID de 3 columnas -->
    <div id="publicaciones-grid" class="publicaciones-grid">
        <div class="loader" ></div>
    </div>
    </div>

</body>

</html>