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
    public partial class Publicacion : UserControl
    {
        public Publicacion()
        {
            InitializeComponent();
        }

        private void pictureBox1_Click(object sender, EventArgs e)
        {

        }
        public async void SetDatos(PublicacionModel pub)
        {
            lblTitulo.Text = pub.titulo;
            portada.Image = null; // Limpiar imagen previa

            // --- ESTO ES LO QUE ESTABA ROJO ---
            portada.BackColor = Color.Red;

            if (!string.IsNullOrEmpty(pub.imagenUrl))
            {
                // 1. CONSTRUIR URL COMPLETA
                string urlFinal = pub.imagenUrl;

                // AJUSTA ESTO a la URL de tu servidor Java
                // Por ejemplo, si tus fotos están en 'webapp/uploads/'
                string baseApi = "http://localhost:8080/mi-primera-api/";

                // Si la URL no empieza por http, le pegamos la base
                if (!urlFinal.StartsWith("http"))
                {
                    urlFinal = baseApi + urlFinal;
                }

                try
                {
                    // 2. DESCARGAR IMAGEN MANUALMENTE (Ignora caché y SSL inseguro)
                    using (HttpClient client = new HttpClient())
                    {
                        // Descargamos los bits de la imagen
                        byte[] imageBytes = await client.GetByteArrayAsync(urlFinal);

                        using (var ms = new System.IO.MemoryStream(imageBytes))
                        {
                            // Convertimos los bits en una imagen real
                            portada.Image = Image.FromStream(ms);
                            portada.SizeMode = PictureBoxSizeMode.Zoom;

                            // Si la imagen carga con éxito, quitamos el fondo rojo
                            portada.BackColor = Color.Transparent;
                        }
                    }
                }
                catch (Exception ex)
                {
                    // Si falla, el recuadro SE QUEDARÁ ROJO.
                    // Esto te dirá en la consola por qué (ej: "404 Not Found")
                    System.Diagnostics.Debug.WriteLine("FALLO de imagen. URL: " + urlFinal);
                    System.Diagnostics.Debug.WriteLine("Error: " + ex.Message);
                }
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}
