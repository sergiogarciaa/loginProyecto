<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nuevo Registro</title>
</head>
<body>

<div class="contenedor form-registro">
	<p class="titulo">Nuevo Usuario</p>
	<form class="form" id="usuarioForm" action="InsertarDatos" method="post">
		<input type="text" id="nombre" name="nombre" class="input" placeholder="Nombre" required>
		<input type="text" id="apellidos" name="apellidos" class="input" placeholder="Apellidos" required>                    
		<input type="text" id="dni" name="dni" class="input" placeholder="DNI" oninput="revisarDni()" required>
		<input type="tel" id="tlf" name="tlf" class="input" placeholder="Telefono" required>                   
		<input type="email" id="email" name="email" class="input" placeholder="Email" required>                                       
		<input type="password" id="contraseña" name="contraseña" class="input" placeholder="Contraseña" oninput="revisarContraseña()" required> 
		<input type="password" id="confContraseña" class="input" placeholder="Confirmar Contraseña" required oninput="revisarContraseña()" required>
		<button type="submit" id="btnRegistro" name="btnRegistro" class="form-btn">Registrarse</button>
		<div class="mensaje" id="mensajeContraseña"></div>  <div class="mensaje" id="mensajeDni"></div>
	</form>
</div>

</body>
</html>	