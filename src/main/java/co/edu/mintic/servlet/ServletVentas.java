package co.edu.mintic.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import co.edu.mintic.controlador.ClienteCtrl;
import co.edu.mintic.controlador.ProductosCtrl;
import co.edu.mintic.controlador.VentaCtrl;
import co.edu.mintic.dto.ClienteDTO;
import co.edu.mintic.dto.DetalleVentaDTO;
import co.edu.mintic.dto.ProductosDTO;
import co.edu.mintic.dto.UsuariosDTO;
import co.edu.mintic.dto.VentaDTO;

/**
 * Servlet implementation class usuarios
 */
@WebServlet("/AdminVentas")
public class ServletVentas extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VentaCtrl ctrl;
	private ProductosCtrl ctrlP;
	private ClienteCtrl ctrlC;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletVentas() {
		super();
		ctrl = new VentaCtrl();
		ctrlP = new ProductosCtrl();
		ctrlC = new ClienteCtrl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = null;
		if (request.getSession().getAttribute("usrLog") != null) {
			try {
				request.getSession().setAttribute("ven", inicializarVenta(request));
			} catch (Exception e) {
				System.err.println("Error cargando la session del vendedor");
				request.setAttribute("msnErr", "Error cargando la session del vendedor");
			}
			rd = request.getRequestDispatcher("ventas/frontVenta.jsp");
		} else {
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
		VentaDTO ven = (VentaDTO) request.getSession().getAttribute("ven");
		String action = request.getParameter("Action");
		
		try {
			obtenerDatosForm(request);
		} catch (Exception e) {
			System.err.println("Error al obtener los datos del formulario::>" + e.getMessage());
			request.setAttribute("msnErr", e.getMessage());
		}

		if (action != null && action.equals("confirmar")) {
			try {
				valiadarObligatorio(request);
				ctrl.crear(ven);
				request.getSession().setAttribute("ven", inicializarVenta(request));
				request.setAttribute("msnOK", "Venta Registrada con Exito");
			} catch (Exception e) {
				System.err.println("Error al obtener los datos del formulario::>" + e.getMessage());
				request.setAttribute("msnErr", e.getMessage());
			}
		}

		rd = request.getRequestDispatcher("ventas/frontVenta.jsp");
		rd.forward(request, response);
	}

	private void valiadarObligatorio(HttpServletRequest request) throws Exception {
		String sms = "";
		VentaDTO ven = (VentaDTO) request.getSession().getAttribute("ven");
		if (ven.getCliente() == null || ven.getCliente().getNombre() == null
				|| ven.getCliente().getNombre().isEmpty()) {
			sms = "El cliente es requerido";
		} else {
			if (ven.getDetalleVentas() != null) {
				for (int i = 0; i < ven.getDetalleVentas().size(); i++) {
					if (ven.getDetalleVentas().get(i).getProducto() == null) {
						sms += "fila " + (i + 1) + ", ";
					}
				}
				if (!sms.isEmpty()) {
					sms = "El producto " + sms + " es requerido para la venta\n ";
				}
				
				for (int i = 0; i < ven.getDetalleVentas().size(); i++) {
					if (ven.getDetalleVentas().get(i).getProducto() == null) {
						sms += "fila " + (i + 1) + ", ";
					}
				}
				if (!sms.isEmpty()) {
					sms = "El producto " + sms + " es requerido para la venta\n ";
				}
			} else {
				sms = "La venta debe contener detalle de productos\n";
			}
		}
		if (!sms.isEmpty()) {
			throw new Exception(sms);
		}

	}

	private VentaDTO inicializarVenta(HttpServletRequest request) throws Exception {
		VentaDTO ven = null;
		try {
			ArrayList<DetalleVentaDTO> listDetalle = new ArrayList<DetalleVentaDTO>();
			listDetalle.add(new DetalleVentaDTO());
			listDetalle.add(new DetalleVentaDTO());
			listDetalle.add(new DetalleVentaDTO());
			ven = new VentaDTO(ctrl.obtenerConsecutivo(), listDetalle,
					(UsuariosDTO) request.getSession().getAttribute("usrLog"));
		} catch (Exception e) {
			throw new Exception("Error al buscar el consecutivo de la venta");
		}
		return ven;
	}

	private VentaDTO obtenerDatosForm(HttpServletRequest request) throws Exception {

		String action = request.getParameter("Action");

		VentaDTO ven = (VentaDTO) request.getSession().getAttribute("ven");
		if (ven == null) {
			ven = inicializarVenta(request);
		}

		if (action != null && action.equals("BuscarCliente")) {
			if (request.getParameter("cedulaCliente") != null
					&& !((String) request.getParameter("cedulaCliente")).isEmpty()) {
				try {
					ven.setCliente((ClienteDTO) ctrlC
							.buscarById(Integer.parseInt((String) request.getParameter("cedulaCliente")),((UsuariosDTO)request.getSession().getAttribute("usrLog")).getToken()));
				} catch (Exception e) {
					throw new Exception("Error al buscar el cliente");
				}

			} else {
				throw new Exception("La cedula del cliente es requerida");
			}
		}

		if (action != null && action.startsWith("BuscarProducto")) {
			DetalleVentaDTO detalleVenta = null;

			int i = Integer.parseInt(action.substring(action.length() - 1));

			detalleVenta = ven.getDetalleVentas().get(i);
			detalleVenta.setCodVenta(ven.getCodVenta());

			String codProdS = (String) request.getParameter("codProducto" + i);
			String canProducto = (String) request.getParameter("canProducto" + i);

			if (codProdS != null && !codProdS.isEmpty()) {

				ProductosDTO productosDTO;
				try {
					productosDTO = (ProductosDTO) ctrlP.buscarById(Integer.parseInt(codProdS));
					detalleVenta.setProducto(productosDTO);
				} catch (Exception e) {
					throw new Exception("Error al buscar el producto de la fila " + (i + 1));
				}
				ven.getDetalleVentas().set(i, detalleVenta);
			} else {
				throw new Exception("El codigo del producto de la fila " + (i + 1) + " es requerido!");
			}			
			
			if (canProducto != null && !canProducto.isEmpty() && Integer.parseInt(canProducto) > 0 ) {
				detalleVenta.setCantProducto(Integer.parseInt(canProducto));
				detalleVenta
						.setValorVenta(detalleVenta.getProducto().getPrecioVenta() * detalleVenta.getCantProducto());
				detalleVenta
						.setValorIva((detalleVenta.getProducto().getIvaCompra() * detalleVenta.getValorVenta()) / 100);
				detalleVenta.setValorTotal(detalleVenta.getValorIva() + detalleVenta.getValorVenta());
			} else if( !canProducto.isEmpty() ) {				
				throw new Exception("El la cantidad del producto de la fila " + (i + 1) + " son requeridos y mayor a 0!");
			}
		}
		return calcularTotales(ven);
	}

	private VentaDTO calcularTotales(VentaDTO ven) {
		double valorIvaTotal = 0;
		double valorVenta = 0;
		double totalVenta = 0;

		for (DetalleVentaDTO det : ven.getDetalleVentas()) {
			if (det.getProducto() != null) {
				valorVenta += det.getValorVenta();
				valorIvaTotal += det.getValorIva();
				totalVenta += det.getValorTotal();
			}
		}

		ven.setValorVenta(valorVenta);
		ven.setIvaVenta(valorIvaTotal);
		ven.setTotalVenta(totalVenta);
		return ven;
	}
}
