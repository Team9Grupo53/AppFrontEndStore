package co.edu.mintic.mediador;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import co.edu.mintic.connection.ConnectionRest;
import co.edu.mintic.dto.AuthoritiesDTO;
import co.edu.mintic.dto.LoginDTO;
import co.edu.mintic.dto.TokenDTO;
import co.edu.mintic.dto.UsuariosDTO;

public class UsuarioMed {

	private HttpURLConnection httpCon;
	//private String authStr;
	

	public UsuarioMed() {
		super();
		//this.authStr = "Basic "+Base64.getEncoder().encodeToString("team1G4:T34m1G4*".getBytes());
	}

	public ArrayList<Object> parsingUsuarios(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Object> lista = new ArrayList<Object>();
		JSONArray usuarios = (JSONArray) jsonParser.parse(json);
		for(Object ob : usuarios) {			
			UsuariosDTO usuario = new UsuariosDTO();
			usuario.setCedula(Integer.parseInt(((JSONObject) ob).get("identityDocument").toString()));
			usuario.setEmail(((JSONObject) ob).get("email").toString());
			usuario.setNombre(((JSONObject) ob).get("name").toString());
			usuario.setPassword(((JSONObject) ob).get("password").toString());
			usuario.setUsuario(((JSONObject) ob).get("nick").toString());
			usuario.setId(((JSONObject) ob).get("id").toString());
			lista.add(usuario);
		}		
		return lista;
	}

	public Object parsingUsuario(String json) throws Exception {
		JSONParser jsonParser = new JSONParser();
		UsuariosDTO usuario = null;
		JSONObject innerObj = (JSONObject) jsonParser.parse(json);
		if (innerObj != null && !innerObj.isEmpty()) {
			usuario = new UsuariosDTO();
			usuario.setCedula(Integer.parseInt(innerObj.get("identityDocument").toString()));
			usuario.setEmail(innerObj.get("email").toString());
			usuario.setNombre(innerObj.get("name").toString());
			usuario.setPassword(innerObj.get("password").toString());
			usuario.setUsuario(innerObj.get("nick").toString());
			System.out.println("innerObj.get(\"id\").toString()::>" + innerObj.get("id").toString());
			usuario.setId(innerObj.get("id").toString());
		}
		return usuario;
	}
	
	public Object parsingToken(String json) throws Exception {
		JSONParser jsonParser = new JSONParser();
		TokenDTO token = null;
		AuthoritiesDTO authoritiesDTO = null;
		List<AuthoritiesDTO> listAuthorities = null;
		JSONObject innerObj = (JSONObject) jsonParser.parse(json);
		if (innerObj != null && !innerObj.isEmpty()) {
			token = new TokenDTO();
			token.setToken(innerObj.get("token").toString());
			token.setBearer(innerObj.get("bearer").toString());
			token.setNombreUsuario(innerObj.get("nombreUsuario").toString());
			
			JSONArray detallesVenta = (JSONArray) jsonParser.parse(((JSONObject) innerObj).get("authorities").toString());			
			
			listAuthorities = new ArrayList<AuthoritiesDTO>();
			for(Object obj : detallesVenta) {				
				authoritiesDTO = new AuthoritiesDTO();
				authoritiesDTO.setAuthority(((JSONObject)obj).get("authority").toString());
				listAuthorities.add(authoritiesDTO);
			}
			token.setAuthorities(listAuthorities);
		}
		return token;
	}
	
	public Object parsingUsuario(String json, Object token) throws Exception {
		JSONParser jsonParser = new JSONParser();
		UsuariosDTO usuario = null;
		JSONObject innerObj = (JSONObject) jsonParser.parse(json);
		if (innerObj != null && !innerObj.isEmpty()) {
			usuario = new UsuariosDTO();
			usuario.setCedula(Integer.parseInt(innerObj.get("identityDocument").toString()));
			usuario.setEmail(innerObj.get("email").toString());
			usuario.setNombre(innerObj.get("name").toString());
			usuario.setPassword(innerObj.get("password").toString());
			usuario.setUsuario(innerObj.get("nick").toString());
			usuario.setId(innerObj.get("id").toString());
			usuario.setToken((TokenDTO) token);
		}
		return usuario;
	}

	public ArrayList<Object> listar(TokenDTO tk) throws Exception {
		ArrayList<Object> lista = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/user/");
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", ((TokenDTO)tk).getBearer() +" "+((TokenDTO)tk).getToken());
			
			System.out.println("httpCon.getResponseCode()::>" + httpCon.getResponseCode());
			if(httpCon.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND || httpCon.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
			   System.out.println("No encontrados:::");
				return null;
			}
			
			InputStream respuesta = httpCon.getInputStream();
			byte[] inp = respuesta.readAllBytes();
			String json = "";
			for (int i = 0; i < inp.length; i++) {
				json += (char) inp[i];
			}
			
			System.out.println("json::>" + json);
			lista = parsingUsuarios(json);

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return lista;
	}

