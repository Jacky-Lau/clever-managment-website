function ClassificationCtrl($scope, $location, layoutService, msgboxService, classifications, selectedClassification) {

	$scope.classifications = classifications;
	$scope.currentClassification = selectedClassification;
	$scope.isDropdownOpened = false;
	$scope.showDetails = false;

	$scope.zoomIn = function() {
		$scope.classificationViewControl.zoomIn();
	};

	$scope.zoomOut = function() {
		$scope.classificationViewControl.zoomOut();
	};

	$scope.reset = function() {
		$scope.classificationViewControl.reset();
	};

	$scope.selectType = function(selectedType) {
		$location.path('/classification/id/' + $scope.currentClassification.id + '/type/id/' + selectedType.id);
	};
	
	$scope.selectClassification = function(classification){
		$location.path('/classification/id/' + classification.id);
	};

	$scope.saveLayout = function() {
		msgboxService('Save', 'Do you want to save classification "' + $scope.currentClassification.name + '" ?').result.then(function(isOk) {
			if (isOk) {
				var settings = $scope.classificationViewControl.getCurrentLayout();
				layoutService.updateClassificationLayouById($scope.currentClassification.id, settings).then(function(result) {
					if (result.succeeded) {
						$scope.currentClassification.layout = settings;
						$scope.addAlert({
							type : 'success',
							msg : 'Save classification "' + $scope.currentClassification.name + '" succeeded.',
						});
					}
				});
			}
		});
	};
}
