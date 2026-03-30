using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GostarApp
{
    public class LoginRequest
    {
        public string Usuario { get; set; }
        public string Password { get; set; }
    }
    public class LoginResponse
    {
        public string token { get; set; }
        public object usuario { get; set; } // Puedes crear una clase UsuarioDTO más detallada aquí
        public string mensaje { get; set; }
    }
}
