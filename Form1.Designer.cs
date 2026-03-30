namespace GostarApp
{
    partial class FormInicio
    {
        /// <summary>
        /// Variable del diseñador necesaria.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Limpiar los recursos que se estén usando.
        /// </summary>
        /// <param name="disposing">true si los recursos administrados se deben desechar; false en caso contrario.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Código generado por el Diseñador de Windows Forms

        /// <summary>
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            this.labelTitulo = new System.Windows.Forms.Label();
            this.labelSubtitulo = new System.Windows.Forms.Label();
            this.CrearCuentaBtn = new MaterialSkin.Controls.MaterialButton();
            this.IniciarSesionBtn = new MaterialSkin.Controls.MaterialButton();
            this.SuspendLayout();
            // 
            // labelTitulo
            // 
            this.labelTitulo.AutoSize = true;
            this.labelTitulo.BackColor = System.Drawing.Color.Transparent;
            this.labelTitulo.Font = new System.Drawing.Font("Microsoft Sans Serif", 27.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.labelTitulo.Location = new System.Drawing.Point(172, 234);
            this.labelTitulo.Margin = new System.Windows.Forms.Padding(0);
            this.labelTitulo.Name = "labelTitulo";
            this.labelTitulo.Size = new System.Drawing.Size(740, 54);
            this.labelTitulo.TabIndex = 0;
            this.labelTitulo.Text = "Te damos la bienvenida a Gostar";
            this.labelTitulo.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            this.labelTitulo.Click += new System.EventHandler(this.label1_Click);
            this.labelTitulo.Resize += new System.EventHandler(this.Form1_Resize);
            // 
            // labelSubtitulo
            // 
            this.labelSubtitulo.AutoSize = true;
            this.labelSubtitulo.BackColor = System.Drawing.Color.Transparent;
            this.labelSubtitulo.Location = new System.Drawing.Point(315, 311);
            this.labelSubtitulo.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.labelSubtitulo.Name = "labelSubtitulo";
            this.labelSubtitulo.Size = new System.Drawing.Size(201, 16);
            this.labelSubtitulo.TabIndex = 1;
            this.labelSubtitulo.Text = "Siempre con ganas de aprender";
            this.labelSubtitulo.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // CrearCuentaBtn
            // 
            this.CrearCuentaBtn.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.CrearCuentaBtn.Density = MaterialSkin.Controls.MaterialButton.MaterialButtonDensity.Default;
            this.CrearCuentaBtn.Depth = 0;
            this.CrearCuentaBtn.HighEmphasis = true;
            this.CrearCuentaBtn.Icon = null;
            this.CrearCuentaBtn.Location = new System.Drawing.Point(512, 400);
            this.CrearCuentaBtn.Margin = new System.Windows.Forms.Padding(4, 6, 4, 6);
            this.CrearCuentaBtn.MouseState = MaterialSkin.MouseState.HOVER;
            this.CrearCuentaBtn.Name = "CrearCuentaBtn";
            this.CrearCuentaBtn.NoAccentTextColor = System.Drawing.Color.Empty;
            this.CrearCuentaBtn.Size = new System.Drawing.Size(128, 36);
            this.CrearCuentaBtn.TabIndex = 4;
            this.CrearCuentaBtn.Text = "CREAR CUENTA";
            this.CrearCuentaBtn.Type = MaterialSkin.Controls.MaterialButton.MaterialButtonType.Contained;
            this.CrearCuentaBtn.UseAccentColor = false;
            this.CrearCuentaBtn.UseVisualStyleBackColor = true;
            this.CrearCuentaBtn.Click += new System.EventHandler(this.CrearCuentaBtn_Click);
            // 
            // IniciarSesionBtn
            // 
            this.IniciarSesionBtn.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.IniciarSesionBtn.BackColor = System.Drawing.Color.Lime;
            this.IniciarSesionBtn.Density = MaterialSkin.Controls.MaterialButton.MaterialButtonDensity.Default;
            this.IniciarSesionBtn.Depth = 0;
            this.IniciarSesionBtn.ForeColor = System.Drawing.SystemColors.Control;
            this.IniciarSesionBtn.HighEmphasis = true;
            this.IniciarSesionBtn.Icon = null;
            this.IniciarSesionBtn.Location = new System.Drawing.Point(375, 400);
            this.IniciarSesionBtn.Margin = new System.Windows.Forms.Padding(4, 6, 4, 6);
            this.IniciarSesionBtn.MouseState = MaterialSkin.MouseState.HOVER;
            this.IniciarSesionBtn.Name = "IniciarSesionBtn";
            this.IniciarSesionBtn.NoAccentTextColor = System.Drawing.Color.Empty;
            this.IniciarSesionBtn.Size = new System.Drawing.Size(128, 36);
            this.IniciarSesionBtn.TabIndex = 3;
            this.IniciarSesionBtn.Text = "Iniciar Sesión";
            this.IniciarSesionBtn.Type = MaterialSkin.Controls.MaterialButton.MaterialButtonType.Contained;
            this.IniciarSesionBtn.UseAccentColor = false;
            this.IniciarSesionBtn.UseVisualStyleBackColor = false;
            this.IniciarSesionBtn.Click += new System.EventHandler(this.IniciarSesionBtn_Click);
            // 
            // FormInicio
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.Control;
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.ClientSize = new System.Drawing.Size(1067, 738);
            this.Controls.Add(this.CrearCuentaBtn);
            this.Controls.Add(this.IniciarSesionBtn);
            this.Controls.Add(this.labelSubtitulo);
            this.Controls.Add(this.labelTitulo);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.MaximizeBox = false;
            this.MinimumSize = new System.Drawing.Size(1067, 738);
            this.Name = "FormInicio";
            this.Padding = new System.Windows.Forms.Padding(4, 79, 4, 4);
            this.Sizable = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Gostar App";
            this.Load += new System.EventHandler(this.FormInicio_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label labelTitulo;
        private System.Windows.Forms.Label labelSubtitulo;
        private MaterialSkin.Controls.MaterialButton CrearCuentaBtn;
        private MaterialSkin.Controls.MaterialButton IniciarSesionBtn;
    }
}

