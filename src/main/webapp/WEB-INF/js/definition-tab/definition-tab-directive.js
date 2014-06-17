angular.module('clever.management.directives.definitionTab', []).directive('definitionTab', [
function() {
	return {
		restrict : 'E',
		scope : {
			definition : '=',
			terminology : '='
		},
		templateUrl : 'js/definition-tab/definition-tab.html',
		controller : function($scope, $element, $attrs) {
			$scope.treeControl = {};

			$scope.isExpandedAll = false;
			$scope.$watch('isExpandedAll', function(newValue, oldValue) {
				if (newValue) {
					if ($scope.treeControl.expand_all) {
						$scope.treeControl.expand_all();
					}
				} else {
					if ($scope.treeControl.collapse_all) {
						$scope.treeControl.collapse_all();
					}
				}
			});

			$scope.selectDefinitionItem = function(definitionItem) {
				$scope.selectedDefinitionItem = definitionItem.label;
			};

			$scope.getOntologyByCode = function(code) {
				if ($scope.terminology && code) {
					var matchedOntology;
					if ($scope.terminology.term) {
						angular.forEach($scope.terminology.term.items, function(value) {
							if (value.code == code) {
								matchedOntology = value;
							}
						});
					}
					if ($scope.terminology.constraint) {
						angular.forEach($scope.terminology.constraint.items, function(value) {
							if (value.code == code) {
								matchedOntology = value;
							}
						});
					}
					return matchedOntology;
				}
			};
		},
		link : function(scope, element, attrs) {

		}
	};
}]);
