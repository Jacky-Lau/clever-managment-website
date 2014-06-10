angular.module('clever.management.directives.archetypeTreeview', []).directive('archetypeTreeview', [
function() {
	return {
		restrict : 'EA',
		scope : {
			archetypeXml : '=',
		},
		link : function(scope, element, attrs) {
			scope.$watch('archetypeXml', function(archetypeXml) {
				if (archetypeXml != '') {
					var x2js = new X2JS();
					var json = x2js.xml_str2json(archetypeXml);
				}
			});
		}
	};
}]);
