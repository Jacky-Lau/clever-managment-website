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
					<nav ng-show="!isNavbarCollapsed" id="navbar" class="vbox">
						<div class="panel-group" id="accordion">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="panel-title row">
										<input type="text" placeholder="Filter by name" class="form-control pull-left" style="width: 50%;margin-left: 15px;" ng-model="archetypeListFilter">
										<a class="pull-right" data-toggle="collapse" data-parent="#accordion" href="#collapseAdvancedSearch" style="vertical-align: middle;padding: 6px 12px;">Advanced</a>
									</div>
								</div>
								<div id="collapseAdvancedSearch" class="panel-collapse collapse">
									<div class="panel-body">
										Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
									</div>
								</div>
							</div>
						</div>
						<div class="flexBox">
							<span ng-show="archetypeList.length == 0"><img src="/clever-management-website/resources/loading.gif" style="max-height: 20px;"></img> Loading...</span>
							<div class="list-group" ng-show="archetypeList.length > 0">
								<a href="#" class="list-group-item" ng-click="selectArchetypeById(0)" ng-class="{'active': selectedArchetypeId == 0}"><b>Overview</b></a>
								<a href="#" class="list-group-item" ng-repeat="archetype in archetypeList | filter:archetypeListFilter | orderBy:'name'" ng-click="selectArchetypeById(archetype.id)" ng-class="{'active': archetype.id == selectedArchetypeId}">{{archetype.name}}</a>
							</div>
						</div>
					</nav>
					<splitter>
						<i style="cursor: pointer" ng-click="openNavbar()" ng-show="isNavbarCollapsed"><span class="glyphicon glyphicon-chevron-right" ></span></i>
						<i style="cursor: pointer" ng-click="collapseNavbar()" ng-show="!isNavbarCollapsed"><span class="glyphicon glyphicon-chevron-left" ></span></i>
					</splitter>
					<div class="flexBox">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs">
							<li ng-repeat="tab in tabs" ng-class="{'active': tab.id == selectedArchetypeId}">
								<a href="{{'#tab_' + tab.id}}" data-toggle="tab">{{tab.title}}&nbsp;<span class="close"> &times; </span></a>
							</li>
						</ul>

						<!-- Tab panes -->
						<div class="tab-content">
							<div class="tab-pane" ng-class="{'active': tab.id == selectedArchetypeId}" id="{{'tab_' + tab.id}}" ng-repeat="tab in tabs">
								{{tab.content}}
							</div>
						</div>
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
