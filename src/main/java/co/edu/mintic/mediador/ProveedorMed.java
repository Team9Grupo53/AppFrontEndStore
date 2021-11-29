package co.edu.mintic.mediador;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import co.edu.mintic.connection.ConnectionRest;
import co.edu.mintic.dto.ProveedoresDTO;

public class ProveedorMed {

	private HttpURLConnection httpCon;
	private String authStr;
	

	public ProveedorMed() {
		super();
		this.authStr = "Basic "+Base64.getEncoder().encodeToString("team1G4:T34m1G4*".getBytes());
	}

	public ArrayList<Object> parsingProveedores(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Object> lista = new ArrayList<Object>();
		JSONArray proveedores = (JSONArray) jsonParser.parse(json);
		Iterator i = proveedores.iterator();
		
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			ProveedoresDTO proveedor = new ProveedoresDTO();
			proveedor.setNit(Integer.parseInt(innerObj.get("nitProveedor").toString()));
			proveedor.setCiudad(innerObj.get("ciudadProveedor").toString());
			proveedor.setDireccion(innerObj.get("direccionProveedor").toString());
			proveedor.setNombre(innerObj.get("nombreProveedor").toString());
			proveedor.setTelefono(innerObj.get("telefonoProveedor").toString());
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
			proveedor.setNit(Integer.parseInt(innerObj.get("nitProveedor").toString()));
			proveedor.setCiudad(innerObj.get("ciudadProveedor").toString());
			proveedor.setDireccion(innerObj.get("direccionProveedor").toString());
			proveedor.setNombre(innerObj.get("nombreProveedor").toString());
			proveedor.setTelefono(innerObj.get("telefonoProveedor").toString());
		}
		return proveedor;
	}

	public ArrayList<Object> listar() throws Exception {
		ArrayList<Object> lista = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("proveedores/listar");
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
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

	public Object buscarById(long id) throws Exception {
		Object usr = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("proveedores/buscar/"+id);
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
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
			if (buscarById(((ProveedoresDTO)proveedor).getNit()) == null) {
				httpCon = (HttpURLConnection) ConnectionRest.getConnection("proveedores/crear");
				httpCon.setRequestMethod("POST");
				httpCon.setDoOutput(true);
				httpCon.setRequestProperty("Accept", "application/json");
				httpCon.setRequestProperty("Content-Type", "application/json");
				httpCon.setRequestProperty("Authorization", authStr);
				
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
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("proveedores/actualizar");
			httpCon.setRequestMethod("PUT");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);

			String data = ((ProveedoresDTO) proveedor).toString();
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al actualizar el proveedor::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al actualizar el proveedor::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al crear el proveedor::>" + e.getMessage());
			throw new Exception("Error al crear el proveedor");
		} finally {
			ConnectionRest.close();
		}
	}
	
	
	public void eliminar(Object proveedor) throws Exception {

		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("proveedores/eliminar");
			httpCon.setRequestMethod("DELETE");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
			
			String data = ((ProveedoresDTO) proveedor).toString();
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
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
