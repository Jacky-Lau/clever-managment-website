function AppLibraryCtrl($scope, $modal) {

	$scope.addNewApp = function() {
		var modalInstance = $modal.open({
			templateUrl : 'js/app-modal/app-modal.html',
			controller : AppModalCtrl,
			backdrop : 'static',
			resolve : {
				type : function(){
					return 'add';
				}
			},
			size : 'lg',
		});
	};
	$scope.editApp = function(app) {
		var modalInstance = $modal.open({
			templateUrl : 'js/app-modal/app-modal.html',
			controller : AppModalCtrl,
			backdrop : 'static',
			resolve : {
				type : function(){
					return 'edit';
				},
				app : app
			},
			size : 'lg',
		});
	};
}
