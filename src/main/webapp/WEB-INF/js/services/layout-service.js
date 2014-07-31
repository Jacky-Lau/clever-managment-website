angular.module('clever.management.services.layout', []).service('layoutService', ['$http', 'CLASSIFICATION_LAYOUT_BY_ID_URL', 'ARCHETYPE_TYPE_LAYOUT_BY_ID_URL',
function($http, CLASSIFICATION_LAYOUT_BY_ID_URL, ARCHETYPE_TYPE_LAYOUT_BY_ID_URL) {

	this.getArchetypeTypeLayoutById = function(id) {
		return $http.get(ARCHETYPE_TYPE_LAYOUT_BY_ID_URL + id + '/layout').then(function(response) {
			return response.data;
		});
	};

	this.getClassificationLayouById = function(id) {
		return $http.get(CLASSIFICATION_LAYOUT_BY_ID_URL + id + '/layout').then(function(response) {
			return response.data;
		});
	};

	this.updateArchetypeTypeLayoutById = function(id, settings) {
		return $http.post(ARCHETYPE_TYPE_LAYOUT_BY_ID_URL + id + '/layout', settings).then(function(response) {
			return response.data;
		});
	};

	this.updateClassificationLayouById = function(id, settings) {
		return $http.post(CLASSIFICATION_LAYOUT_BY_ID_URL + id + '/layout', settings).then(function(response) {
			return response.data;
		});
	};

}]);
