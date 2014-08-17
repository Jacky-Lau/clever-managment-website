function ClassificationCtrl($scope, $location, layoutService, busyService, msgboxService, classifications, selectedClassification) {

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
		msgboxService('iSave', 'iSaveLayoutQuiz').result.then(function(isOk) {
			if (isOk) {
				busyService.setBusy(true, 'iSaving');
				var layout = $scope.classificationViewControl.getCurrentLayout();
				layoutService.updateClassificationLayouById($scope.currentClassification.id, layout).then(function(result) {
					if (result.succeeded) {
						$scope.currentClassification.layout = layout;
						$scope.addAlert({
							type : 'success',
							msg : 'iSaveLayoutSucceeded',
						});
					}
					busyService.setBusy(false);
				});
			}
		});
	};
}
