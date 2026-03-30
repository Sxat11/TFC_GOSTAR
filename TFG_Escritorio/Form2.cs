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
                            card.SetDatos(pub);
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

        private void publicacion1_Load(object sender, EventArgs e)
        {

        }
    }
}
