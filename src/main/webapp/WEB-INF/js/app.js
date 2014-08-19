angular.module('clever.management.directives', ['clever.management.directives.splitter', 'clever.management.directives.overview', 'clever.management.directives.classificationView', 'clever.management.directives.filesModel', 'clever.management.directives.fileModel', 'clever.management.directives.headerTab', 'clever.management.directives.terminologyTab', 'clever.management.directives.definitionTab', 'clever.management.directives.resizable', 'angularBootstrapNavTree', 'toggle-switch']);
angular.module('clever.management.services', ['clever.management.services.fileUpload', 'clever.management.services.archetypeRetrieve', 'clever.management.services.archetypeParse', 'clever.management.services.appLibrary', 'clever.management.services.layout', 'clever.management.services.classification', 'clever.management.services.msgbox', 'clever.management.services.busy']);
angular.module('clever.management.filters', ['clever.management.filters.pretty', 'clever.management.filters.unsafe', 'clever.management.filters.timestamp']);
angular.module('clever.management.controllers', ['clever.management.controllers.app', 'clever.management.controllers.archetypeDisplayCtrl']);
angular.module('clever.management.i18n', ['clever.management.i18n.zh', 'clever.management.i18n.en']);
angular.module('cleverManagementApp', ['ngRoute', 'ui.bootstrap', 'ui.utils', 'ng-context-menu', 'pascalprecht.translate', 'clever.management.i18n', 'clever.management.directives', 'clever.management.controllers', 'clever.management.services', 'clever.management.filters', 'clever.management.config']).config(function($routeProvider, $translateProvider, $compileProvider) {
	
	$compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|mias-iv|mias-cpoe|shine):/);
	
	// route config
	$routeProvider.when("/", {
		templateUrl : 'home.html',
		controller : HomeCtrl
	}).when("/management", {
		templateUrl : 'management.html',
		controller : ManagementCtrl
	}).when("/upload", {
		templateUrl : 'upload.html',
		controller : UploadCtrl
	}).when("/appLibrary", {
		templateUrl : 'app-library.html',
		controller : AppLibraryCtrl
	}).when("/classification/id/:classificationId/type/id/:typeId", {
		templateUrl : 'archetype.html',
		controller : ArchetypeCtrl
	}).when("/concept", {
		templateUrl : 'concept.html',
	}).when("/login", {
		templateUrl : 'login.html',
		controller : LoginCtrl
	}).when("/about", {
		templateUrl : 'about.html',
	}).when("/classification/id/:classificationId", {
		templateUrl : 'classification.html',
		controller : ClassificationCtrl,
		resolve : {
			classifications : function(classificationService) {
				return classificationService.then(function(service) {
					return service.getClassifications();
				});
			},
			selectedClassification : function(classificationService, $route) {
				return classificationService.then(function(service) {
					return service.getClassificationById($route.current.params.classificationId);
				});
			}
		}
	}).otherwise({
		redirectTo : '/'
	});
	//translate config
	$translateProvider.preferredLanguage('zh');
}).run(function($rootScope, $location, $http, busyService, AUTHENTICATION_URL) {
	// register listener to watch route changes
	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		busyService.setBusy(true);
		var classificationReg = new RegExp('/classification/id/.*');
		if (next.originalPath != '/' && next.originalPath != '/appLibrary' && !classificationReg.test(next.originalPath) && next.originalPath != '' && next.originalPath != '/archetype' && next.originalPath != '/about' && next.originalPath != '/concept') {
			$http.get(AUTHENTICATION_URL).then(function(response) {
				if (response.data != 'true') {
					$location.path("/login");
				} else if (response.data == 'true' && next.originalPath == '/login') {
					$location.path("/");
				}
			});
		}
	});
	$rootScope.$on("$routeChangeSuccess", function(event, next, current) {
		busyService.setBusy(false);
	});
});
