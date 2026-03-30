<?php
require_once 'modelo/Receta.php';

class RecetaController {
    public function index() {
        $recetaModelo = new Receta();
        $recetas = $recetaModelo->getAll();
        include 'vista/principal.php';
    }
}
?>