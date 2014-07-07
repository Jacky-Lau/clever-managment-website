function UploadModalCtrl($scope, $modalInstance, fileUploadService) {

	$scope.title = "Upload Archetypes";
	$scope.fileList = [];
	$scope.status = "AddingFile";

	$scope.validateFiles = function() {
		$scope.status = "Validating";
		fileUploadService.validateFiles($scope.fileList).then(function(results) {
			var isValidationPast = true;
			var isAllValid = true;
			angular.forEach(results, function(result) {
				if (result.status == 'INVALID') {
					isValidationPast = false;
				}
				angular.forEach($scope.fileList, function(file) {
					if (file.name == result.name) {
						file.status = result.status;
						file.message = result.message;
					}
				});
			});
			if (isValidationPast) {
				$scope.status = "ValidationPast";
			} else {
				$scope.status = "ValidationFailed";
			}
		});
	};

	$scope.uploadFiles = function() {
		fileUploadService.uploadFiles($scope.fileList).then(function(result) {
			$modalInstance.close(result);
		});
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
