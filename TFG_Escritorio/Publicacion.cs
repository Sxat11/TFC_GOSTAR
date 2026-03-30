using System;
using System.Drawing;
using System.Net.Http;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;

namespace GostarApp
{
    public partial class Publicacion : UserControl
    {
        private PublicacionModel _pub;
        private string _token;
        private readonly string baseApi = "http://localhost:8080/mi-primera-api/";

        public Publicacion()
        {
            InitializeComponent();
            // Asignar el evento click al corazón desde el código por seguridad
            btnLike.Click += btnLike_Click;
            btnLike.Cursor = Cursors.Hand;
        }

        public void SetDatos(PublicacionModel pub, string token)
        {
            _pub = pub;
            _token = token;

            lblTitulo.Text = pub.titulo;
            lblLikesCount.Text = pub.likes.ToString();

            // 1. Configurar estado visual inicial del like
            ActualizarIconoLike(pub.likedByCurrentUser);

            // 2. Cargar la imagen
            CargarImagen(pub.imagenPrincipal);
        }

        private void ActualizarIconoLike(bool isLiked)
        {
            // Nota: Asegúrate de tener estas imágenes en tus Recursos (Resources)
            // Si no las tienes, puedes usar colores de fondo temporalmente:
            if (isLiked)
            {
                //btnLike.Image = Properties.Resources.corazon_lleno;
                lblLikesCount.ForeColor = Color.Red;
            }
            else
            {
                //btnLike.Image = Properties.Resources.corazon_vacio;
                lblLikesCount.ForeColor = Color.Black;
            }
        }

        private async void btnLike_Click(object sender, EventArgs e)
        {
            if (string.IsNullOrEmpty(_token) || _pub == null) return;

            // --- LÓGICA OPTIMISTA (Cambio visual inmediato) ---
            bool nuevoEstado = !_pub.likedByCurrentUser;
            int likesAnteriores = _pub.likes;

            _pub.likedByCurrentUser = nuevoEstado;
            _pub.likes += nuevoEstado ? 1 : -1;

            lblLikesCount.Text = _pub.likes.ToString();
            ActualizarIconoLike(nuevoEstado);

            // --- LLAMADA AL SERVIDOR ---
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    client.DefaultRequestHeaders.Authorization =
                        new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", _token);

                    // La URL según tu PublicacionService.java
                    string url = $"{baseApi}rest/publicaciones/{_pub.id}/like";

                    HttpResponseMessage response;
                    if (nuevoEstado)
                        response = await client.PostAsync(url, null);
                    else
                        response = await client.DeleteAsync(url);

                    if (!response.IsSuccessStatusCode)
                    {
                        throw new Exception("El servidor rechazó la acción");
                    }
                }
            }
            catch (Exception ex)
            {
                // REVERTIR si algo falla
                _pub.likedByCurrentUser = !nuevoEstado;
                _pub.likes = likesAnteriores;

                lblLikesCount.Text = _pub.likes.ToString();
                ActualizarIconoLike(_pub.likedByCurrentUser);

                MessageBox.Show("Error al conectar con el servidor: " + ex.Message,
                                "Gostar App", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private async void CargarImagen(string urlRelativa)
        {
            portada.Image = null; // Limpiar
            portada.BackColor = Color.LightGray; // Color de carga

            if (string.IsNullOrEmpty(urlRelativa)) return;

            string urlFinal = urlRelativa.StartsWith("http") ? urlRelativa : baseApi + urlRelativa;

            try
            {
                using (HttpClient client = new HttpClient())
                {
                    byte[] imageBytes = await client.GetByteArrayAsync(urlFinal);
                    using (var ms = new MemoryStream(imageBytes))
                    {
                        portada.Image = Image.FromStream(ms);
                        portada.SizeMode = PictureBoxSizeMode.Zoom;
                        portada.BackColor = Color.Transparent;
                    }
                }
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine("Error cargando imagen: " + ex.Message);
                portada.BackColor = Color.Salmon; // Indicador de error visual
            }
        }
    }
}