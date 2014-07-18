<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags"%>
<html ng-app="cleverManagementApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>CLEVER Management</title>
		<!-- css init -->
		<%@ include file="jspf/css-init.jspf"%>
	</head>
	<body ng-controller="AppCtrl" resizable>
		<!-- header -->
		<%@ include file="jspf/header.jspf"%>
		<div class="container content-container" ng-style="{height: windowHeight - 100}" style="overflow: auto;" ng-view>
			
		</div>

		<!-- footer -->
		<%@ include file="jspf/footer.jspf"%>
		<!-- js init -->
		<%@ include file="jspf/js-init.jspf"%>
		
		<!-- partials -->
		<!-- home -->
		<script type="text/ng-template" id="home.html">
			<%@ include file="partials/home.html"%>
		</script>
		<!-- upload -->
		<script type="text/ng-template" id="upload.html">
			<%@ include file="partials/upload.html"%>
		</script>
		<!-- deploy -->
		<script type="text/ng-template" id="deploy.html">
			<%@ include file="partials/deploy.html"%>
		</script>
		<!-- app library -->
		<script type="text/ng-template" id="app-library.html">
			<%@ include file="partials/app-library.html"%>
		</script>
			
	</body>
</html>
