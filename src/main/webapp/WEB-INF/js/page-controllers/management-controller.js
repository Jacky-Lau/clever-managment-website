function ManagementCtrl($scope, $http, $timeout, busyService, msgboxService, archetypeRetrieveService, ARCHETYPE_DEPLOY_URL, AUTHENTICATION_URL) {

	$scope.latestVersionArchetypeIds = [];

	$http.get(AUTHENTICATION_URL).then(function(response) {
		if (response.data == 'true') {
			refreshData();
		}
	});

	function refreshData() {
		$scope.allHosts = [];
		$scope.deployedArchetypeIds = [];
		$scope.draftCount = 0;
		$scope.teamReviewCount = 0;
		$scope.publishedCount = 0;
		$scope.deprecatedCount = 0;
		busyService.setBusy(true);
		archetypeRetrieveService.getDeployedArchetypeIds().then(function(deployedArchetypeIds) {
			$scope.deployedArchetypeIds = deployedArchetypeIds;
			$scope.deployedArchetypesCount = deployedArchetypeIds.length;
			archetypeRetrieveService.getLatestVersionArchetypeIds().then(function(result) {
				$scope.allHostsCount = result.length;
				angular.forEach(result, function(host) {
					var isDeployed = false;
					var name = host.name + '.' + host.latestVersion;
					if ($scope.deployedArchetypeIds.indexOf(name) != -1) {
						isDeployed = true;
					}
					if (host.lifeCycle == 'Draft') {
						$scope.draftCount++;
					} else if (host.lifeCycle == 'TeamReview') {
						$scope.teamReviewCount++;
					} else if (host.lifeCycle == 'Published') {
						$scope.publishedCount++;
					} else if (host.lifeCycle == 'Deprecated') {
						$scope.deprecatedCount++;
					}
					$scope.allHosts.push({
						name : name,
						lifeCycle : host.lifeCycle,
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
