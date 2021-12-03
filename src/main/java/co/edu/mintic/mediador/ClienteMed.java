package co.edu.mintic.mediador;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import co.edu.mintic.connection.ConnectionRest;
import co.edu.mintic.dto.ClienteDTO;
import co.edu.mintic.dto.TokenDTO;

public class ClienteMed {

	private HttpURLConnection httpCon;
	//private String authStr;
	

	public ClienteMed() {
		super();
		//this.authStr = "Basic "+Base64.getEncoder().encodeToString("team1G4:T34m1G4*".getBytes());
	}

	public ArrayList<Object> parsingClientes(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Object> lista = new ArrayList<Object>();
		JSONArray clientes = (JSONArray) jsonParser.parse(json);
		Iterator i = clientes.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			ClienteDTO cliente = new ClienteDTO();
			cliente.setCedula(Integer.parseInt(innerObj.get("documentnumber").toString()));
			cliente.setDireccion(innerObj.get("address").toString());
			cliente.setEmail(innerObj.get("email").toString());
			cliente.setNombre(innerObj.get("name").toString());
			cliente.setTelefono(innerObj.get("phonenumber").toString());
			cliente.setId(innerObj.get("id").toString());
			lista.add(cliente);
		}
		return lista;
	}

	public Object parsingCliente(String json) throws Exception {
		JSONParser jsonParser = new JSONParser();
		ClienteDTO cliente = null;
		JSONObject innerObj = (JSONObject) jsonParser.parse(json);
		if (innerObj != null && !innerObj.isEmpty()) {
			cliente = new ClienteDTO();
			cliente.setCedula(Integer.parseInt(innerObj.get("documentnumber").toString()));
			cliente.setDireccion(innerObj.get("address").toString());
			cliente.setEmail(innerObj.get("email").toString());
			cliente.setNombre(innerObj.get("name").toString());
			cliente.setTelefono(innerObj.get("phonenumber").toString());
			cliente.setId(innerObj.get("id").toString());
		}
		return cliente;
	}

	public ArrayList<Object> listar(TokenDTO tk) throws Exception {
		ArrayList<Object> lista = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/client/");
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", tk.getBearer() +" "+tk.getToken());
			
			System.out.println("httpCon.getResponseCode()::>" + httpCon.getResponseCode());
			if(httpCon.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND || httpCon.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
			   return null;
			}
			
			
			InputStream respuesta = httpCon.getInputStream();
			byte[] inp = respuesta.readAllBytes();
			String json = "";
			for (int i = 0; i < inp.length; i++) {
				json += (char) inp[i];
			}
			System.out.println("json::>" + json);
			lista = parsingClientes(json);

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return lista;
	}

	public Object buscarById(long id, TokenDTO tk) throws Exception {
		Object usr = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/client/document/"+id);
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", tk.getBearer() +" "+tk.getToken());
						
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
			if(json!=null && !json.isEmpty() && !json.equals("null")) {
				usr = parsingCliente(json);
			}else {
				System.out.println("json:Entreeeeeee:>"+json);
				throw new Exception("Cliente no encontrado");
			}

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return usr;
	}

	public void crear(Object cliente) throws Exception {

		try {
			if (buscarById(((ClienteDTO)cliente).getCedula(),((ClienteDTO)cliente).getToken()) == null) {
				
				httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/client");
				httpCon.setRequestMethod("POST");
				httpCon.setDoOutput(true);
				httpCon.setRequestProperty("Accept", "application/json");
				httpCon.setRequestProperty("Content-Type", "application/json");
				httpCon.setRequestProperty("Authorization", ((ClienteDTO)cliente).getToken().getBearer() +" "+((ClienteDTO)cliente).getToken().getToken());
				
				String data = ((ClienteDTO) cliente).toString();
				byte[] out = data.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = httpCon.getOutputStream();
				stream.write(out);
				System.out.println("httpCon.getResponseCode() Agregar ::>"+httpCon.getResponseCode());
				if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
					System.err.println("Error al crear el cliente::>" + httpCon.getResponseCode() + "<::>"
							+ httpCon.getResponseMessage());
					throw new Exception("Error al crear el cliente::>" + httpCon.getResponseCode());
				}
			}else {
				System.err.println("cliente ya existe::>");
				throw new Exception("cliente ya existe::>");
			}

		} catch (Exception e) {
			System.err.println("Error al crear el cliente::>" + e.getMessage());
			throw new Exception("Error al crear el cliente");
		} finally {
			ConnectionRest.close();
		}
	}

	public void actualizar(Object cliente) throws Exception {

		try {
			System.out.println("((ClienteDTO)cliente).getId()::>"+((ClienteDTO)cliente).getId());
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/client/"+((ClienteDTO)cliente).getId());
			httpCon.setRequestMethod("PUT");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", ((ClienteDTO)cliente).getToken().getBearer() +" "+((ClienteDTO)cliente).getToken().getToken());

			String data = ((ClienteDTO) cliente).toString();
			System.out.println("data Actualizar::>"+data);
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al actualizar el cliente::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al actualizar el cliente::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al actualizar el cliente::>" + e.getMessage());
			throw new Exception("Error al actualizar el cliente");
		} finally {
			ConnectionRest.close();
		}
	}
	
	
	public void eliminar(Object cliente) throws Exception {

		try {
			
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/client/"+((ClienteDTO)cliente).getId());
			httpCon.setRequestMethod("DELETE");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization",  ((ClienteDTO)cliente).getToken().getBearer() +" "+((ClienteDTO)cliente).getToken().getToken());
			
			//String data = ((ClienteDTO) cliente).toString();
			//byte[] out = data.getBytes(StandardCharsets.UTF_8);
			//OutputStream stream = httpCon.getOutputStream();
			//stream.write(out);
			
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al eliminar el usuarios::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al eliminar el usuarios::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al eliminar el cliente::>" + e.getMessage());
			throw new Exception("Error al eliminar el cliente");
		} finally {
			ConnectionRest.close();
		}
	}
	
	
}
