-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-04-2026 a las 09:20:55
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `red_social`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicaciones`
--

CREATE TABLE `publicaciones` (
  `id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `titulo` varchar(200) NOT NULL,
  `duracion` varchar(50) DEFAULT NULL,
  `imagen_principal` varchar(500) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `dificultad` varchar(20) DEFAULT NULL,
  `calorias` int(11) DEFAULT NULL,
  `likes` int(11) DEFAULT 0,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `publicaciones`
--

INSERT INTO `publicaciones` (`id`, `usuario_id`, `titulo`, `duracion`, `imagen_principal`, `descripcion`, `dificultad`, `calorias`, `likes`, `fecha_creacion`) VALUES
(27, 21, 'Ensalada Cesar 2', '20 min', 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c', 'Una ensalada muy rica la cual haremos rapidamente', 'Fácil', 100, 3, '2026-02-24 00:41:26'),
(28, 21, 'Pizza clásica', '60 min', 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38', 'La deliciosa pizza', 'Media', 500, 3, '2026-02-24 00:44:39'),
(31, 25, 'Manu', '20 min', 'hola', 'Gran receta', 'Media', 200, 2, '2026-04-02 18:57:00'),
(32, 25, 'Receta 2', '12', 'ULSA', 'dsada', 'Media', 12, 1, '2026-04-02 19:30:59'),
(34, 21, 'Pasta 4', '20 mins', 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c', '', 'Fácil', 123, 1, '2026-04-08 17:50:01'),
(35, 21, 'Menu Vegano', '232', '\"https://images.unsplash.com/photo-1546069901-ba9599a7e63c\"', '', 'Media', 123, 1, '2026-04-08 18:21:06');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicacion_imagenes`
--

CREATE TABLE `publicacion_imagenes` (
  `id` int(11) NOT NULL,
  `publicacion_id` int(11) NOT NULL,
  `url` varchar(500) NOT NULL,
  `orden` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `publicacion_imagenes`
--

INSERT INTO `publicacion_imagenes` (`id`, `publicacion_id`, `url`, `orden`) VALUES
(24, 28, 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38', 1),
(25, 28, 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38', 2),
(26, 28, 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38', 3),
(28, 31, 'Holaa', 1),
(29, 31, 'das', 2),
(31, 34, '123', 1),
(32, 35, 'dsada', 1),
(33, 27, 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c', 1),
(34, 27, 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c', 2),
(35, 27, 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicacion_ingredientes`
--

CREATE TABLE `publicacion_ingredientes` (
  `id` int(11) NOT NULL,
  `publicacion_id` int(11) NOT NULL,
  `ingrediente` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `publicacion_ingredientes`
--

INSERT INTO `publicacion_ingredientes` (`id`, `publicacion_id`, `ingrediente`) VALUES
(39, 28, 'Masa'),
(40, 28, 'Queso'),
(41, 28, 'Tomate'),
(46, 31, 'Huevos'),
(47, 32, 'Muchos'),
(49, 34, 'fds'),
(50, 35, 'dsada'),
(51, 27, 'Lechuga'),
(52, 27, 'Tomate'),
(53, 27, '2 huevos'),
(54, 27, 'Cebolla');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicacion_likes`
--

CREATE TABLE `publicacion_likes` (
  `usuario_id` int(11) NOT NULL,
  `publicacion_id` int(11) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `publicacion_likes`
--

INSERT INTO `publicacion_likes` (`usuario_id`, `publicacion_id`, `fecha`) VALUES
(21, 27, '2026-04-09 19:03:00'),
(21, 28, '2026-04-09 19:02:59'),
(21, 31, '2026-04-09 19:02:58'),
(21, 34, '2026-04-09 19:02:54'),
(21, 35, '2026-04-09 19:02:55'),
(25, 27, '2026-04-02 18:55:55'),
(25, 28, '2026-04-02 18:55:52'),
(25, 31, '2026-04-02 19:16:57'),
(25, 32, '2026-04-02 22:47:13');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `publicacion_pasos`
--

CREATE TABLE `publicacion_pasos` (
  `id` int(11) NOT NULL,
  `publicacion_id` int(11) NOT NULL,
  `paso` text NOT NULL,
  `orden` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `publicacion_pasos`
--

INSERT INTO `publicacion_pasos` (`id`, `publicacion_id`, `paso`, `orden`) VALUES
(36, 28, 'Amasas la masa', 1),
(37, 28, 'Agregas la salsa', 2),
(41, 31, 'Pelar', 1),
(42, 32, 'Hola', 1),
(44, 34, 'fdsf', 1),
(45, 35, 'dsada', 1),
(46, 27, 'Limpiamos la lechuga', 1),
(47, 27, 'La metemos en un bol', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `bio` text DEFAULT NULL,
  `avatar_url` varchar(255) DEFAULT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  `ultimo_acceso` timestamp NULL DEFAULT NULL,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `username`, `email`, `password_hash`, `nombre`, `bio`, `avatar_url`, `fecha_registro`, `ultimo_acceso`, `activo`) VALUES
(21, 'admin', 'admin@gmail.com', 'vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=', 'Gostar_Admin   ', 'Admin de Gostar\n\nPaís: Venezuela, Región: Meaño\nPaís: Venezuela, Región: Meaño', NULL, '2026-02-24 00:39:19', '2026-04-09 19:02:49', 1),
(22, 'Manu', 'manusaxo@gmail.com', 'vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=', 'Manuel Hay', 'País: es, Región: Pontevedra', NULL, '2026-02-24 10:59:51', '2026-03-27 23:55:02', 1),
(24, 'manuelh', 'manusaxoalto@gmail.com', 'D/4avRoIIVNTwjPW4AlhPpXuxCU4Mqdhryj/N6xaFQw=', 'Manuel Hay', 'País: pt, Región: Oporto', NULL, '2026-03-27 23:41:22', '2026-03-27 23:41:22', 1),
(25, 'admin2', 'manu@gmail.com', 'vLFfghR5tNV3K9DKhmwArV+SbjWAcgZZzIDTnJ0JgCo=', 'Manuel Hay', 'Nuevo usuario de Gostar\nPaís: España, Región: Vigo', '', '2026-03-30 16:00:17', '2026-04-06 22:45:25', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `usuario_id` (`usuario_id`);

--
-- Indices de la tabla `publicacion_imagenes`
--
ALTER TABLE `publicacion_imagenes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `publicacion_id` (`publicacion_id`);

--
-- Indices de la tabla `publicacion_ingredientes`
--
ALTER TABLE `publicacion_ingredientes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `publicacion_id` (`publicacion_id`);

--
-- Indices de la tabla `publicacion_likes`
--
ALTER TABLE `publicacion_likes`
  ADD PRIMARY KEY (`usuario_id`,`publicacion_id`),
  ADD KEY `publicacion_id` (`publicacion_id`);

--
-- Indices de la tabla `publicacion_pasos`
--
ALTER TABLE `publicacion_pasos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `publicacion_id` (`publicacion_id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT de la tabla `publicacion_imagenes`
--
ALTER TABLE `publicacion_imagenes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT de la tabla `publicacion_ingredientes`
--
ALTER TABLE `publicacion_ingredientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT de la tabla `publicacion_pasos`
--
ALTER TABLE `publicacion_pasos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `publicaciones`
--
ALTER TABLE `publicaciones`
  ADD CONSTRAINT `publicaciones_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `publicacion_imagenes`
--
ALTER TABLE `publicacion_imagenes`
  ADD CONSTRAINT `publicacion_imagenes_ibfk_1` FOREIGN KEY (`publicacion_id`) REFERENCES `publicaciones` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `publicacion_ingredientes`
--
ALTER TABLE `publicacion_ingredientes`
  ADD CONSTRAINT `publicacion_ingredientes_ibfk_1` FOREIGN KEY (`publicacion_id`) REFERENCES `publicaciones` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `publicacion_likes`
--
ALTER TABLE `publicacion_likes`
  ADD CONSTRAINT `publicacion_likes_ibfk_1` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `publicacion_likes_ibfk_2` FOREIGN KEY (`publicacion_id`) REFERENCES `publicaciones` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `publicacion_pasos`
--
ALTER TABLE `publicacion_pasos`
  ADD CONSTRAINT `publicacion_pasos_ibfk_1` FOREIGN KEY (`publicacion_id`) REFERENCES `publicaciones` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
