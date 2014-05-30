function uploadModalCtrl($scope, $modalInstance) {

	$scope.title = "Upload Archetypes";
	$scope.isUploadEnabled = false;
	$scope.fileList = [];

	$scope.ok = function() {
		$modalInstance.close();
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

	$scope.deleteFile = function(fileName) {
		for ( i = 0; i < $scope.fileList; i++) {
			if (fileList[i].name == fileName) {
				fileList.splice(i, 1);
			}
		}
	};

}
