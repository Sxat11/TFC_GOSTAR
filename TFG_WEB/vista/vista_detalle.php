<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR - Detalle de Receta</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
        }

        body {
            background-color: #f5f5f5;
        }

        .navbar {
            background: white;
            padding: 15px 30px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 3px solid #008080;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #008080;
            cursor: pointer;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .username {
            font-weight: 600;
            color: #333;
        }

        .logout-btn {
            padding: 8px 16px;
            background: #ff4444;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .back-btn {
            padding: 8px 16px;
            background: #f0f0f0;
            color: #333;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }

        .container {
            max-width: 800px;
            margin: 30px auto;
            padding: 0 20px;
        }

        /* Cabecera de navegación */
        .nav-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        /* Tarjeta principal de la receta */
        .receta-card {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
        }

        /* Imagen principal */
        .receta-imagen-container {
            width: 100%;
            height: 400px;
            overflow: hidden;
            position: relative;
        }

        .receta-imagen {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .receta-duracion {
            position: absolute;
            bottom: 20px;
            right: 20px;
            background: rgba(0, 0, 0, 0.8);
            color: white;
            padding: 8px 16px;
            border-radius: 25px;
            font-size: 16px;
            font-weight: 500;
        }

        /* Información del autor */
        .autor-info {
            display: flex;
            align-items: center;
            gap: 15px;
            padding: 20px;
            border-bottom: 1px solid #eee;
        }

        .autor-avatar {
            width: 50px;
            height: 50px;
            border-radius: 50%;
            background: #008080;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
            font-weight: bold;
        }

        .autor-detalles {
            flex: 1;
        }

        .autor-nombre {
            font-weight: bold;
            font-size: 18px;
            color: #333;
        }

        .autor-username {
            color: #008080;
            font-size: 14px;
        }

        .fecha-publicacion {
            color: #999;
            font-size: 12px;
        }

        /* Contenido de la receta */
        .receta-contenido {
            padding: 30px;
        }

        .receta-titulo {
            font-size: 32px;
            color: #008080;
            margin-bottom: 15px;
        }

        .receta-descripcion {
            font-size: 16px;
            color: #666;
            line-height: 1.6;
            margin-bottom: 30px;
            padding: 20px;
            background: #f9f9f9;
            border-radius: 8px;
            border-left: 4px solid #008080;
        }

        /* Metadata */
        .receta-metadata {
            display: flex;
            gap: 20px;
            margin-bottom: 30px;
            flex-wrap: wrap;
        }

        .metadata-item {
            background: #f0f0f0;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        /* Secciones */
        .seccion {
            margin-bottom: 40px;
        }

        .seccion-titulo {
            font-size: 22px;
            color: #333;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #008080;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        /* Ingredientes */
        .ingredientes-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 15px;
        }

        .ingrediente-item {
            background: #f9f9f9;
            padding: 12px 15px;
            border-radius: 8px;
            border-left: 3px solid #008080;
            font-size: 15px;
            color: #555;
        }

        /* Pasos */
        .pasos-list {
            list-style: none;
            counter-reset: paso-counter;
        }

        .paso-item {
            counter-increment: paso-counter;
            margin-bottom: 20px;
            padding: 20px;
            background: #f9f9f9;
            border-radius: 8px;
            position: relative;
            padding-left: 60px;
        }

        .paso-item::before {
            content: counter(paso-counter);
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            width: 30px;
            height: 30px;
            background: #008080;
            color: white;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
        }

        /* Galería de imágenes */
        .galeria {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
            gap: 15px;
            margin-top: 20px;
        }

        .galeria-item {
            position: relative;
            padding-top: 100%;
            overflow: hidden;
            border-radius: 8px;
            cursor: pointer;
        }

        .galeria-item img {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.3s;
        }

        .galeria-item:hover img {
            transform: scale(1.1);
        }

        /* Acciones */
        .receta-acciones {
            display: flex;
            gap: 20px;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }

        .action-btn {
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 8px;
            transition: all 0.2s;
        }

        .like-btn {
            background: #ff4444;
            color: white;
        }

        .like-btn:hover {
            background: #ff6666;
            transform: scale(1.05);
        }

        .like-btn.liked {
            background: #ff1744;
        }

        .edit-btn {
            background: #008080;
            color: white;
        }

        .edit-btn:hover {
            background: #006666;
        }

        .delete-btn {
            background: #ff4444;
            color: white;
        }

        .delete-btn:hover {
            background: #ff6666;
        }

        /* Modal para imágenes */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.9);
            align-items: center;
            justify-content: center;
        }

        .modal-content {
            max-width: 90%;
            max-height: 90%;
            object-fit: contain;
        }

        .modal-close {
            position: absolute;
            top: 20px;
            right: 30px;
            color: white;
            font-size: 40px;
            cursor: pointer;
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

        .error-mensaje {
            text-align: center;
            padding: 60px;
            background: white;
            border-radius: 8px;
            color: #ff4444;
        }

        .error-mensaje button {
            margin-top: 20px;
            padding: 12px 24px;
            background: #008080;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
</head>

<body>
    <!-- Modal para imágenes grandes -->
    <div id="imageModal" class="modal" onclick="cerrarModal()">
        <span class="modal-close">&times;</span>
        <img class="modal-content" id="modalImage">
    </div>

    <nav class="navbar">
          <a href="vista_principal.php" class="logo-main">
                    <img src="../GostarLogoLargo.png" alt="GOSTAR Logo" height="60px">
                </a>
        <div class="user-info">
            <span class="username" id="username">Cargando...</span>
            <a href="vista_crear_publicacion.php" style="background: #008080; color: white; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; text-decoration: none;">+ Nueva Receta</a>
            <button class="logout-btn" onclick="logout()">Cerrar sesión</button>
        </div>
    </nav>

    <div class="container">
        <div class="nav-header">
            <button class="back-btn" onclick="window.history.back()">← Volver</button>
        </div>

        <div id="receta-container">
            <div class="loader"></div>
        </div>
    </div>

    <script>
        const token = localStorage.getItem('token');
        let recetaActual = null;
        let usuarioActual = null;

        if (!token) {
            window.location.href = 'index.php';
        }

        // Obtener ID de la receta de la URL
        const urlParams = new URLSearchParams(window.location.search);
        const recetaId = urlParams.get('id');

        if (!recetaId) {
            window.location.href = 'vista_principal.php';
        }

        document.addEventListener('DOMContentLoaded', function() {
            cargarUsuario();
            cargarReceta();
        });

        async function cargarUsuario() {
            try {
                const response = await fetch('http://localhost:8080/mi-primera-api/rest/auth/perfil', {
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });
                usuarioActual = await response.json();
                document.getElementById('username').textContent = usuarioActual.nombre || usuarioActual.username;
            } catch (error) {
                console.error('Error cargando usuario:', error);
            }
        }

        async function cargarReceta() {
            try {
                const response = await fetch(`http://localhost:8080/mi-primera-api/rest/publicaciones/${recetaId}`, {
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                if (!response.ok) {
                    throw new Error('Receta no encontrada');
                }

                recetaActual = await response.json();
                mostrarReceta();

            } catch (error) {
                console.error('Error:', error);
                document.getElementById('receta-container').innerHTML = `
                    <div class="error-mensaje">
                        <h2> Receta no encontrada</h2>
                        <p>La receta que buscas no existe o ha sido eliminada</p>
                        <button onclick="window.location.href='vista_principal.php'">Ir al inicio</button>
                    </div>
                `;
            }
        }

        function mostrarReceta() {
            const container = document.getElementById('receta-container');

            // Formatear fecha
            const fecha = new Date(recetaActual.fechaCreacion);
            const fechaFormateada = fecha.toLocaleDateString('es-ES', {
                day: 'numeric',
                month: 'long',
                year: 'numeric'
            });

            // Obtener inicial del autor
            const inicial = (recetaActual.usuarioNombre || 'U').charAt(0).toUpperCase();

            // Generar HTML de ingredientes
            const ingredientesHTML = recetaActual.ingredientes?.map(ing =>
                `<div class="ingrediente-item">${ing}</div>`
            ).join('') || '<p>No hay ingredientes</p>';

            // Generar HTML de pasos
            const pasosHTML = recetaActual.pasos?.map(paso =>
                `<li class="paso-item">${paso}</li>`
            ).join('') || '<p>No hay pasos</p>';

            // Generar HTML de galería
            const galeriaHTML = recetaActual.imagenesAdicionales?.map(img =>
                `<div class="galeria-item" onclick="abrirModal('${img}')">
                    <img src="${img}" alt="Imagen adicional">
                </div>`
            ).join('') || '';

            // Verificar si el usuario actual es el autor
            const esAutor = usuarioActual && usuarioActual.id === recetaActual.usuarioId;

            container.innerHTML = `
                <div class="receta-card">
                    <div class="receta-imagen-container">
                        <img src="${recetaActual.imagenPrincipal}" alt="${recetaActual.titulo}" class="receta-imagen">
                        <span class="receta-duracion">⏱️ ${recetaActual.duracion}</span>
                    </div>

                    <div class="autor-info">
                        <div class="autor-avatar">${inicial}</div>
                        <div class="autor-detalles">
                            <div class="autor-nombre">${recetaActual.usuarioNombre || 'Usuario'}</div>
                            <div class="fecha-publicacion">Publicado el ${fechaFormateada}</div>
                        </div>
                    </div>

                    <div class="receta-contenido">
                        <h1 class="receta-titulo">${recetaActual.titulo}</h1>
                        
                        <div class="receta-metadata">
                            <span class="metadata-item"> ${recetaActual.dificultad || 'No especificada'}</span>
                            <span class="metadata-item"> ${recetaActual.calorias || 0} kcal</span>
                        </div>

                        <div class="receta-descripcion">
                            ${recetaActual.descripcion || 'Sin descripción'}
                        </div>

                        <div class="seccion">
                            <h2 class="seccion-titulo"> Ingredientes</h2>
                            <div class="ingredientes-grid">
                                ${ingredientesHTML}
                            </div>
                        </div>

                        <div class="seccion">
                            <h2 class="seccion-titulo"> Pasos a seguir</h2>
                            <ul class="pasos-list">
                                ${pasosHTML}
                            </ul>
                        </div>

                        ${recetaActual.imagenesAdicionales?.length ? `
                            <div class="seccion">
                                <h2 class="seccion-titulo"> Más imágenes</h2>
                                <div class="galeria">
                                    ${galeriaHTML}
                                </div>
                            </div>
                        ` : ''}

                       

<div class="receta-acciones">
    <button class="action-btn like-btn ${recetaActual.likedByCurrentUser ? 'liked' : ''}" onclick="toggleLike()">
         <span id="like-count">${recetaActual.likes || 0}</span> Me gusta
    </button>
    
    ${esAutor ? `
        <button class="action-btn edit-btn" onclick="editarReceta()">
             Editar receta
        </button>
        <button class="action-btn delete-btn" onclick="eliminarReceta()">
             Eliminar
        </button>
    ` : ''}
</div>
                    </div>
                </div>
            `;
        }

        async function toggleLike() {
            try {
                const response = await fetch(`http://localhost:8080/mi-primera-api/rest/publicaciones/${recetaId}/like`, {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    document.getElementById('like-count').textContent = data.likes;

                    const likeBtn = document.querySelector('.like-btn');
                    if (data.liked) {
                        likeBtn.classList.add('liked');
                    } else {
                        likeBtn.classList.remove('liked');
                    }

                    recetaActual.likes = data.likes;
                    recetaActual.likedByCurrentUser = data.liked;
                }
            } catch (error) {
                console.error('Error al dar like:', error);
                alert('Error al procesar el like');
            }
        }

        function abrirModal(imgSrc) {
            const modal = document.getElementById('imageModal');
            const modalImg = document.getElementById('modalImage');
            modal.style.display = 'flex';
            modalImg.src = imgSrc;
        }

        function cerrarModal() {
            document.getElementById('imageModal').style.display = 'none';
        }

        function editarReceta() {
            window.location.href = `vista_editar_publicacion.php?id=${recetaId}`;
        }

        async function eliminarReceta() {
            if (!confirm('¿Estás seguro de que quieres eliminar esta receta?')) {
                return;
            }

            try {
                const response = await fetch(`http://localhost:8080/mi-primera-api/rest/publicaciones/${recetaId}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                if (response.ok) {
                    alert('Receta eliminada correctamente');
                    window.location.href = 'vista_principal.php';
                } else {
                    alert('Error al eliminar la receta');
                }
            } catch (error) {
                console.error('Error:', error);
                alert('Error de conexión');
            }
        }

        function logout() {
            if (token) {
                fetch('http://localhost:8080/mi-primera-api/rest/auth/logout', {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                }).finally(() => {
                    localStorage.removeItem('token');
                    localStorage.removeItem('usuario');
                    window.location.href = 'index.php';
                });
            }
        }

        // Cerrar modal con tecla ESC
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Escape') {
                cerrarModal();
            }
        });

        async function eliminarReceta() {
            if (!confirm('¿Estás seguro de que quieres eliminar esta receta? Esta acción no se puede deshacer.')) {
                return;
            }

            const token = localStorage.getItem('token');
            const deleteBtn = document.querySelector('.delete-btn');
            const originalText = deleteBtn.textContent;

            try {
                deleteBtn.textContent = 'Eliminando...';
                deleteBtn.disabled = true;

                const response = await fetch(`http://localhost:8080/mi-primera-api/rest/publicaciones/${recetaId}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                const data = await response.json();

                if (response.ok) {
                    alert(' Receta eliminada correctamente');
                    window.location.href = 'vista_perfil.php';
                } else {
                    alert(' Error: ' + (data.error || 'No se pudo eliminar la receta'));
                    deleteBtn.textContent = originalText;
                    deleteBtn.disabled = false;
                }
            } catch (error) {
                console.error('Error:', error);
                alert(' Error de conexión al eliminar la receta');
                deleteBtn.textContent = originalText;
                deleteBtn.disabled = false;
            }
        }
    </script>
</body>

</html>