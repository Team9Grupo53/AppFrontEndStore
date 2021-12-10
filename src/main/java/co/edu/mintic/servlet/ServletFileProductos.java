package co.edu.mintic.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import co.edu.mintic.controlador.ProductosCtrl;
import co.edu.mintic.controlador.ProveedorCtrl;
import co.edu.mintic.dto.ProductosDTO;
import co.edu.mintic.dto.TokenDTO;
import co.edu.mintic.dto.UsuariosDTO;

/**
 * Servlet implementation class FilePrueba2
 */
@WebServlet("/AdminFileProductos")
@MultipartConfig
public class ServletFileProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductosCtrl ctrl;
	private ProveedorCtrl ctrlProve;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletFileProductos() {
		super();
		ctrl = new ProductosCtrl();
		ctrlProve = new ProveedorCtrl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("productos/frontFileProductos.jsp");
		rd.forward(request, response);
	}

	private boolean validateCSV(String nameFile) {
		return nameFile.toUpperCase().endsWith("CSV");
	}

	private boolean validateNitProveedor(String nitProveedor, TokenDTO tk) {
		try {
			if (ctrlProve.buscarById(Integer.parseInt(nitProveedor),tk) != null) {
				return true;
			}
		} catch (Exception e) {
			System.err.println("Error al validar el nit del proveedor" + e.getMessage());
		}
		return false;
	}

	private void procesar(HttpServletRequest request, String line) throws Exception {
		String[] campos = null;

		campos = line.split(",");
		
		System.out.println("Campos::>"+campos[2]);

		if (validateNitProveedor(campos[2],((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken())) {
			try {
				System.out.println("Campos IIIIIIF::>"+campos[0]);
				ProductosDTO prod = new ProductosDTO();
				prod.setCodigoProducto(Integer.parseInt(campos[0]));
				prod.setNombreProducto(campos[1]);
				prod.setNitProveedor(Integer.parseInt(campos[2]));
				prod.setPrecioCompra(Double.parseDouble(campos[3]));
				prod.setIvaCompra(Double.parseDouble(campos[4]));
				prod.setPrecioVenta(Double.parseDouble(campos[5]));	
				prod.setToken(((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());
				ctrl.crear(prod);
				
			} catch (Exception e) {
				System.err.println("Error al procesar las lineas del archivo::>" + e.getMessage());
				throw new Exception("Error al procesar las lineas del archivo");
			}
		} else {			
			throw new Exception("El nit del proveedor no existe");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = null;

		String str = "";
		Part filePart = request.getPart("archivo");
		
		if (filePart != null && filePart.getSize() > 0) {

			if (validateCSV(filePart.getSubmittedFileName())) {

				InputStream fileContent = filePart.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(fileContent));

				while ((str = reader.readLine()) != null) {
					try {
						if(str!=null && !str.isEmpty()) {
							procesar(request, str);
							request.setAttribute("msnOK", "Se cargo con exito el archivo");
						}						
					} catch (Exception e) {
						request.setAttribute("msnErr", e.getMessage());
						break;
					}
				}

			} else {
				request.setAttribute("msnErr", "El archivo debe ser un CSV");
			}

		} else {
			request.setAttribute("msnErr", "Debe seleccionar un archivo");
		}

		rd = request.getRequestDispatcher("productos/frontFileProductos.jsp");
		rd.forward(request, response);
	}

}
