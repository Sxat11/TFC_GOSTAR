using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GostarApp
{
    public class UsuarioRegistro
    {
        public string username { get; set; }
        public string email { get; set; }
        public string passwordHash { get; set; } // Tu API lo llama así internamente antes de hashearlo
        public string nombre { get; set; }
        public string bio { get; set; }
        public string avatarUrl { get; set; }
    }
}
