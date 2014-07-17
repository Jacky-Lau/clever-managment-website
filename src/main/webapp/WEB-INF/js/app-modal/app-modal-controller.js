function AppModalCtrl($scope, $modalInstance, $http, type, WEBSITE_DOMAIN, TEMP_URL) {

	var undefined;
	$scope.file = undefined;
	$scope.imgErrorMsg = undefined;
	$scope.imgPath = undefined;

	if (type == 'add') {
		$scope.editMode = false;
		$scope.title = 'Add new application';
	} else if (type == 'edit') {
		$scope.editMode = true;
		$scope.title = 'Edit application';
	}

	$scope.previewImg = function(file) {
		var formData = new FormData();
		formData.append('img', file.file);
		$http.post(TEMP_URL + '/img', formData, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(function(result) {
			if (result.succeeded) {
				$scope.imgPath = WEBSITE_DOMAIN + result.message;
				$scope.imgErrorMsg = undefined;
			} else {
				$scope.imgErrorMsg = result.message;
				$scope.file = undefined;
				$scope.imgPath = undefined;
			}
		}).error(function() {
			$scope.imgErrorMsg = 'Preview ' + file.name + ' failed.';
			$scope.file = undefined;
			$scope.imgPath = undefined;
		});
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
}