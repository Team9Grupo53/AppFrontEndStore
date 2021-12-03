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
import co.edu.mintic.dto.ProveedoresDTO;
import co.edu.mintic.dto.TokenDTO;

public class ProveedorMed {

	private HttpURLConnection httpCon;
	//private String authStr;
	

	public ProveedorMed() {
		super();
		//this.authStr = "Basic "+Base64.getEncoder().encodeToString("team1G4:T34m1G4*".getBytes());
	}

	public ArrayList<Object> parsingProveedores(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Object> lista = new ArrayList<Object>();
		JSONArray proveedores = (JSONArray) jsonParser.parse(json);
		Iterator i = proveedores.iterator();
		
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			ProveedoresDTO proveedor = new ProveedoresDTO();
			proveedor.setNit(Integer.parseInt(innerObj.get("nit").toString()));
			proveedor.setCiudad(innerObj.get("city").toString());
			proveedor.setDireccion(innerObj.get("address").toString());
			proveedor.setNombre(innerObj.get("name").toString());
			proveedor.setTelefono(innerObj.get("phoneNumber").toString());
			proveedor.setId(innerObj.get("id").toString());
			lista.add(proveedor);
		}
		return lista;
	}

	public Object parsingProveedor(String json) throws Exception {
		JSONParser jsonParser = new JSONParser();
		ProveedoresDTO proveedor = null;
		JSONObject innerObj = (JSONObject) jsonParser.parse(json);
		if (innerObj != null && !innerObj.isEmpty()) {
			proveedor = new ProveedoresDTO();
			proveedor.setNit(Integer.parseInt(innerObj.get("nit").toString()));
			proveedor.setCiudad(innerObj.get("city").toString());
			proveedor.setDireccion(innerObj.get("address").toString());
			proveedor.setNombre(innerObj.get("name").toString());
			proveedor.setTelefono(innerObj.get("phoneNumber").toString());
			proveedor.setId(innerObj.get("id").toString());
		}
		return proveedor;
	}

	public ArrayList<Object> listar(TokenDTO tk) throws Exception {
		ArrayList<Object> lista = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/supplier/");
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization",  ((TokenDTO)tk).getBearer() +" "+((TokenDTO)tk).getToken());
			
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
			lista = parsingProveedores(json);

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
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/supplier/nit/"+id);
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
			if(json!=null && !json.isEmpty()) {
				usr = parsingProveedor(json);
			}		

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return usr;
	}

	public void crear(Object proveedor) throws Exception {

		try {
			if (buscarById(((ProveedoresDTO)proveedor).getNit(),((ProveedoresDTO)proveedor).getToken()) == null) {
				httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/supplier/");
				httpCon.setRequestMethod("POST");
				httpCon.setDoOutput(true);
				httpCon.setRequestProperty("Accept", "application/json");
				httpCon.setRequestProperty("Content-Type", "application/json");
				httpCon.setRequestProperty("Authorization", ((ProveedoresDTO)proveedor).getToken().getBearer() +" "+((ProveedoresDTO)proveedor).getToken().getToken());
				
				String data = ((ProveedoresDTO) proveedor).toString();
				byte[] out = data.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = httpCon.getOutputStream();
				stream.write(out);
				if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
					System.err.println("Error al crear el proveedor::>" + httpCon.getResponseCode() + "<::>"
							+ httpCon.getResponseMessage());
					throw new Exception("Error al crear el proveedor::>" + httpCon.getResponseCode());
				}
			}else {
				System.err.println("Proveedor ya existe::>");
				throw new Exception("Proveedor ya existe::>");
			}

		} catch (Exception e) {
			System.err.println("Error al crear el proveedor::>" + e.getMessage());
			throw new Exception("Error al crear el proveedor");
		} finally {
			ConnectionRest.close();
		}
	}

	public void actualizar(Object proveedor) throws Exception {

		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/supplier/"+((ProveedoresDTO)proveedor).getId());
			httpCon.setRequestMethod("PUT");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", ((ProveedoresDTO)proveedor).getToken().getBearer() +" "+((ProveedoresDTO)proveedor).getToken().getToken());

			String data = ((ProveedoresDTO) proveedor).toString();
			//System.out.println("data::::::>"+data);
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al actualizar el proveedor::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al actualizar el proveedor::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al modificar el proveedor::>" + e.getMessage());
			throw new Exception("Error al modificar el proveedor");
		} finally {
			ConnectionRest.close();
		}
	}
	
	
	public void eliminar(Object proveedor) throws Exception {

		try {
			System.out.println("((ProveedoresDTO)proveedor).getToken()::>"+((ProveedoresDTO)proveedor).getToken());
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("api/supplier/"+((ProveedoresDTO)proveedor).getId());
			httpCon.setRequestMethod("DELETE");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", ((ProveedoresDTO)proveedor).getToken().getBearer() +" "+((ProveedoresDTO)proveedor).getToken().getToken());
			
			//String data = ((ProveedoresDTO) proveedor).toString();
			//byte[] out = data.getBytes(StandardCharsets.UTF_8);
			//OutputStream stream = httpCon.getOutputStream();
			//stream.write(out);
			
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al eliminar el proveedor::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al eliminar el proveedor::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al crear el proveedor::>" + e.getMessage());
			throw new Exception("Error al crear el proveedor");
		} finally {
			ConnectionRest.close();
		}
	}
	
	

}
