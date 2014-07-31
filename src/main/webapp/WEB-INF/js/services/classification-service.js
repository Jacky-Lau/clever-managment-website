angular.module('clever.management.services.classification', []).service('classificationService', ['$http', '$q', 'layoutService', 'CLASSIFICATIONS_URL', 'CLASSIFICATION_BY_ID_URL',
function($http, $q, layoutService, CLASSIFICATIONS_URL, CLASSIFICATION_BY_ID_URL) {

	var serviceDeferred = $q.defer();

	var classifications = [];
	$http.get(CLASSIFICATIONS_URL).then(function(response) {
		angular.forEach(response.data, function(classification, index) {
			classification.loaded = false;
			classifications.push(classification);
		});
		serviceDeferred.resolve({
			getClassifications : getClassifications,
			getClassificationById : getClassificationById,
		});
	});

	function getClassifications() {
		return classifications;
	}

	function getClassificationById(id) {
		var deferred = $q.defer();
		var selectedClassification;
		angular.forEach(classifications, function(classification) {
			if (classification.id == id) {
				selectedClassification = classification;
				return false;
			}
		});
		//if (!selectedClassification.loaded) {
		$http.get(CLASSIFICATION_BY_ID_URL + selectedClassification.id).then(function(response) {
			selectedClassification.archetypeTypeInfos = response.data.archetypeTypeInfos;
			selectedClassification.archetypeTypeRelationshipInfos = response.data.archetypeTypeRelationshipInfos;
			selectedClassification.loaded = true;
			layoutService.getClassificationLayouById(selectedClassification.id).then(function(layout) {
				selectedClassification.layout = layout;
				deferred.resolve(selectedClassification);
			});
		});
		//} else {
		//	deferred.resolve(selectedClassification);
		//}
		return deferred.promise;
	}

	return serviceDeferred.promise;
}]);
