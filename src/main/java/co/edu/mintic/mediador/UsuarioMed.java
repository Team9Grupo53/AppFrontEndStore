package co.edu.mintic.mediador;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import co.edu.mintic.connection.ConnectionRest;
import co.edu.mintic.dto.UsuariosDTO;

public class UsuarioMed {

	private HttpURLConnection httpCon;
	private String authStr;
	

	public UsuarioMed() {
		super();
		this.authStr = "Basic "+Base64.getEncoder().encodeToString("team1G4:T34m1G4*".getBytes());
	}

	public ArrayList<Object> parsingUsuarios(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Object> lista = new ArrayList<Object>();
		JSONArray usuarios = (JSONArray) jsonParser.parse(json);
		for(Object ob : usuarios) {			
			UsuariosDTO usuario = new UsuariosDTO();
			usuario.setCedula(Integer.parseInt(((JSONObject) ob).get("cedulaUsuario").toString()));
			usuario.setEmail(((JSONObject) ob).get("emailUsuario").toString());
			usuario.setNombre(((JSONObject) ob).get("nombreUsuario").toString());
			usuario.setPassword(((JSONObject) ob).get("password").toString());
			usuario.setUsuario(((JSONObject) ob).get("usuario").toString());
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
			usuario.setCedula(Integer.parseInt(innerObj.get("cedulaUsuario").toString()));
			usuario.setEmail(innerObj.get("emailUsuario").toString());
			usuario.setNombre(innerObj.get("nombreUsuario").toString());
			usuario.setPassword(innerObj.get("password").toString());
			usuario.setUsuario(innerObj.get("usuario").toString());
		}
		return usuario;
	}

	public ArrayList<Object> listar() throws Exception {
		ArrayList<Object> lista = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("usuarios/listar");
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
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

	public Object buscarById(long id) throws Exception {
		Object usr = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("usuarios/buscar/"+id);
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
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
			if (buscarById(((UsuariosDTO)usuario).getCedula()) == null) {
				httpCon = (HttpURLConnection) ConnectionRest.getConnection("usuarios/crear");
				httpCon.setRequestMethod("POST");
				httpCon.setDoOutput(true);
				httpCon.setRequestProperty("Accept", "application/json");
				httpCon.setRequestProperty("Content-Type", "application/json");
				httpCon.setRequestProperty("Authorization", authStr);
				
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
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("usuarios/actualizar");
			httpCon.setRequestMethod("PUT");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);

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
			System.err.println("Error al crear el usuario::>" + e.getMessage());
			throw new Exception("Error al crear el usuarios");
		} finally {
			ConnectionRest.close();
		}
	}
	
	
	public void eliminar(Object usuario) throws Exception {

		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("usuarios/eliminar");
			httpCon.setRequestMethod("DELETE");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
			
			String data = ((UsuariosDTO) usuario).toString();
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al eliminar el usuarios::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al eliminar el usuarios::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al crear el usuario::>" + e.getMessage());
			throw new Exception("Error al crear el usuarios");
		} finally {
			ConnectionRest.close();
		}
	}
	
	public Object loginUsuario(String user, String pass) throws Exception {
		Object usr = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("usuarios/validar/"+user+"/"+pass);
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
			
			InputStream respuesta = httpCon.getInputStream();
			byte[] inp = respuesta.readAllBytes();
			String json = "";
			for (int i = 0; i < inp.length; i++) {
				json += (char) inp[i];
			}
			System.out.println("json validacion::>" + json);
			if(json!=null && !json.isEmpty()) {
				usr = parsingUsuario(json);
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
