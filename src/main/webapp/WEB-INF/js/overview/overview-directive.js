angular.module('clever.management.directives.overview', []).directive('overview', [
function() {
	return {
		restrict : 'EA',
		scope: {
			archetypeList : '@'
		},
		link : function(scope, element, attrs) {
			
		}
	};
}]);

// MxGraph useful examples: graphlayout, codec, layers, scrollbars, tree, userobject
