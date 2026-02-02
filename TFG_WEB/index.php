


</html> 
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR</title>
    <!--  <link rel="stylesheet" href="style.css"> -->
    <link rel="icon" type="image/x-icon" href="GostarTrans2.png">
</head>

<body>

    <style>
        /* Reset básico */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            /* Degradado suave que recuerda a la cocina natural */
            background: linear-gradient(135deg, #1a1a1a 0%, #0d1117 100%);
            color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            /* Organiza el contenido (main y footer) en vertical */
            min-height: 100vh;
            /* Ocupa el 100% de la altura de la ventana */
            margin: 0;
        }

        .container {
            display: flex;
            width: 100%;
            max-width: 1100px;
            padding: 40px;
            gap: 50px;

        }

        /* Sección del Logo - Solución al fondo blanco */
        .logo-section {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .logo-section img {
            max-width: 350px;
            height: auto;
            /* EL TRUCO: Si tu PNG tiene fondo blanco, esto lo mezcla con el fondo oscuro */
            mix-blend-mode: screen;
            filter: drop-shadow(0 0 20px rgba(76, 175, 80, 0.3));
            /* Brillo verde saludable */
        }

        /* Sección de Autenticación */
        .auth-section {
            flex: 1.2;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        h1 {
            font-size: 52px;
            line-height: 1.1;
            margin-bottom: 30px;
            color: #ffffff;
            font-weight: 800;
        }

        h2 {
            font-size: 28px;
            margin-bottom: 25px;
            color: #4CAF50;
            /* Verde Saludable */
        }

        .button-group {
            max-width: 320px;
        }

        .btn {
            width: 100%;
            padding: 14px;
            border-radius: 12px;
            /* Menos circular, más moderno */
            border: none;
            font-weight: 700;
            cursor: pointer;
            margin-bottom: 15px;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 12px;
            transition: all 0.3s ease;
            font-size: 15px;
        }

        /* Botones sociales */
        .btn-white {
            background-color: #ffffff;
            color: #333;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .btn-white:hover {
            background-color: #f9f9f9;
            transform: translateY(-2px);
        }

        /* Botón Principal: Verde Tradición/Salud */
        .btn-blue {
            background-color: #4CAF50;
            color: #fff;
        }

        .btn-blue:hover {
            background-color: #45a049;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(76, 175, 80, 0.4);
        }

        /* Separador */
        .separator {
            text-align: center;
            position: relative;
            margin: 20px 0;
        }

        .separator::before {
            content: "";
            position: absolute;
            top: 50%;
            left: 0;
            width: 100%;
            height: 1px;
            background-color: #333;
        }

        .separator span {
            background-color: #0d1117;
            padding: 0 15px;
            position: relative;
            color: #888;
            font-size: 14px;
        }

        .terms {
            font-size: 12px;
            color: #888;
            line-height: 1.6;
            margin-top: 10px;
        }

        .terms a {
            color: #4CAF50;
            text-decoration: none;
        }

        /* Login */
        .login-group {
            margin-top: 50px;
        }

        .login-group h3 {
            font-size: 18px;
            margin-bottom: 15px;
            color: #ccc;
        }

        .btn-outline {
            background-color: transparent;
            border: 2px solid #4CAF50;
            color: #4CAF50;
        }

        .btn-outline:hover {
            background-color: rgba(76, 175, 80, 0.1);
        }

        /* footer */
        .site-footer {
            width: 100%;
            text-align: center;
            padding: 25px 0;
            background-color: rgba(0, 0, 0, 0.5);
            /* Un fondo sutil */
            border-top: 1px solid #222;
            /* Línea divisoria elegante */
        }

        .site-footer p {
            color: #ffffff;
            font-size: 16px;
            opacity: 0.7;
            /* Para que sea discreto y no distraiga del login */
        }



        /* Adaptación móvil */
        @media (max-width: 900px) {
            .container {
                flex-direction: column;
                text-align: center;
                padding: 20px;
            }

            .logo-section img {
                max-width: 180px;
                margin-bottom: 30px;
            }

            .button-group,
            .login-group {
                margin-left: auto;
                margin-right: auto;
            }

            h1 {
                font-size: 36px;
            }
        }
    </style>



    <main class="container">
        <section class="logo-section">
            <div class="logo-placeholder"><img src="GostarTrans2.png" alt="Logo de GOSTAR"></div>
        </section>

        <section class="auth-section">
            <h1>Tu descubridor de recetas en tiempo real</h1>
            <h2>Únete hoy</h2>

            <div class="button-group">
                <button class="btn btn-white">
                    <img src="https://www.google.com/favicon.ico" alt="Google"> Registrarse con Google
                </button>
                <button class="btn btn-white">
                    <img src="https://www.apple.com/favicon.ico" alt="Apple"> Registrarse con Apple
                </button>

                <div class="separator"><span>o</span></div>

                <button class="btn btn-blue">Crear cuenta</button>

                <p class="terms">
                    Al registrarte, aceptas los <a href="#">Términos de servicio</a> y la <a href="#">Política de
                        privacidad</a>.
                </p>
            </div>

            <div class="login-group">
                <h3>¿Ya tienes una cuenta?</h3>
                <button class="btn btn-outline">Iniciar sesión</button>
            </div>
        </section>
    </main>
</body>
<footer class="site-footer">
    <p>© 2026 GOSTAR. Todos los derechos reservados.</p>
</footer>

<!-- </html> Ayudame a que se vea mejor con la temática de mi aplicación que es consiste en una aplicación en la que
compartir nuestras recetas y tradiciones al mundo, así como fomentar la buena alimentación y hábitos saludables. -->