namespace GostarApp
{
    partial class Form2
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form2));
            this.materialTabControl1 = new MaterialSkin.Controls.MaterialTabControl();
            this.tabPage3 = new System.Windows.Forms.TabPage();
            this.panel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.tabPage2 = new System.Windows.Forms.TabPage();
            this.label1 = new System.Windows.Forms.Label();
            this.materialButton1 = new MaterialSkin.Controls.MaterialButton();
            this.materialTextBox1 = new MaterialSkin.Controls.MaterialTextBox();
            this.tabPage5 = new System.Windows.Forms.TabPage();
            this.label5 = new System.Windows.Forms.Label();
            this.materialTextBox23 = new MaterialSkin.Controls.MaterialTextBox2();
            this.label6 = new System.Windows.Forms.Label();
            this.materialTextBox24 = new MaterialSkin.Controls.MaterialTextBox2();
            this.label4 = new System.Windows.Forms.Label();
            this.materialTextBox22 = new MaterialSkin.Controls.MaterialTextBox2();
            this.label3 = new System.Windows.Forms.Label();
            this.materialTextBox21 = new MaterialSkin.Controls.MaterialTextBox2();
            this.label2 = new System.Windows.Forms.Label();
            this.tabPage4 = new System.Windows.Forms.TabPage();
            this.tabPage1 = new System.Windows.Forms.TabPage();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.label7 = new System.Windows.Forms.Label();
            this.imageList1 = new System.Windows.Forms.ImageList(this.components);
            this.publicacion6 = new GostarApp.Publicacion();
            this.publicacion5 = new GostarApp.Publicacion();
            this.publicacion4 = new GostarApp.Publicacion();
            this.publicacion3 = new GostarApp.Publicacion();
            this.publicacion2 = new GostarApp.Publicacion();
            this.publicacion1 = new GostarApp.Publicacion();
            this.materialTabControl1.SuspendLayout();
            this.tabPage3.SuspendLayout();
            this.tabPage2.SuspendLayout();
            this.tabPage5.SuspendLayout();
            this.tabPage1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            this.SuspendLayout();
            // 
            // materialTabControl1
            // 
            this.materialTabControl1.Controls.Add(this.tabPage3);
            this.materialTabControl1.Controls.Add(this.tabPage2);
            this.materialTabControl1.Controls.Add(this.tabPage5);
            this.materialTabControl1.Controls.Add(this.tabPage4);
            this.materialTabControl1.Controls.Add(this.tabPage1);
            this.materialTabControl1.Depth = 0;
            this.materialTabControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.materialTabControl1.ImageList = this.imageList1;
            this.materialTabControl1.Location = new System.Drawing.Point(4, 79);
            this.materialTabControl1.Margin = new System.Windows.Forms.Padding(4);
            this.materialTabControl1.MouseState = MaterialSkin.MouseState.HOVER;
            this.materialTabControl1.Multiline = true;
            this.materialTabControl1.Name = "materialTabControl1";
            this.materialTabControl1.SelectedIndex = 0;
            this.materialTabControl1.ShowToolTips = true;
            this.materialTabControl1.Size = new System.Drawing.Size(1748, 878);
            this.materialTabControl1.TabIndex = 0;
            // 
            // tabPage3
            // 
            this.tabPage3.Controls.Add(this.panel1);
            this.tabPage3.ImageKey = "pimi.png";
            this.tabPage3.Location = new System.Drawing.Point(4, 39);
            this.tabPage3.Margin = new System.Windows.Forms.Padding(4);
            this.tabPage3.Name = "tabPage3";
            this.tabPage3.Size = new System.Drawing.Size(1740, 835);
            this.tabPage3.TabIndex = 2;
            this.tabPage3.Text = "Recetas";
            this.tabPage3.UseVisualStyleBackColor = true;
            this.tabPage3.Click += new System.EventHandler(this.tabPage3_Click);
            // 
            // panel1
            // 
            this.panel1.AutoScroll = true;
            this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(1740, 835);
            this.panel1.TabIndex = 0;
            this.panel1.Paint += new System.Windows.Forms.PaintEventHandler(this.panel1_Paint);
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.label1);
            this.tabPage2.Controls.Add(this.materialButton1);
            this.tabPage2.Controls.Add(this.materialTextBox1);
            this.tabPage2.ImageKey = "lupa1.png";
            this.tabPage2.Location = new System.Drawing.Point(4, 39);
            this.tabPage2.Margin = new System.Windows.Forms.Padding(4);
            this.tabPage2.Name = "tabPage2";
            this.tabPage2.Padding = new System.Windows.Forms.Padding(4);
            this.tabPage2.Size = new System.Drawing.Size(1740, 835);
            this.tabPage2.TabIndex = 1;
            this.tabPage2.Text = "Buscador";
            this.tabPage2.UseVisualStyleBackColor = true;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 48F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.ForeColor = System.Drawing.Color.Lime;
            this.label1.Location = new System.Drawing.Point(500, 171);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(720, 91);
            this.label1.TabIndex = 2;
            this.label1.Text = "GOSTAR Searcher";
            // 
            // materialButton1
            // 
            this.materialButton1.AutoSize = false;
            this.materialButton1.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.materialButton1.Density = MaterialSkin.Controls.MaterialButton.MaterialButtonDensity.Default;
            this.materialButton1.Depth = 0;
            this.materialButton1.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.materialButton1.HighEmphasis = true;
            this.materialButton1.Icon = ((System.Drawing.Image)(resources.GetObject("materialButton1.Icon")));
            this.materialButton1.Location = new System.Drawing.Point(1179, 458);
            this.materialButton1.Margin = new System.Windows.Forms.Padding(5, 7, 5, 7);
            this.materialButton1.MouseState = MaterialSkin.MouseState.HOVER;
            this.materialButton1.Name = "materialButton1";
            this.materialButton1.NoAccentTextColor = System.Drawing.Color.Empty;
            this.materialButton1.Size = new System.Drawing.Size(145, 62);
            this.materialButton1.TabIndex = 1;
            this.materialButton1.Text = "Buscar";
            this.materialButton1.Type = MaterialSkin.Controls.MaterialButton.MaterialButtonType.Contained;
            this.materialButton1.UseAccentColor = false;
            this.materialButton1.UseVisualStyleBackColor = true;
            this.materialButton1.Click += new System.EventHandler(this.materialButton1_Click);
            // 
            // materialTextBox1
            // 
            this.materialTextBox1.AnimateReadOnly = false;
            this.materialTextBox1.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.materialTextBox1.Depth = 0;
            this.materialTextBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.materialTextBox1.LeadingIcon = null;
            this.materialTextBox1.Location = new System.Drawing.Point(435, 458);
            this.materialTextBox1.Margin = new System.Windows.Forms.Padding(4);
            this.materialTextBox1.MaxLength = 50;
            this.materialTextBox1.MouseState = MaterialSkin.MouseState.OUT;
            this.materialTextBox1.Multiline = false;
            this.materialTextBox1.Name = "materialTextBox1";
            this.materialTextBox1.Size = new System.Drawing.Size(735, 50);
            this.materialTextBox1.TabIndex = 0;
            this.materialTextBox1.Text = "";
            this.materialTextBox1.TrailingIcon = null;
            // 
            // tabPage5
            // 
            this.tabPage5.Controls.Add(this.label5);
            this.tabPage5.Controls.Add(this.materialTextBox23);
            this.tabPage5.Controls.Add(this.label6);
            this.tabPage5.Controls.Add(this.materialTextBox24);
            this.tabPage5.Controls.Add(this.label4);
            this.tabPage5.Controls.Add(this.materialTextBox22);
            this.tabPage5.Controls.Add(this.label3);
            this.tabPage5.Controls.Add(this.materialTextBox21);
            this.tabPage5.Controls.Add(this.label2);
            this.tabPage5.ImageKey = "anadir.png";
            this.tabPage5.Location = new System.Drawing.Point(4, 39);
            this.tabPage5.Margin = new System.Windows.Forms.Padding(4);
            this.tabPage5.Name = "tabPage5";
            this.tabPage5.Size = new System.Drawing.Size(1740, 835);
            this.tabPage5.TabIndex = 4;
            this.tabPage5.Text = "Nueva";
            this.tabPage5.UseVisualStyleBackColor = true;
            this.tabPage5.Click += new System.EventHandler(this.tabPage5_Click);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(490, 452);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(146, 32);
            this.label5.TabIndex = 8;
            this.label5.Text = "Categoria:";
            // 
            // materialTextBox23
            // 
            this.materialTextBox23.AnimateReadOnly = false;
            this.materialTextBox23.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.materialTextBox23.CharacterCasing = System.Windows.Forms.CharacterCasing.Normal;
            this.materialTextBox23.Depth = 0;
            this.materialTextBox23.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.materialTextBox23.HideSelection = true;
            this.materialTextBox23.LeadingIcon = null;
            this.materialTextBox23.Location = new System.Drawing.Point(733, 436);
            this.materialTextBox23.MaxLength = 32767;
            this.materialTextBox23.MouseState = MaterialSkin.MouseState.OUT;
            this.materialTextBox23.Name = "materialTextBox23";
            this.materialTextBox23.PasswordChar = '\0';
            this.materialTextBox23.PrefixSuffixText = null;
            this.materialTextBox23.ReadOnly = false;
            this.materialTextBox23.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.materialTextBox23.SelectedText = "";
            this.materialTextBox23.SelectionLength = 0;
            this.materialTextBox23.SelectionStart = 0;
            this.materialTextBox23.ShortcutsEnabled = true;
            this.materialTextBox23.Size = new System.Drawing.Size(250, 48);
            this.materialTextBox23.TabIndex = 7;
            this.materialTextBox23.TabStop = false;
            this.materialTextBox23.TextAlign = System.Windows.Forms.HorizontalAlignment.Left;
            this.materialTextBox23.TrailingIcon = null;
            this.materialTextBox23.UseSystemPasswordChar = false;
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(490, 375);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(179, 32);
            this.label6.TabIndex = 6;
            this.label6.Text = "Ingredientes:";
            // 
            // materialTextBox24
            // 
            this.materialTextBox24.AnimateReadOnly = false;
            this.materialTextBox24.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.materialTextBox24.CharacterCasing = System.Windows.Forms.CharacterCasing.Normal;
            this.materialTextBox24.Depth = 0;
            this.materialTextBox24.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.materialTextBox24.HideSelection = true;
            this.materialTextBox24.LeadingIcon = null;
            this.materialTextBox24.Location = new System.Drawing.Point(733, 359);
            this.materialTextBox24.MaxLength = 32767;
            this.materialTextBox24.MouseState = MaterialSkin.MouseState.OUT;
            this.materialTextBox24.Name = "materialTextBox24";
            this.materialTextBox24.PasswordChar = '\0';
            this.materialTextBox24.PrefixSuffixText = null;
            this.materialTextBox24.ReadOnly = false;
            this.materialTextBox24.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.materialTextBox24.SelectedText = "";
            this.materialTextBox24.SelectionLength = 0;
            this.materialTextBox24.SelectionStart = 0;
            this.materialTextBox24.ShortcutsEnabled = true;
            this.materialTextBox24.Size = new System.Drawing.Size(250, 48);
            this.materialTextBox24.TabIndex = 5;
            this.materialTextBox24.TabStop = false;
            this.materialTextBox24.TextAlign = System.Windows.Forms.HorizontalAlignment.Left;
            this.materialTextBox24.TrailingIcon = null;
            this.materialTextBox24.UseSystemPasswordChar = false;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(490, 287);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(171, 32);
            this.label4.TabIndex = 4;
            this.label4.Text = "Descripcion:";
            // 
            // materialTextBox22
            // 
            this.materialTextBox22.AnimateReadOnly = false;
            this.materialTextBox22.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.materialTextBox22.CharacterCasing = System.Windows.Forms.CharacterCasing.Normal;
            this.materialTextBox22.Depth = 0;
            this.materialTextBox22.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.materialTextBox22.HideSelection = true;
            this.materialTextBox22.LeadingIcon = null;
            this.materialTextBox22.Location = new System.Drawing.Point(733, 287);
            this.materialTextBox22.MaxLength = 32767;
            this.materialTextBox22.MouseState = MaterialSkin.MouseState.OUT;
            this.materialTextBox22.Name = "materialTextBox22";
            this.materialTextBox22.PasswordChar = '\0';
            this.materialTextBox22.PrefixSuffixText = null;
            this.materialTextBox22.ReadOnly = false;
            this.materialTextBox22.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.materialTextBox22.SelectedText = "";
            this.materialTextBox22.SelectionLength = 0;
            this.materialTextBox22.SelectionStart = 0;
            this.materialTextBox22.ShortcutsEnabled = true;
            this.materialTextBox22.Size = new System.Drawing.Size(250, 48);
            this.materialTextBox22.TabIndex = 3;
            this.materialTextBox22.TabStop = false;
            this.materialTextBox22.TextAlign = System.Windows.Forms.HorizontalAlignment.Left;
            this.materialTextBox22.TrailingIcon = null;
            this.materialTextBox22.UseSystemPasswordChar = false;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(490, 226);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(122, 32);
            this.label3.TabIndex = 2;
            this.label3.Text = "Nombre:";
            // 
            // materialTextBox21
            // 
            this.materialTextBox21.AnimateReadOnly = false;
            this.materialTextBox21.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.materialTextBox21.CharacterCasing = System.Windows.Forms.CharacterCasing.Normal;
            this.materialTextBox21.Depth = 0;
            this.materialTextBox21.Font = new System.Drawing.Font("Microsoft Sans Serif", 16F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Pixel);
            this.materialTextBox21.HideSelection = true;
            this.materialTextBox21.LeadingIcon = null;
            this.materialTextBox21.Location = new System.Drawing.Point(733, 210);
            this.materialTextBox21.MaxLength = 32767;
            this.materialTextBox21.MouseState = MaterialSkin.MouseState.OUT;
            this.materialTextBox21.Name = "materialTextBox21";
            this.materialTextBox21.PasswordChar = '\0';
            this.materialTextBox21.PrefixSuffixText = null;
            this.materialTextBox21.ReadOnly = false;
            this.materialTextBox21.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.materialTextBox21.SelectedText = "";
            this.materialTextBox21.SelectionLength = 0;
            this.materialTextBox21.SelectionStart = 0;
            this.materialTextBox21.ShortcutsEnabled = true;
            this.materialTextBox21.Size = new System.Drawing.Size(250, 48);
            this.materialTextBox21.TabIndex = 1;
            this.materialTextBox21.TabStop = false;
            this.materialTextBox21.TextAlign = System.Windows.Forms.HorizontalAlignment.Left;
            this.materialTextBox21.TrailingIcon = null;
            this.materialTextBox21.UseSystemPasswordChar = false;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 48F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(389, 68);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(659, 91);
            this.label2.TabIndex = 0;
            this.label2.Text = "NUEVA RECETA";
            // 
            // tabPage4
            // 
            this.tabPage4.ImageKey = "favoritos.png";
            this.tabPage4.Location = new System.Drawing.Point(4, 39);
            this.tabPage4.Margin = new System.Windows.Forms.Padding(4);
            this.tabPage4.Name = "tabPage4";
            this.tabPage4.Size = new System.Drawing.Size(1740, 835);
            this.tabPage4.TabIndex = 3;
            this.tabPage4.Text = "Te Gusta";
            this.tabPage4.UseVisualStyleBackColor = true;
            this.tabPage4.Click += new System.EventHandler(this.tabPage4_Click);
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.publicacion6);
            this.tabPage1.Controls.Add(this.publicacion5);
            this.tabPage1.Controls.Add(this.publicacion4);
            this.tabPage1.Controls.Add(this.publicacion3);
            this.tabPage1.Controls.Add(this.publicacion2);
            this.tabPage1.Controls.Add(this.publicacion1);
            this.tabPage1.Controls.Add(this.pictureBox1);
            this.tabPage1.Controls.Add(this.label7);
            this.tabPage1.ImageKey = "icono.png";
            this.tabPage1.Location = new System.Drawing.Point(4, 39);
            this.tabPage1.Margin = new System.Windows.Forms.Padding(4);
            this.tabPage1.Name = "tabPage1";
            this.tabPage1.Padding = new System.Windows.Forms.Padding(4);
            this.tabPage1.Size = new System.Drawing.Size(1740, 835);
            this.tabPage1.TabIndex = 0;
            this.tabPage1.Text = "Mi Perfil";
            this.tabPage1.UseVisualStyleBackColor = true;
            this.tabPage1.Click += new System.EventHandler(this.tabPage1_Click);
            // 
            // pictureBox1
            // 
            this.pictureBox1.Location = new System.Drawing.Point(22, 17);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(201, 189);
            this.pictureBox1.TabIndex = 1;
            this.pictureBox1.TabStop = false;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 36F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(246, 75);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(248, 69);
            this.label7.TabIndex = 0;
            this.label7.Text = "Mi Perfil";
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "icono.png");
            this.imageList1.Images.SetKeyName(1, "pimi.png");
            this.imageList1.Images.SetKeyName(2, "lupa1.png");
            this.imageList1.Images.SetKeyName(3, "anadir.png");
            this.imageList1.Images.SetKeyName(4, "favoritos.png");
            // 
            // publicacion6
            // 
            this.publicacion6.BackColor = System.Drawing.SystemColors.ControlDark;
            this.publicacion6.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.publicacion6.Location = new System.Drawing.Point(870, 544);
            this.publicacion6.Margin = new System.Windows.Forms.Padding(4);
            this.publicacion6.Name = "publicacion6";
            this.publicacion6.Size = new System.Drawing.Size(400, 250);
            this.publicacion6.TabIndex = 7;
            // 
            // publicacion5
            // 
            this.publicacion5.BackColor = System.Drawing.SystemColors.ControlDark;
            this.publicacion5.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.publicacion5.Location = new System.Drawing.Point(444, 544);
            this.publicacion5.Margin = new System.Windows.Forms.Padding(4);
            this.publicacion5.Name = "publicacion5";
            this.publicacion5.Size = new System.Drawing.Size(400, 250);
            this.publicacion5.TabIndex = 6;
            // 
            // publicacion4
            // 
            this.publicacion4.BackColor = System.Drawing.SystemColors.ControlDark;
            this.publicacion4.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.publicacion4.Location = new System.Drawing.Point(22, 544);
            this.publicacion4.Margin = new System.Windows.Forms.Padding(4);
            this.publicacion4.Name = "publicacion4";
            this.publicacion4.Size = new System.Drawing.Size(400, 250);
            this.publicacion4.TabIndex = 5;
            // 
            // publicacion3
            // 
            this.publicacion3.BackColor = System.Drawing.SystemColors.ControlDark;
            this.publicacion3.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.publicacion3.Location = new System.Drawing.Point(870, 268);
            this.publicacion3.Margin = new System.Windows.Forms.Padding(4);
            this.publicacion3.Name = "publicacion3";
            this.publicacion3.Size = new System.Drawing.Size(400, 250);
            this.publicacion3.TabIndex = 4;
            // 
            // publicacion2
            // 
            this.publicacion2.BackColor = System.Drawing.SystemColors.ControlDark;
            this.publicacion2.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.publicacion2.Location = new System.Drawing.Point(444, 268);
            this.publicacion2.Margin = new System.Windows.Forms.Padding(4);
            this.publicacion2.Name = "publicacion2";
            this.publicacion2.Size = new System.Drawing.Size(400, 250);
            this.publicacion2.TabIndex = 3;
            // 
            // publicacion1
            // 
            this.publicacion1.BackColor = System.Drawing.SystemColors.ControlDark;
            this.publicacion1.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.publicacion1.Location = new System.Drawing.Point(22, 268);
            this.publicacion1.Margin = new System.Windows.Forms.Padding(4);
            this.publicacion1.Name = "publicacion1";
            this.publicacion1.Size = new System.Drawing.Size(400, 250);
            this.publicacion1.TabIndex = 2;
            // 
            // Form2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1756, 961);
            this.Controls.Add(this.materialTabControl1);
            this.DrawerShowIconsWhenHidden = true;
            this.DrawerTabControl = this.materialTabControl1;
            this.DrawerUseColors = true;
            this.Margin = new System.Windows.Forms.Padding(4);
            this.Name = "Form2";
            this.Padding = new System.Windows.Forms.Padding(4, 79, 4, 4);
            this.Text = "Gostar";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.materialTabControl1.ResumeLayout(false);
            this.tabPage3.ResumeLayout(false);
            this.tabPage2.ResumeLayout(false);
            this.tabPage2.PerformLayout();
            this.tabPage5.ResumeLayout(false);
            this.tabPage5.PerformLayout();
            this.tabPage1.ResumeLayout(false);
            this.tabPage1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private MaterialSkin.Controls.MaterialTabControl materialTabControl1;
        private System.Windows.Forms.TabPage tabPage1;
        private System.Windows.Forms.TabPage tabPage2;
        private System.Windows.Forms.ImageList imageList1;
        private System.Windows.Forms.TabPage tabPage5;
        private System.Windows.Forms.TabPage tabPage4;
        private MaterialSkin.Controls.MaterialButton materialButton1;
        private MaterialSkin.Controls.MaterialTextBox materialTextBox1;
        private System.Windows.Forms.TabPage tabPage3;
        private System.Windows.Forms.FlowLayoutPanel panel1;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label4;
        private MaterialSkin.Controls.MaterialTextBox2 materialTextBox22;
        private System.Windows.Forms.Label label3;
        private MaterialSkin.Controls.MaterialTextBox2 materialTextBox21;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label5;
        private MaterialSkin.Controls.MaterialTextBox2 materialTextBox23;
        private System.Windows.Forms.Label label6;
        private MaterialSkin.Controls.MaterialTextBox2 materialTextBox24;
        private System.Windows.Forms.Label label7;
        private Publicacion publicacion6;
        private Publicacion publicacion5;
        private Publicacion publicacion4;
        private Publicacion publicacion3;
        private Publicacion publicacion2;
        private Publicacion publicacion1;
        private System.Windows.Forms.PictureBox pictureBox1;
    }
}