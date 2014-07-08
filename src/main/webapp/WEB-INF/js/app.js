angular.module('clever.management.directives', ['clever.management.directives.splitter', 'clever.management.directives.overview', 'clever.management.directives.filesModel', 'clever.management.directives.headerTab', 'clever.management.directives.terminologyTab', 'clever.management.directives.definitionTab', 'angularBootstrapNavTree', 'toggle-switch']);
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
	}).otherwise({
		redirectTo : '/'
	});
}]);
