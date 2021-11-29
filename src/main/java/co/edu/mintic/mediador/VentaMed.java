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
import co.edu.mintic.dto.ClienteDTO;
import co.edu.mintic.dto.DetalleVentaDTO;
import co.edu.mintic.dto.ProductosDTO;
import co.edu.mintic.dto.ReporteVentasDTO;
import co.edu.mintic.dto.UsuariosDTO;
import co.edu.mintic.dto.VentaDTO;

public class VentaMed {
	
	private HttpURLConnection httpCon;
	private String authStr;
	
	public VentaMed() {
		super();
		this.authStr = "Basic "+Base64.getEncoder().encodeToString("team1G4:T34m1G4*".getBytes());
	}
	
	private String obtenerJson(InputStream respuesta) throws Exception {
		byte[] inp = respuesta.readAllBytes();
		String json = "";
		for (int i = 0; i < inp.length; i++) {
			json += (char) inp[i];
		}
		System.out.println("json::>" + json);
		return json;
	}
	
	public ArrayList<Object> parsingVentas(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Object> lista = new ArrayList<Object>();
		DetalleVentaDTO detalleVentaDTO = null;
		ArrayList<DetalleVentaDTO> lisDetalleVenta = null;
		VentaDTO ventaDTO= null;
		JSONArray ventas = (JSONArray) jsonParser.parse(json);		
		for(Object ob : ventas) {
			ventaDTO= new VentaDTO();
			ventaDTO.setCodVenta(Long.parseLong(((JSONObject) ob).get("codigoVenta").toString()));
			ventaDTO.setIvaVenta(Double.valueOf(((JSONObject) ob).get("ivaVenta").toString()));
			ventaDTO.setTotalVenta(Double.valueOf(((JSONObject) ob).get("totalVenta").toString()));
			ventaDTO.setValorVenta(Double.valueOf(((JSONObject) ob).get("valorVenta").toString()));

			UsuariosDTO usuario = new UsuariosDTO();
			usuario.setCedula(Integer.parseInt(((JSONObject)((JSONObject) ob).get("usuario")).get("cedulaUsuario").toString()));
			ventaDTO.setUsuario(usuario);
			
			ClienteDTO cliente = new ClienteDTO();
			usuario.setCedula(Integer.parseInt(((JSONObject)((JSONObject) ob).get("cliente")).get("cedulaCliente").toString()));
			ventaDTO.setCliente(cliente);
			
			
			JSONArray detallesVenta = (JSONArray) jsonParser.parse(((JSONObject) ob).get("detalleVentas").toString());			
			
			lisDetalleVenta = new ArrayList<DetalleVentaDTO>();
			for(Object obj : detallesVenta) {				
				detalleVentaDTO = new DetalleVentaDTO();
				
				detalleVentaDTO.setCodDetalleVenta(Long.parseLong(((JSONObject) obj).get("codigoDetalleVenta").toString()));
				detalleVentaDTO.setCantProducto(Integer.parseInt(((JSONObject) obj).get("cantidadProducto").toString()));
				detalleVentaDTO.setValorIva(Double.valueOf(((JSONObject) obj).get("valorIva").toString()));
				detalleVentaDTO.setValorTotal(Double.valueOf(((JSONObject) obj).get("valorTotal").toString()));
				detalleVentaDTO.setValorVenta(Double.valueOf(((JSONObject) obj).get("valorVenta").toString()));
				
				ProductosDTO ProductosDTO = new ProductosDTO();
				ProductosDTO.setCodigoProducto(Integer.parseInt(((JSONObject) obj).get("codigoProducto").toString()));
				detalleVentaDTO.setProducto(ProductosDTO);
				
				lisDetalleVenta.add(detalleVentaDTO);
			}
			
			ventaDTO.setDetalleVentas(lisDetalleVenta);
			
			lista.add(ventaDTO);
		}
		return lista;
	}

