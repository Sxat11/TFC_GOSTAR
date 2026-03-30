using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace GostarApp
{
    using MaterialSkin;
    using MaterialSkin.Controls;

    public partial class FormInicio : MaterialSkin.Controls.MaterialForm
    {
        public FormInicio()
        {
            InitializeComponent();
            // Inicializamos MaterialSkin
            var materialSkinManager = MaterialSkinManager.Instance;

            // Tema Light (puedes cambiar a DARK si quieres)
            materialSkinManager.Theme = MaterialSkinManager.Themes.LIGHT;

            // Paleta personalizada: verde principal, rojo secundario
            materialSkinManager.ColorScheme = new ColorScheme(
                Primary.Green600,   // Color principal: verde
                Primary.Green800,   // Color oscuro (barras, hover)
                Primary.Red500,     // Color secundario: rojo
                Accent.Green400,    // Color de acento para botones destacados
                TextShade.WHITE     // Color del texto

            );
            materialSkinManager.AddFormToManage(this);
        }

        private void Form1_Resize(object sender, EventArgs e)
        {
            // 1. Centro horizontal y vertical del formulario
            int centroX = this.ClientSize.Width / 2;
            int centroY = this.ClientSize.Height / 2;

            // 2. Definimos distancias
            int separacionBotones = 20;
            int margenEntreElementos = 25;

            // 3. Posicionar Título (Centrado arriba)
            labelTitulo.Left = centroX - (labelTitulo.Width / 2);
            labelTitulo.Top = centroY - 120; // Lo subimos un poco del centro

            // 4. Posicionar Subtítulo (Justo debajo del título)
            //labelSubtitulo.Left = centroX - (labelSubtitulo.Width / 2);
            //labelSubtitulo.Top = labelTitulo.Bottom + margenEntreElementos;

            // 5. Posicionar Botones (Debajo del subtítulo)
            int filaBotonesY = labelSubtitulo.Bottom + (margenEntreElementos * 2);

            // Botón Izquierdo
            IniciarSesionBtn.Left = centroX - IniciarSesionBtn.Width - (separacionBotones / 2);
            IniciarSesionBtn.Top = filaBotonesY;

            // Botón Derecho
            CrearCuentaBtn.Left = centroX + (separacionBotones / 2);
            CrearCuentaBtn.Top = filaBotonesY;
        }

        private void IniciarSesionBtn_Click(object sender, EventArgs e)
        {
            FormIniciarSesion f = new FormIniciarSesion();
            this.Hide();
            f.Show();

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void FormInicio_Load(object sender, EventArgs e)
        {

            labelTitulo.Font = AppFonts.Title;
            labelSubtitulo.Font = AppFonts.Subtitle;
            //labelTexto.Font = AppFonts.Body;
        }

        private void CrearCuentaBtn_Click(object sender, EventArgs e)
        {
            FormCrearCuenta f = new FormCrearCuenta();
            this.Hide();
            f.ShowDialog();
        }
    }
}
