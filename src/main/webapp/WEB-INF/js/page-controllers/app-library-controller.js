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
					msg : 'Add application succeeded.'
				});
				refreshData();
			} else {
				$scope.addAlert({
					type : 'danger',
					msg : 'Add application failed.'
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
					msg : 'Edit application succeeded.'
				});
				refreshData();
			} else {
				$scope.addAlert({
					type : 'danger',
					msg : 'Edit application failed.'
				});
			}
		});
	};

	$scope.deleteApplication = function(app) {
		msgboxService('Delete Application', 'Are you sure you want to delete application ' + app.name + '?').result.then(function(confirm) {
			if (confirm) {
				appLibraryService.deleteApplication(app).then(function(result) {
					$scope.addAlert({
						type : 'warning',
						msg : 'Delete application ' + app.name + ' succeeded.'
					});
					refreshData();
				});
			}
		});
	};

	function refreshData() {
		appLibraryService.getAllApplications().then(function(result) {
			$scope.applications = result;
		});
	}

}
