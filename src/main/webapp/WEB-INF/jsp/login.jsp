<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>CLEVER Management</title>
		<!-- css init -->
		<%@ include file="jspf/css-init.jspf"%>

		<body>
			<!-- NavBar -->
			<nav class="navbar navbar-default" role="navigation">
				<div class="container-fluid">
					<!-- Brand and toggle get grouped for better mobile display -->
					<div class="navbar-header">
						<a class="navbar-brand" href="#">CLEVER Management</a>
					</div>

					<!-- Collect the nav links, forms, and other content for toggling -->
					<div class="collapse navbar-collapse" id="clever-navbar-collapse-1">
						<ul class="nav navbar-nav navbar-right">
						</ul>
					</div><!-- /.navbar-collapse -->
				</div><!-- /.container-fluid -->
			</nav>

			<div class="container">
				<div class="row">
					<div class="col-md-6"></div>
					<div class="col-md-6">
						<form class="form-signin" action="j_spring_security_check"
						method="post" role="form">
							<h2 class="form-signin-heading">Please log in</h2>
							<c:if test="${error == true}">
								<span style="color: red">User name or password is not correct</span>
							</c:if>
							<br>
							<input type="text" name="j_username" class="form-control"
							placeholder="User name" required autofocus>
							<br>
							<input
							type="password" name="j_password" class="form-control"
							placeholder="Password" required>
							<br>
							<div class="row">
								<div class="col-md-6">
									<button type="submit" class="btn btn-lg btn-success btn-block">
										Login
									</button>
								</div>
							</div>
						</form>
					</div>

				</div>

				<!-- FOOTER -->
				<%@ include file="jspf/footer.jspf"%>
			</div>
			<!-- /container -->

			<!-- js init -->
			<%@ include file="jspf/js-init.jspf"%>
		</body>
</html>