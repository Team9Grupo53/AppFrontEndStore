<%@page import="java.util.ArrayList"%>
<%@page import="co.edu.mintic.dto.UsuariosDTO"%>
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

</head>
<body>
	<header class="bg-dark text-light">
		<jsp:include page="../menu.jsp"></jsp:include>
		<div class="card-title bg-dark text-light text-center">
			<h5>Gestión de Usuarios</h5>
		</div>
	</header>
	<main class="container-fluid">
		<div class="row mt-2">
			<div class="col-lg-5 col-md-6 col-12">
				<form action="./AdminUsuarios" method="post">
					<div class="card">
						<div class="card-header bg-dark bg-gradient text-light">
							<span>Formulario Gestión de Usuario</span>
						</div>
						<div class="card-body">
							<div class="mb-3 row">
								<label for="cedula-txt" class="col-sm-3 col-form-label">(*)Cedula:</label>
								<div class="col-sm">
									<input id="cedula-txt" class="form-control"
										placeholder="Ingrese la Cedula" type="number" name="cedula"
										value="<%=request.getAttribute("usr") != null ? ((UsuariosDTO) request.getAttribute("usr")).getCedula() : ""%>">
								</div>
							</div>
							<div class="mb-3 row">
								<label for="email-txt" class="col-sm-3 col-form-label">Email:</label>
								<div class="col-sm">
									<input id="email-txt" class="form-control"
										placeholder="email@dominio.com" type="email" name="email"
										value="<%=request.getAttribute("usr") != null ? ((UsuariosDTO) request.getAttribute("usr")).getEmail() : ""%>">
								</div>
							</div>

							<div class="mb-3 row">
								<label for="nombre-txt" class="col-sm-3 col-form-label">Nombre:</label>
								<div class="col-sm">
									<input id="nombre-txt" class="form-control"
										placeholder="Ingrese el Nombre" type="text" name="nombre"
										value="<%=request.getAttribute("usr") != null ? ((UsuariosDTO) request.getAttribute("usr")).getNombre() : ""%>">
								</div>
							</div>
							<div class="mb-3 row">
								<label for="user-txt" class="col-sm-3 col-form-label">Usuario:</label>
								<div class="col-sm">
									<input id="user-txt" class="form-control"
										placeholder="Ingrese el Usuario" type="text" name="userlog"
										value="<%=request.getAttribute("usr") != null ? ((UsuariosDTO) request.getAttribute("usr")).getUsuario() : ""%>">
								</div>
							</div>
							<div class="mb-3 row">
								<label for="pass-txt" class="col-sm-3 col-form-label">Contraseña:</label>
								<div class="col-sm">
									<input id="pass-txt" class="form-control"
										placeholder="Ingrese el Password" type="text" name="passAdd"
										value="<%=request.getAttribute("usr") != null ? ((UsuariosDTO) request.getAttribute("usr")).getPassword() : ""%>">
								</div>
							</div>


							<div style="color: gray;">(*) Filtro para
								Buscar/Actualizar/Eliminar</div>
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
				if (request.getAttribute("listaUsuario") != null) {
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
									<td>Cedula</td>
									<td>Nombre</td>
									<td>Correo</td>
									<td>Usuario</td>
									<td>Password</td>
								</tr>
							</thead>
							<tbody>
								<%
								for (UsuariosDTO usr : (ArrayList<UsuariosDTO>) request.getAttribute("listaUsuario")) {
								%>
								<tr>
									<td><%=usr.getCedula()%></td>
									<td><%=usr.getNombre()%></td>
									<td><%=usr.getEmail()%></td>
									<td><%=usr.getUsuario()%></td>
									<td><%=usr.getPassword()%></td>									
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
		</div>
	</main>
</body>
</html>