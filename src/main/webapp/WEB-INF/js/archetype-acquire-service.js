angular.module('clever.management.services.archetypeAcquire', []).service('archetypeAcquireService', ['$http',
function($http) {

	this.getArchetypeList = function(url) {
		return $http.get(url).then(function(response) {
			return response.data;
		});
	};
	
	
}]); 