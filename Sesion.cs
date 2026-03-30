using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace GostarApp
{
    public static class Sesion
    {
        // Aquí guardaremos el token que nos dio el login
        public static string Token { get; set; }
        public static dynamic Usuario { get; set; } // O tu clase UsuarioDTO
    }
}
