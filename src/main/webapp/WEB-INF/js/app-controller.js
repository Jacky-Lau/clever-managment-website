function AppCtrl($scope, $modal, $timeout, archetypeRetrieveService) {
	
	$scope.windowHeight;
	
	$scope.windowWidth;
	
	$scope.alerts = [];
	
	$scope.addAlert = function(alert) {
		$scope.alerts.push(alert);
		$timeout(function() {
			if ($scope.alerts.length > 0) {
				$scope.alerts.pop();
			}
		}, 5000);
	};

	$scope.closeAlert = function(index) {
		$scope.alerts.splice(index, 1);
	}; 

	$scope.openUploadModal = function(size) {
		var modalInstance = $modal.open({
			templateUrl : 'js/upload-modal/upload-modal.html',
			controller : UploadModalCtrl,
			backdrop : 'static',
			size : size,
		});

		modalInstance.result.then(function(result) {
			var msgBoxInstance = $modal.open({
				template : '<div class="modal-header">' +
								'<h3 class="modal-title">{{title}}</h3>' +
							'</div>' +
							'<div class="modal-body">' +
								'<div class="content-container">' +
									'<div class="row">' +
										'{{content}}' +
									'</div>' +
								'</div>' +
							'</div>' +
							'<div class="modal-footer">' +
								'<button class="btn btn-primary text-center" ng-click="ok()">' +
									'OK' +
								'</button>' +
							'</div>',	
				controller : function($scope, $modalInstance) {
					$scope.ok = function() {
						$modalInstance.close();
					};
					if (result.succeeded) {
						$scope.title = "Succeeded";
						$scope.content = "Upload archetypes succeeded.";
					} else {
						$scope.title = "Failed";
						$scope.content = "Upload archetypes failed, error: " + result.message;
					}
				},
				backdrop : 'static',
				size : 'sm',
			});
		});
	};
}