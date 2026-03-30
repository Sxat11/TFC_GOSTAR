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
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GostarApp
{
    public partial class Form2 : MaterialForm
    {
        public Form2()
        {
            InitializeComponent();
            MaterialSkinManager.Instance.AddFormToManage(this);

            // Protocolos de seguridad
            System.Net.ServicePointManager.SecurityProtocol =
                System.Net.SecurityProtocolType.Tls12 |
                System.Net.SecurityProtocolType.Tls11 |
                System.Net.SecurityProtocolType.Tls;

            CargarPublicaciones(); // Carga la pestaña principal

            // SUSCRIPCIÓN POR CÓDIGO (Por si el paso 1 te falla)
            this.materialTabControl1.SelectedIndexChanged += new System.EventHandler(this.materialTabControl1_SelectedIndexChanged);
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
                            card.SetDatos(pub, Sesion.Token);
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
                    p.SetDatos(pub, Sesion.Token);
                    panel1.Controls.Add(p);
                }
            }
        }

        private void materialButton1_Click(object sender, EventArgs e)
        {

        }

        private void panel1_Paint(object sender, PaintEventArgs e)
        {

        }

        private void pageBuscador_Click(object sender, EventArgs e)
        {
            lblGostarBuscador.Text = "Gostar Buscador";
            lblGostarBuscador.Font = new Font("Arial", 40, FontStyle.Bold); // Tamaño grande
            lblGostarBuscador.ForeColor = Color.LimeGreen; // Color verde llamativo
            lblGostarBuscador.TextAlign = ContentAlignment.MiddleCenter;
        }

        private async void btnBuscar_Click(object sender, EventArgs e)
        {
            // 1. Obtener el texto
            string query = txtBuscador.Text.Trim();
            if (string.IsNullOrEmpty(query)) return;
            lblGostarBuscador.Text = "Gostar Buscador";
            lblGostarBuscador.Font = new Font("Arial", 40, FontStyle.Bold); // Tamaño grande
            lblGostarBuscador.ForeColor = Color.LimeGreen; // Color verde llamativo
            lblGostarBuscador.TextAlign = ContentAlignment.MiddleCenter;
            btnBuscar.Enabled = false;

            // 2. LIMPIEZA SELECTIVA
            // En lugar de panel2.Controls.Clear(), vamos a borrar solo los UserControls de tipo Publicacion
            // para que el botón, el buscador y el título se queden donde están.
            panel2.SuspendLayout();

            for (int i = panel2.Controls.Count - 1; i >= 0; i--)
            {
                // Si el control es una Publicación (receta), lo quitamos.
                // Los TextBox, Buttons y Labels de título se ignoran.
                if (panel2.Controls[i] is Publicacion)
                {
                    panel2.Controls.RemoveAt(i);
                }
            }

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Authorization =
                        new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Sesion.Token);

                    string url = $"http://localhost:8080/mi-primera-api/rest/publicaciones/buscar?q={Uri.EscapeDataString(query)}";
                    HttpResponseMessage response = await client.GetAsync(url);

                    if (response.IsSuccessStatusCode)
                    {
                        string json = await response.Content.ReadAsStringAsync();
                        var resultados = JsonConvert.DeserializeObject<List<PublicacionModel>>(json);

                        if (resultados != null && resultados.Count > 0)
                        {
                            // Punto de partida: Debajo de tu buscador (ajusta el eje Y según tu diseño)
                            int puntoY = txtBuscador.Bottom + 20;

                            foreach (var pub in resultados)
                            {
                                Publicacion card = new Publicacion();
                                card.SetDatos(pub, Sesion.Token);

                                // Posicionamiento manual en el mismo panel
                                card.Location = new Point(20, puntoY);
                                card.Width = panel2.Width - 60; // Ajustar al ancho del panel

                                panel2.Controls.Add(card);

                                // Bajamos el punto Y para la siguiente carta
                                puntoY += card.Height + 15;
                            }
                        }
                        else
                        {
                            MessageBox.Show("No se encontraron resultados.");
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al buscar: " + ex.Message);
            }
            finally
            {
                panel2.ResumeLayout();
                btnBuscar.Enabled = true;
            }
        }

        private void pageBuscador_Paint(object sender, PaintEventArgs e)
        {
            lblGostarBuscador.Text = "Gostar Buscador";
            lblGostarBuscador.Font = new Font("Arial", 40, FontStyle.Bold); // Tamaño grande
            lblGostarBuscador.ForeColor = Color.LimeGreen; // Color verde llamativo
            lblGostarBuscador.TextAlign = ContentAlignment.MiddleCenter;
        }

        private async void pageFavoritos_Click(object sender, EventArgs e)
        {
            // MENSAJE 1: ¿Entra aquí?
            MessageBox.Show("Evento disparado");

            panel3.SuspendLayout();
            panel3.AutoScroll = true;

            // Limpieza
            for (int i = panel3.Controls.Count - 1; i >= 0; i--)
            {
                if (panel3.Controls[i] is Publicacion) panel3.Controls.RemoveAt(i);
            }

            // Título
            Label lblFav = panel3.Controls.Find("lblTituloFav", false).FirstOrDefault() as Label;
            if (lblFav == null)
            {
                lblFav = new Label
                {
                    Name = "lblTituloFav",
                    Text = "Tus Recetas Favoritas",
                    Font = new Font("Arial", 45, FontStyle.Bold),
                    ForeColor = Color.LimeGreen,
                    Size = new Size(panel3.Width - 40, 80),
                    Location = new Point(20, 20),
                    TextAlign = ContentAlignment.MiddleCenter
                };
                panel3.Controls.Add(lblFav);
            }

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Authorization =
                        new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Sesion.Token);

                    string url = "http://localhost:8080/mi-primera-api/rest/publicaciones?page=1&limit=100";
                    var response = await client.GetAsync(url);

                    if (response.IsSuccessStatusCode)
                    {
                        string json = await response.Content.ReadAsStringAsync();
                        var todas = JsonConvert.DeserializeObject<List<PublicacionModel>>(json);

                        // MENSAJE 2: ¿Cuántas recetas llegan en total?
                        // MessageBox.Show("Recetas recibidas del servidor: " + todas.Count);

                        var misFavoritos = todas.Where(pub => pub.likedByCurrentUser == true).ToList();

                        // MENSAJE 3: ¿Cuántas pasan el filtro?
                        // MessageBox.Show("Recetas con Like: " + misFavoritos.Count);

                        if (misFavoritos.Count > 0)
                        {
                            int puntoY = lblFav.Bottom + 40;
                            foreach (var pub in misFavoritos)
                            {
                                Publicacion card = new Publicacion();
                                card.SetDatos(pub, Sesion.Token);
                                card.Location = new Point((panel3.Width - card.Width) / 2, puntoY);
                                panel3.Controls.Add(card);
                                puntoY += card.Height + 20;
                            }
                        }
                        else
                        {
                            // Si llega aquí es que el filtro dio 0
                            Label lblVacio = new Label
                            {
                                Text = "No tienes favoritos guardados.",
                                Font = new Font("Arial", 14),
                                ForeColor = Color.Gray,
                                Size = new Size(panel3.Width - 40, 100),
                                Location = new Point(20, lblFav.Bottom + 50),
                                TextAlign = ContentAlignment.MiddleCenter
                            };
                            panel3.Controls.Add(lblVacio);
                        }
                    }
                    else
                    {
                        MessageBox.Show("Error del servidor: " + response.StatusCode);
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error crítico: " + ex.Message);
            }
            finally
            {
                panel3.ResumeLayout();
            }
        }
        private void materialTabControl1_SelectedIndexChanged(object sender, EventArgs e)
        {
            // Cambia "pageFavoritos" por el Name real que sale en tus propiedades
            if (materialTabControl1.SelectedTab.Name == "pageFavoritos")
            {
                // Usamos _ = para llamar a un método async desde uno normal sin bloquear
                _ = CargarFavoritosEnPanel3();
            }
        }

        private async Task CargarFavoritosEnPanel3()
        {
            // 1. Limpieza y preparación
            panel3.Invoke((Action)(() => {
                panel3.SuspendLayout();
                panel3.Controls.Clear();
                panel3.AutoScroll = true;

                // 2. Título inmediato
                Label lblFav = new Label
                {
                    Name = "lblTituloFav",
                    Text = "Tus Recetas Favoritas",
                    Font = new Font("Arial", 40, FontStyle.Bold),
                    ForeColor = Color.LimeGreen,
                    Size = new Size(panel3.Width - 40, 80),
                    Location = new Point(20, 20),
                    TextAlign = ContentAlignment.MiddleCenter
                };
                panel3.Controls.Add(lblFav);
                panel3.ResumeLayout();
            }));

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Authorization =
                        new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", Sesion.Token);

                    string url = "http://localhost:8080/mi-primera-api/rest/publicaciones?page=1&limit=100";
                    var response = await client.GetStringAsync(url);
                    var todas = JsonConvert.DeserializeObject<List<PublicacionModel>>(response);

                    // Filtrado igual que en tu JS
                    var misFavoritos = todas.Where(pub => pub.likedByCurrentUser == true).ToList();

                    panel3.Invoke((Action)(() => {
                        panel3.SuspendLayout();
                        if (misFavoritos.Count > 0)
                        {
                            // Buscamos el título para posicionar debajo
                            var titulo = panel3.Controls["lblTituloFav"];
                            int puntoY = titulo.Bottom + 40;

                            foreach (var pub in misFavoritos)
                            {
                                Publicacion card = new Publicacion();
                                card.SetDatos(pub, Sesion.Token);
                                card.Location = new Point((panel3.Width - card.Width) / 2, puntoY);
                                panel3.Controls.Add(card);
                                puntoY += card.Height + 20;
                            }
                        }
                        else
                        {
                            Label lblVacio = new Label
                            {
                                Text = "No tienes favoritos guardados.",
                                Font = new Font("Arial", 14),
                                ForeColor = Color.Gray,
                                Size = new Size(panel3.Width - 40, 100),
                                Location = new Point(20, 200),
                                TextAlign = ContentAlignment.MiddleCenter
                            };
                            panel3.Controls.Add(lblVacio);
                        }
                        panel3.ResumeLayout();
                    }));
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error de conexión: " + ex.Message);
            }
        }

    }
    }
