<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html ng-app="cleverManagementApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>CLEVER Management</title>
		<!-- css init -->
		<link rel="stylesheet"
		href="/clever-management-website/resources/font.css" />
		<link rel="stylesheet"
		href="/clever-management-website/resources/lib/bootstrap.min.css" />
		<link rel="stylesheet"
		href="/clever-management-website/resources/app.css" />
	</head>
	<body ng-controller="AppCtrl">
		<!-- header -->
		<%@ include file="jspf/header.jspf"%>

		<mainarea>
			<mainarea-navbar>
			<span style="background-color: red">{{isNavbarCollapsed}}</span>		
			</mainarea-navbar>
			<mainarea-splitter>
			</mainarea-splitter>
			<mainarea-content style="background-color: blue">
			{{isNavbarCollapsed}}
			</mainarea-content>
		</mainarea>

		<!-- footer -->
		<%@ include file="jspf/footer.jspf"%>
		<!-- js init -->
		<%@ include file="jspf/js-init.jspf"%>
	</body>
</html>
