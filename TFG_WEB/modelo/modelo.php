<?php
class Receta {
    private $db;
    
    public function __construct() {
        $this->db = new PDO('mysql:host=localhost;dbname=red_social', 'root', '');
    }
    
    public function getAll() {
        $stmt = $this->db->query("SELECT * FROM publicaciones");
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}
?>