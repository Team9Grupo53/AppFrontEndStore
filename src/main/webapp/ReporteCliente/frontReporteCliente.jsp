<%@page import="java.util.ArrayList"%>
<%@page import="co.edu.mintic.dto.ClienteDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Reporte de Clientes</title>
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
</head>
<body>
<header class="bg-dark text-light">
		<jsp:include page="../menu.jsp"></jsp:include>
		<div class="card-title bg-dark text-light text-center">
							<h5>Reporte de Clientes</h5>
						</div>
	</header>
	<main class="container-fluid">
		<div class="row mt-2">
			<div class="col-lg-5 col-md-6 col-12">
				<form action="./ReporteClientes" method="post">
					<div class="card">
						<div class="card-header bg-dark bg-gradient text-light">
							<span>Formulario Reporte de Clientes</span>
						</div>						
						
						<div class="card-footer">
							<button type="submit" class="btn btn-outline-dark" name="Listar">Listar</button>
						</div>
					</div>
		       </form>
			</div>
			<div class="col-lg col-md col">
				<%
				if (request.getAttribute("listaCliente") != null) {
				%>
				<div class="card">
					<div class="card-header bg-dark bg-gradient text-light">
						<span>Reporte de Clientes</span>
					</div>
					<div class="card-body">
						<table id="TablaPresentacion"
							class="table table-hover table-striped">
							<thead>
								<tr>
									<td>Cedula</td>
									<td>Direccion</td>
									<td>Correo</td>
									<td>Nombre</td>
									<td>Telefono</td>
								</tr>
							</thead>
							<tbody>
								<%
								for (ClienteDTO cli : (ArrayList<ClienteDTO>) request.getAttribute("listaCliente")) {
								%>
								<tr>
									<td><%=cli.getCedula()%></td>
									<td><%=cli.getDireccion()%></td>
									<td><%=cli.getEmail()%></td>
									<td><%=cli.getNombre()%></td>
									<td><%=cli.getTelefono()%></td>
								</tr>
								<%
								}
								%>
							</tbody>
						</table>

					</div>
					<!-- <div class="card-footer"></div> -->
					<%
					}
					%>

					<%
					if (request.getAttribute("msnOK") != null) {
					%>
					<div class="card">
						<div class="card-header bg-dark bg-gradient text-light">
							<span>Mensaje del Sistema</span>
						</div>
						<div class="card-body">
							<div class="alert alert-dismissible alert-success">
								<strong><%=request.getAttribute("msnOK")%></strong>
								<!-- <p id="msnOK" style="color: green;"></p> -->
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
								<strong><%=request.getAttribute("msnErr")%></strong>
								<!-- <p id="msnErr" style="color: red;"></p> -->
							</div>
						</div>
					</div>
					<%
					}
					%>
				</div>
			</div>
		</div>
	</main>
</body>
</html>