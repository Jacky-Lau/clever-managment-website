function ArchetypeDisplayCtrl($scope) {

	$scope.init = function(tab) {
		
	};

	$scope.selectedTab = 'Definition';
	$scope.selectTab = function(tab) {
		$scope.selectedTab = tab;
	};
}
