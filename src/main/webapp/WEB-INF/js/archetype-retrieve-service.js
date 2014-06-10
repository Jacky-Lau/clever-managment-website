angular.module('clever.management.services.archetypeRetrieve', []).service('archetypeRetrieveService', ['$http', 'ARCHETYPE_LIST_URL', 'ARCHETYPE_BY_ID_URL', 'ARCHETYPE_BY_NAME_URL',
function($http, ARCHETYPE_LIST_URL, ARCHETYPE_BY_ID_URL, ARCHETYPE_BY_NAME_URL) {

	this.getArchetypeList = function() {
		return $http.get(ARCHETYPE_LIST_URL).then(function(response) {
			return response.data;
		});
	};

	this.getArchetypeXmlById = function(archetypeId) {
		return $http.get(ARCHETYPE_BY_ID_URL + archetypeId + '.xml').then(function(response) {
			return response.data;
		});
	};

	this.getArchetypeXmlByName = function(archetypeName) {
		return $http.get(ARCHETYPE_BY_NAME_URL + archetypeName + '.xml').then(function(response) {
			return response.data;
		});
	};
	
	this.getArchetypeAdlById = function(archetypeId) {
		return $http.get(ARCHETYPE_BY_ID_URL + archetypeId + '.adl').then(function(response) {
			return response.data;
		});
	};

	this.getArchetypeAdlByName = function(archetypeName) {
		return $http.get(ARCHETYPE_BY_NAME_URL + archetypeName + '.adl').then(function(response) {
			return response.data;
		});
	};

}]);
