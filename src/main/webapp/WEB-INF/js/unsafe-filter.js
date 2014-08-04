angular.module('clever.management.filters.unsafe', []).filter('unsafe', ['$sce',
function($sce) {
	return function(value) {
		return $sce.trustAsHtml(value);
	};
}]); 