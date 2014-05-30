function AppCtrl($scope, $modal) {
	$scope.isNavbarCollapsed = false;
	$scope.openNavbar = function() {
		$scope.isNavbarCollapsed = false;
	};
	$scope.collapseNavbar = function() {
		$scope.isNavbarCollapsed = true;
	};
	$scope.openUploadModal = function(size) {
		var modalInstance = $modal.open({
			templateUrl : 'resources/upload-modal/upload-modal.html',
			controller : uploadModalCtrl,
			backdrop: 'static',
			size : size,
		});
	};
}