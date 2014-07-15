angular.module('clever.management.directives.resizable', []).directive('resizable', ['$window',
function($window) {
	return {
		restrict : 'A',
		link : function($scope) {
			$scope.initializeWindowSize = function() {
				$scope.windowHeight = $window.innerHeight;
				$scope.windowWidth = $window.innerWidth;
			};
			$scope.initializeWindowSize();
			angular.element($window).bind('resize', function() {
				$scope.initializeWindowSize();
				$scope.$apply();
			});
		}
	};
}]);
