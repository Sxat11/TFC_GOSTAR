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
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GostarApp
{
    public partial class FormCrearCuenta : MaterialForm
    {
        public FormCrearCuenta()
        {
            InitializeComponent();
            var materialSkinManager = MaterialSkinManager.Instance;
            materialSkinManager.AddFormToManage(this);

        }

        private void FormCrearCuenta_Load(object sender, EventArgs e)
        {
            
        }

        private async Task CrearCuentaBtn_ClickAsync(object sender, EventArgs e)
        {
          
        }

        private void FormCrearCuenta_FormClosed(object sender, FormClosedEventArgs e)
        {

            Application.Exit();
        
    }

        private async void CrearCuentaBtn_Click(object sender, EventArgs e)
        {
            // 1. Validaciones básicas en el cliente
            if (string.IsNullOrWhiteSpace(txtUserReg.Text) || string.IsNullOrWhiteSpace(txtPassReg.Text))
            {
                MessageBox.Show("Username y Password son obligatorios.");
                return;
            }

            CrearCuentaBtn.Enabled = false;

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    string url = "http://localhost:8080/mi-primera-api/rest/auth/registro";

                    // 2. Creamos el objeto con los datos de tus MaterialTextBox
                    var nuevosDatos = new UsuarioRegistro
                    {
                        username = txtUserReg.Text.Trim(),
                        email = txtEmailReg.Text.Trim(),
                        passwordHash = txtPassReg.Text, // La API hará el hash SHA-256
                        nombre = txtNombreReg.Text.Trim(),
                        bio = "Nuevo usuario de Gostar",
                        avatarUrl = ""
                    };

                    // 3. Serializar y enviar
                    string json = JsonConvert.SerializeObject(nuevosDatos);
                    var content = new StringContent(json, Encoding.UTF8, "application/json");

                    HttpResponseMessage response = await client.PostAsync(url, content);
                    string responseBody = await response.Content.ReadAsStringAsync();

                    if (response.IsSuccessStatusCode)
                    {
                        // Tu API devuelve un LoginResponse al registrarse con éxito
                        var resultado = JsonConvert.DeserializeObject<LoginResponse>(responseBody);

                        Sesion.Token = resultado.token; // Guardamos el token generado automáticamente

                        MessageBox.Show("¡Cuenta creada con éxito!", "Bienvenido");

                        // Ir al formulario principal
                        Form2 principal = new Form2();
                        principal.Show();
                        this.Hide();
                    }
                    else
                    {
                        // Manejar errores (409 Conflict si el user ya existe, etc.)
                        var errorData = JsonDocument.Parse(responseBody);
                        string msg = errorData.RootElement.GetProperty("error").GetString();
                        MessageBox.Show("Error: " + msg);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error de conexión: " + ex.Message);
            }
            finally
            {
                CrearCuentaBtn.Enabled = true;
            }
        }
    }
}
