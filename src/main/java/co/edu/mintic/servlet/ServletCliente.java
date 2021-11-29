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

@WebServlet("/AdminClientes")
public class ServletCliente extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ClienteCtrl ctrl;

	public ServletCliente() {
		super();
		ctrl = new ClienteCtrl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("cliente/frontCliente.jsp");
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

		} else {
			if (request.getParameter("Buscar") != null) {
				try {
					if (request.getParameter("cedula") != null && !request.getParameter("cedula").equals("")) {
						cli = obtenerDatosForm(request);
						cli = (ClienteDTO) ctrl.buscarById(cli.getCedula());
						System.out.println("cli:>" + cli);
						if (cli != null) {
							request.setAttribute("cli", cli);
						} else {
							request.setAttribute("msnErr", "No se encontraron datos de Cliente para la cedula ingresada");
						}
					} else {
						request.setAttribute("msnErr", "La cedula del Cliente es requerida");
					}

				} catch (Exception e) {
					System.out.println("Error listando los Clientes:>" + e.getMessage());
					request.setAttribute("msnErr", "Error listando los Clientes");
				}

			} else if (request.getParameter("Agregar") != null) {
				if (valiadarObligatorio(request)) {
					cli = obtenerDatosForm(request);
					try {
						ctrl.crear(cli);
						request.setAttribute("msnOK", "Cliente creado con exito");
					} catch (Exception e) {
						System.out.println("Error agregando Cliente:>" + e.getMessage());
						request.setAttribute("msnErr", "Error agregando los Clientes");
					}

				} else {
					request.setAttribute("msnErr", "Debe gestionar todos los campos");
				}
			} else if (request.getParameter("Modificar") != null) {
				if (valiadarObligatorio(request)) {
					cli = obtenerDatosForm(request);
					try {

						if (ctrl.buscarById(cli.getCedula()) != null) {
							ctrl.actualizar(cli);
							request.setAttribute("msnOK", "Cliente modificado con Exito");
						} else {
							request.setAttribute("msnErr", "El Cliente a actualizar no existe");
						}

					} catch (Exception e) {
						System.out.println("Error modificado los Clientes:>" + e.getMessage());
						request.setAttribute("msnErr", "Error modificado los Clientes");
					}
				} else {
					request.setAttribute("msnErr", "Debe gestionar todos los campos");
				}
			} else if (request.getParameter("Eliminar") != null) {
				if (request.getParameter("cedula") != null && !request.getParameter("cedula").equals("")) {
					cli = obtenerDatosForm(request);
					try {

						if (ctrl.buscarById(cli.getCedula()) != null) {
							ctrl.eliminar(cli);
							request.setAttribute("msnOK", "Cliente eliminado con exito");
						} else {
							request.setAttribute("msnErr", "El Cliente a eliminar no existe");
						}

					} catch (Exception e) {
						System.out.println("Error eliminando los Clientes:>" + e.getMessage());
						request.setAttribute("msnErr", "Error eliminando los Clientes");
					}
				} else {
					request.setAttribute("msnErr", "La cedula del Cliente es requerida");
				}
			}
		}

		rd = request.getRequestDispatcher("cliente/frontCliente.jsp");
		rd.forward(request, response);
	}

	private boolean valiadarObligatorio(HttpServletRequest request) {
		if (request.getParameter("cedula") != null && !request.getParameter("cedula").equals("")
				&& request.getParameter("direccion") != null && !request.getParameter("direccion").equals("")
				&& request.getParameter("email") != null && !request.getParameter("email").equals("")
				&& request.getParameter("nombre") != null && !request.getParameter("nombre").equals("")
				&& request.getParameter("telefono") != null && !request.getParameter("telefono").equals("")) {
			return true;
		} else {
			return false;
		}

	}

	private ClienteDTO obtenerDatosForm(HttpServletRequest request) {
		ClienteDTO cli = new ClienteDTO();
		cli.setCedula(Integer.parseInt((String) request.getParameter("cedula")));
		cli.setDireccion((String) request.getParameter("direccion"));
		cli.setEmail((String) request.getParameter("email"));
		cli.setNombre((String) request.getParameter("nombre"));
		cli.setTelefono((String) request.getParameter("telefono"));
		return cli;
	}

}
