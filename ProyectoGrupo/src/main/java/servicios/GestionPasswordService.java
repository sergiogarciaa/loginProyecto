package servicios;

import javax.crypto.SecretKey;

/**
 * Clase servicio que proporciona metodos para gestionar el encriptado de la contraseña de la entidad Usuario.
 */
public interface GestionPasswordService {
	
	/**
	 * Encripta la contraseña con algoritmo bcrypt
	 * @param contraseña a encriptar del usuario
	 * @return contraseña encriptada del usuario
	 */
	 public String encriptarContraseña(String contraseña);
	 
	 /**
	  * Desencripta la contraseña con algoritmo bcrypt
	  * @param contraseñaIngresada por el usuario
	  * @param contraseñaAlmacenada en la base de datos
	  * @return true en caso de que sean iguales las contraseñas
	  */
	 public boolean verificarContraseña(String contraseñaIngresada, String contraseñaAlmacenada);
}	