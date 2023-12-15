package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servicios.GestionPasswordImpl;
import servicios.GestionPasswordService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import entidades.Usuario;

/**
 * Servlet implementation class IniciarSesion
 */

public class IniciarSesion extends HttpServlet {
	String secretKey = "TuClaveSecreta";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IniciarSesion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	 
	            throws ServletException, IOException {
	        String email = request.getParameter("email");
	        String clave = request.getParameter("contraseña");

	        // Encripta la contraseña ingresada por el usuario
	        try {
				clave = encrypt(clave, secretKey);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        // Iniciar el objeto usuario con el metodo para traer todos los campos
	        Usuario usuario = verificarCredenciales(email, clave);
	        if (usuario.getClaveUsuario().equals(clave)) {
	            // Credenciales válidas, establece la sesión
	            // Redirige a la página principal
	            response.sendRedirect("index.jsp");
	        } else {
	            // Credenciales inválidas, redirige a la página de inicio de sesión con un mensaje de error
	        	System.out.println("Mala contraseña");
	            response.sendRedirect("login.jsp?error=1");
	        }
	    }

	 	//Verificar y traer los campos
	    private Usuario verificarCredenciales(String email, String clave) {
	    	try {
	            String apiUrl = "http://192.168.30.154:8090/usuarios/email/" + email;
	            HttpGet httpGet = new HttpGet(apiUrl);

	            HttpClient httpClient = HttpClients.createDefault();
	            HttpResponse httpResponse = httpClient.execute(httpGet);

	            // Verificar el código de respuesta (200 indica éxito)
	            if (httpResponse.getStatusLine().getStatusCode() == 200) {
	                // Procesar la respuesta y convertirla a un objeto Usuario
	                String jsonUsuario = EntityUtils.toString(httpResponse.getEntity());

	                // Convertir JSON a objeto Usuario
	                ObjectMapper objectMapper = new ObjectMapper();
	                // Error aqui porque no hay los mismos campos en la api que en mi usuario
	                Usuario usuario = objectMapper.readValue(jsonUsuario, Usuario.class);
	                System.out.println(usuario.getClaveUsuario());
	                return usuario;
	            } else {
	                // Manejar el caso en el que la solicitud no fue exitosa
	                System.out.println("Error al obtener el usuario. Código de respuesta: " + httpResponse.getStatusLine().getStatusCode());
	                return null;
	            }
	        } catch (Exception e) {
	            // Manejar excepciones, como IOException o JsonProcessingException
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
		 private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException {
		        MessageDigest sha = MessageDigest.getInstance("SHA-256");
		        byte[] keyBytes = sha.digest(secretKey.getBytes(StandardCharsets.UTF_8));
		        keyBytes = Arrays.copyOf(keyBytes, 16); // Ajustar a 16 bytes para una clave de 128 bits
		        return new SecretKeySpec(keyBytes, "AES");
		    }
		 
		 public static String decrypt(String encryptedText, String secretKey) throws Exception {
		        Cipher cipher = Cipher.getInstance("AES");
		        SecretKey key = generateKey(secretKey);
		        cipher.init(Cipher.DECRYPT_MODE, key);
		        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
		        return new String(decryptedBytes, StandardCharsets.UTF_8);
		    }

			public static String encrypt(String plainText, String secretKey) throws Exception {
		        Cipher cipher = Cipher.getInstance("AES");
		        SecretKey key = generateKey(secretKey);
		        cipher.init(Cipher.ENCRYPT_MODE, key);
		        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
		        return Base64.getEncoder().encodeToString(encryptedBytes);
		    }
}
