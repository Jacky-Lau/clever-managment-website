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
	<body ng-controller="appCtrl" resizable>

		<alert ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">
			&nbsp;&nbsp;&nbsp;&nbsp;{{alert.msg}}
		</alert>
		<!-- header -->
		<%@ include file="jspf/header.jspf"%>
		<!-- content -->
		<div class="container content-container">
			<div class="col-sm-1 col-md-2 col-lg-2"></div>
			<div class="col-sm-10 col-md-8 col-lg-8" ng-view></div>
			<div class="col-sm-1 col-md-2 col-lg-2"></div>
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
		<!-- archetype -->
		<script type="text/ng-template" id="archetype.html">
			<%@ include file="partials/archetype.html"%>
		</script>
		<!-- overview -->
		<script type="text/ng-template" id="overview.html">
			<%@ include file="partials/overview.html"%>
		</script>
		<!-- login -->
		<script type="text/ng-template" id="login.html">
			<%@ include file="partials/login.html"%>
		</script>
		<!-- classification -->
		<script type="text/ng-template" id="classification.html">
			<%@ include file="partials/classification.html"%>
		</script>
		<!-- classification view -->
		<script type="text/ng-template" id="classification-view.html">
			<%@ include file="partials/classification-view.html"%>
		</script>

	</body>
</html>
