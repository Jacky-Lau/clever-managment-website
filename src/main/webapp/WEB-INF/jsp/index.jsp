<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
uri="http://www.springframework.org/security/tags"%>
<html ng-app="cleverManagementApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title translate="iTitle"></title>
		<link rel="icon" href="/clever-management-website/img/logo.png" />
		<!-- css init -->
		<%@ include file="jspf/css-init.jspf"%>
	</head>
	<body ng-controller="appCtrl" resizable style="font-family: Microsoft YaHei;" ng-cloak>

		<!-- Busy -->
		<div ng-show="isBusy" class="vbox" ng-style="{'height': windowHeight, 'width' : windowWidth}" style="position: absolute;top: 0;left: 0;background-color: grey;opacity: 0.7;z-index: 1000;">
			<span style="position: absolute;color: black;font-size: 2em;" ng-style="{'top' : windowHeight/2, 'left' : windowWidth/2}"><img src="/clever-management-website/img/loading.gif" style="max-height: 30px;"></img> {{busyHint | translate}}</span>
		</div>
		
		<alert ng-repeat="alert in alerts" type="{{alert.type}}" close="closeAlert($index)">
			&nbsp;&nbsp;&nbsp;&nbsp;{{alert.msg | translate}}
		</alert>
		<!-- header -->
		<%@ include file="jspf/header.jspf"%>
		<!-- content -->
		<div class="hbox center-box" >
			<div class="container content-container page-width" ng-view>
		</div>
		<!-- footer -->
		<%@ include file="jspf/footer.jspf"%>

		<!-- js init -->
		<script type="text/javascript">
			function getUserName() {
				var userName = '${userName}';
				if (userName == '') {
					userName = 'admin';
				}
				return userName;
			}
		</script>
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
		<!-- management -->
		<script type="text/ng-template" id="management.html">
			<%@ include file="partials/management.html"%>
		</script>
		<!-- about -->
		<script type="text/ng-template" id="about.html">
			<%@ include file="partials/about.html"%>
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
		<!-- concept -->
		<script type="text/ng-template" id="concept.html">
			<%@ include file="partials/concept.html"%>
		</script>
		<!-- classification view -->
		<script type="text/ng-template" id="classification-view.html">
			<%@ include file="partials/classification-view.html"%>
		</script>

	</body>
</html>