	public Object buscarById(long id,TokenDTO tk) throws Exception {
		Object usr = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/user/document/"+id);
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", ((TokenDTO)tk).getBearer() +" "+((TokenDTO)tk).getToken());
			
			System.out.println("httpCon.getResponseCode()::>" + httpCon.getResponseCode());
			if(httpCon.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
			   return null;
			}
			InputStream respuesta = httpCon.getInputStream();
			byte[] inp = respuesta.readAllBytes();
			String json = "";
			for (int i = 0; i < inp.length; i++) { 
				json += (char) inp[i];
			}
						
			System.out.println("json::>" + json);
			if(json!=null && !json.isEmpty()) {
				usr = parsingUsuario(json);
			}		

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return usr;
	}

	public void crear(Object usuario) throws Exception {

		try {
			if (buscarById(((UsuariosDTO)usuario).getCedula(),((UsuariosDTO)usuario).getToken()) == null) {
				httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/user/");
				httpCon.setRequestMethod("POST");
				httpCon.setDoOutput(true);
				httpCon.setRequestProperty("Accept", "application/json");
				httpCon.setRequestProperty("Content-Type", "application/json");
				httpCon.setRequestProperty("Authorization", ((UsuariosDTO)usuario).getToken().getBearer() +" "+((UsuariosDTO)usuario).getToken().getToken());
				
				System.out.println("::::::::>"+((UsuariosDTO) usuario).toString());
				
				String data = ((UsuariosDTO) usuario).toString();
				byte[] out = data.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = httpCon.getOutputStream();
				stream.write(out);
				if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
					System.err.println("Error al crear el usuarios::>" + httpCon.getResponseCode() + "<::>"
							+ httpCon.getResponseMessage());
					throw new Exception("Error al crear el usuarios::>" + httpCon.getResponseCode());
				}
			}else {
				System.err.println("Usuario ya existe::>");
				throw new Exception("Usuario ya existe::>");
			}

		} catch (Exception e) {
			System.err.println("Error al crear el usuario::>" + e.getMessage());
			throw new Exception("Error al crear el usuarios");
		} finally {
			ConnectionRest.close();
		}
	}

	public void actualizar(Object usuario) throws Exception {

		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/user/"+((UsuariosDTO)usuario).getId());
			httpCon.setRequestMethod("PUT");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization",  ((UsuariosDTO)usuario).getToken().getBearer() +" "+((UsuariosDTO)usuario).getToken().getToken());

			String data = ((UsuariosDTO) usuario).toString();
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al actualizar el usuarios::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al actualizar el usuarios::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al actualizar el usuario::>" + e.getMessage());
			throw new Exception("Error al actualizar el usuarios");
		} finally {
			ConnectionRest.close();
		}
	}
	
	
	public void eliminar(Object usuario) throws Exception {

		try {
			
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/user/"+ ((UsuariosDTO)usuario).getId());
			httpCon.setRequestMethod("DELETE");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization",  ((UsuariosDTO)usuario).getToken().getBearer() +" "+((UsuariosDTO)usuario).getToken().getToken());
			System.out.println("httpCon.getResponseCode()::>" + httpCon.getResponseCode());
			//String data = ((UsuariosDTO) usuario).toString();
			//byte[] out = data.getBytes(StandardCharsets.UTF_8);
			//OutputStream stream = httpCon.getOutputStream();
			//stream.write(out);
			
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al eliminar el usuarios::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al eliminar el usuarios::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al eliminar el usuario::>" + e.getMessage());
			throw new Exception("Error al eliminar el usuarios");
		} finally {
			ConnectionRest.close();
		}
	}
	
	public Object loginUsuario(String user, String pass) throws Exception {
		Object tk = null;
		Object usr = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/user/login/");
			httpCon.setRequestMethod("POST");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			//httpCon.setRequestProperty("Authorization", authStr);
			
			LoginDTO login = new LoginDTO(user,pass);
			System.out.println("login validacion::>" + login);
			String data = login.toString();
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			
			
			InputStream respuesta = httpCon.getInputStream();
			byte[] inp = respuesta.readAllBytes();
			String json = "";
			for (int i = 0; i < inp.length; i++) {
				json += (char) inp[i];
			}
			System.out.println("json validacion::>" + json);
			if(json!=null && !json.isEmpty()) {
				tk = parsingToken(json);
			}
			
			//consutar datos del usuario logueado
			
			
			try {
				httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/user/nick/"+user);
				httpCon.setRequestMethod("GET");
				httpCon.setRequestProperty("Accept", "application/json");
				httpCon.setRequestProperty("Authorization", ((TokenDTO)tk).getBearer() +" "+((TokenDTO)tk).getToken());
				InputStream res = httpCon.getInputStream();
				byte[] inp2 = res.readAllBytes();
				String jsonU = "";
				for (int i = 0; i < inp2.length; i++) {
					jsonU += (char) inp2[i];
				}
				System.out.println("jsonU::>" + jsonU);
				if(jsonU!=null && !jsonU.isEmpty()) {
					usr = parsingUsuario(jsonU,tk);
				}		

			} catch (Exception e) {
				System.err.println("No hay resultados para consulta de usuario login::>" + e.getMessage());
				throw new Exception("No hay resultados para consulta de usuario login");
			} finally {
				ConnectionRest.close();
			}

		} catch (Exception e) {
			System.err.println("No hay resultados para esa validacion::>" + e.getMessage());
			throw new Exception("No hay resultados para esa validacion");
		} finally {
			ConnectionRest.close();
		}

		return usr;
	}

}
