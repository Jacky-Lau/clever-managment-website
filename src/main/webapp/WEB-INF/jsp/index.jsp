<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>CLEVER Browser</title>
		<!-- css init -->
		<link rel="stylesheet"
		href="/clever-management-website/resources/font.css" />
		<link rel="stylesheet"
		href="/clever-management-website/resources/lib/bootstrap.min.css" />

	</head>
	<body>
		<%@ include file="jspf/header.jspf"%>
		<a data-toggle="collapse" data-toggle="collapse" data-target="#target"> Collapsible Group Item #1 </a>
		<div class="row" id="main container"> 
			<div class="col-md-2 collapse width" id="target">
				dfafa
			</div>
			<div class="col-md-10">
				dfasdfasf
			</div>
		</div>
		
		

		<%@ include file="jspf/footer.jspf"%>
		<!-- js init -->
		<script
		src="/clever-management-website/resources/lib/jquery-2.1.1.min.js"></script>
		<script
		src="/clever-management-website/resources/lib/bootstrap.min.js"></script>
	</body>
</html>
