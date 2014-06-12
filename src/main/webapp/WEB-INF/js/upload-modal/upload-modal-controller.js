function UploadModalCtrl($scope, $modalInstance, fileUploadService) {

	$scope.title = "Upload Archetypes";
	$scope.fileList = [];
	$scope.commitSequence;

	$scope.isUploadEnabled = function() {
		return !!$scope.commitSequence && $scope.fileList.length > 0;
	};

	$scope.ok = function() {
		var uploadedFileCount = 0;
		angular.forEach($scope.fileList, function(file, index) {
			if (file.status == 'UPLOADED') {
				uploadedFileCount++;
			}
		});
		$modalInstance.close(uploadedFileCount);
	};

	$scope.uploadFiles = function() {
		if (!!$scope.commitSequence) {
			fileUploadService.getCommitSequence().then(function(commitSequence) {
				$scope.commitSequence = commitSequence;
				uploadFiles();
			});
		} else {
			uploadFiles();
		}
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

	$scope.overwriteFile = function(fileName) {
		for ( i = 0; i < $scope.fileList.length; i++) {
			if ($scope.fileList[i].name == fileName) {
				fileUploadService.uploadSingleFileToUrl($scope.fileList[i], $scope.commitSequence, true);
			}
		}
	};

	function uploadFiles() {
		for ( i = 0; i < $scope.fileList.length; i++) {
			fileUploadService.uploadSingleFileToUrl($scope.fileList[i], $scope.commitSequence, false);
		}
	}

}
