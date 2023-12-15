<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="estilos/menu.css">
<link rel="stylesheet" href="estilos/estilo.css">

<script src="script/script.js"></script>
<title>Inicio</title>
<!-- cambio -->
</head>
<body>
	<nav>	
		<div class="menu">
		<div class="logo"><a>Biblioteca</a></div>
			<ul class="nav-links">
		<li><a onclick="cargarContenido('Administrar.jsp')">Administrar</a></li>
		<li><a onclick="cargarContenido('registro.jsp')">Registrar</a></li>
		<li><a onclick="cargarContenido('IniciarSesion.jsp')">Login</a></li>
			</ul>
		</div>
	</nav>
 
	<div id="caja">
       
    </div>	
	
    
</body>
</html>