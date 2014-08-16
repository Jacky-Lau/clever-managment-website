angular.module('clever.management.services.msgbox', []).service('msgboxService', ['$modal',
function($modal) {
		return function(title, content){
			return msgBoxInstance = $modal.open({
				template : '<div class="modal-header">' +
								'<h3 class="modal-title">{{title | translate}}</h3>' +
							'</div>' +
							'<div class="modal-body">' +
								'<div class="content-container">' +
									'<div class="row">' +
										'{{content | translate}}' +
									'</div>' +
								'</div>' +
							'</div>' +
							'<div class="modal-footer">' +
								'<button class="btn btn-primary text-center" ng-click="ok()">' +
									'OK' +
								'</button>' +
								'<button class="btn btn-warning text-center" ng-click="cancel()">' +
									'Cancel' +
								'</button>' +
							'</div>',	
				controller : function($scope, $modalInstance) {
					$scope.ok = function() {
						$modalInstance.close(true);
					};
					$scope.cancel = function() {
						$modalInstance.dismiss();
					};
					$scope.title = title;
					$scope.content = content;
				},
				backdrop : 'static',
				size : 'sm',
			});
	};
}]);
