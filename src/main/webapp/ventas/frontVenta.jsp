<%@page import="java.util.ArrayList"%>
<%@page import="co.edu.mintic.dto.VentaDTO"%>
<%@page import="co.edu.mintic.dto.DetalleVentaDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>TIENDA ON-LINE</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU"
	crossorigin="anonymous">

 <script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-/bQdsTh/da6pkI1MST/rWKFNjaCP5gBSY4sEBT38Q/9RBh9AH40zEOg7Hlq2THRZ"
	crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/1b2ad533f3.js" crossorigin="anonymous"></script>

<script type="text/javascript">
	
</script>

</head>
<body>
	<header class="bg-dark text-light">
		<jsp:include page="../menu.jsp"></jsp:include>
		<div class="card-title bg-dark text-light text-center">
			<h5>Gestión de Ventas</h5>
		</div>
	</header>
	<main class="container-fluid">
		<div class="row mt-2">
			<div class="col-lg-6 col-md-7 col-12">
				<form action="./AdminVentas" method="post">
					<div class="card">
						<div class="card-header bg-dark bg-gradient text-light">
							<span>Formulario Gestión de Ventas</span>
						</div>
						<div class="card-body">
							<div class="row">
								<label for="cedula-txt" class="col-sm-4 col-form-label">Consecutivo:</label>
								<div class="col-sm">
									<label for="nombre-txt" class="col-sm-3 col-form-label">${ven.codVenta}</label>
								</div>
							</div>
							<div class="row">
								<label for="email-txt" class="col-sm-4 col-form-label">Cédula
									Cliente:</label>
								<div class="col-sm input-group">
									<input id="email-txt" class="form-control"
										placeholder="Ingrese la Cedula Cliente" type="text"
										name="cedulaCliente" value="${ven.cliente.cedula}">
									<button class="btn btn-outline-dark" type="submit" id="Action"
										name="Action" value="BuscarCliente"><i class="fas fa-search"></i></button>
								</div>
							</div>

							<div class="row">
								<label for="nombre-txt" class="col-sm-4 col-form-label">Nombre
									Cliente:</label>
								<div class="col-sm">
									<label for="nombre-txt" class="col-form-label">${ven.cliente.nombre}</label>
								</div>
							</div>
							<div class="row">
								<table id="TablaPresentacion"
									class="table table-hover table-striped">
									<thead>
										<tr>
											<td>Cod. Producto</td>
											<td>Nombre Producto</td>
											<td>Cant.</td>
											<td>Vlr. Total</td>
										</tr>
									</thead>
									<tbody>
										<%
										if (request.getSession().getAttribute("ven") != null) {
											ArrayList<DetalleVentaDTO> venDet = (ArrayList<DetalleVentaDTO>) ((VentaDTO) request.getSession()
											.getAttribute("ven")).getDetalleVentas();
											System.out.println("venDet::>" + venDet);
											for (int i = 0; i < venDet.size(); i++) {
										%>
										<tr>
											<td><div class="col-sm input-group">
													<input class="form-control"
														placeholder="Ingrese el codigo del producto" type="text"
														name="codProducto<%=i%>"
														value="<%=venDet.get(i).getProducto() != null ? venDet.get(i).getProducto().getCodigoProducto() : ""%>">
													<button class="btn btn-outline-dark" type="submit"
														id="actionBuscar<%=i%>" name="Action"
														value="BuscarProducto<%=i%>"><i class="fas fa-search"></i></button>
												</div></td>
											<td><%=venDet.get(i).getProducto() != null ? venDet.get(i).getProducto().getNombreProducto() : ""%></td>
											<td><input class="form-control"
												placeholder="Ingrese la cantidad" type="number"
												name="canProducto<%=i%>"
												value="<%=venDet.get(i).getCantProducto()>0?venDet.get(i).getCantProducto():""%>"
												onchange="document.getElementById('actionBuscar<%=i%>').click();"></td>
											<td><%=venDet.get(i).getValorTotal()%></td>
										</tr>
										<%
										}
										}
										%>
									</tbody>
									<tfoot>
										<tr>
											<td></td>
											<td></td>
											<td>Total Venta</td>
											<td>${ven.valorVenta}</td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td>Total IVA</td>
											<td>${ven.ivaVenta}</td>
										</tr>
										<tr>
											<td></td>
											<td></td>
											<td>Total con IVA</td>
											<td>${ven.totalVenta}</td>
										</tr>
									</tfoot>
								</table>
							</div>

						</div>
						<div class="card-footer">
							<button type=submit class="btn btn-outline-dark" onclick="return confirm('Estas seguro de registrar la ventas?')" name="Action" value="confirmar">Confirmar</button>
							
						</div>
					</div>
				</form>
			</div>
			<div class="col-lg col-md col">
				<%
				if (request.getAttribute("msnOK") != null) {
				%>
				<div class="card">
					<div class="card-header bg-dark bg-gradient text-light">
						<span>Mensaje del Sistema</span>
					</div>
					<div class="card-body">
						<div class="alert alert-dismissible alert-success">
							<strong>${msnOK}</strong>
						</div>
					</div>
				</div>
				<%
				} else if (request.getAttribute("msnErr") != null) {
				%>
				<div class="card">
					<div class="card-header bg-dark bg-gradient text-light">
						<span>Mensaje del Sistema</span>
					</div>
					<div class="card-body">
						<div class="alert alert-dismissible alert-danger">
							<strong>${msnErr}</strong>
						</div>
					</div>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</main>

</body>
</html>