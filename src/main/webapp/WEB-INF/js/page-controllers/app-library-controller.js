function AppLibraryCtrl($scope, $modal, $timeout, appLibraryService, msgboxService, WEBSITE_DOMAIN) {

	$scope.applications = [];
	$scope.websiteDomain = WEBSITE_DOMAIN;

	refreshData();

	$scope.addNewApp = function() {
		var modalInstance = $modal.open({
			templateUrl : 'js/app-modal/app-modal.html',
			controller : AppModalCtrl,
			backdrop : 'static',
			resolve : {
				type : function() {
					return 'add';
				},
				app : function() {
					return;
				}
			},
			size : 'lg',
		});
		modalInstance.result.then(function(result) {
			if (result.succeeded) {
				$scope.addAlert({
					type : 'success',
					msg : 'iAddAppSucceeded'
				});
				refreshData();
			} else {
				$scope.addAlert({
					type : 'danger',
					msg : 'iAddAppFailed'
				});
			}
		});
	};

	$scope.editApp = function(app) {
		var modalInstance = $modal.open({
			templateUrl : 'js/app-modal/app-modal.html',
			controller : AppModalCtrl,
			backdrop : 'static',
			resolve : {
				type : function() {
					return 'edit';
				},
				app : function() {
					return app;
				}
			},
			size : 'lg',
		});
		modalInstance.result.then(function(result) {
			if (result.succeeded) {
				$scope.addAlert({
					type : 'success',
					msg : 'iEditAppSucceeded'
				});
				refreshData();
			} else {
				$scope.addAlert({
					type : 'danger',
					msg : 'iEditAppFailed'
				});
			}
		});
	};

	$scope.deleteApplication = function(app) {
		msgboxService('iDelete', 'iDeleteAppQuiz').result.then(function(confirm) {
			if (confirm) {
				appLibraryService.deleteApplication(app).then(function(result) {
					$scope.addAlert({
						type : 'warning',
						msg : 'iDeleteAppSucceeded'
					});
					refreshData();
				});
			}
		});
	};

	$scope.getFixedText = function(text, width, wordWidth, trimWordCount) {
		wordWidth = wordWidth || 7;
		trimWordCount = trimWordCount || 3;
		var max = parseInt(width / wordWidth);
		var fixedText = text;
		if (text && text.length > max) {
			fixedText = text.substring(0, max - trimWordCount) + '...';
		}
		return fixedText;
	};

	function refreshData() {
		appLibraryService.getAllApplications().then(function(result) {
			$scope.applications = result;
		});
	}

}
