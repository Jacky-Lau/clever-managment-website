angular.module('clever.management.directives.terminologyTab', []).directive('terminologyTab', [
function() {
	return {
		restrict : 'E',
		scope : {
			terminology : '='
		},
		template : '<table class="table table-hover">' +
						'<thead>' +
							'<tr>' +
								'<th>#</th>' +
								'<th>Code</th>' +
								'<th>Text</th>' +
								'<th>Description</th>' +
							'</tr>' +
						'</thead>' +
						'<tbody>' +
							'<tr ng-repeat="ontology in terminology.items">' +
								'<th>{{$index+1}}</th>' +
								'<th>{{ontology.code}}</th>' +
								'<th>{{ontology.text}}</th>' +
								'<th>{{ontology.description}}</th>' +
							'</tr>' +
						'</tbody>' +
					'</table>',
		replace : true,	
	};
}]);