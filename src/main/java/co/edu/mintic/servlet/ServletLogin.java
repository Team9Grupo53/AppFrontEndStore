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
 * Servlet implementation class ServletLogin
 */
@WebServlet("/Login")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioCtrl ctrl;
	RequestDispatcher rd = null;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletLogin() {
		super();
		ctrl = new UsuarioCtrl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		rd = request.getRequestDispatcher("./");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		try {
			String usuario = request.getParameter("usuario");
			String pass = request.getParameter("pass");
			UsuariosDTO usr = (UsuariosDTO) ctrl.loginUsuario(usuario, pass);
			if ( usr != null) {
				request.getSession().setAttribute("usrLog", usr);
				rd = request.getRequestDispatcher("principal.jsp");				
			} else {
				request.setAttribute("msnErr", "Usuario/Password Erroneo!");
				rd = request.getRequestDispatcher("./");
			}
			rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// response.sendRedirect(request.getContextPath() + "/index.html");
	}

}
