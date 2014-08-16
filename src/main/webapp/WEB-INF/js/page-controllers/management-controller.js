function ManagementCtrl($scope, $http, $timeout, busyService, msgboxService, archetypeRetrieveService, ARCHETYPE_DEPLOY_URL) {

	$scope.latestVersionArchetypeIds = [];

	refreshData();

	function refreshData() {
		$scope.allLatestArchetypeIds = [];
		$scope.deployedArchetypeIds = [];
		busyService.setBusy(true);
		archetypeRetrieveService.getDeployedArchetypeIds().then(function(deployedArchetypeIds) {
			$scope.deployedArchetypeIds = deployedArchetypeIds;
			$scope.deployedArchetypesCount = deployedArchetypeIds.length;
			archetypeRetrieveService.getLatestVersionArchetypeIds().then(function(result) {
				$scope.allArchetypesCount = result.length;
				angular.forEach(result, function(id) {
					var isDeployed = false;
					for (var i = 0; i < $scope.deployedArchetypeIds.length; i++) {
						if (id == $scope.deployedArchetypeIds[i]) {
							isDeployed = true;
							break;
						}
					}
					$scope.latestVersionArchetypeIds.push({
						name : id,
						deployed : isDeployed,
					});
				});
				busyService.setBusy(false);
			});
		});
	}


	$scope.deploy = function() {
		busyService.setBusy(true, 'iDeploying');
		$http.get(ARCHETYPE_DEPLOY_URL).then(function(response) {
			busyService.setBusy(false);
			if (response.data == 'true') {
				msgboxService("iSucceeded", "iManagementDeploySucceeded");
				refreshData();
			} else {
				msgboxService("iFailed", "iManagementDeployFailed");
			}
		});
	};
}
