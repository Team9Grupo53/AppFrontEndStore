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
@WebServlet("/ReporteUsuarios")
public class ServletReporteUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioCtrl ctrl;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletReporteUsuarios() {
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
			rd = request.getRequestDispatcher("ReporteUsuarios/frontReporteUsuario.jsp");
		}else {
			request.setAttribute("msnErr", "Debe realizar el Login!");
			rd = request.getRequestDispatcher("./Login");
		}
		rd.forward(request, response);
	}
	
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

			}

		rd = request.getRequestDispatcher("ReporteUsuarios/frontReporteUsuario.jsp");
		rd.forward(request, response);
	}

	

}
