angular.module('clever.management.directives.fileModel', []).directive('fileModel', ['$parse',
function($parse) {
	return {
		restrict : 'A',
		scope : {
			file : '=',
			onChange : '&',
		},
		link : function(scope, element, attrs) {
			element.on('change', function() {
				scope.file = {
					file : this.files[0],
					path : element.val()
				};
				scope.onChange({
					file : scope.file
				});
				element.val('');
				//scope.$parent.$digest();
				scope.$apply();
			});
		}
	};
}]);
