function UploadCtrl($scope, $modal, fileUploadService, msgboxService) {
	
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
		$scope.status = 'Uploading';
		fileUploadService.uploadFiles($scope.fileList).then(function(result) {
			if (result.succeeded) {
				msgboxService("iSucceeded", "iManagementUploadSucceeded");
				$scope.status = 'UploadSucceeded';
			} else {
				msgboxService("iFailed", "iManagementUploadFailed");
				$scope.status = 'UploadFailed';
			}
		});
	};

	$scope.deleteFile = function(fileName) {
		for ( i = 0; i < $scope.fileList.length; i++) {
			if ($scope.fileList[i].name == fileName) {
				$scope.fileList.splice(i, 1);
			}
		}
	};

	$scope.reset = function() {
		$scope.fileList = [];
		$scope.status = "AddingFile";
	};

}
