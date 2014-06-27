function UploadModalCtrl($scope, $modalInstance, fileUploadService) {

	$scope.title = "Upload Archetypes";
	$scope.fileList = [];
	$scope.status = "AddingFile";

	$scope.ok = function() {
		var uploadedFileCount = 0;
		angular.forEach($scope.fileList, function(file, index) {
			if (file.status == 'UPLOADED') {
				uploadedFileCount++;
			}
		});
		$modalInstance.close(uploadedFileCount);
	};

	$scope.validateFiles = function() {
		$scope.status = "Validating";
		fileUploadService.validateFiles($scope.fileList).then(function(results) {
			var isValidationPast = true;
			var isAllValid = true;
			angular.forEach(results, function(result) {
				if (result.status == 'INVALID') {
					isValidationPast = false;
				}
				if (result.status != 'VALID') {
					isAllValid = false;
				}
				angular.forEach($scope.fileList, function(file) {
					if (file.name == result.name) {
						file.status = result.status;
						file.message = result.message;
					}
				});
			});
			if (isValidationPast) {
				if (isAllValid) {
					$scope.status = "ValidationPast";
				} else {
					$scope.status = "ExistChangedFile";
				}
			} else {
				$scope.status = "ValidationFailed";
			}
		});
	};

	$scope.uploadFiles = function() {
		if (!$scope.commitSequence) {
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

	function uploadFiles() {
		for ( i = 0; i < $scope.fileList.length; i++) {
			fileUploadService.uploadSingleFileToUrl($scope.fileList[i], $scope.commitSequence, false);
		}
	}

}
