<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR - Mi Perfil</title>
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
            border-bottom: 3px solid #298300;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
            color: #298300;
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
            font-size: medium;

        }

        .crear-btn {
            padding: 8px 16px;
            background: #298300;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: medium;
            text-decoration: none;
        }

        .nav-link {
            display: flex;
            align-items: center;
            gap: 10px;
            text-decoration: none;
            color: #333;
            font-weight: 600;
        }

        .icon {
            height: 30px;
            width: auto;
        }

        .container {
            max-width: 800px;
            margin: 30px auto;
            padding: 0 20px;
        }

        /* Tarjeta de perfil */
        .profile-card {
            background: white;
            border-radius: 8px;
            padding: 40px;
            margin-bottom: 30px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            display: flex;
            gap: 40px;
            align-items: center;
        }

        .profile-avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            background: #298300;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 48px;
            font-weight: bold;
            box-shadow: 0 4px 12px rgba(0, 128, 128, 0.3);
        }

        .profile-info {
            flex: 1;
        }

        .profile-name {
            font-size: 28px;
            font-weight: bold;
            color: #333;
            margin-bottom: 5px;
        }

        .profile-username {
            font-size: 16px;
            color: #298300;
            margin-bottom: 10px;
        }

        .profile-email {
            font-size: 14px;
            color: #666;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .profile-bio {
            font-size: 14px;
            color: #666;
            line-height: 1.6;
            padding: 15px;
            background: #f9f9f9;
            border-radius: 8px;
            margin-top: 10px;
        }

        .profile-stats {
            display: flex;
            gap: 30px;
            margin-top: 20px;
        }

        .stat {
            text-align: center;
        }

        .stat-number {
            font-size: 24px;
            font-weight: bold;
            color: #298300;
        }

        .stat-label {
            font-size: 12px;
            color: #666;
            text-transform: uppercase;
        }

        /* Tabs */
        .tabs {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
            border-bottom: 2px solid #ddd;
            padding-bottom: 10px;
        }

        .tab {
            padding: 10px 20px;
            cursor: pointer;
            font-weight: 600;
            color: #666;
            transition: all 0.2s;
        }

        .tab.active {
            color: #298300;
            border-bottom: 3px solid #298300;
            margin-bottom: -12px;
        }

        /* Grid de publicaciones */
        .publicaciones-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }

        .publicacion-card {
            background: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s;
            cursor: pointer;
        }

        .publicacion-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
        }

        .publicacion-image {
            width: 100%;
            height: 150px;
            object-fit: cover;
        }

        .publicacion-info {
            padding: 15px;
        }

        .publicacion-titulo {
            font-weight: bold;
            color: #333;
            margin-bottom: 5px;
        }

        .publicacion-duracion {
            font-size: 12px;
            color: #008080;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .publicacion-likes {
            font-size: 12px;
            color: #ff4444;
            display: flex;
            align-items: center;
            gap: 5px;
            margin-top: 8px;
        }

        .no-publicaciones {
            text-align: center;
            padding: 60px;
            background: white;
            border-radius: 8px;
            color: #666;
            grid-column: 1 / -1;
        }

        .no-publicaciones button {
            margin-top: 20px;
            padding: 12px 24px;
            background: #298300;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        .loader {
            border: 4px solid #f3f3f3;
            border-top: 4px solid #298300;
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

        .edit-btn {
            padding: 8px 16px;
            background: #f0f0f0;
            color: #333;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }

        .edit-btn:hover {
            background: #e0e0e0;
        }
    </style>
        <link rel="icon" href="FondoNegro.ico" type="image/ico">

</head>

<body>
    <nav class="navbar">
        <a href="vista_principal.php" class="logo-main">
                    <img src="../GostarLogoLargo.png" alt="GOSTAR Logo" height="60px">
                </a>
        <div class="user-info">
            <span class="username" id="username">Cargando...</span>
            <a href="vista_crear_publicacion.php" class="crear-btn">Nueva Receta</a>
            <button class="logout-btn" onclick="logout()">Cerrar sesión</button>
        </div>
    </nav>

    <div class="container">
        <!-- Tarjeta de perfil -->
        <div class="profile-card" id="profile-card">
            <div class="profile-avatar" id="profile-avatar">?</div>
            <div class="profile-info">
                <div class="profile-name" id="profile-name">Cargando...</div>
                <div class="profile-username" id="profile-username">@cargando</div>
                <div class="profile-email" id="profile-email"> cargando...</div>

                <div class="profile-stats">
                    <div class="stat">
                        <div class="stat-number" id="stat-recetas">0</div>
                        <div class="stat-label">Recetas</div>
                    </div>
                    <div class="stat">
                        <div class="stat-number" id="stat-likes">0</div>
                        <div class="stat-label">Likes</div>
                    </div>
                </div>

                <div class="profile-bio" id="profile-bio"></div>
                <button class="edit-btn" onclick="editarPerfil()"> Editar perfil</button>
            </div>
        </div>

        <!-- Tabs -->
        <div class="tabs">
            <div class="tab active" onclick="cambiarTab('publicaciones')">Mis Recetas</div>
            <div class="tab" onclick="cambiarTab('favoritos')">Favoritos</div>
        </div>

        <!-- Contenedor de publicaciones -->
        <div id="publicaciones-container">
            <div class="loader"></div>
        </div>
    </div>

    <script>
        const token = localStorage.getItem('token');
        let usuarioActual = null;
        let tabActual = 'publicaciones';

        // Verificar token al cargar
        if (!token) {
            window.location.href = '../index.php';
        }

        // Cargar datos al iniciar
        document.addEventListener('DOMContentLoaded', function() {
            cargarPerfil();
        });

        async function cargarPerfil() {
            try {
                // Obtener datos del usuario desde el token
                const response = await fetch('http://localhost:8080/mi-primera-api/rest/auth/perfil', {
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                if (!response.ok) {
                    throw new Error('Error al cargar perfil');
                }

                const usuario = await response.json();
                usuarioActual = usuario;

                // Actualizar UI del perfil
                document.getElementById('username').textContent = usuario.nombre || usuario.username;
                document.getElementById('profile-name').textContent = usuario.nombre || 'Usuario';
                document.getElementById('profile-username').textContent = '@' + (usuario.username || 'usuario');
                document.getElementById('profile-email').textContent = ' ' + (usuario.email || '');
                document.getElementById('profile-bio').textContent = usuario.bio || 'Sin biografía aún';

                // Avatar con inicial
                const inicial = (usuario.nombre || usuario.username || 'U').charAt(0).toUpperCase();
                document.getElementById('profile-avatar').textContent = inicial;

                // Cargar publicaciones del usuario
                cargarMisPublicaciones();

            } catch (error) {
                console.error('Error:', error);
                mostrarError('Error al cargar el perfil');
            }
        }

       async function cargarMisPublicaciones() {
    const container = document.getElementById('publicaciones-container');

    try {
        // Obtener ID del usuario actual
        const perfilResponse = await fetch('http://localhost:8080/mi-primera-api/rest/auth/perfil', {
            headers: { 'Authorization': 'Bearer ' + token }
        });
        const perfil = await perfilResponse.json();
        const usuarioId = perfil.id;
        console.log(' Usuario ID actual:', usuarioId);

        // Obtener TODAS las publicaciones
        const response = await fetch('http://localhost:8080/mi-primera-api/rest/publicaciones?page=1&limit=100', {
            headers: { 'Authorization': 'Bearer ' + token }
        });

        if (!response.ok) {
            throw new Error('Error HTTP: ' + response.status);
        }

        const todasPublicaciones = await response.json();
        console.log(' Respuesta completa:', todasPublicaciones);
        
        if (todasPublicaciones.length > 0) {
            const primeraPub = todasPublicaciones[0];
            console.log(' PRIMERA PUBLICACIÓN (COMPLETA):', primeraPub);
            console.log(' CAMPOS DISPONIBLES:', Object.keys(primeraPub));
            console.log(' Valor de usuarioId:', primeraPub.usuarioId);
            console.log(' Valor de usuario_id:', primeraPub.usuario_id);
            console.log(' Valor de userId:', primeraPub.userId);
        }

        // Filtrar solo las del usuario actual
        const misPublicaciones = todasPublicaciones.filter(pub => {
            // Comprobar TODOS los posibles nombres
            const posibleId = pub.usuarioId || pub.usuario_id || pub.userId || pub.user_id;
            console.log(`Pub ID ${pub.id}: posibleId=${posibleId}, tipo=${typeof posibleId}, usuarioActual=${usuarioId}, coincide=${posibleId === usuarioId}`);
            return posibleId === usuarioId;
        });

        console.log(' Mis publicaciones filtradas:', misPublicaciones);

        // Actualizar estadísticas
        document.getElementById('stat-recetas').textContent = misPublicaciones.length;
        const totalLikes = misPublicaciones.reduce((sum, pub) => sum + (pub.likes || 0), 0);
        document.getElementById('stat-likes').textContent = totalLikes;

        mostrarPublicaciones(misPublicaciones);

    } catch (error) {
        console.error('Error completo:', error);
        container.innerHTML = `
            <div class="no-publicaciones">
                <p style="color: #ff4444;">Error: ${error.message}</p>
                <button onclick="location.reload()">Reintentar</button>
            </div>
        `;
    }
}

        async function cargarFavoritos() {
            const container = document.getElementById('publicaciones-container');

            // Por ahora, mostrar mensaje de próximamente
            container.innerHTML = `
                <div class="no-publicaciones">
                    <h3> Próximamente</h3>
                    <p>La sección de favoritos estará disponible pronto</p>
                </div>
            `;
        }

       function mostrarPublicaciones(publicaciones) {
    console.log(' Mostrando publicaciones:', publicaciones);
    const container = document.getElementById('publicaciones-container');

    if (!publicaciones || publicaciones.length === 0) {
        container.innerHTML = `
            <div class="no-publicaciones">
                <h3>¡No has publicado ninguna receta!</h3>
                <p>Comparte tu primera receta con la comunidad</p>
                <button onclick="window.location.href='vista_crear_publicacion.php'">
                    Crear primera receta
                </button>
            </div>
        `;
        return;
    }

    let html = '<div class="publicaciones-grid">';
    publicaciones.forEach(pub => {
        console.log('Renderizando pub:', pub.id, pub.titulo);
        html += `
            <div class="publicacion-card" onclick="verDetalle(${pub.id})">
                <img src="${pub.imagenPrincipal}" alt="${pub.titulo}" class="publicacion-image">
                <div class="publicacion-info">
                    <div class="publicacion-titulo">${pub.titulo}</div>
                    <div class="publicacion-duracion"> ${pub.duracion}</div>
                </div>
            </div>
        `;
    });
    html += '</div>';
    container.innerHTML = html;
}

        function cambiarTab(tab) {
            tabActual = tab;

            // Actualizar clases de los tabs
            document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
            event.target.classList.add('active');

            // Cargar contenido según tab
            if (tab === 'publicaciones') {
                cargarMisPublicaciones();
            } else if (tab === 'favoritos') {
                cargarFavoritos();
            }
        }

        function verDetalle(publicacionId) {
            window.location.href = `vista_detalle.php?id=${publicacionId}`;
        }

        function editarPerfil() {
            window.location.href = 'vista_editar_perfil.php';
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
                    window.location.href = '../index.php';
                });
            }
        }

        function mostrarError(mensaje) {
            const container = document.getElementById('publicaciones-container');
            container.innerHTML = `
                <div class="no-publicaciones">
                    <p style="color: #ff4444;">${mensaje}</p>
                    <button onclick="location.reload()">Reintentar</button>
                </div>
            `;
        }

        function verDetalle(publicacionId) {
            window.location.href = `vista_detalle.php?id=${publicacionId}`;
        }
    </script>
</body>

</html>