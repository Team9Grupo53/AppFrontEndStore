package co.edu.mintic.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.mintic.controlador.ProductosCtrl;
import co.edu.mintic.dto.ProductosDTO;
import co.edu.mintic.dto.UsuariosDTO;

/**
 * Servlet implementation class productos
 */
@WebServlet("/AdminProductos")
public class ServletProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductosCtrl ctrl;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletProductos() {
		super();
		ctrl = new ProductosCtrl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("productos/frontProductos.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		ProductosDTO usr = null;
		if (request.getParameter("Listar") != null) {

			try {
				request.setAttribute("listaProducto", ctrl.listar(((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken()));
			} catch (Exception e) {
				System.out.println("Error listando los productos::>" + e.getMessage());
				request.setAttribute("msnErr", "Error listando los productos");
			}

		} else {
			if (request.getParameter("Buscar") != null) {
				try {
					if (request.getParameter("codigoProducto") != null && !request.getParameter("codigoProducto").equals("")) {
						usr = new ProductosDTO ();
						usr.setCodigoProducto(Integer.parseInt(request.getParameter("codigoProducto")));
						usr = (ProductosDTO) ctrl.buscarById(usr.getCodigoProducto(),((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());
						System.out.println("usr:::>"+usr);
						if (usr != null) {
							request.setAttribute("usr", usr);
						}else {
							request.setAttribute("msnErr", "No se encontraron datos del producto para la codigo ingresado");
						}
					} else {
						request.setAttribute("msnErr", "El codigo del producto es requerido");
					}

				} catch (Exception e) {
					System.out.println("Error listando los Productos::>" + e.getMessage());
					request.setAttribute("msnErr", "Error buscando los productos");
				}

			} else if (request.getParameter("Agregar") != null) {
				if (valiadarObligatorio(request)) {
					usr = obtenerDatosForm(request);
					try {
						ctrl.crear(usr);
						request.setAttribute("msnOK", "Producto Creado con Exito");
					} catch (Exception e) {
						System.out.println("Error agregando el producto::>" + e.getMessage());
						request.setAttribute("msnErr", "Error agregando el producto");
					}

				} else {
					request.setAttribute("msnErr", "Debe gestionar todos los campos");
				}
			} else if (request.getParameter("Modificar") != null) {
				if (valiadarObligatorio(request)) {
					usr = obtenerDatosForm(request);
					try {
						ProductosDTO prod = (ProductosDTO) ctrl.buscarById(usr.getCodigoProducto(),((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken()) ;
						if (prod!= null) {
							usr.setId(prod.getId());
							ctrl.actualizar(usr);
							request.setAttribute("msnOK", "Producto Modificado con Exito");
						} else {
							request.setAttribute("msnErr", "El producto a actualizar no existe");
						}

					} catch (Exception e) {
						System.out.println("Error Modificado el producto::>" + e.getMessage());
						request.setAttribute("msnErr", "Error Modificado el producto");
					}
				} else {
					request.setAttribute("msnErr", "Debe gestionar todos los campos");
				}
				
			} else if (request.getParameter("Eliminar") != null) {
				if (request.getParameter("codigoProducto") != null && !request.getParameter("codigoProducto").equals("")) {
					
					try {
						usr =  (ProductosDTO)ctrl.buscarById(Integer.parseInt(request.getParameter("codigoProducto")),((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());

						if (usr != null) {
							usr.setToken(((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());
							ctrl.eliminar(usr);
							request.setAttribute("msnOK", "Producto Eliminado con Exito");
						} else {
							request.setAttribute("msnErr", "El producto  a eliminar no existe");
						}

					} catch (Exception e) {
						System.out.println("Error eliminando el producto::>" + e.getMessage());
						request.setAttribute("msnErr", "Error eliminando el producto");
					}
				} else {
					request.setAttribute("msnErr", "El codigo del producto es requerido");
				}
			}
		}

		rd = request.getRequestDispatcher("productos/frontProductos.jsp");
		rd.forward(request, response);
	}

	private boolean valiadarObligatorio(HttpServletRequest request) {
		if (request.getParameter("codigoProducto") != null && !request.getParameter("codigoProducto").equals("")
				&& request.getParameter("nitProveedor") != null && !request.getParameter("nitProveedor").equals("")
				&& request.getParameter("ivaCompra") != null && !request.getParameter("ivaCompra").equals("")
				&& request.getParameter("nombreProducto") != null && !request.getParameter("nombreProducto").equals("")
				&& request.getParameter("precioCompra") != null && !request.getParameter("precioCompra").equals("")
			    && request.getParameter("precioVenta") != null && !request.getParameter("precioVenta").equals("")){
			return true;
		} else {
			return false;
		}

	}

	private ProductosDTO obtenerDatosForm(HttpServletRequest request) {
		ProductosDTO usr = new ProductosDTO();
		usr.setCodigoProducto(Integer.parseInt((String) request.getParameter("codigoProducto")));
		usr.setNitProveedor (Integer.parseInt((String) request.getParameter("nitProveedor")));
		usr.setIvaCompra(Double.parseDouble((String) request.getParameter("ivaCompra")));
		usr.setNombreProducto((String) request.getParameter("nombreProducto"));
		usr.setPrecioCompra(Double.parseDouble((String) request.getParameter("precioCompra")));
		usr.setPrecioVenta(Double.parseDouble((String) request.getParameter("precioVenta")));
		usr.setToken(((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());
		return usr;
	}

}
