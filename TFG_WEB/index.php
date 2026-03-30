<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR - Iniciar Sesión</title>
    <style>
        /* Estilos Globales */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
        }

        body {
            background-color: #ffffff;
            /* Pocket usa fondos muy claros o blancos */
            color: #1a1a1a;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .main-wrapper {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .container {
            display: flex;
            width: 100%;
            max-width: 1000px;
            align-items: center;
            gap: 60px;
        }

        /* Sección Izquierda: Visual e Info */
        .promo-section {
            flex: 1;
            text-align: center;
        }

        .promo-section img {
            max-width: 100%;
            height: auto;
            /* Simula el efecto de la imagen de dispositivos de Pocket */
            filter: drop-shadow(0 10px 20px rgba(0, 0, 0, 0.05));
        }

        /* Sección Derecha: El Formulario (La Tarjeta) */
        .auth-card {
            flex: 0 0 400px;
            background: #ffffff;
            padding: 40px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            text-align: center;
        }

        h1 {
            font-size: 28px;
            margin-bottom: 30px;
            font-weight: 700;
            color: #1a1a1a;
        }

        /* Inputs de texto */
        .input-group {
            margin-bottom: 15px;
            position: relative;
        }

        .input-field {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
            transition: border-color 0.2s;
        }

        .input-field:focus {
            outline: none;
            border-color: #008080;
            /* El verde azulado de tu logo */
        }

        /* Botón Principal */
        .btn-login {
            width: 100%;
            padding: 14px;
            background-color: #008080;
            /* Cambia al verde de GOSTAR */
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 18px;
            font-weight: 600;
            cursor: pointer;
            margin-top: 10px;
        }

        .btn-login:hover {
            background-color: #006666;
        }

        .forgot-link {
            display: block;
            margin: 15px 0;
            color: #008080;
            text-decoration: none;
            font-size: 14px;
        }

        /* Separador OR */
        .separator {
            display: flex;
            align-items: center;
            text-align: center;
            margin: 25px 0;
            color: #888;
            font-size: 14px;
        }

        .separator::before,
        .separator::after {
            content: '';
            flex: 1;
            border-bottom: 1px solid #eee;
        }

        .separator span {
            padding: 0 10px;
            text-transform: uppercase;
        }

        /* Botones Sociales */
        .btn-social {
            width: 100%;
            padding: 12px;
            margin-bottom: 12px;
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 4px;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.2s;
        }

        .btn-social:hover {
            background-color: #f9f9f9;
        }

        .btn-social img {
            width: 18px;
            height: 18px;
        }

        .signup-text {
            margin-top: 25px;
            font-size: 14px;
        }

        .signup-text a {
            color: #008080;
            text-decoration: none;
            font-weight: 600;
        }

        /* Footer */
        .site-footer {
            padding: 20px;
            text-align: center;
            font-size: 13px;
            color: rgb(0, 0, 0);
            border-top: 1px solid #eee;
        }

        /* Responsive */
        @media (max-width: 850px) {
            .container {
                flex-direction: column;
                gap: 40px;
            }

            .promo-section {
                display: none;
                /* Ocultamos la imagen en móvil como suele hacer Pocket */
            }

            .auth-card {
                flex: 1;
                width: 100%;
                max-width: 400px;
                border: none;
                box-shadow: none;
            }
        }
    </style>
    <link rel="icon" href="FondoNegro.ico" type="image/ico">
</head>

<body>
    
    <main class="main-wrapper">
        <div class="container">
            <section class="promo-section">
                <img src="GostarTrans2.png" alt="Gostar Devices Preview">
            </section>
            <div style="text-align: center; margin-bottom: 20px;">
                <!--   <img src="GostarTrans2.png" alt="GOSTAR" style="height: 40px; width: auto;"> -->
            </div>
            <section class="auth-card">
                <h1>Log In</h1>

                <!-- En la sección del formulario, cambia el <form> por: -->
                <form id="loginForm">
                    <div class="input-group">
                        <input type="text" id="username" class="input-field" placeholder="Email o nombre de usuario" required>
                    </div>
                    <div class="input-group">
                        <input type="password" id="password" class="input-field" placeholder="Contraseña" required>
                    </div>

                    <div id="error-mensaje" style="color: #ff3333; margin-bottom: 10px; display: none;"></div>
                    <div id="success-mensaje" style="color: #00cc66; margin-bottom: 10px; display: none;"></div>

                    <button type="submit" class="btn-login">Log In</button>

                    <a href="#" class="forgot-link">¿Olvidaste tu usuario o contraseña?</a>
                </form>

                <div class="separator">
                    <span>o</span>
                </div>

                <button class="btn-social" onclick="location.href='/vista/vista_principal.php'">
                    <img src="https://www.google.com/favicon.ico" alt="Google"> Continuar con Google

                </button>
                <button class="btn-social">
                    <img src="https://www.apple.com/favicon.ico" alt="Apple"> Continuar con Apple
                </button>
                <button class="btn-social">
                    <img src="https://www.facebook.com/favicon.ico" alt="Facebook"> Continuar con Facebook
                </button>

                <p class="signup-text">
                    ¿No tienes cuenta? <a href="/vista/vista_registro.php">Regístrate ahora</a>
                </p>
            </section>
        </div>
    </main>

    <footer class="site-footer">
        <p>© 2026 GOSTAR. Manuel Hay Fernández. Todos los derechos reservados. </p>
    </footer>

<script>
document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    // Ocultar mensajes anteriores
    document.getElementById('error-mensaje').style.display = 'none';
    document.getElementById('success-mensaje').style.display = 'none';
    
    const datos = {
        usernameOrEmail: document.getElementById('username').value,
        password: document.getElementById('password').value
    };
    
    try {
        const response = await fetch('http://localhost:8080/mi-primera-api/rest/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });
        
        const data = await response.json();
        
        if (response.status === 200) {
            mostrarExito('¡Login exitoso! Redirigiendo...');
            
            // Guardar token y datos del usuario
            localStorage.setItem('token', data.token);
            localStorage.setItem('usuario', JSON.stringify(data.usuario));
            
            // Redirigir después de 2 segundos
            setTimeout(() => {
                window.location.href = '/vista/vista_principal.php';
            }, 2000);
            
        } else {
            mostrarError(data.error || 'Error en el login');
        }
        
    } catch (error) {
        console.error('Error:', error);
        mostrarError('Error de conexión con el servidor');
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

// Si ya hay sesión, redirigir
if (localStorage.getItem('token')) {
    window.location.href = '/vista/vista_principal.php';
}
</script>

</body>

</html>