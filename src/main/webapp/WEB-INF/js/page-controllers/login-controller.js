function LoginCtrl($scope, $routeParams, $http, $location, $window) {

	var undefined;
	$scope.error = $routeParams.error || false;
	$scope.userName = undefined;
	$scope.password = undefined;

	$scope.login = function() {
		var formData = new FormData();
		formData.append("j_username", $scope.userName);
		formData.append("j_password", $scope.password);
		$http.post("/clever-management-website/j_spring_security_check", formData).then(function(response) {
			if (response.data.succeeded == 'true') {
				$scope.errorMsg = undefined;
				$window.history.back();
			} else if (response.data.succeeded == 'false') {
				$scope.errorMsg = 'User name or password is not correct.';
				$scope.password = undefined;
			} else {
				$scope.errorMsg = 'Login failed.';
				$scope.password = undefined;
			}
		});
	};
}
