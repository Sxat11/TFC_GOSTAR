using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GostarApp
{
    public class PublicacionModel
    {

        public int id { get; set; }
        public string titulo { get; set; }
        public string contenido { get; set; }
        public string autor { get; set; }
        // Asumiremos que esta es la URL de la foto de la publicación
        public string imagenUrl { get; set; }
        public string avatarUrl { get; set; } // Opcional: foto del autor

    }
}
