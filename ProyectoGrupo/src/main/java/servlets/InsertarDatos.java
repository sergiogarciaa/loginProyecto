package servlets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.databind.ObjectMapper;

import entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.GestionPasswordImpl;
import servicios.GestionPasswordService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

@WebServlet("/InsertarDatos")
public class InsertarDatos extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 241566496352402022L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Clave que proporcionas (asegúrate de guardarla de forma segura)
        String secretKey = "TuClaveSecreta";
		
    	String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellidos");
        String dni = request.getParameter("dni");
        String telefono = request.getParameter("tlf");
        String email = request.getParameter("email");
        String clave = request.getParameter("contraseña");
        //encriptar clave

        try {
			clave = encrypt(clave, secretKey);
		      Usuario usuario = new Usuario(dni, nombre, apellido, telefono, email, clave);
	
		      String url = "http://192.168.30.154:8090/usuarios"; // Reemplaza con la URL correcta de tu API
		        
		        // Convertir el objeto a JSON
		        ObjectMapper objectMapper = new ObjectMapper();
		        String jsonUsuario;
		        try {
		            jsonUsuario = objectMapper.writeValueAsString(usuario);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return;
		        }
		     // Crear cliente HTTP
		        HttpClient httpClient = HttpClients.createDefault();

		        // Crear solicitud POST
		        HttpPost httpPost = new HttpPost(url);

		        // Configurar encabezados y cuerpo de la solicitud
		        httpPost.setHeader("Content-Type", "application/json");
		        httpPost.setEntity(new StringEntity(jsonUsuario, "UTF-8"));

		        try {
		            // Enviar la solicitud y obtener la respuesta
		            HttpResponse httpResponse = httpClient.execute(httpPost);

		            // Procesar la respuesta
		            int statusCode = httpResponse.getStatusLine().getStatusCode();
		            System.out.println("Codigo de respuesta: " + statusCode);

		            // AquÃ­ puedes leer la respuesta si es necesario
		            // InputStream inputStream = httpResponse.getEntity().getContent();
		            // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		            // String linea;
		            // while ((linea = bufferedReader.readLine()) != null) {
		            //     System.out.println(linea);
		            // }

		            // Cerrar recursos si es necesario
		            // bufferedReader.close();
		            // inputStream.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
		        response.sendRedirect("index.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
    }

	public static String encrypt(String plainText, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey key = generateKey(secretKey);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
	// Método para descifrar un texto con AES
    public static String decrypt(String encryptedText, String secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKey key = generateKey(secretKey);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // Método para generar una clave SecretKey a partir de una cadena
    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(secretKey.getBytes(StandardCharsets.UTF_8));
        keyBytes = Arrays.copyOf(keyBytes, 16); // Ajustar a 16 bytes para una clave de 128 bits
        return new SecretKeySpec(keyBytes, "AES");
    }

}
