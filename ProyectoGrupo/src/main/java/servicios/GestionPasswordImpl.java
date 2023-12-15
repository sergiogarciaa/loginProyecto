package servicios;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.password4j.Hash;
import com.password4j.Password;

/**
 * Servicio de gestión de contraseñas que implementa la interfaz GestionPasswordService
 */
@Service
public class GestionPasswordImpl implements GestionPasswordService {

	// Clave que proporcionas (asegúrate de guardarla de forma segura)
    String secretKey = "TuClaveSecreta";
    
	@Override
	public String encriptarContraseña(String contraseña) {
		Hash hash = Password.hash(contraseña).withBcrypt();
		return hash.getResult();
	}

	@Override
	public boolean verificarContraseña(String contraseñaIngresada, String contraseñaAlmacenada) {
		if (contraseñaIngresada.equals(contraseñaAlmacenada)) 
			return true;
		else
			return false;
	}


}