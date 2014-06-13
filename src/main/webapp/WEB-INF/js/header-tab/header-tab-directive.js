angular.module('clever.management.directives.headerTab', []).directive('headerTab', [
function() {
	return {
		restrict : 'E',
		scope : {
			archetypeId : '=',
			conceptCode : '=',
			description : '=',
			terminology : '='
		},
		template : '<table class="table table-hover">' +
						'<tbody>' +
							'<tr>' +
								'<th><b>Archetype ID</b></th>' +
								'<th>{{archetypeId}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Concept name</b></th>' +
								'<th>{{getOntologyByCode(conceptCode).text}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Concept description</b></th>' +
								'<th>{{getOntologyByCode(conceptCode).description}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Copyright</b></th>' +
								'<th>{{description.copyright}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Purpose</b></th>' +
								'<th>{{description.purpose}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Use</b></th>' +
								'<th>{{description.use}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Misuse</b></th>' +
								'<th>{{description.misuse}}</th>' +
							'</tr>' +
						'</tbody>' +
					'</table>',
		replace : true,
		link : function(scope, element, attrs) {
			scope.getOntologyByCode = function(code) {
				if (scope.terminology && code) {
					var matchedOntology;
					angular.forEach(scope.terminology.items, function(value) {
						if (value.code == code) {
							matchedOntology = value;
						}
					});
					return matchedOntology;
				}

			};
		}
	};
}]);