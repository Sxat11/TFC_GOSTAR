<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR - Editar Perfil</title>
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
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
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
            max-width: 600px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .edit-card {
            background: white;
            border-radius: 12px;
            padding: 40px;
            box-shadow: 0 4px 16px rgba(0,0,0,0.1);
        }

        .edit-card h1 {
            color: #008080;
            margin-bottom: 30px;
            text-align: center;
            font-size: 28px;
        }

        .avatar-section {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 30px;
        }

        .avatar-preview {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            background: #008080;
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 48px;
            font-weight: bold;
            margin-bottom: 15px;
            position: relative;
            overflow: hidden;
        }

        .avatar-preview img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .avatar-edit-btn {
            background: #008080;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 20px;
            cursor: pointer;
            font-size: 14px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #555;
            font-size: 14px;
        }

        .form-group input,
        .form-group textarea,
        .form-group select {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.2s;
        }

        .form-group input:focus,
        .form-group textarea:focus,
        .form-group select:focus {
            outline: none;
            border-color: #00cc66;
        }

        .form-group textarea {
            resize: vertical;
            min-height: 100px;
        }

        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }

        .form-actions {
            display: flex;
            gap: 15px;
            margin-top: 30px;
        }

        .btn-primary {
            flex: 1;
            padding: 14px;
            background: #00cc66;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
        }

        .btn-primary:hover {
            background: #00cc66;
        }

        .btn-secondary {
            flex: 1;
            padding: 14px;
            background: #f0f0f0;
            color: #333;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: background 0.2s;
        }

        .btn-secondary:hover {
            background: #e0e0e0;
        }

        .error {
            color: #ff4444;
            margin-bottom: 15px;
            padding: 10px;
            background: #ffe6e6;
            border-radius: 5px;
            display: none;
        }

        .success {
            color: #00cc66;
            margin-bottom: 15px;
            padding: 10px;
            background: #e6ffe6;
            border-radius: 5px;
            display: none;
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
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }

        .info-text {
            font-size: 12px;
            color: #999;
            margin-top: 5px;
        }

        .char-count {
            text-align: right;
            font-size: 12px;
            color: #999;
            margin-top: 5px;
        }
    </style>
