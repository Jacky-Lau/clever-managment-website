function DeployCtrl($scope, $http, $timeout, archetypeRetrieveService, ARCHETYPE_DEPLOY_URL) {

	$scope.latestVersionArchetypeIds = [];
	$scope.buttonTitle = "Deploy";

	refreshData();

	function refreshData() {
		$scope.allLatestArchetypeIds = [];
		$scope.deployedArchetypeIds = [];
		archetypeRetrieveService.getDeployedArchetypeIds().then(function(deployedArchetypeIds) {
			$scope.deployedArchetypeIds = deployedArchetypeIds;
			archetypeRetrieveService.getLatestVersionArchetypeIds().then(function(result) {
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
			});
		});
	}


	$scope.deploy = function() {
		$scope.buttonTitle = "Deploying...";
		$http.get(ARCHETYPE_DEPLOY_URL).then(function(response) {
			if (response.data == 'true') {
				msgboxService("Succeeded", "Deploy archetypes succeeded.");
				refreshData();
			} else {
				msgboxService("Failed", "Deploy archetypes failed.");
			}
			$scope.buttonTitle = "Deploy";
		});
	};
}
