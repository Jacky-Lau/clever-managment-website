angular.module('clever.management.services.archetypeRetrieve', []).service('archetypeRetrieveService', ['$http', 'ARCHETYPE_BRIEF_INFO_URL', 'ALL_ARCHETYPE_LIST_URL', 'DEPLOYED_ARCHETYPE_LIST_URL', 'ARCHETYPE_BY_ID_URL', 'ARCHETYPE_BY_NAME_URL',
function($http, ARCHETYPE_BRIEF_INFO_URL, ALL_ARCHETYPE_LIST_URL, DEPLOYED_ARCHETYPE_LIST_URL, ARCHETYPE_BY_ID_URL, ARCHETYPE_BY_NAME_URL) {

	this.getArchetypesBriefInfo = function() {
		return getFromUrl(ARCHETYPE_BRIEF_INFO_URL);
	};

	this.getAllArchetypeIds = function() {
		return getFromUrl(ALL_ARCHETYPE_LIST_URL);
	};

	this.getDeployedArchetypeIds = function() {
		return getFromUrl(DEPLOYED_ARCHETYPE_LIST_URL);
	};

	this.getArchetypeXmlById = function(archetypeId) {
		return getFromUrl(ARCHETYPE_BY_ID_URL + archetypeId + '.xml');
	};

	this.getArchetypeXmlByName = function(archetypeName) {
		return getFromUrl(ARCHETYPE_BY_NAME_URL + archetypeName + '.xml');
	};

	this.getArchetypeAdlById = function(archetypeId) {
		return getFromUrl(ARCHETYPE_BY_ID_URL + archetypeId + '.adl');
	};

	this.getArchetypeAdlByName = function(archetypeName) {
		return getFromUrl(ARCHETYPE_BY_NAME_URL + archetypeName + '.adl');
	};

	function getFromUrl(url) {
		return $http.get(url).then(function(response) {
			return response.data;
		});
	}

}]);
