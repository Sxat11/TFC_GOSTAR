using MaterialSkin;
using MaterialSkin.Controls;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using static System.Collections.Specialized.BitVector32;

namespace GostarApp
{
    public partial class FormIniciarSesion: MaterialForm
    {
        public FormIniciarSesion()
        {
            InitializeComponent();

            // 2. Solo necesitas estas dos líneas para heredar el estilo del FormInicio
            var materialSkinManager = MaterialSkinManager.Instance;
            materialSkinManager.AddFormToManage(this);

        }

        private async void IniciarSesionBtn_Click(object sender, EventArgs e)
        {
            // 1. Validaciones previas
            if (string.IsNullOrEmpty(txtUsuario.Text) || string.IsNullOrEmpty(txtPassword.Text))
            {
                MessageBox.Show("Por favor, rellene todos los campos.");
                return;
            }

            // Bloqueamos el botón para evitar spam
            IniciarSesionBtn.Enabled = false;

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    // URL de tu API (Asegúrate de que el puerto sea el correcto)
                    string url = "http://localhost:8080/mi-primera-api/rest/auth/login";

                    // Creamos el objeto que espera tu API (LoginRequest en Java)
                    var loginData = new
                    {
                        usernameOrEmail = txtUsuario.Text.Trim(),
                        password = txtPassword.Text
                    };

                    string json = JsonConvert.SerializeObject(loginData);
                    var content = new StringContent(json, Encoding.UTF8, "application/json");

                    // 2. Ejecutar la petición
                    HttpResponseMessage response = await client.PostAsync(url, content);

                    // --- AQUÍ IMPLEMENTAMOS EL SUCCESS ---
                    if (response.IsSuccessStatusCode)
                    {
                        string responseBody = await response.Content.ReadAsStringAsync();

                        // Deserializamos la respuesta (Token, UsuarioDTO, etc.)
                        var resultado = JsonConvert.DeserializeObject<LoginResponse>(responseBody);

                        // Guardamos el token globalmente
                        Sesion.Token = resultado.token;

                        MessageBox.Show("¡Login exitoso!", "Bienvenido", MessageBoxButtons.OK, MessageBoxIcon.Information);

                        //3.ABRIR EL NUEVO FORMULARIO
                       Form2 principal = new Form2();
                        principal.Show(); // Mostramos el nuevo
                        this.Hide();      // Ocultamos el login
                    }
                    else if (response.StatusCode == System.Net.HttpStatusCode.Unauthorized)
                    {
                        MessageBox.Show("Usuario o contraseña incorrectos.", "Error de acceso", MessageBoxButtons.OK, MessageBoxIcon.Stop);
                    }
                    else
                    {
                        MessageBox.Show("Error en el servidor: " + response.ReasonPhrase);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error de conexión: " + ex.Message);
            }
            finally
            {
                // Siempre reactivamos el botón al terminar
                IniciarSesionBtn.Enabled = true;
            }
        }

        private void FormIniciarSesion_FormClosed(object sender, FormClosedEventArgs e)
        {
            // Cierra todos los hilos y ventanas de la aplicación inmediatamente
            Application.Exit();
        }
    }
}