	public Object parsingVenta(String json) throws Exception {
		JSONParser jsonParser = new JSONParser();		
		DetalleVentaDTO detalleVentaDTO = null;
		ArrayList<DetalleVentaDTO> lisDetalleVenta = null;
		VentaDTO ventaDTO= null;		
		JSONObject innerObj = (JSONObject) jsonParser.parse(json);
		
		if(innerObj != null && !innerObj.isEmpty()) {
		
			ventaDTO= new VentaDTO();
			ventaDTO.setCodVenta(Long.parseLong(innerObj.get("codigoVenta").toString()));
			ventaDTO.setIvaVenta(Double.valueOf(innerObj.get("ivaVenta").toString()));
			ventaDTO.setTotalVenta(Double.valueOf(innerObj.get("totalVenta").toString()));
			ventaDTO.setValorVenta(Double.valueOf(innerObj.get("valorVenta").toString()));

			UsuariosDTO usuario = new UsuariosDTO();
			usuario.setCedula(Integer.parseInt(((JSONObject)innerObj.get("usuario")).get("cedulaUsuario").toString()));
			ventaDTO.setUsuario(usuario);
			
			ClienteDTO cliente = new ClienteDTO();
			usuario.setCedula(Integer.parseInt(((JSONObject)innerObj.get("cliente")).get("cedulaCliente").toString()));
			ventaDTO.setCliente(cliente);
			
			
			JSONArray detallesVenta = (JSONArray) jsonParser.parse(innerObj.get("detalleVentas").toString());			
			
			lisDetalleVenta = new ArrayList<DetalleVentaDTO>();
			
			for(Object obj : detallesVenta) {
				
				detalleVentaDTO = new DetalleVentaDTO();
				
				detalleVentaDTO.setCodDetalleVenta(Long.parseLong(((JSONObject) obj).get("codigoDetalleVenta").toString()));
				detalleVentaDTO.setCantProducto(Integer.parseInt(((JSONObject) obj).get("cantidadProducto").toString()));
				detalleVentaDTO.setValorIva(Double.valueOf(((JSONObject) obj).get("valorIva").toString()));
				detalleVentaDTO.setValorTotal(Double.valueOf(((JSONObject) obj).get("valorTotal").toString()));
				detalleVentaDTO.setValorVenta(Double.valueOf(((JSONObject) obj).get("valorVenta").toString()));
				
				ProductosDTO ProductosDTO = new ProductosDTO();
				ProductosDTO.setCodigoProducto(Integer.parseInt(((JSONObject) obj).get("codigoProducto").toString()));
				detalleVentaDTO.setProducto(ProductosDTO);
				
				lisDetalleVenta.add(detalleVentaDTO);
			}
			
			ventaDTO.setDetalleVentas(lisDetalleVenta);
		}
		return ventaDTO;
	}

	public ArrayList<Object> listar() throws Exception {
		ArrayList<Object> lista = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("ventas/listar");
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
			lista = parsingVentas(obtenerJson(httpCon.getInputStream()));

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return lista;
	}
	
	

	public Object buscarById(long id) throws Exception {
		Object obj = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("ventas/buscar/"+id);
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
			String json = obtenerJson(httpCon.getInputStream());
			if(json!=null && !json.isEmpty()) {
				obj = parsingVenta(json);
			}		

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return obj;
	}

