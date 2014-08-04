angular.module('clever.management.services.archetypeRetrieve', []).service('archetypeRetrieveService', ['$http', 'ARCHETYPES_BY_TYPE_ID_URL', 'LATEST_VERSION_ARCHETYPE_LIST_URL', 'DEPLOYED_ARCHETYPE_LIST_URL', 'ARCHETYPE_BY_ID_URL', 'ARCHETYPE_BY_NAME_URL',
function($http, ARCHETYPES_BY_TYPE_ID_URL, LATEST_VERSION_ARCHETYPE_LIST_URL, DEPLOYED_ARCHETYPE_LIST_URL, ARCHETYPE_BY_ID_URL, ARCHETYPE_BY_NAME_URL) {

	this.getArchetypesBriefInfoByTypeId = function(typeId) {
		return getFromUrl(ARCHETYPES_BY_TYPE_ID_URL + typeId);
	};

	this.getLatestVersionArchetypeIds = function() {
		return getFromUrl(LATEST_VERSION_ARCHETYPE_LIST_URL);
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
