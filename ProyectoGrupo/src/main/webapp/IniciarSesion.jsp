<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Iniciar Sesion</title>
</head>
<body>
<div class="contenedor form-registro">
	<p class="titulo">Login</p>
	<form class="form" id="usuarioForm" action="IniciarSesion" method="post">
		<input type="email" id="email" name="email" class="input" placeholder="Email" required>                                       
		<input type="password" id="contraseña" name="contraseña" class="input" placeholder="Contraseña" oninput="revisarContraseña()" required> 
		<button type="submit" id="btnRegistro" name="btnRegistro" class="form-btn">Registrarse</button>
		<div class="mensaje" id="mensajeContraseña"></div>  <div class="mensaje" id="mensajeDni"></div>
	</form>
</div>
</body>
</html>