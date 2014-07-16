angular.module('clever.management.directives', ['clever.management.directives.splitter', 'clever.management.directives.overview', 'clever.management.directives.filesModel', 'clever.management.directives.headerTab', 'clever.management.directives.terminologyTab', 'clever.management.directives.definitionTab', 'clever.management.directives.resizable', 'angularBootstrapNavTree', 'toggle-switch']);
angular.module('clever.management.services', ['clever.management.services.fileUpload', 'clever.management.services.archetypeRetrieve', 'clever.management.services.archetypeParse', 'clever.management.services.msgbox']);
angular.module('clever.management.filters', ['clever.management.filters.pretty']);
angular.module('cleverManagementApp', ['ngRoute', 'ui.bootstrap', 'clever.management.directives', 'clever.management.services', 'clever.management.filters', 'clever.management.config']).config(['$routeProvider',
function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : '/clever-management-website/js/partials/home.html',
		controller : HomeCtrl
	}).when("/home", {
		templateUrl : '/clever-management-website/js/partials/home.html',
		controller : HomeCtrl
	}).when("/deploy", {
		templateUrl : '/clever-management-website/js/partials/deploy.html',
		controller : DeployCtrl
	}).when("/upload", {
		templateUrl : '/clever-management-website/js/partials/upload.html',
		controller : UploadCtrl
	}).when("/appLibrary", {
		templateUrl : '/clever-management-website/js/partials/app-library.html',
		controller : AppLibraryCtrl
	}).otherwise({
		redirectTo : '/'
	});
}]).run(function($rootScope, $location, $http, AUTHENTICATION_URL) {
	// register listener to watch route changes
	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		if (next.originalPath != '/') {
			$http.get(AUTHENTICATION_URL).then(function(response) {
				if (response.data != 'true') {
					$location.path("/");
				}
			});
		}
	});
});
