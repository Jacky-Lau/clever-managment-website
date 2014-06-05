function AppCtrl($scope, $modal, archetypeAcquireService) {

	$scope.isNavbarCollapsed = false;

	$scope.openNavbar = function() {
		$scope.isNavbarCollapsed = false;
	};

	$scope.collapseNavbar = function() {
		$scope.isNavbarCollapsed = true;
	};

	$scope.archetypeList = [];

	archetypeAcquireService.getArchetypeList('/clever-management-website/archetypeList').then(function(archetypeList) {
		$scope.archetypeList = archetypeList;
	});

	$scope.selectArchetypeById = function(archetypeId) {
		$scope.selectedArchetypeId = archetypeId;
	};

	$scope.tabs = [{
		id : '1',
		title : 'Dynamic Title 1',
		content : 'Dynamic content 1'
	}, {
		id : '2',
		title : 'Dynamic Title 2',
		content : 'Dynamic content 2'
	}];

	$scope.selectedArchetypeId = 0;

	$scope.openUploadModal = function(size) {
		var modalInstance = $modal.open({
			templateUrl : 'resources/upload-modal/upload-modal.html',
			controller : uploadModalCtrl,
			backdrop : 'static',
			size : size,
		});
	};
}