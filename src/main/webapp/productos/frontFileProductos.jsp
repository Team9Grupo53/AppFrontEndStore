<%@page import="java.util.ArrayList"%>
<%@page import="co.edu.mintic.dto.ProductosDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Gestion de Productos</title>
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
			<h5>Gestión de Productos</h5>
		</div>
	</header>
	<main class="container-fluid">
		<div class="row mt-2">
			<div class="col-lg-5 col-md-6 col-12">

				<form action="./AdminFileProductos" method="post"
					enctype="multipart/form-data">

					<div class="card">
						<div class="card-header bg-dark bg-gradient text-light">
							<span>Formulario Gestión de Productos</span>
						</div>
						<div class="card-body">
							<div class="input-group">
								<input type="file" class="form-control" id="archivo"
									name="archivo" aria-describedby="archivo" aria-label="Upload">
								<button class="btn btn-outline-dark" type="submit" id="archivo">Cargar</button>
							</div>
						</div>
						<div class="card-footer">
							<button class="btn btn-outline-dark" type="button" name="volver"
								role="link" onclick="window.location='./AdminProductos'">Volver</button>
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