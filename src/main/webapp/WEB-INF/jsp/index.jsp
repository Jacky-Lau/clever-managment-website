<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html ng-app="cleverManagementApp">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>CLEVER Management</title>
		<!-- css init -->
		<%@ include file="jspf/css-init.jspf"%>
	</head>
	<body ng-controller="AppCtrl">
		<!-- header -->
		<%@ include file="jspf/header.jspf"%>
		<div class="container content-container" >
			<div class="row" style="max-height: 800px">
				<div class="col-xs-12 col-md-12 col-lg-12 hbox">
					<!-- left side navbar -->
					<nav ng-show="!isNavbarCollapsed" id="navbar" class="vbox">
						<div class="panel-group" id="accordion">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="panel-title row">
										<input type="text" placeholder="Filter by name" class="form-control pull-left" style="width: 50%;margin-left: 15px;" ng-model="archetypeListFilter">
									</div>
								</div>
								<div class="panel-collapse collapse">
									<div class="panel-body">
										Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic synth nesciunt you probably haven't heard of them accusamus labore sustainable VHS.
									</div>
								</div>
							</div>
						</div>
						<!-- archetype list -->
						<div class="flexBox">
							<div class="list-group" ng-show="archetypeList.length > 0" style="max-height: 750px; overflow: auto;">
								<a href class="list-group-item" ng-click="selectOverview()" ng-class="{'active': selectedArchetypeId == 0}"><b>Overview</b></a>
								<span ng-show="archetypeList.length == 0"><img src="/clever-management-website/img/loading.gif" style="max-height: 20px;"></img> Loading...</span>
								<a href ng-show="archetypeList.length > 0" class="list-group-item" ng-repeat="archetype in archetypeList | filter:archetypeListFilter | orderBy:'name'" ng-click="selectArchetype(archetype)" ng-class="{'active': archetype.id == selectedArchetypeId}">{{archetype.name}}</a>
							</div>
						</div>
					</nav>
					<!-- splitter -->
					<splitter is-adjust-enabled="!isNavbarCollapsed" style="height: 800px;">
						<i style="cursor: pointer" ng-click="expandNavbar()" ng-show="isNavbarCollapsed"><span class="glyphicon glyphicon-chevron-right" ></span></i>
						<i style="cursor: pointer" ng-click="collapseNavbar()" ng-show="!isNavbarCollapsed"><span class="glyphicon glyphicon-chevron-left" ></span></i>
					</splitter>
					<!-- content display -->
					<div class="flexBox">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs">
							<li ng-class="{'active': selectedArchetypeId == 0}">
								<a ng-click="selectOverview()" style="cursor: pointer;"><b>&nbsp;Overview&nbsp;</b></a>
							</li>
							<li ng-repeat="tab in tabs" ng-class="{'active': tab.id == selectedArchetypeId}">
								<a ng-click="selectTab(tab)" style="cursor: pointer;" tooltip-placement="bottom" tooltip-popup-delay='500' tooltip='{{tab.title}}'>{{getTabTitle(tab)}}&nbsp;<span class="close" ng-click="closeTab(tab)"> &times; </span></a>
							</li>
						</ul>

						<!-- Tab panes -->
						<div class="tab-content">
							<div class="tab-pane" ng-class="{'active': selectedArchetypeId == 0}" style="height: 750px;">
								<span ng-show="archetypeList.length == 0"><img src="/clever-management-website/img/loading.gif" style="max-height: 20px;"></img> Loading...</span>
								<div overview ng-show="archetypeList.length > 0" archetype-list="archetypeList" double-click="selectArchetype(selectedArchetype)" animation="false"></div>
							</div>
							<div class="tab-pane" ng-class="{'active': tab.id == selectedArchetypeId}" ng-repeat="tab in tabs" ng-controller="ArchetypeDisplayCtrl" ng-init="init(tab)">
								<div class="container content-container">
									<div class="row">
										<div class="col-xs-8 col-md-8 col-lg-8">
											<h3>{{title}}</h3>
										</div>
										<div class="col-xs-4 col-md-4 col-lg-4">
											<div class="input-group pull-right" style="padding: 5px;width: 260px;margin-top: 5px;">
												<span class="input-group-addon" style="width: 100px;">Language:</span>
												<div class="btn-group" dropdown is-open="isDropdownOpened">
													<button type="button" class="btn btn-default dropdown-toggle" style="width: 160px;">
														{{selectedLanguage.code}} <span class="glyphicon glyphicon-chevron-down pull-right" style="margin-top: 2px;"></span>
													</button>
													<ul class="dropdown-menu" role="menu">
														<li ng-repeat="language in languages">
															<a href class="text-center" ng-click="selectLanguage(language)">{{language.code}}</a>
														</li>
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>

								<!-- Nav tabs -->
								<ul class="nav nav-tabs">
									<li ng-class="{'active': selectedTab == 'Header'}">
										<a ng-click="selectTab('Header')" style="cursor: pointer;">Header</a>
									</li>
									<li ng-class="{'active': selectedTab == 'Definition'}">
										<a ng-click="selectTab('Definition')" style="cursor: pointer;">Definition</a>
									</li>
									<li ng-class="{'active': selectedTab == 'Terminology'}">
										<a ng-click="selectTab('Terminology')" style="cursor: pointer;">Terminology</a>
									</li>
									<li ng-class="{'active': selectedTab == 'Xml'}">
										<a ng-click="selectTab('Xml')" style="cursor: pointer;">XML</a>
									</li>
									<li ng-class="{'active': selectedTab == 'Adl'}">
										<a ng-click="selectTab('Adl')" style="cursor: pointer;">ADL</a>
									</li>
								</ul>
								<!-- Tab panes -->
								<div class="tab-content">
									<!-- Header -->
									<div class="tab-pane" ng-class="{'active': selectedTab == 'Header'}" style="overflow: auto;height: 700px;">
										<span ng-show="!xmlText"><img src="/clever-management-website/img/loading.gif" style="max-height: 20px;"></img> Loading...</span>
										<header-tab ng-show="xmlText" archetype-id="header.id" concept-code="header.concept" description="currentDescription" terminology="currentTerminology"></header-tab>
									</div>
									<!-- Definition -->
									<div class="tab-pane" ng-class="{'active': selectedTab == 'Definition'}" style="height: 700px;">
										<span ng-show="!xmlText"><img src="/clever-management-website/img/loading.gif" style="max-height: 20px;"></img> Loading...</span>
										<div ng-show="xmlText" class="container content-container">
											<div class="row" style="padding: 5px 0px;">
												<div class="col-xs-12 col-md-12 col-lg-12">
													<span><b>Expand All:&nbsp;&nbsp;&nbsp;</b></span>
													<toggle-switch model="isExpandedAll">
														<toggle-switch>
												</div>
											</div>
											<div class="row" style="overflow: auto;height: 680px;">
												<div class="col-xs-8 col-md-8 col-lg-8">
													<abn-tree tree-data="treeData" tree-control="treeControl" terminology="currentTerminology" on-select="selectDefinitionItem(branch)" icon-expand="glyphicon glyphicon-chevron-right" icon-collapse="glyphicon glyphicon-chevron-down" icon-leaf="glyphicon glyphicon-th-large"></abn-tree>
												</div>
												<div class="col-xs-4 col-md-4 col-lg-4">
													<div ng-show="selectedDefinitionItem">
														<h4>{{selectedDefinitionItem.text}}</h4>
														<div ng-if="selectedDefinitionItem.code" class="panel panel-default">
															<div class="panel-heading">
																Terminology
															</div>
															<div class="panel-body">
																<span>Code: {{selectedDefinitionItem.code}}</span>
																<br>
																<span>Text: {{}}</span>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- Terminology -->
									<div class="tab-pane" ng-class="{'active': selectedTab == 'Terminology'}" style="overflow: auto;height: 700px;">
										<span ng-show="!xmlText"><img src="/clever-management-website/img/loading.gif" style="max-height: 20px;"></img> Loading...</span>
										<terminology-tab ng-show="xmlText" terminology="currentTerminology"></terminology-tab>
									</div>
									<!-- Xml -->
									<div class="tab-pane" ng-class="{'active': selectedTab == 'Xml'}" style="overflow: auto;height: 700px;">
										<span ng-show="!xmlText"><img src="/clever-management-website/img/loading.gif" style="max-height: 20px;"></img> Loading...</span>
										<pre ng-show="xmlText" ng-bind-html-unsafe="text|pretty">{{xmlText}}</pre>
									</div>
									<!-- Adl -->
									<div class="tab-pane" ng-class="{'active': selectedTab == 'Adl'}" style="overflow: auto;height: 700px;">
										<span ng-show="!adlText"><img src="/clever-management-website/img/loading.gif" style="max-height: 20px;"></img> Loading...</span>
										<pre ng-show="adlText" ng-bind-html-unsafe="text|pretty">{{adlText}}</pre>
									</div>
								</div>
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
