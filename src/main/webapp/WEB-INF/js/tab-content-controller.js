function tabContentCtrl($scope) {
	$scope.selectedTab = 'Tree';
	$scope.selectTab = function(tab) {
		$scope.selectedTab = tab;
	};
}
