function AppCtrl($scope, $modal, archetypeRetrieveService) {

	var undefined;

	$scope.isNavbarCollapsed = true;

	$scope.archetypeList = [];

	retrieveArchetypeFileList();

	$scope.tabs = [];

	$scope.tabTitleLength = 15;

	$scope.selectedArchetypeId = 0;

	$scope.expandNavbar = function() {
		$scope.isNavbarCollapsed = false;
	};

	$scope.collapseNavbar = function() {
		$scope.isNavbarCollapsed = true;
	};

	$scope.selectOverview = function() {
		$scope.selectedArchetypeId = 0;
	};

	$scope.selectArchetype = function(archetype) {
		var contained = false;
		angular.forEach($scope.tabs, function(tab, index) {
			if (tab.id == archetype.id) {
				contained = true;
			}
		});
		if (!contained) {
			var index = $scope.tabs.push({
				'id' : archetype.id,
				'title' : archetype.name,
				'xmlPromise' : archetypeRetrieveService.getArchetypeXmlById(archetype.id),
				'adlPromise' : archetypeRetrieveService.getArchetypeAdlById(archetype.id),
			});
		}
		$scope.selectedArchetypeId = archetype.id;
	};

	$scope.getTabTitle = function(tab) {
		if (tab.title.length > $scope.tabTitleLength) {
			return tab.title.substring(0, $scope.tabTitleLength / 2) + '...' + tab.title.substring(tab.title.length - $scope.tabTitleLength / 2, tab.title.length);
		}
	};

	$scope.selectTab = function(tab) {
		$scope.selectedArchetypeId = tab.id;
	};

	$scope.closeTab = function(tab) {
		var index = $scope.tabs.indexOf(tab);
		if ($scope.selectedArchetypeId == tab.id && $scope.tabs.length > 1) {
			var newSelectedIndex = index == $scope.tabs.length - 1 ? index - 1 : index + 1;
			$scope.selectedArchetypeId = $scope.tabs[newSelectedIndex].id;
		}
		$scope.tabs.splice(index, 1);
		if ($scope.tabs.length == 0) {
			$scope.selectedArchetypeId = 0;
		}
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
					if (result.isSucceeded == 'true') {
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

	function retrieveArchetypeFileList() {
		archetypeRetrieveService.getArchetypeList().then(function(archetypeList) {
			$scope.archetypeList = archetypeList;
		});
	}

}