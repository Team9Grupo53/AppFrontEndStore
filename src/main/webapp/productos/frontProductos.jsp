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
			
				<form action="./AdminProductos" method="post">
				
					<div class="card">
						<div class="card-header bg-dark bg-gradient text-light">
							<span>Formulario Gestión de Productos</span>
						</div>
						<div class="card-body">
							<div class="mb-3 row">
								<label for="cedula-txt" class="col-sm-4 col-form-label">(*) Cod Producto:</label>
								<div class="col-sm">
									<input id="codigoP-txt" class="form-control"
										placeholder="Ingrese código Producto" type="number" name="codigoProducto"
										value="<%=request.getAttribute("usr") != null ? ((ProductosDTO) request.getAttribute("usr")).getCodigoProducto() : ""%>">
								</div>
							</div>
							<div class="mb-3 row">
								<label for="nitP-txt" class="col-sm-4 col-form-label">Nit Proveedor:</label>
								<div class="col-sm">
									<input id="nitP-txt" class="form-control"
										placeholder="Nit proveedor" type="number" name="nitProveedor"
										value="<%=request.getAttribute("usr") != null ? ((ProductosDTO) request.getAttribute("usr")).getNitProveedor() : ""%>">
								</div>
							</div>

							<div class="mb-3 row">
								<label for="ivaP-txt" class="col-sm-4 col-form-label">Iva Compra:</label>
								<div class="col-sm">
									<input id="ivaP-txt" class="form-control"
										placeholder="Iva compra" type="number" name="ivaCompra"
										value="<%=request.getAttribute("usr") != null ? ((ProductosDTO) request.getAttribute("usr")).getIvaCompra() : ""%>">
								</div>
							</div>

							<div class="mb-3 row">
								<label for="nombre-txt" class="col-sm-4 col-form-label">Nombre Producto:</label>
								<div class="col-sm">
									<input id="nombre-txt" class="form-control"
										placeholder="Ingrese nombre del producto" type=text" name="nombreProducto"
										value="<%=request.getAttribute("usr") != null ? ((ProductosDTO) request.getAttribute("usr")).getNombreProducto() : ""%>">
								</div>
							</div>
							
								<div class="mb-3 row">
								<label for="precio-txt" class="col-sm-4 col-form-label">Precio compra:</label>
								<div class="col-sm">
									<input id="precio-txt" class="form-control"
										placeholder="Ingrese precio de compra" type="number" name="precioCompra"
										value="<%=request.getAttribute("usr") != null ? ((ProductosDTO) request.getAttribute("usr")).getPrecioCompra() : ""%>">
								</div>
							</div>

							<div class="mb-3 row">
								<label for="precioV-txt" class="col-sm-4 col-form-label">Precio Venta:</label>
								<div class="col-sm">
									<input id="user-txt" class="form-control"
										placeholder="Ingrese el precio de venta" type="number" name="precioVenta"
										value="<%=request.getAttribute("usr") != null ? ((ProductosDTO) request.getAttribute("usr")).getPrecioVenta() : ""%>">
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
							<button type="submit" class="btn btn-outline-dark" name="Modificar">Modificar</button>
							<button type="submit" class="btn btn-outline-dark" name="Eliminar">Eliminar</button>
							<button class="btn btn-outline-dark" type="button" name="archivos" role="link" onclick="window.location='./AdminFileProductos'" >Archivo</button>

						</div>
					</div>
				</form>
			</div>
			<div class="col-lg col-md col">
				<%
				if (request.getAttribute("listaProducto") != null) {
				%>
				<div class="card">
					<div class="card-header bg-dark bg-gradient text-light">
						<span>Listado de Productos</span>
					</div>
					<div class="card-body">
						<table id="TablaPresentacion"
							class="table table-hover table-striped">
							<thead>
								<tr>
									<td>Codigo Producto</td>
									<td>Nit Proveedor</td>
									<td>Iva Compra</td>
									<td>Nombre Producto</td>
									<td>Precio Compra</td>
									<td>Precio Venta</td>
								</tr>
							</thead>
							<tbody>
								<%
								for (ProductosDTO usr : (ArrayList<ProductosDTO>) request.getAttribute("listaProducto")) {
								%>
								<tr>
									<td><%=usr.getCodigoProducto()%></td>
									<td><%=usr.getNitProveedor()%></td>
									<td><%=usr.getIvaCompra()%></td>
									<td><%=usr.getNombreProducto()%></td>
									<td><%=usr.getPrecioCompra()%></td>
									<td><%=usr.getPrecioVenta()%></td>
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