	public void crear(Object venta) throws Exception {

		try {
			if (buscarById(((VentaDTO)venta).getCodVenta()) == null) {
				httpCon = (HttpURLConnection) ConnectionRest.getConnection("ventas/crear");
				httpCon.setRequestMethod("POST");
				httpCon.setDoOutput(true);
				httpCon.setRequestProperty("Accept", "application/json");
				httpCon.setRequestProperty("Content-Type", "application/json");
				httpCon.setRequestProperty("Authorization", authStr);
				
				System.err.println("La Venta::>"+(((VentaDTO) venta).toString()));
				String data = ((VentaDTO) venta).toString();
				byte[] out = data.getBytes(StandardCharsets.UTF_8);
				OutputStream stream = httpCon.getOutputStream();
				stream.write(out);
				if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
					System.err.println("Error al crear la Venta::>" + httpCon.getResponseCode() + "<::>"
							+ httpCon.getResponseMessage());
					throw new Exception("Error al crear la Venta::>" + httpCon.getResponseCode());
				}
			}else {
				System.err.println("Venta ya existe::>");
				throw new Exception("Venta ya existe::>");
			}

		} catch (Exception e) {
			System.err.println("Error al crear la Venta::>" + e.getMessage());
			throw new Exception("Error al crear la Venta");
		} finally {
			ConnectionRest.close();
		}
	}

	public void actualizar(Object venta) throws Exception {

		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("ventas/actualizar");
			httpCon.setRequestMethod("PUT");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);

			String data = ((VentaDTO) venta).toString();
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al actualizar la venta::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al actualizar la venta::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al crear la venta::>" + e.getMessage());
			throw new Exception("Error al crear la venta");
		} finally {
			ConnectionRest.close();
		}
	}
	
	
	public void eliminar(Object venta) throws Exception {

		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("ventas/eliminar");
			httpCon.setRequestMethod("DELETE");
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
			
			String data = ((VentaDTO) venta).toString();
			byte[] out = data.getBytes(StandardCharsets.UTF_8);
			OutputStream stream = httpCon.getOutputStream();
			stream.write(out);
			if (httpCon.getResponseCode() != HttpURLConnection.HTTP_OK) {
				System.err.println("Error al eliminar la venta::>" + httpCon.getResponseCode() + "<::>"
						+ httpCon.getResponseMessage());
				throw new Exception("Error al eliminar la venta::>" + httpCon.getResponseCode());
			}

		} catch (Exception e) {
			System.err.println("Error al crear la venta::>" + e.getMessage());
			throw new Exception("Error al crear la venta");
		} finally {
			ConnectionRest.close();
		}
	}	
	
	public ArrayList<Object> parsingReporte(String json) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		ArrayList<Object> lista = new ArrayList<Object>();
		JSONArray report = (JSONArray) jsonParser.parse(json);
		
		for(Object ob : report) {			
			ReporteVentasDTO reporte = new ReporteVentasDTO();
			reporte.setCedulaCliente(Integer.parseInt(((JSONObject) ob).get("cedulaCliente").toString()));
			reporte.setNombreCliente(((JSONObject) ob).get("nombreCliente").toString());
			reporte.setTotalVenta(Double.valueOf(((JSONObject) ob).get("totalVenta").toString()));
			lista.add(reporte);
		}
		return lista;
	}
	
	public ArrayList<Object> listarReporte() throws Exception {
		ArrayList<Object> lista = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("ventas/listarReporte");
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
			
			lista = parsingReporte(obtenerJson(httpCon.getInputStream()));

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return lista;
	}
	
	public Long parsingConsecutivo(String json) throws ParseException {
		Long cod = null;
		JSONParser jsonParser = new JSONParser();
		JSONObject innerObj = (JSONObject) jsonParser.parse(json);
		
		if(innerObj != null && !innerObj.isEmpty()) {					
			cod = Long.parseLong(innerObj.get("id").toString());
		}
		return cod;
	}
	
	public Long obtenerConsecutivo() throws Exception {
		Long cod = null;
		try {
			httpCon = (HttpURLConnection) ConnectionRest.getConnection("ventas/buscarSigCod");
			httpCon.setRequestMethod("GET");
			httpCon.setRequestProperty("Accept", "application/json");
			httpCon.setRequestProperty("Authorization", authStr);
			
			String json =obtenerJson(httpCon.getInputStream());
			if(json!=null && !json.isEmpty()) {
				cod = parsingConsecutivo(json);
			}		

		} catch (Exception e) {
			System.err.println("No hay resultados para esa consulta::>" + e.getMessage());
			throw new Exception("No hay resultados para esa consulta");
		} finally {
			ConnectionRest.close();
		}

		return cod;
	}
	
	
}
