angular.module('clever.management.services.layout', []).service('layoutService', ['$http', 'LAYOUTS_URL', 'LAYOUT_BY_ID_URL',
function($http, LAYOUTS_URL, LAYOUT_BY_ID_URL) {

	this.getAllLayouts = function() {
		return $http.get(LAYOUTS_URL).then(function(response) {
			return response.data;
		});
	};

	this.getLayoutById = function(id) {
		return $http.get(LAYOUT_BY_ID_URL + id).then(function(response) {
			return response.data;
		});
	};

	this.updateLayoutById = function(id, settings) {
		return $http.post(LAYOUT_BY_ID_URL + id, settings).then(function(response) {
			return response.data;
		});
	};
}]);
