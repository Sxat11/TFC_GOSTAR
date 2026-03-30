namespace GostarApp
{
    partial class Publicacion
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

        #region Código generado por el Diseñador de componentes

        /// <summary> 
        /// Método necesario para admitir el Diseñador. No se puede modificar
        /// el contenido de este método con el editor de código.
        /// </summary>
        private void InitializeComponent()
        {
            this.portada = new System.Windows.Forms.PictureBox();
            this.lblTitulo = new System.Windows.Forms.Label();
            this.lblLikesCount = new System.Windows.Forms.Label();
            this.btnLike = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.portada)).BeginInit();
            this.SuspendLayout();
            // 
            // portada
            // 
            this.portada.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Center;
            this.portada.Location = new System.Drawing.Point(0, 0);
            this.portada.Margin = new System.Windows.Forms.Padding(4);
            this.portada.Name = "portada";
            this.portada.Size = new System.Drawing.Size(400, 194);
            this.portada.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.portada.TabIndex = 2;
            this.portada.TabStop = false;
            // 
            // lblTitulo
            // 
            this.lblTitulo.AutoSize = true;
            this.lblTitulo.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitulo.Location = new System.Drawing.Point(4, 199);
            this.lblTitulo.Margin = new System.Windows.Forms.Padding(4, 0, 4, 0);
            this.lblTitulo.Name = "lblTitulo";
            this.lblTitulo.Size = new System.Drawing.Size(266, 39);
            this.lblTitulo.TabIndex = 3;
            this.lblTitulo.Text = "PAELLA DIEGO";
            // 
            // lblLikesCount
            // 
            this.lblLikesCount.AutoSize = true;
            this.lblLikesCount.Font = new System.Drawing.Font("Microsoft Sans Serif", 19.8F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblLikesCount.Location = new System.Drawing.Point(360, 200);
            this.lblLikesCount.Name = "lblLikesCount";
            this.lblLikesCount.Size = new System.Drawing.Size(37, 39);
            this.lblLikesCount.TabIndex = 4;
            this.lblLikesCount.Text = "0";
            // 
            // btnLike
            // 
            this.btnLike.Location = new System.Drawing.Point(277, 202);
            this.btnLike.Name = "btnLike";
            this.btnLike.Size = new System.Drawing.Size(45, 37);
            this.btnLike.TabIndex = 5;
            this.btnLike.UseVisualStyleBackColor = true;
            // 
            // Publicacion
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ControlDark;
            this.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.Controls.Add(this.btnLike);
            this.Controls.Add(this.lblLikesCount);
            this.Controls.Add(this.lblTitulo);
            this.Controls.Add(this.portada);
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "Publicacion";
            this.Size = new System.Drawing.Size(400, 250);
            ((System.ComponentModel.ISupportInitialize)(this.portada)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.PictureBox portada;
        private System.Windows.Forms.Label lblTitulo;
        private System.Windows.Forms.Label lblLikesCount;
        private System.Windows.Forms.Button btnLike;
    }
}
