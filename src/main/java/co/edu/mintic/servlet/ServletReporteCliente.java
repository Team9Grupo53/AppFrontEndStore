package co.edu.mintic.servlet;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.mintic.controlador.ClienteCtrl;
import co.edu.mintic.dto.ClienteDTO;

@WebServlet("/ReporteClientes")
public class ServletReporteCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteCtrl ctrl;

	public ServletReporteCliente() {
		super();
		ctrl = new ClienteCtrl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("ReporteCliente/frontReporteCliente.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		ClienteDTO cli = null;
		if (request.getParameter("Listar") != null) {

			try {
				request.setAttribute("listaCliente", ctrl.listar());
			} catch (Exception e) {
				System.out.println("Error en listando los Clientes:>" + e.getMessage());
				request.setAttribute("msnErr", "Error en listando de los Clientes");
			}

		}

		rd = request.getRequestDispatcher("ReporteCliente/frontReporteCliente.jsp");
		rd.forward(request, response);
	}

}
