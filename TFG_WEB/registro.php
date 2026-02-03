<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GOSTAR - Registro</title>
    <link rel="stylesheet" href="styles.css">
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

        /* Sección Derecha: El Formulario (La Tarjeta) */
        .auth-section {
            flex: 0 0 400px;
            background: #ffffff;
            padding: 40px;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            text-align: center;
        }

        .auth-section h2 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333333;
        }

        .form-group {
            margin-bottom: 15px;
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
            color: #555555;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            font-size: 16px;
        }

        .btn-register {
            width: 100%;
            padding: 12px;
            background-color: #007bff;
            color: #ffffff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 10px;
        }

        .btn-register:hover {
            background-color: #0056b3;
        }

        .login-text {
            margin-top: 15px;
            font-size: 14px;
            color: #666666;
        }

        .login-text a {
            color: #007bff;
            text-decoration: none;
        }

        .login-text a:hover {
            text-decoration: underline;
        }



        @media (min-width: 768px) {
            .auth-section {
                max-width: 500px;
                margin-left: auto;
                margin-right: auto;
            }
        }
    </style>
</head>

<body>
    <main class="main-wrapper">
        <div class="container">
            <section class="auth-section">
                <h2>Regístrate en GOSTAR</h2>
                <form action="procesar_registro.php" method="POST">
                    <div class="form-group">
                        <label for="name">Name </label>
                        <input type="text" id="name" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="surname">Surname</label>
                        <input type="text" id="surname" name="surname" required>
                    </div>

                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label for="confirm_password">Confirm password</label>
                        <input type="password" id="confirm_password" name="confirm_password" required>
                    </div>
                    <div class="form-group">
                        <label for="country">País</label>
                        <select id="country" name="country" required>
                            <option value="" disabled selected>Selecciona tu país</option>
                            <!-- Banderas con emogis -->
                            <option value="es">🇪🇸   España</option>  
                            <option value="fr">🇫🇷 Francia   </option>
                            <option value="it">🇮🇹 Italia</option>
                            <option value="pt">🇵🇹 Portugal</option>
                            <option value="de">🇩🇪 Alemania</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="region">Región (Opcional) </label>
                        <input type="text" id="region" name="region" placeholder="Ex: Barcelona, A Coruña...">

                    </div>
                    <!-- <div class="form-group">
                        <label for="username">Nombre de Usuario</label>
                        <input type="text" id="username" name="username" required>
                    </div> -->



                    <button type="submit" class="btn-register">Registrarse</button>
                </form>
                <p class="login-text">
                    ¿Ya tienes una cuenta? <a href="index2.php">Inicia sesión aquí</a>
                </p>
            </section>
        </div>
    </main>
</body>

</html>