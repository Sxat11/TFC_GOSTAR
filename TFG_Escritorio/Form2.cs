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

namespace GostarApp
{
    public partial class Form2: MaterialForm
    {
        public Form2()
        {
         
            InitializeComponent();
            MaterialSkinManager.Instance.AddFormToManage(this);
            System.Net.ServicePointManager.SecurityProtocol =
        System.Net.SecurityProtocolType.Tls12 |
        System.Net.SecurityProtocolType.Tls11 |
        System.Net.SecurityProtocolType.Tls;
            CargarPublicaciones();
        }
        private async Task CargarPublicaciones()
        {
            panel1.SuspendLayout();
            panel1.Controls.Clear();

            try
            {
                // --- 1. CREAR EL TÍTULO "GOSTAR" ---
                Label lblGostar = new Label();
                lblGostar.Text = "GOSTAR.app";
                lblGostar.Font = new Font(Font.FontFamily, 60);
                lblGostar.ForeColor = Color.Green; // Color verde
                lblGostar.TextAlign = ContentAlignment.MiddleCenter;
                lblGostar.Size = new Size(panel1.Width - 40, 80); // Que ocupe casi todo el ancho
                lblGostar.Margin = new Padding(140, 20, 20, 10); // Espaciado para que no esté pegado

                // Añadimos el título PRIMERO para que esté arriba
                panel1.Controls.Add(lblGostar);

                using (HttpClient client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Authorization =
                        new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Sesion.Token);

                    string url = "http://localhost:8080/mi-primera-api/rest/publicaciones?page=1&limit=20";
                    HttpResponseMessage response = await client.GetAsync(url);

                    if (response.IsSuccessStatusCode)
                    {
                        string json = await response.Content.ReadAsStringAsync();
                        var publicaciones = JsonConvert.DeserializeObject<List<PublicacionModel>>(json);

                        // --- 2. AÑADIR LAS PUBLICACIONES ABAJO ---
                        foreach (var pub in publicaciones)
                        {
                            Publicacion card = new Publicacion();
                            card.SetDatos(pub,Sesion.Token);
                            card.Margin = new Padding(15); // Margen para que no se peguen

                            panel1.Controls.Add(card);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error de red: " + ex.Message);
            }
            finally
            {
                panel1.ResumeLayout();
            }
        }
        
         
        
        private void tabPage3_Click(object sender, EventArgs e)
        {

        }

        private async void tabPage1_Click(object sender, EventArgs e) // Mi Perfil
        {
            // Código espagueti: Petición directa en el evento sin capa de servicio
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Sesion.Token);
                    var response = await client.GetAsync("http://localhost:8080/mi-primera-api/rest/usuarios/perfil");
                    var json = await response.Content.ReadAsStringAsync();
                    dynamic perfil = JsonConvert.DeserializeObject(json);

                    // Asignación directa a controles que probablemente ni existen o están sueltos
                    MessageBox.Show("Bienvenido a tu perfil: " + perfil.nombre + " " + perfil.apellido);
                    // Aquí meteríamos lógica de dibujo manual si no tuviéramos un user control
                }
            }
            catch (Exception ex) { MessageBox.Show(ex.ToString()); }
        }

        private async void tabPage5_Click(object sender, EventArgs e) // Nueva Publicación
        {
            // Spaguetti: Hardcoding de datos y mezcla de lógica
            string textoPost = "Nueva Publicación";

            if (!string.IsNullOrEmpty(textoPost))
            {
                using (HttpClient client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Sesion.Token);
                    var content = new StringContent(JsonConvert.SerializeObject(new { contenido = textoPost }), Encoding.UTF8, "application/json");
                    var res = await client.PostAsync("http://localhost:8080/mi-primera-api/rest/publicaciones", content);

                    if (res.IsSuccessStatusCode)
                    {
                        MessageBox.Show("Publicado con éxito!");
                        // Forzamos un recargue de todo el panel de forma ineficiente
                        CargarPublicaciones();
                    }
                }
            }
        }

        private async void tabPage4_Click(object sender, EventArgs e) // Me gusta
        {
            // Spaguetti: Reutilizamos el panel1 para algo totalmente distinto borrando lo anterior
            panel1.Controls.Clear();
            Label l = new Label() { Text = "Tus Likes", Font = new Font("Arial", 20), AutoSize = true };
            panel1.Controls.Add(l);

            using (HttpClient client = new HttpClient())
            {
                client.DefaultRequestHeaders.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Sesion.Token);
                var json = await client.GetStringAsync("http://localhost:8080/mi-primera-api/rest/publicaciones/likes");
                var likes = JsonConvert.DeserializeObject<List<PublicacionModel>>(json);

                foreach (var pub in likes)
                {
                    // Duplicamos lógica de CargarPublicaciones aquí mismo porque sí
                    Publicacion p = new Publicacion();
                    p.SetDatos(pub,Sesion.Token);
                    panel1.Controls.Add(p);
                }
            }
        }

        private void materialButton1_Click(object sender, EventArgs e)
        {
            // Botón de cerrar sesión tirado en medio de la nada
            Sesion.Token = null;
            this.Hide();
            // Asumimos que Form1 es el Login
            foreach (Form f in Application.OpenForms)
            {
                if (f.Name == "Form1") f.Show();
            }
            this.Close();
        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {

        }
    }
}
