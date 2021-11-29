package co.edu.mintic.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.mintic.controlador.UsuarioCtrl;
import co.edu.mintic.dto.UsuariosDTO;

/**
 * Servlet implementation class usuarios
 */
@WebServlet("/AdminUsuarios")
public class ServletUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioCtrl ctrl;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletUsuarios() {
		super();
		ctrl = new UsuarioCtrl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher rd = null;
		if(request.getSession().getAttribute("usrLog") != null) {
			rd = request.getRequestDispatcher("usuarios/frontUsuario.jsp");
		}else {
			request.setAttribute("msnErr", "Debe realizar el Login!");
			rd = request.getRequestDispatcher("./Login");
		}
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		UsuariosDTO usr = null;
		if (request.getParameter("Listar") != null) {

			try {
				request.setAttribute("listaUsuario", ctrl.listar());
			} catch (Exception e) {
				System.out.println("Error listando los Usuarios::>" + e.getMessage());
				request.setAttribute("msnErr", "Error listando los Usuarios");
			}

		} else {
			if (request.getParameter("Buscar") != null) {
				try {
					if (request.getParameter("cedula") != null && !request.getParameter("cedula").equals("")) {
						usr = obtenerDatosForm(request);
						usr = (UsuariosDTO) ctrl.buscarById(usr.getCedula());
						System.out.println("usr:::>"+usr);
						if (usr != null) {
							request.setAttribute("usr", usr);
						}else {
							request.setAttribute("msnErr", "No se encontraron datos de usuario para la cedula ingresada");
						}
					} else {
						request.setAttribute("msnErr", "La cedula del usuario es requerida");
					}

				} catch (Exception e) {
					System.out.println("Error buscando los Usuarios::>" + e.getMessage());
					request.setAttribute("msnErr", "Error buscando los Usuarios");
				}

			} else if (request.getParameter("Agregar") != null) {
				if (valiadarObligatorio(request)) {
					usr = obtenerDatosForm(request);
					try {
						ctrl.crear(usr);
						request.setAttribute("msnOK", "Usuario Creado con Exito");
					} catch (Exception e) {
						System.out.println("Error agregando los Usuarios::>" + e.getMessage());
						request.setAttribute("msnErr", "Error agregando los Usuarios");
					}

				} else {
					request.setAttribute("msnErr", "Debe gestionar todos los campos");
				}
			} else if (request.getParameter("Modificar") != null) {
				if (valiadarObligatorio(request)) {
					usr = obtenerDatosForm(request);
					try {

						if (ctrl.buscarById(usr.getCedula()) != null) {
							ctrl.actualizar(usr);
							request.setAttribute("msnOK", "Usuario Modificado con Exito");
						} else {
							request.setAttribute("msnErr", "El usuario a actualizar no existe");
						}

					} catch (Exception e) {
						System.out.println("Error Modificado los Usuarios::>" + e.getMessage());
						request.setAttribute("msnErr", "Error Modificado los Usuarios");
					}
				} else {
					request.setAttribute("msnErr", "Debe gestionar todos los campos");
				}
			} else if (request.getParameter("Eliminar") != null) {
				if (request.getParameter("cedula") != null && !request.getParameter("cedula").equals("")) {
					usr = obtenerDatosForm(request);
					try {

						if (ctrl.buscarById(usr.getCedula()) != null) {
							ctrl.eliminar(usr);
							request.setAttribute("msnOK", "Usuario Eliminado con Exito");
						} else {
							request.setAttribute("msnErr", "El usuario a eliminado no existe");
						}

					} catch (Exception e) {
						System.out.println("Error eliminando los Usuarios::>" + e.getMessage());
						request.setAttribute("msnErr", "Error eliminando los Usuarios");
					}
				} else {
					request.setAttribute("msnErr", "La cedula del usuario es requerida");
				}
			}
		}

		rd = request.getRequestDispatcher("usuarios/frontUsuario.jsp");
		rd.forward(request, response);
	}

	private boolean valiadarObligatorio(HttpServletRequest request) {
		if (request.getParameter("cedula") != null && !request.getParameter("cedula").equals("")
				&& request.getParameter("email") != null && !request.getParameter("email").equals("")
				&& request.getParameter("nombre") != null && !request.getParameter("nombre").equals("")
				&& request.getParameter("passAdd") != null && !request.getParameter("passAdd").equals("")
				&& request.getParameter("userlog") != null && !request.getParameter("userlog").equals("")) {
			return true;
		} else {
			return false;
		}

	}

	private UsuariosDTO obtenerDatosForm(HttpServletRequest request) {
		UsuariosDTO usr = new UsuariosDTO();
		usr.setCedula(Integer.parseInt((String) request.getParameter("cedula")));
		usr.setEmail((String) request.getParameter("email"));
		usr.setNombre((String) request.getParameter("nombre"));
		usr.setPassword((String) request.getParameter("passAdd"));
		usr.setUsuario((String) request.getParameter("userlog"));
		return usr;
	}

}
