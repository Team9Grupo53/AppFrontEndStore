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
<%request.getSession().invalidate(); %>
	<header class="bg-dark text-light">
		<div class="bg-dark text-light text-center">
			<h3 id="TituloPrincial">TIENDA ON-LINE</h3>
		</div>
	</header>
	<main class="container-fluid">
		<form action="./Login" method="post">
			<section>
				<div class="container py-3">
					<div class="row d-flex justify-content-center align-items-center">
						<div class="col-12 col-md-8 col-lg-6 col-xl-5">
							<div class="card bg-dark text-white" style="border-radius: 1rem;">
								<div class="card-body p-5 text-center">
									<div class="mb-md-2">
										<h2 class="fw-bold text-uppercase  mb-5">Login</h2>

										<div class="form-outline form-white mb-3">
											<label class="form-label" for="typeusuariolX">Usuario</label>
											<input type="text" id="typeusuariolX"
												class="form-control form-control-lg" name="usuario"
												placeholder="Ingrese el Usuario.." required />
										</div>

										<div class="form-outline form-white mb-3">
											<label class="form-label" for="typePasswordX">Password</label>
											<input type="password" id="typePasswordX"
												class="form-control form-control-lg" name="pass"
												placeholder="Ingrese el Password.." required />
										</div>
										<div class="mt-3">
											<button class="btn btn-outline-light btn-lg  " type="submit">Login</button>
										</div>
										<%
										if (request.getAttribute("msnErr") != null) {
										%>
										<div class="alert alert-dismissible alert-danger mt-3">
											<strong>${msnErr}</strong>
										</div>
										<%
										}
										%>
									</div>
								</div>
							</div>

						</div>

					</div>

				</div>
			</section>
		</form>

${usrLog.nombre}
	</main>
</body>
</html>