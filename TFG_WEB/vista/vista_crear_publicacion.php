<?php
// Iniciar sesión para verificar token
session_start();
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR - Crear Receta</title>
    <style>
        /* Mismos estilos que antes */
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
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="logo">GOSTAR</div>
        <div class="user-info">
            <span class="username" id="username">Cargando...</span>
            <button class="logout-btn" onclick="logout()">Cerrar sesión</button>
        </div>
    </nav>

    <div class="container">
        <div class="form-card">
            <h1>Comparte tu receta</h1>
            
            <div id="error-mensaje" class="error"></div>
            <div id="success-mensaje" class="success"></div>
            
            <form id="crearRecetaForm">
                <!-- Información básica -->
                <div class="section-title">Información básica</div>
                
                <div class="form-group">
                    <label for="titulo">Título de la receta *</label>
                    <input type="text" id="titulo" name="titulo" required placeholder="Ej: Tortilla de patatas">
                </div>
                
                <div class="row">
                    <div class="col">
                        <div class="form-group">
                            <label for="duracion">Duración *</label>
                            <input type="text" id="duracion" name="duracion" required placeholder="Ej: 30 minutos">
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
                            <label for="calorias">Calorías (opcional)</label>
                            <input type="number" id="calorias" name="calorias" placeholder="Ej: 350">
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-group">
                            <label for="imagenPrincipal">URL de imagen principal *</label>
                            <input type="text" id="imagenPrincipal" name="imagenPrincipal" required placeholder="https://ejemplo.com/imagen.jpg">
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="descripcion">Descripción</label>
                    <textarea id="descripcion" name="descripcion" placeholder="Cuéntanos sobre esta receta..."></textarea>
                </div>
                
                <!-- Ingredientes -->
                <div class="section-title">Ingredientes</div>
                <div class="dynamic-list" id="ingredientes-container">
                    <div class="dynamic-item">
                        <input type="text" class="ingrediente" placeholder="Ej: 4 huevos" required>
                        <button type="button" class="btn-remove" onclick="eliminarIngrediente(this)">×</button>
                    </div>
                </div>
                <button type="button" class="btn-add" onclick="agregarIngrediente()">+ Añadir ingrediente</button>
                
                <!-- Pasos -->
                <div class="section-title">Pasos de preparación</div>
                <div class="dynamic-list" id="pasos-container">
                    <div class="dynamic-item">
                        <input type="text" class="paso" placeholder="Ej: Pelar y cortar las patatas" required>
                        <button type="button" class="btn-remove" onclick="eliminarPaso(this)">×</button>
                    </div>
                </div>
                <button type="button" class="btn-add" onclick="agregarPaso()">+ Añadir paso</button>
                
                <!-- Imágenes adicionales -->
                <div class="section-title">Imágenes adicionales (opcional)</div>
                <div class="dynamic-list" id="imagenes-container">
                    <div class="dynamic-item">
                        <input type="text" class="imagen" placeholder="https://ejemplo.com/imagen1.jpg">
                        <button type="button" class="btn-remove" onclick="eliminarImagen(this)">×</button>
                    </div>
                </div>
                <button type="button" class="btn-add" onclick="agregarImagen()">+ Añadir imagen</button>
                
                <button type="submit" class="btn-submit">Publicar receta</button>
            </form>
        </div>
    </div>

    <script>
        // Verificar token al cargar
        const token = localStorage.getItem('token');
        if (!token) {
            window.location.href = 'index2.php';
        }

        // Mostrar nombre de usuario
        const usuario = JSON.parse(localStorage.getItem('usuario') || '{}');
        document.getElementById('username').textContent = usuario.nombre || usuario.username;

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

        // Enviar formulario - AHORA LLAMA DIRECTAMENTE A LA API
        document.getElementById('crearRecetaForm').addEventListener('submit', async function(e) {
            e.preventDefault();
            
            document.getElementById('error-mensaje').style.display = 'none';
            document.getElementById('success-mensaje').style.display = 'none';
            
            // Recoger ingredientes
            const ingredientes = [];
            document.querySelectorAll('.ingrediente').forEach(input => {
                if (input.value.trim()) {
                    ingredientes.push(input.value.trim());
                }
            });
            
            // Recoger pasos
            const pasos = [];
            document.querySelectorAll('.paso').forEach(input => {
                if (input.value.trim()) {
                    pasos.push(input.value.trim());
                }
            });
            
            // Recoger imágenes adicionales
            const imagenesAdicionales = [];
            document.querySelectorAll('.imagen').forEach(input => {
                if (input.value.trim()) {
                    imagenesAdicionales.push(input.value.trim());
                }
            });
            
            // Validar que haya al menos un ingrediente y un paso
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
                calorias: document.getElementById('calorias').value ? parseInt(document.getElementById('calorias').value) : 0,
                ingredientes: ingredientes,
                pasos: pasos,
                imagenesAdicionales: imagenesAdicionales
            };
            
            console.log('Enviando datos:', datos);
            
            try {
                // ⚠️ IMPORTANTE: Llamada DIRECTA a la API Java
                const response = await fetch('http://localhost:8080/mi-primera-api/rest/publicaciones', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + token
                    },
                    body: JSON.stringify(datos)
                });
                
                const data = await response.json();
                
                if (response.status === 201) {
                    mostrarExito('¡Receta publicada con éxito!');
                    setTimeout(() => {
                        window.location.href = 'vista_principal.php';
                    }, 2000);
                } else {
                    mostrarError(data.error || 'Error al publicar la receta');
                }
                
            } catch (error) {
                console.error('Error completo:', error);
                mostrarError('Error de conexión: ' + error.message);
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
            localStorage.removeItem('token');
            localStorage.removeItem('usuario');
            window.location.href = 'index2.php';
        }
    </script>
</body>
</html>