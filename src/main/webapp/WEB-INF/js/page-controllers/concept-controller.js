function ConceptCtrl($scope, archetypeRetrieveService) {
	$scope.currentClassification = {
		name : 'openEHR',
	};

	$scope.originalWidth = 1136;
	$scope.observationCoords = [ 480, 70, 665, 140 ];
	$scope.evaluationCoords = [ 275, 385, 430, 520 ];
	$scope.instructionCoords = [ 485, 515, 660, 590 ];
	$scope.actionCoords = [ 820, 285, 985, 355 ];
	$scope.demographicCoords = [ 950, 65, 1130, 100 ];
	$scope.adminCoords = [ 950, 160, 1130, 200 ];

	$scope.getCoords = function(width, originalCoords) {
		var persent = width / $scope.originalWidth;
		return originalCoords[0] * persent + ',' + originalCoords[1] * persent
				+ ',' + originalCoords[2] * persent + ',' + originalCoords[3]
				* persent;
	};

}
