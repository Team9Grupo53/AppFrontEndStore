package co.edu.mintic.servlet;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.mintic.controlador.ProveedorCtrl;
import co.edu.mintic.dto.ProveedoresDTO;
import co.edu.mintic.dto.UsuariosDTO;

@WebServlet("/AdminProveedores")
public class ServletProveedores extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProveedorCtrl ctrl;

	public ServletProveedores() {
		super();
		ctrl = new ProveedorCtrl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("proveedores/frontProveedores.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		ProveedoresDTO pro = null;
		if (request.getParameter("Listar") != null) {

			try {
				request.setAttribute("listaProveedor", ctrl.listar(((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken()));
			} catch (Exception e) {
				System.out.println("Error en listando los proveedores:>" + e.getMessage());
				request.setAttribute("msnErr", "Error en listando de los proveedores");
			}

		} else {
			if (request.getParameter("Buscar") != null) {
				try {
					if (request.getParameter("nit") != null && !request.getParameter("nit").equals("")) {
						pro = obtenerDatosForm(request);
						pro = (ProveedoresDTO) ctrl.buscarById(pro.getNit(),((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());
						System.out.println("pro:>" + pro);
						if (pro != null) {
							request.setAttribute("pro", pro);
						} else {
							request.setAttribute("msnErr", "No se encontraron datos de usuario para el nit ingresada");
						}
					} else {
						request.setAttribute("msnErr", "El nit del usuario es requerido");
					}

				} catch (Exception e) {
					System.out.println("Error listando los proveedores:>" + e.getMessage());
					request.setAttribute("msnErr", "Error listando los proveedores");
				}

			} else if (request.getParameter("Agregar") != null) {
				if (valiadarObligatorio(request)) {
					pro = obtenerDatosForm(request);
					try {
						ctrl.crear(pro);
						request.setAttribute("msnOK", "Proveedor creado con exito");
					} catch (Exception e) {
						System.out.println("Error agregando proveedor:>" + e.getMessage());
						request.setAttribute("msnErr", "Error agregando los proveedores");
					}

				} else {
					request.setAttribute("msnErr", "Debe gestionar todos los campos");
				}
			} else if (request.getParameter("Modificar") != null) {
				if (valiadarObligatorio(request)) {
					pro = obtenerDatosForm(request);
					try {
						ProveedoresDTO pro2 = (ProveedoresDTO) ctrl.buscarById(pro.getNit(),((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());
						if (pro2 != null) {
							pro.setId(pro2.getId());
							ctrl.actualizar(pro);
							request.setAttribute("msnOK", "Proveedor modificado con Exito");
						} else {
							request.setAttribute("msnErr", "El proveedor a actualizar no existe");
						}

					} catch (Exception e) {
						System.out.println("Error modificado los proveedores:>" + e.getMessage());
						request.setAttribute("msnErr", "Error modificado los proveedores");
					}
				} else {
					request.setAttribute("msnErr", "Debe gestionar todos los campos");
				}
			} else if (request.getParameter("Eliminar") != null) {
				if (request.getParameter("nit") != null && !request.getParameter("nit").equals("")) {
					pro = obtenerDatosForm(request);
					try {
						pro = (ProveedoresDTO) ctrl.buscarById(pro.getNit(),((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());
						if (pro != null) {
							pro.setToken(((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());
							ctrl.eliminar(pro);
							request.setAttribute("msnOK", "Proveedor eliminado con exito");
						} else {
							request.setAttribute("msnErr", "El proveedor a eliminar no existe");
						}

					} catch (Exception e) {
						System.out.println("Error eliminando los proveedores:>" + e.getMessage());
						request.setAttribute("msnErr", "Error eliminando los proveedores");
					}
				} else {
					request.setAttribute("msnErr", "El nit del proveedor es requerido");
				}
			}
		}

		rd = request.getRequestDispatcher("proveedores/frontProveedores.jsp");
		rd.forward(request, response);
	}

	private boolean valiadarObligatorio(HttpServletRequest request) {
		if (request.getParameter("nit") != null && !request.getParameter("nit").equals("")
				&& request.getParameter("ciudad") != null && !request.getParameter("ciudad").equals("")
				&& request.getParameter("direccion") != null && !request.getParameter("direccion").equals("")
				&& request.getParameter("nombre") != null && !request.getParameter("nombre").equals("")
				&& request.getParameter("telefono") != null && !request.getParameter("telefono").equals("")) {
			return true;
		} else {
			return false;
		}

	}

	private ProveedoresDTO obtenerDatosForm(HttpServletRequest request) {
		ProveedoresDTO pro = new ProveedoresDTO();
		pro.setNit(Integer.parseInt((String) request.getParameter("nit")));
		pro.setCiudad((String) request.getParameter("ciudad"));
		pro.setDireccion((String) request.getParameter("direccion"));
		pro.setNombre((String) request.getParameter("nombre"));
		pro.setTelefono((String) request.getParameter("telefono"));
		pro.setToken(((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken());
		return pro;
	}

}
