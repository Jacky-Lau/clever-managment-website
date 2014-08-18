function ClassificationCtrl($scope, $location, layoutService, busyService, msgboxService, classifications, selectedClassification) {

	$scope.classifications = classifications;
	$scope.currentClassification = selectedClassification;
	$scope.statistics = {
		demographicCount : 0,
		adminEntryCount : 0,
		obersvationCount : 0,
		evaluationCount : 0,
		instructionCount : 0,
		actionCount : 0,
	};
	// Statistics
	angular.forEach(selectedClassification.archetypeTypeInfos, function(type) {
		angular.forEach(type.archetypeHostInfos, function(host) {
			if (host.rmEntity == 'PERSON') {
				$scope.statistics.demographicCount++;
			} else if (host.rmEntity == 'EVALUATION') {
				$scope.statistics.evaluationCount++;
			} else if (host.rmEntity == 'OBSERVATION') {
				$scope.statistics.obersvationCount++;
			} else if (host.rmEntity == 'ACTION') {
				$scope.statistics.actionCount++;
			} else if (host.rmEntity == 'INSTRUCTION') {
				$scope.statistics.instructionCount++;
			} else if (host.rmEntity == 'ADMIN_ENTRY') {
				$scope.statistics.adminEntryCount++;
			}
		});
	});
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

	$scope.selectClassification = function(classification) {
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
