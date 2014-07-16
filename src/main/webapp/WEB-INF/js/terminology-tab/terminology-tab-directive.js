angular.module('clever.management.directives.terminologyTab', []).directive('terminologyTab', [
function() {
	return {
		restrict : 'E',
		scope : {
			terminology : '=',
		},
		templateUrl : 'js/terminology-tab/terminology-tab.html',
		replace : true,	
		controller : function($scope, $element, $attrs){
			$scope.isTermCollapsed = false;
			$scope.isConstraintCollapsed = false;
		},
	};
}]);