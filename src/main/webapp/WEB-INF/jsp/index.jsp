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
		<div class="container content-container" >
			<div class="row" style="margin-bottom: 20px">
				<div class="btn-group">
					<div class="col-xs-12 col-md-12 col-lg-12">
						<button type="button" class="btn btn-default" ng-click="openUploadModal('lg')">
							<span class="glyphicon glyphicon-upload"></span> Upload archetypes
						</button>
						<button type="button" class="btn btn-default">
							<span class="glyphicon glyphicon-plus"></span> New archetype
						</button>
					</div>
				</div>
			</div>
			<div class="row" style="height: 500px">
				<div class="col-xs-12 col-md-12 col-lg-12 hbox">
					<nav ng-show="!isNavbarCollapsed" id="navbar">
						dfdfasdf
					</nav>
					<splitter>
						<i style="cursor: pointer" ng-click="openNavbar()" ng-show="isNavbarCollapsed"><span class="glyphicon glyphicon-chevron-right" ></span></i>
						<i style="cursor: pointer" ng-click="collapseNavbar()" ng-show="!isNavbarCollapsed"><span class="glyphicon glyphicon-chevron-left" ></span></i>
					</splitter>
					<div class="flexBox">
						dfasfasd
					</div>
				</div>

			</div>
		</div>

		<!-- footer -->
		<%@ include file="jspf/footer.jspf"%>
		<!-- js init -->
		<%@ include file="jspf/js-init.jspf"%>
	</body>
</html>
