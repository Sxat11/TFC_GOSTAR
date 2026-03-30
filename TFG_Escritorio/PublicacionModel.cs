public class PublicacionModel
{
    public int id { get; set; }
    public string titulo { get; set; }
    public string imagenPrincipal { get; set; } // En Java se llama imagen_principal o imagenPrincipal
    public int likes { get; set; }
    public bool likedByCurrentUser { get; set; }
    public string duracion { get; set; }
}