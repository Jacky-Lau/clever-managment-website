function ConceptCtrl($scope, archetypeRetrieveService) {
	$scope.currentClassification = {
		name : 'openEHR',
	};

	$scope.originalWidth = 1552;
	$scope.observationCoords = [ 630, 135, 870, 235 ];
	$scope.evaluationCoords = [ 330, 485, 595, 670 ];
	$scope.instructionCoords = [ 645, 700, 875, 805 ];
	$scope.actionCoords = [ 1115, 360, 1175, 425 ];
	$scope.demographicCoords = [ 1300, 80, 1530, 130 ];
	$scope.adminCoords = [ 1290, 210, 1425, 265 ];

	$scope.getCoords = function(width, originalCoords) {
		var persent = width / $scope.originalWidth;
		return originalCoords[0] * persent + ',' + originalCoords[1] * persent
				+ ',' + originalCoords[2] * persent + ',' + originalCoords[3]
				* persent;
	};

}