</head>
<body>
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
        <button class="back-btn" onclick="window.location.href='vista_perfil.php'">← Volver al perfil</button>
        
        <div class="edit-card">
            <h1>Editar Perfil</h1>
            
            <div id="error-mensaje" class="error"></div>
            <div id="success-mensaje" class="success"></div>
            
            <div id="cargando" class="loader"></div>
            
            <form id="editarPerfilForm" style="display: none;">
                <!-- Avatar -->
                <div class="avatar-section">
                    <div class="avatar-preview" id="avatarPreview">
                        <span id="avatarInicial">?</span>
                    </div>
                    <button type="button" class="avatar-edit-btn" onclick="cambiarAvatar()"> Cambiar foto</button>
                    <input type="file" id="avatarInput" accept="image/*" style="display: none;">
                </div>

                <!-- Nombre completo -->
                <div class="form-group">
                    <label for="nombre">Nombre completo</label>
                    <input type="text" id="nombre" name="nombre" placeholder="Tu nombre completo">
                </div>

                <!-- Username (solo lectura) -->
                <div class="form-group">
                    <label for="username">Nombre de usuario</label>
                    <input type="text" id="username" name="username" readonly style="background: #f5f5f5;">
                    <div class="info-text">El nombre de usuario no se puede cambiar</div>
                </div>

                <!-- Email -->
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="tu@email.com">
                </div>

                <!-- Biografía -->
                <div class="form-group">
                    <label for="bio">Biografía</label>
                    <textarea id="bio" name="bio" placeholder="Cuéntanos algo sobre ti..." maxlength="500"></textarea>
                    <div class="char-count" id="charCount">0/500</div>
                </div>

                <!-- Ubicación (País y Región) -->
                <div class="form-row">
                    <div class="form-group">
                        <label for="pais">País</label>
                        <select id="pais" name="pais">
                            <option value="">Selecciona un país</option>
                            <option value="España">🇪🇸 España</option>
                            <option value="Francia">🇫🇷 Francia</option>
                            <option value="Italia">🇮🇹 Italia</option>
                            <option value="Portugal">🇵🇹 Portugal</option>
                            <option value="Alemania">🇩🇪 Alemania</option>
                            <option value="Reino Unido">🇬🇧 Reino Unido</option>
                            <option value="Estados Unidos">🇺🇸 Estados Unidos</option>
                            <option value="México">🇲🇽 México</option>
                            <option value="Argentina">🇦🇷 Argentina</option>
                            <option value="Chile">🇨🇱 Chile</option>
                            <option value="Colombia">🇨🇴 Colombia</option>
                            <option value="Perú">🇵🇪 Perú</option>
                            <option value="Venezuela">🇻🇪 Venezuela</option>
                            <option value="Otro"> Otro</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="region">Región/Ciudad</label>
                        <input type="text" id="region" name="region" placeholder="Ej: Barcelona, Madrid...">
                    </div>
                </div>

                <!-- Enlaces a redes sociales (opcional) -->
                <div class="form-group">
                    <label for="website">Sitio web (opcional)</label>
                    <input type="url" id="website" name="website" placeholder="https://tusitio.com">
                </div>

                <div class="form-group">
                    <label for="instagram">Instagram (opcional)</label>
                    <input type="text" id="instagram" name="instagram" placeholder="@usuario">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn-primary">Guardar cambios</button>
                    <button type="button" class="btn-secondary" onclick="window.location.href='vista_perfil.php'">Cancelar</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        const token = localStorage.getItem('token');
        let usuarioActual = null;

        if (!token) {
            window.location.href = '../index.php';
        }

        document.addEventListener('DOMContentLoaded', function() {
            cargarDatosUsuario();
        });

        async function cargarDatosUsuario() {
            try {
                const response = await fetch('http://localhost:8080/mi-primera-api/rest/auth/perfil', {
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }
                });

                if (!response.ok) {
                    throw new Error('Error al cargar perfil');
                }

                usuarioActual = await response.json();
                console.log('Usuario cargado:', usuarioActual);
                
                // Mostrar nombre de usuario en navbar
                document.getElementById('username').textContent = usuarioActual.nombre || usuarioActual.username;
                
                // Rellenar formulario
                rellenarFormulario();
                
                // Ocultar loader y mostrar formulario
                document.getElementById('cargando').style.display = 'none';
                document.getElementById('editarPerfilForm').style.display = 'block';

            } catch (error) {
                console.error('Error:', error);
                document.getElementById('cargando').style.display = 'none';
                mostrarError('Error al cargar los datos del perfil');
            }
        }

        function rellenarFormulario() {
            // Avatar
            const inicial = (usuarioActual.nombre || usuarioActual.username || 'U').charAt(0).toUpperCase();
            document.getElementById('avatarInicial').textContent = inicial;
            
            // Nombre
            document.getElementById('nombre').value = usuarioActual.nombre || '';
            
            // Username (solo lectura)
            document.getElementById('username').value = usuarioActual.username || '';
            
            // Email
            document.getElementById('email').value = usuarioActual.email || '';
            
            // Bio
            const bio = usuarioActual.bio || '';
            document.getElementById('bio').value = bio;
            document.getElementById('charCount').textContent = bio.length + '/500';
            
            // Parsear bio para obtener país y región (si existe)
            if (bio) {
                const paisMatch = bio.match(/País: ([^,]+)/);
                const regionMatch = bio.match(/Región: (.+)$/);
                
                if (paisMatch) {
                    document.getElementById('pais').value = paisMatch[1].trim();
                }
                
                if (regionMatch) {
                    document.getElementById('region').value = regionMatch[1].trim();
                }
            }
            
            // Website e Instagram (no están en el modelo actual, los dejamos vacíos)
            document.getElementById('website').value = '';
            document.getElementById('instagram').value = '';
        }

        // Contador de caracteres para bio
        document.getElementById('bio').addEventListener('input', function() {
            const count = this.value.length;
            document.getElementById('charCount').textContent = count + '/500';
        });

        function cambiarAvatar() {
            document.getElementById('avatarInput').click();
        }

        document.getElementById('avatarInput').addEventListener('change', function(e) {
            const file = e.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    const avatarPreview = document.getElementById('avatarPreview');
                    avatarPreview.innerHTML = `<img src="${e.target.result}" alt="Avatar">`;
                    
                    // Aquí podrías subir la imagen a un servidor y obtener URL
                    // Por ahora, guardamos la URL temporal
                    console.log('Avatar seleccionado:', file.name);
                };
                reader.readAsDataURL(file);
            }
        });

        // Enviar formulario - AHORA CON LLAMADA REAL A LA API
        document.getElementById('editarPerfilForm').addEventListener('submit', async function(e) {
            e.preventDefault();
            
            document.getElementById('error-mensaje').style.display = 'none';
            document.getElementById('success-mensaje').style.display = 'none';
            
            // Recoger datos
            const nombre = document.getElementById('nombre').value;
            const email = document.getElementById('email').value;
            const bio = document.getElementById('bio').value;
            const pais = document.getElementById('pais').value;
            const region = document.getElementById('region').value;
            
            // Construir bio con país y región
            let nuevaBio = bio;
            if (pais) {
                nuevaBio = (nuevaBio ? nuevaBio + '\n' : '') + 'País: ' + pais;
            }
            if (region) {
                nuevaBio = (nuevaBio ? nuevaBio + ', ' : '') + 'Región: ' + region;
            }
            
            const datosActualizados = {
                nombre: nombre,
                email: email,
                bio: nuevaBio
            };
            
            console.log('Enviando datos:', datosActualizados);
            
            // Mostrar loading en el botón
            const submitBtn = document.querySelector('.btn-primary');
            const originalText = submitBtn.textContent;
            submitBtn.textContent = 'Guardando...';
            submitBtn.disabled = true;
            
            try {
                //  LLAMADA REAL A LA API
                const response = await fetch('http://localhost:8080/mi-primera-api/rest/auth/perfil', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + token
                    },
                    body: JSON.stringify(datosActualizados)
                });
                
                const data = await response.json();
                
                if (response.ok) {
                    mostrarExito('Perfil actualizado correctamente');
                    
                    // Actualizar localStorage
                    if (usuarioActual) {
                        usuarioActual.nombre = nombre;
                        usuarioActual.email = email;
                        usuarioActual.bio = nuevaBio;
                        localStorage.setItem('usuario', JSON.stringify(usuarioActual));
                    }
                    
                    // Redirigir después de 2 segundos
                    setTimeout(() => {
                        window.location.href = 'vista_perfil.php';
                    }, 2000);
                    
                } else {
                    mostrarError(data.error || 'Error al actualizar el perfil');
                    console.error('Error response:', data);
                    submitBtn.textContent = originalText;
                    submitBtn.disabled = false;
                }
                
            } catch (error) {
                console.error('Error completo:', error);
                mostrarError('Error de conexión: ' + error.message);
                submitBtn.textContent = originalText;
                submitBtn.disabled = false;
            }
        });

        function mostrarError(mensaje) {
            const errorDiv = document.getElementById('error-mensaje');
            errorDiv.textContent = mensaje;
            errorDiv.style.display = 'block';
        }

        function mostrarExito(mensaje) {
            const successDiv = document.getElementById('success-mensaje');
            successDiv.textContent = mensaje;
            successDiv.style.display = 'block';
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
    </script>
</body>
</html>