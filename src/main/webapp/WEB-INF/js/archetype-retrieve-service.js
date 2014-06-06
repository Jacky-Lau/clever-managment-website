angular.module('clever.management.services.archetypeRetrieve', []).service('archetypeRetrieveService', ['$http', 'ARCHETYPE_LIST_URL', 'ARCHETYPE_BY_ID_URL', 'ARCHETYPE_BY_NAME_URL',
function($http, ARCHETYPE_LIST_URL, ARCHETYPE_BY_ID_URL, ARCHETYPE_BY_NAME_URL) {

	this.getArchetypeList = function() {
		return $http.get(ARCHETYPE_LIST_URL).then(function(response) {
			return response.data;
		});
	};

	this.getArchetypeById = function(archetypeId) {
		return $http.get(ARCHETYPE_BY_ID_URL + archetypeId).then(function(response) {
			return response.data;
		});
	};

	this.getArchetypeByName = function(archetypeName) {
		return $http.get(ARCHETYPE_BY_NAME_URL + archetypeName).then(function(response) {
			return response.data;
		});
	};

}]);
