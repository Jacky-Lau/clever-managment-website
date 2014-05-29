function mainareaCtrl($scope) {
	$scope.isNavbarCollapsed = false;
	$scope.collapseNavbar = function() {
		$scope.isNavbarCollapsed = true;
	};
	$scope.openNavbar = function() {
		$scope.isNavbarCollapsed = false;
	};
	$scope.navbarStyle = {
		'width' : 300
	};
	this.setNavbarWidth = function(width) {
		$scope.navbarStyle = {
			'width' : width
		};
	};

}

function mainareaNavbarCtrl($scope) {

}

function mainareaSplitterCtrl($scope) {

}

function mainareaContentCtrl($scope) {

}