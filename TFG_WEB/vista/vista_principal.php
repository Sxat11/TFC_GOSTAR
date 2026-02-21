<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR - Página Principal</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        @media (min-width: 768px) {
            .auth-section {
                max-width: 500px;
                margin-left: auto;
                margin-right: auto;
            }
        }

        .navbar {
            background: white;
            padding: 15px 0;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
        }

        .container {
            /* max-width: 1100px; */
            margin: 0 auto;
            display: flex;
            justify-content: space-between;
            /* Empuja el grupo de links a la derecha */
            align-items: center;
            padding: 0 20px;
        }

        .nav-group {
            display: flex;
            gap: 25px;
            /* Espacio entre Favoritos y Tú */
            align-items: center;
        }

        .nav-link {
            display: flex;
            align-items: center;
            /* Alinea icono y texto verticalmente al centro */
            gap: 10px;
            /* Espacio entre el icono y la palabra */
            text-decoration: none;
            color: #333;
            font-family: sans-serif;
            font-weight: 600;
        }

        .icon {
            height: 30px;
            /* Tamaño del icono */
            width: auto;
        }

        .nav-link:hover {
            color: rgb(41, 131, 0);
            /* Color opcional al pasar el mouse */
        }

        /* Estilos para las publicaciones */
        .publicaciones-container {
            max-width: 500px;
            margin: 0 auto;
            padding: 20px;
        }

        .post-container {
            background: #fff;
            border: 1px solid #dbdbdb;
            border-radius: 8px;
            font-family: sans-serif;
            overflow: hidden;
            margin-bottom: 30px;
        }

        /* Cabecera */
        .post-header {
            display: flex;
            align-items: center;
            padding: 12px;
            gap: 10px;
        }

        .post-avatar {
            width: 35px;
            height: 35px;
            border-radius: 50%;
            object-fit: cover;
            background: #008080;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
            font-size: 16px;
        }

        .post-info {
            display: flex;
            flex-direction: column;
            flex: 1;
        }

        .post-username {
            font-weight: 600;
            font-size: 14px;
        }

        .post-date {
            font-size: 11px;
            color: #8e8e8e;
        }

        /* Imagen */
        .post-image-container {
            position: relative;
            width: 100%;
        }

        .post-image {
            width: 100%;
            display: block;
            max-height: 400px;
            object-fit: cover;
        }

        .post-duration {
            position: absolute;
            bottom: 15px;
            right: 15px;
            background: rgba(0, 0, 0, 0.7);
            color: white;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 14px;
        }

        /* Contenido y Botones */
        .post-content {
            padding: 12px;
        }

        .post-title {
            font-size: 18px;
            font-weight: bold;
            color: #008080;
            margin-bottom: 8px;
        }

        .post-description {
            font-size: 14px;
            color: #666;
            margin-bottom: 12px;
        }

        .post-actions {
            display: flex;
            gap: 15px;
            margin-bottom: 10px;
        }

        .action-btn {
            background: none;
            border: none;
            font-size: 20px;
            cursor: pointer;
            padding: 0;
            transition: transform 0.2s;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .action-btn:hover {
            transform: scale(1.1);
        }

        .like-count {
            font-size: 14px;
            color: #333;
        }

        .post-text {
            font-size: 14px;
            line-height: 1.4;
            color: #262626;
        }

        .loading {
            text-align: center;
            padding: 40px;
            color: #666;
        }

        .no-recetas {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 8px;
            border: 1px solid #dbdbdb;
        }

        .no-recetas button {
            margin-top: 20px;
            padding: 12px 24px;
            background: #008080;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        .loader {
            border: 4px solid #f3f3f3;
            border-top: 4px solid #008080;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            animation: spin 1s linear infinite;
            margin: 40px auto;
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
            color: #008080;
            margin-bottom: 10px;
        }

        .welcome-section p {
            color: #666;
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
                window.location.href = 'index2.php';
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
                    window.location.href = 'index2.php';
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
                    window.location.href = '../index2.php';
                });
            } else {
                window.location.href = '../index2.php';
            }
        }

        function verDetalle(publicacionId) {
            window.location.href = `vista_detalle_receta.php?id=${publicacionId}`;
        }

        // Función para cargar publicaciones
        async function cargarPublicaciones() {
            const token = localStorage.getItem('token');
            const container = document.getElementById('publicaciones-dinamicas');

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
            const container = document.getElementById('publicaciones-dinamicas');

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
                // Formatear fecha
                const fecha = new Date(pub.fechaCreacion || pub.fecha_creacion);
                const fechaFormateada = fecha.toLocaleDateString('es-ES', {
                    day: 'numeric',
                    month: 'long',
                    year: 'numeric'
                });

                // Obtener inicial para avatar
                const inicial = (pub.usuarioNombre || 'U').charAt(0).toUpperCase();

                html += `
                    <div class="post-container">
                        <div class="post-header">
                            <div class="post-avatar">${inicial}</div>
                            <div class="post-info">
                                <span class="post-username">${pub.usuarioNombre || 'Usuario'}</span>
                                <span class="post-date">${fechaFormateada}</span>
                            </div>
                        </div>

                        <div class="post-image-container">
                            <img src="${pub.imagenPrincipal}" alt="${pub.titulo}" class="post-image">
                            <span class="post-duration">⏱️ ${pub.duracion}</span>
                        </div>

                        <div class="post-content">
                            <h3 class="post-title">${pub.titulo}</h3>
                            <p class="post-description">${pub.descripcion || ''}</p>
                            
                            <div class="post-actions">
                                <button class="action-btn" onclick="darLike(${pub.id}, this)">
                                    ❤️ <span class="like-count">${pub.likes || 0}</span>
                                </button>
                                <button class="action-btn" onclick="verDetalle(${pub.id})">
                                    📖 Ver receta
                                </button>
                            </div>
                        </div>
                    </div>
                `;
            });

            container.innerHTML = html;
        }

        async function darLike(publicacionId, boton) {
            const token = localStorage.getItem('token');

            try {
                const response = await fetch(`http://localhost:8080/mi-primera-api/rest/publicaciones/${publicacionId}/like`, {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    const likeSpan = boton.querySelector('.like-count');
                    if (likeSpan) {
                        likeSpan.textContent = data.likes;
                    }
                }
            } catch (error) {
                console.error('Error al dar like:', error);
            }
        }

        function verDetalle(publicacionId) {
            alert('Ver detalle de receta ' + publicacionId + ' - Próximamente');
            window.location.href = `vista_detalle.php?id=${publicacionId}`;
        }
    </script>

    <header>
        <nav class="navbar">
            <div class="container">
                <a href="#" class="logo-main">
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
                <a href="/index2.php" class="nav-link">Todas las categorias</a>
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
                    style="background: #008080; color: white; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; margin-left: 15px;">
                    + Nueva Receta
                </button>
            </div>
        </nav>
    </header>

    <div class="welcome-section">
        <h2>Bienvenido a GOSTAR</h2>
        <p>¿Qué vamos a hacer hoy?</p>
    </div>

    <!-- Contenedor para publicaciones dinámicas -->
    <div id="publicaciones-dinamicas" class="publicaciones-container">
        <div class="loader"></div>
    </div>

</body>

</html>