function AppModalCtrl($scope, $modalInstance, type, app, appLibraryService, WEBSITE_DOMAIN) {

	var undefined;

	$scope.nameValidation = {
		validated : true,
		msg : undefined,
	};
	$scope.urlValidation = {
		validated : true,
		msg : undefined,
	};
	$scope.descriptionValidation = {
		validated : true,
		msg : undefined,
	};
	$scope.imgValidation = {
		validated : true,
		msg : undefined,
	};

	$scope.application = {
		id : undefined,
		name : undefined,
		description : undefined,
		url : undefined,
		img : {
			file : undefined,
			path : undefined,
		},
	};

	if (type == 'add') {
		$scope.editMode = false;
		$scope.title = 'iAppAdd';
	} else if (type == 'edit') {
		$scope.editMode = true;
		$scope.title = 'iAppEdit';
		$scope.application.id = app.id;
		$scope.application.name = app.name;
		$scope.application.description = app.description;
		$scope.application.url = app.url;
		$scope.imgPath = WEBSITE_DOMAIN + app.imgPath;
	}

	$scope.previewImg = function(file) {
		appLibraryService.uploadTempImage(file.file).then(function(result) {
			if (result.succeeded) {
				$scope.imgPath = WEBSITE_DOMAIN + result.message;
				$scope.imgValidation.validated = true;
			} else {
				$scope.imgValidation.validated = false;
				$scope.imgValidation.msg = result.message;
				$scope.imgPath = undefined;
			}
		});
	};

	$scope.confirm = function() {
		validate();
		if ($scope.nameValidation.validated && $scope.descriptionValidation.validated && $scope.urlValidation.validated && $scope.imgValidation.validated) {
			if (!$scope.editMode) {
				appLibraryService.uploadNewApplication($scope.application).then(function(result) {
					$modalInstance.close(result);
				});
			} else {
				appLibraryService.updateApplication($scope.application).then(function(result) {
					$modalInstance.close(result);
				});
			}
		}
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};

	function validate() {
		if (!$scope.application.name || $scope.application.name == '') {
			$scope.nameValidation.validated = false;
			$scope.nameValidation.msg = "Name can not be empty.";
		} else {
			$scope.nameValidation.validated = true;
		}
		if (!$scope.application.description || $scope.application.description == '') {
			$scope.descriptionValidation.validated = false;
			$scope.descriptionValidation.msg = "Description can not be empty.";
		} else {
			$scope.descriptionValidation.validated = true;
		}
		if (!$scope.application.url || $scope.application.url == '') {
			$scope.urlValidation.validated = false;
			$scope.urlValidation.msg = "URL can not be empty.";
		} else {
			$scope.urlValidation.validated = true;
		}
		if (!$scope.editMode && !$scope.application.img.file) {
			$scope.imgValidation.validated = false;
			$scope.imgValidation.msg = "Please choose an image.";
		}
	}

}