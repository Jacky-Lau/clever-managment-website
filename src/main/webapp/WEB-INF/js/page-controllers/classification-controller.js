function ClassificationCtrl($scope, $http, CLASSIFICATIONS_URL, CLASSIFICATION_BY_ID_URL) {

	initClassifications();	

	// Init classifications
	function initClassifications() {
		$scope.classifications = [];
		$http.get(CLASSIFICATIONS_URL).then(function(response) {
			angular.forEach(response.data, function(classification, index) {
				$http.get(CLASSIFICATION_BY_ID_URL + classification.id).then(function(response) {
					$scope.classifications.push(response.data);
					if (index == 0) {
						$scope.currentClassification = $scope.classifications[0];
					}
				});
			});
		});
	}

}
