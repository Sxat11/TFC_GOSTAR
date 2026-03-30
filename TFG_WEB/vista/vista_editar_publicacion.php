<?php
session_start();
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR - Editar Receta</title>
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
            margin-bottom: 20px;
        }

        .container {
            max-width: 800px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .form-card {
            background: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .form-card h1 {
            color: #008080;
            margin-bottom: 30px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
            color: #555;
        }

        input[type="text"],
        input[type="number"],
        textarea,
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }

        textarea {
            resize: vertical;
            min-height: 100px;
        }

        .section-title {
            font-size: 18px;
            font-weight: bold;
            color: #008080;
            margin: 30px 0 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #008080;
        }

        .dynamic-list {
            background: #f9f9f9;
            padding: 20px;
            border-radius: 4px;
            margin-bottom: 20px;
        }

        .dynamic-item {
            display: flex;
            gap: 10px;
            margin-bottom: 10px;
            align-items: center;
        }

        .dynamic-item input {
            flex: 1;
        }

        .btn-add {
            background: #008080;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin-top: 10px;
        }

        .btn-remove {
            background: #ff4444;
            color: white;
            border: none;
            width: 30px;
            height: 30px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        .btn-submit {
            background: #008080;
            color: white;
            border: none;
            padding: 15px 30px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 18px;
            font-weight: bold;
            width: 100%;
            margin-top: 30px;
        }

        .btn-submit:hover {
            background: #006666;
        }

        .error {
            color: #ff3333;
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

        .row {
            display: flex;
            gap: 20px;
        }

        .col {
            flex: 1;
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
    </style>
</head>
<body>
    <nav class="navbar">
          <a href="vista_principal.php" class="logo-main">
                    <img src="../GostarLogoLargo.png" alt="GOSTAR Logo" height="60px">
                </a>
        <div class="user-info">
            <span class="username" id="username">Cargando...</span>
            <button class="logout-btn" onclick="logout()">Cerrar sesión</button>
        </div>
    </nav>

    <div class="container">
        <button class="back-btn" onclick="window.history.back()">← Volver</button>
        
        <div class="form-card">
            <h1>Editar Receta</h1>
            
            <div id="error-mensaje" class="error"></div>
            <div id="success-mensaje" class="success"></div>
            <div id="cargando" class="loader"></div>
            
            <form id="editarRecetaForm" style="display: none;">
                <!-- Información básica -->
                <div class="section-title">Información básica</div>
                
                <div class="form-group">
                    <label for="titulo">Título de la receta *</label>
                    <input type="text" id="titulo" name="titulo" required>
                </div>
                
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="duracion">Duración *</label>
                            <input type="text" id="duracion" name="duracion" required>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-group">
                            <label for="dificultad">Dificultad</label>
                            <select id="dificultad" name="dificultad">
                                <option value="Fácil">Fácil</option>
                                <option value="Media">Media</option>
                                <option value="Difícil">Difícil</option>
                            </select>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="calorias">Calorías</label>
                            <input type="number" id="calorias" name="calorias">
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-group">
                            <label for="imagenPrincipal">URL de imagen principal *</label>
                            <input type="text" id="imagenPrincipal" name="imagenPrincipal" required>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="descripcion">Descripción</label>
                    <textarea id="descripcion" name="descripcion"></textarea>
                </div>
                
                <!-- Ingredientes -->
                <div class="section-title">Ingredientes</div>
                <div class="dynamic-list" id="ingredientes-container"></div>
                <button type="button" class="btn-add" onclick="agregarIngrediente()">+ Añadir ingrediente</button>
                
                <!-- Pasos -->
                <div class="section-title">Pasos de preparación</div>
                <div class="dynamic-list" id="pasos-container"></div>
                <button type="button" class="btn-add" onclick="agregarPaso()">+ Añadir paso</button>
                
                <!-- Imágenes adicionales -->
                <div class="section-title">Imágenes adicionales</div>
                <div class="dynamic-list" id="imagenes-container"></div>
                <button type="button" class="btn-add" onclick="agregarImagen()">+ Añadir imagen</button>
                
                <button type="submit" class="btn-submit">Guardar cambios</button>
            </form>
        </div>
    </div>

    <script>
        const token = localStorage.getItem('token');
        const urlParams = new URLSearchParams(window.location.search);
        const publicacionId = urlParams.get('id');

        if (!token) window.location.href = '../index.php';
        if (!publicacionId) window.location.href = 'vista_principal.php';

        let usuarioActual = null;
        let publicacionOriginal = null;

        document.addEventListener('DOMContentLoaded', function() {
            cargarUsuario();
            cargarPublicacion();
        });

        async function cargarUsuario() {
            try {
                const response = await fetch('http://localhost:8080/mi-primera-api/rest/auth/perfil', {
                    headers: { 'Authorization': 'Bearer ' + token }
                });
                usuarioActual = await response.json();
                document.getElementById('username').textContent = usuarioActual.nombre || usuarioActual.username;
            } catch (error) {
                console.error('Error:', error);
            }
        }

        async function cargarPublicacion() {
            try {
                const response = await fetch(`http://localhost:8080/mi-primera-api/rest/publicaciones/${publicacionId}`, {
                    headers: { 'Authorization': 'Bearer ' + token }
                });

                if (!response.ok) {
                    throw new Error('No se pudo cargar la publicación');
                }

                publicacionOriginal = await response.json();
                
                // Verificar que el usuario es el autor
                if (publicacionOriginal.usuarioId !== usuarioActual?.id) {
                    alert('No tienes permiso para editar esta publicación');
                    window.location.href = 'vista_principal.php';
                    return;
                }

                rellenarFormulario();
                document.getElementById('cargando').style.display = 'none';
                document.getElementById('editarRecetaForm').style.display = 'block';

            } catch (error) {
                console.error('Error:', error);
                document.getElementById('cargando').style.display = 'none';
                document.getElementById('error-mensaje').textContent = 'Error al cargar la publicación';
                document.getElementById('error-mensaje').style.display = 'block';
            }
        }

        function rellenarFormulario() {
            // Datos básicos
            document.getElementById('titulo').value = publicacionOriginal.titulo || '';
            document.getElementById('duracion').value = publicacionOriginal.duracion || '';
            document.getElementById('imagenPrincipal').value = publicacionOriginal.imagenPrincipal || '';
            document.getElementById('descripcion').value = publicacionOriginal.descripcion || '';
            document.getElementById('dificultad').value = publicacionOriginal.dificultad || 'Media';
            document.getElementById('calorias').value = publicacionOriginal.calorias || 0;

            // Ingredientes
            const ingContainer = document.getElementById('ingredientes-container');
            if (publicacionOriginal.ingredientes && publicacionOriginal.ingredientes.length > 0) {
                publicacionOriginal.ingredientes.forEach(ing => {
                    const div = document.createElement('div');
                    div.className = 'dynamic-item';
                    div.innerHTML = `
                        <input type="text" class="ingrediente" value="${ing}" required>
                        <button type="button" class="btn-remove" onclick="eliminarIngrediente(this)">×</button>
                    `;
                    ingContainer.appendChild(div);
                });
            } else {
                agregarIngrediente();
            }

            // Pasos
            const pasosContainer = document.getElementById('pasos-container');
            if (publicacionOriginal.pasos && publicacionOriginal.pasos.length > 0) {
                publicacionOriginal.pasos.forEach(paso => {
                    const div = document.createElement('div');
                    div.className = 'dynamic-item';
                    div.innerHTML = `
                        <input type="text" class="paso" value="${paso}" required>
                        <button type="button" class="btn-remove" onclick="eliminarPaso(this)">×</button>
                    `;
                    pasosContainer.appendChild(div);
                });
            } else {
                agregarPaso();
            }

            // Imágenes adicionales
            const imgContainer = document.getElementById('imagenes-container');
            if (publicacionOriginal.imagenesAdicionales && publicacionOriginal.imagenesAdicionales.length > 0) {
                publicacionOriginal.imagenesAdicionales.forEach(img => {
                    const div = document.createElement('div');
                    div.className = 'dynamic-item';
                    div.innerHTML = `
                        <input type="text" class="imagen" value="${img}">
                        <button type="button" class="btn-remove" onclick="eliminarImagen(this)">×</button>
                    `;
                    imgContainer.appendChild(div);
                });
            } else {
                agregarImagen();
            }
        }

        // Funciones para manejar listas dinámicas
        function agregarIngrediente() {
            const container = document.getElementById('ingredientes-container');
            const div = document.createElement('div');
            div.className = 'dynamic-item';
            div.innerHTML = `
                <input type="text" class="ingrediente" placeholder="Ej: 4 huevos" required>
                <button type="button" class="btn-remove" onclick="eliminarIngrediente(this)">×</button>
            `;
            container.appendChild(div);
        }

        function eliminarIngrediente(boton) {
            const container = document.getElementById('ingredientes-container');
            if (container.children.length > 1) {
                boton.parentElement.remove();
            }
        }

        function agregarPaso() {
            const container = document.getElementById('pasos-container');
            const div = document.createElement('div');
            div.className = 'dynamic-item';
            div.innerHTML = `
                <input type="text" class="paso" placeholder="Ej: Pelar y cortar las patatas" required>
                <button type="button" class="btn-remove" onclick="eliminarPaso(this)">×</button>
            `;
            container.appendChild(div);
        }

        function eliminarPaso(boton) {
            const container = document.getElementById('pasos-container');
            if (container.children.length > 1) {
                boton.parentElement.remove();
            }
        }

        function agregarImagen() {
            const container = document.getElementById('imagenes-container');
            const div = document.createElement('div');
            div.className = 'dynamic-item';
            div.innerHTML = `
                <input type="text" class="imagen" placeholder="https://ejemplo.com/imagen.jpg">
                <button type="button" class="btn-remove" onclick="eliminarImagen(this)">×</button>
            `;
            container.appendChild(div);
        }

        function eliminarImagen(boton) {
            boton.parentElement.remove();
        }

        // Enviar formulario
        document.getElementById('editarRecetaForm').addEventListener('submit', async function(e) {
            e.preventDefault();
            
            document.getElementById('error-mensaje').style.display = 'none';
            document.getElementById('success-mensaje').style.display = 'none';
            
            // Recoger datos
            const ingredientes = [];
            document.querySelectorAll('.ingrediente').forEach(input => {
                if (input.value.trim()) ingredientes.push(input.value.trim());
            });
            
            const pasos = [];
            document.querySelectorAll('.paso').forEach(input => {
                if (input.value.trim()) pasos.push(input.value.trim());
            });
            
            const imagenesAdicionales = [];
            document.querySelectorAll('.imagen').forEach(input => {
                if (input.value.trim()) imagenesAdicionales.push(input.value.trim());
            });
            
            if (ingredientes.length === 0) {
                mostrarError('Debes añadir al menos un ingrediente');
                return;
            }
            
            if (pasos.length === 0) {
                mostrarError('Debes añadir al menos un paso');
                return;
            }
            
            const datos = {
                titulo: document.getElementById('titulo').value,
                duracion: document.getElementById('duracion').value,
                imagenPrincipal: document.getElementById('imagenPrincipal').value,
                descripcion: document.getElementById('descripcion').value,
                dificultad: document.getElementById('dificultad').value,
                calorias: parseInt(document.getElementById('calorias').value) || 0,
                ingredientes: ingredientes,
                pasos: pasos,
                imagenesAdicionales: imagenesAdicionales
            };
            
            const submitBtn = document.querySelector('.btn-submit');
            const originalText = submitBtn.textContent;
            submitBtn.textContent = 'Guardando...';
            submitBtn.disabled = true;
            
            try {
                const response = await fetch(`http://localhost:8080/mi-primera-api/rest/publicaciones/${publicacionId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + token
                    },
                    body: JSON.stringify(datos)
                });
                
                const data = await response.json();
                
                if (response.ok) {
                    mostrarExito('¡Receta actualizada correctamente!');
                    setTimeout(() => {
                        window.location.href = `vista_detalle.php?id=${publicacionId}`;
                    }, 2000);
                } else {
                    mostrarError(data.error || 'Error al actualizar');
                    submitBtn.textContent = originalText;
                    submitBtn.disabled = false;
                }
                
            } catch (error) {
                console.error('Error:', error);
                mostrarError('Error de conexión');
                submitBtn.textContent = originalText;
                submitBtn.disabled = false;
            }
        });

        function mostrarError(mensaje) {
            document.getElementById('error-mensaje').textContent = mensaje;
            document.getElementById('error-mensaje').style.display = 'block';
        }

        function mostrarExito(mensaje) {
            document.getElementById('success-mensaje').textContent = mensaje;
            document.getElementById('success-mensaje').style.display = 'block';
        }

        function logout() {
            fetch('http://localhost:8080/mi-primera-api/rest/auth/logout', {
                method: 'POST',
                headers: { 'Authorization': 'Bearer ' + token }
            }).finally(() => {
                localStorage.removeItem('token');
                localStorage.removeItem('usuario');
                window.location.href = '../index.php';
            });
        }
    </script>
</body>
</html>