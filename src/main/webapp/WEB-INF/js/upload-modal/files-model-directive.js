angular.module('clever.management.directives.filesModel', []).directive('filesModel', ['$parse',
function($parse) {
	return {
		restrict : 'A',
		controller : function($parse, $element, $attrs, $scope) {
			$element.on('change', function() {
				var newFiles = this.files;
				for (var i = 0; i < newFiles.length; i++) {
					var contained = false;
					for (var j = 0; j < $scope.fileList.length; j++) {
						if (newFiles[i].name == $scope.fileList[j].name) {
							contained = true;
						}
					}
					if (!contained) {
						$scope.fileList.push({
							name : newFiles[i].name,
							size : newFiles[i].size,
							
						});
					}
				}
				$scope.$apply();
			});
		}
	};
}]);
