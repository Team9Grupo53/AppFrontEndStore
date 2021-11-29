package co.edu.mintic.servlet;

import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.mintic.mediador.VentaMed;
import co.edu.mintic.dto.ReporteVentasDTO;

@WebServlet("/AdminReporteVentas")
public class ServletReporteVentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VentaMed med;

	public ServletReporteVentas() {
		super();
		med = new VentaMed();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		if(request.getSession().getAttribute("usrLog") != null) {
			rd = request.getRequestDispatcher("reporteVentas/frontReporteVentas.jsp");
		}else {
			request.setAttribute("msnErr", "Debe realizar el Login!");
			rd = request.getRequestDispatcher("./Login");
		}
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		ReporteVentasDTO ven = null;
		if (request.getParameter("Listar") != null) {

			try {
				request.setAttribute("listaReporteVentas", med.listarReporte());
			} catch (Exception e) {
				System.out.println("Error listando las ventas de los clientes:>" + e.getMessage());
				request.setAttribute("msnErr", "Error listando las ventas de los clientes");
			}

		} 

		rd = request.getRequestDispatcher("reporteVentas/frontReporteVentas.jsp");
		rd.forward(request, response);
	}

	

}
