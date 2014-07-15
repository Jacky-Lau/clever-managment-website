function DeployCtrl($scope, $http, $timeout, archetypeRetrieveService, ARCHETYPE_DEPLOY_URL) {

	$scope.latestVersionArchetypeIds = [];
	$scope.alerts = [];
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
				$scope.alerts.push({
					type : 'success',
					msg : 'Deploy archetypes succeeded.'
				});
				$timeout(function() {
					$scope.alerts.shift();
				}, 5000);
				refreshData();
			} else {
				$scope.alerts.push({
					type : 'danger',
					msg : 'Deploy archetypes failed.'
				});
				$timeout(function() {
					$scope.alerts.shift();
				}, 5000);
			}
			$scope.buttonTitle = "Deploy";
		});
	};
}
