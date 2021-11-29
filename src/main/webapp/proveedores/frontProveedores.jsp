<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="co.edu.mintic.dto.ProveedoresDTO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestión de Proveedor</title>
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
							<h5>Gestión de Proveedor</h5>
						</div>
	</header>
	<main class="container-fluid">
		<div class="row mt-2">
			<div class="col-lg-5 col-md-6 col-12">
				<form action="./AdminProveedores" method="post">
					<div class="card">
						<div class="card-header bg-dark bg-gradient text-light">
							<span>Formulario Gestión de Proveedor</span>
						</div>
						<div class="card-body">
							<div class="mb-3 row">
								<label for="nit-txt" class="col-sm-3 col-form-label">(*)Nit:</label>
								<div class="col-sm">
									<input id="nit-txt" class="form-control"
										placeholder="Ingrese el nit" type="number" name="nit"
										value="<%=request.getAttribute("pro") != null ? ((ProveedoresDTO) request.getAttribute("pro")).getNit() : ""%>">
								</div>
							</div>
							<div class="mb-3 row">
								<label for="ciudad-txt" class="col-sm-3 col-form-label">Ciudad:</label>
								<div class="col-sm">
									<input id="ciudad-txt" class="form-control"
										placeholder="Ingrese la ciudad" type="text" name="ciudad"
										value="<%=request.getAttribute("pro") != null ? ((ProveedoresDTO) request.getAttribute("pro")).getCiudad() : ""%>">
								</div>
							</div>
							
							<div class="mb-3 row">
								<label for="direccion-txt" class="col-sm-3 col-form-label">Direccion:</label>
								<div class="col-sm">
									<input id="direccion-txt" class="form-control"
										placeholder="Ingrese la direccion" type="text" name="direccion"
										value="<%=request.getAttribute("pro") != null ? ((ProveedoresDTO) request.getAttribute("pro")).getDireccion() : ""%>">
								</div>
							</div>

							<div class="mb-3 row">
								<label for="nombre-txt" class="col-sm-3 col-form-label">Nombre:</label>
								<div class="col-sm">
									<input id="nombre-txt" class="form-control"
										placeholder="Ingrese el Nombre" type="text" name="nombre"
										value="<%=request.getAttribute("pro") != null ? ((ProveedoresDTO) request.getAttribute("pro")).getNombre() : ""%>">
								</div>
							</div>
							
							<div class="mb-3 row">
								<label for="telefono-txt" class="col-sm-3 col-form-label">Telefono:</label>
								<div class="col-sm">
									<input id="telefono-txt" class="form-control"
										placeholder="Ingrese el telefono" type="text" name="telefono"
										value="<%=request.getAttribute("pro") != null ? ((ProveedoresDTO) request.getAttribute("pro")).getTelefono() : ""%>">
								</div>
							</div>

							
							<div style="color: gray;">
								(*) Filtro para Buscar/Actualizar/Eliminar
							</div>
						</div>
						<div class="card-footer">
							<button type="submit" class="btn btn-outline-dark" name="Listar">Listar</button>
							<button type="submit" class="btn btn-outline-dark" name="Buscar">Buscar</button>
							<button type="submit" class="btn btn-outline-dark" name="Agregar">Agregar</button>
							<button type="submit" class="btn btn-outline-dark"
								name="Modificar">Modificar</button>
							<button type="submit" class="btn btn-outline-dark"
								name="Eliminar">Eliminar</button>

						</div>
					</div>
				</form>
			</div>
			<div class="col-lg col-md col">
				<%
				if (request.getAttribute("listaProveedor") != null) {
				%>
				<div class="card">
					<div class="card-header bg-dark bg-gradient text-light">
						<span>Listado de Usuarios</span>
					</div>
					<div class="card-body">
						<table id="TablaPresentacion"
							class="table table-hover table-striped">
							<thead>
								<tr>
									<td>Nit</td>
									<td>Ciudad</td>
									<td>Direccion</td>
									<td>Nombre</td>
									<td>Telefono</td>
									
								</tr>
							</thead>
							<tbody>
								<%
								for (ProveedoresDTO pro : (ArrayList<ProveedoresDTO>) request.getAttribute("listaProveedor")){
								%>
								<tr>
									<td><%=pro.getNit()%></td>
									<td><%=pro.getCiudad()%></td>
									<td><%=pro.getDireccion()%></td>
									<td><%=pro.getNombre()%></td>
									<td><%=pro.getTelefono()%></td>
									
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