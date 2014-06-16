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
								'<th style="font-weight: normal">{{archetypeId}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Concept name</b></th>' +
								'<th style="font-weight: normal">{{getOntologyByCode(conceptCode).text}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Concept description</b></th>' +
								'<th style="font-weight: normal">{{getOntologyByCode(conceptCode).description}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Copyright</b></th>' +
								'<th style="font-weight: normal">{{description.copyright}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Purpose</b></th>' +
								'<th style="font-weight: normal">{{description.purpose}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Use</b></th>' +
								'<th style="font-weight: normal">{{description.use}}</th>' +
							'</tr>' +
							'<tr>' +
								'<th><b>Misuse</b></th>' +
								'<th style="font-weight: normal">{{description.misuse}}</th>' +
							'</tr>' +
						'</tbody>' +
					'</table>',
		replace : true,
		link : function(scope, element, attrs) {
			scope.getOntologyByCode = function(code) {
				if (scope.terminology && code) {
					var matchedOntology;
					if (scope.terminology.term) {
						angular.forEach(scope.terminology.term.items, function(value) {
							if (value.code == code) {
								matchedOntology = value;
							}
						});
					}
					if (scope.terminology.constraint) {
						angular.forEach(scope.terminology.constraint.items, function(value) {
							if (value.code == code) {
								matchedOntology = value;
							}
						});
					}
					return matchedOntology;
				}
			}; 
		}
	};
}]);