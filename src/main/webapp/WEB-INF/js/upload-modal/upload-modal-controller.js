function uploadModalCtrl($scope, $modalInstance) {

	$scope.title = "Upload Archetypes";
	$scope.fileList = [];
	$scope.commitSequence = null;

	$scope.isUploadEnabled = function() {
		return $scope.commitSequence == null && $scope.fileList.length > 0;
	};

	$scope.uploadFiles = function() {

	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

	$scope.deleteFile = function(fileName) {
		for ( i = 0; i < $scope.fileList.length; i++) {
			if ($scope.fileList[i].name == fileName) {
				$scope.fileList.splice(i, 1);
			}
		}
	};

}
