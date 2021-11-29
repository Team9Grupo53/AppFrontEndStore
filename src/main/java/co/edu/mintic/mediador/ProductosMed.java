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
import co.edu.mintic.dto.ProductosDTO;

public class ProductosMed {

	private HttpURLConnection httpCon;
	private String authStr;
	

	public ProductosMed() {
		super();
		this.authStr = "Basic "+Base64.getEncoder().encodeToString("team1G4:T34m1G4*".getBytes());
	}

	public ArrayList<Object> parsingProductos(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Object> lista = new ArrayList<Object>();
		JSONArray productos = (JSONArray) jsonParser.parse(json);
		Iterator i = productos.iterator();
		while (i.hasNext()) {
			JSONObject innerObj = (JSONObject) i.next();
			ProductosDTO producto = new ProductosDTO();
			producto.setCodigoProducto(Integer.parseInt(innerObj.get("codigoProducto").toString()));
			producto.setNitProveedor(Integer.parseInt(((JSONObject)innerObj.get("proveedor")).get("nitProveedor").toString()));
			producto.setIvaCompra(Double.parseDouble(innerObj.get("ivaCompra").toString()));
			producto.setNombreProducto(innerObj.get("nombreProducto").toString());
			producto.setPrecioCompra(Double.parseDouble(innerObj.get("precioCompra").toString()));
			producto.setPrecioVenta (Double.parseDouble(innerObj.get("precioVenta").toString()));
			lista.add(producto);
		}
		return lista;
	}

	public Object parsingProducto(String json) throws Exception {
		JSONParser jsonParser = new JSONParser();
		ProductosDTO producto = null;
		JSONObject innerObj = (JSONObject) jsonParser.parse(json);
		if (innerObj != null && !innerObj.isEmpty()) {
			producto = new ProductosDTO();
			producto.setCodigoProducto(Integer.parseInt(innerObj.get("codigoProducto").toString()));
			producto.setNitProveedor(Integer.parseInt(((JSONObject)innerObj.get("proveedor")).get("nitProveedor").toString()));
			producto.setIvaCompra(Double.parseDouble(innerObj.get("ivaCompra").toString()));
			producto.setNombreProducto(innerObj.get("nombreProducto").toString());
			producto.setPrecioCompra(Double.parseDouble(innerObj.get("precioCompra").toString()));
			producto.setPrecioVenta (Double.parseDouble(innerObj.get("precioVenta").toString()));
		
		}
		return producto;
	}

	public ArrayList<Object> listar() throws Exception {
		ArrayList<Object> lista = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("productos/listar");
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
			lista = parsingProductos(json);

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
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("productos/buscar/"+id);
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
			if(json!=null && !json.isEmpty() && !json.equals("null") ) {
				usr = parsingProducto(json);
			}else {
				throw new Exception("No hay resultados para esa consulta");
			}

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return usr;
	}

	public void crear(Object producto) throws Exception {

		try {
			if (buscarById(((ProductosDTO)producto).getCodigoProducto()) == null) {
				httpCon = (HttpURLConnection) ConnectionRest.getConnection("productos/crear");
				httpCon.setRequestMethod("POST");
				httpCon.setDoOutput(true);
				httpCon.setRequestProperty("Accept", "application/json");
				httpCon.setRequestProperty("Content-Type", "application/json");
				httpCon.setRequestProperty("Authorization", authStr);
				
				String data = ((ProductosDTO) producto).toString();
				byte[] out = data.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = httpCon.getOutputStream();
				stream.write(out);
				if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
					System.err.println("Error al crear el producto::>" + httpCon.getResponseCode() + "<::>"
							+ httpCon.getResponseMessage());
					throw new Exception("Error al crear el producto::>" + httpCon.getResponseCode());
				}
			}else {
				System.err.println("El producto ya existe::>");
				throw new Exception("El producto ya existe::>");
			}

		} catch (Exception e) {
			System.err.println("Error al crear el producto::>" + e.getMessage());
			throw new Exception("Error al crear el producto");
		} finally {
			ConnectionRest.close();
		}
	}

	public void actualizar(Object producto) throws Exception {

		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("productos/actualizar");
			httpCon.setRequestMethod("PUT");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);

			String data = ((ProductosDTO) producto).toString();
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al actualizar el producto::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al actualizar el producto::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al crear el producto::>" + e.getMessage());
			throw new Exception("Error al crear el producto");
		} finally {
			ConnectionRest.close();
		}
	}
	
	
	public void eliminar(Object producto) throws Exception {

		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("productos/eliminar");
			httpCon.setRequestMethod("DELETE");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
			
		    String data = ((ProductosDTO) producto).toString(); 
		    System.out.println("Data = "+data);
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al eliminar el producto::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al eliminar el producto::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al eliminar el producto::>" + e.getMessage());
			throw new Exception("Error al eliminar el producto");
		} finally {
			ConnectionRest.close();
		}
	}
	

}
