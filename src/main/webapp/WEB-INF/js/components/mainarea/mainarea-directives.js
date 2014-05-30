angular.module('clever.management.directives.mainarea', []).directive('mainarea', function() {
	return {
		restrict : 'EA',
		scope : {},
		controller : function($scope) {

			$scope.isNavbarCollapsed = false;
			$scope.collapseNavbar = function() {
				$scope.isNavbarCollapsed = true;
			};
			$scope.openNavbar = function() {
				$scope.isNavbarCollapsed = false;
			};
			$scope.navbarWidth = 300;
			this.setNavbarWidth = function(width) {
				$scope.navbarWidth = width;
			};
			$scope.getNavbarStyle = function(nvabarWidth) {
				return {
					width : nvabarWidth
				};
			};

		},
		link : function(scope, element, attrs) {
			element.addClass('hbox');
			element.css({
				height : '500px',
				width : '100%'
			});
		},
	};
}).directive('mainareaNavbar', function() {
	return {
		require : '^mainarea',
		transclude : true,
		restrict : 'EA',
		controller : function($scope){
			
		},
		templateUrl : 'resources/components/mainarea/mainarea-navbar.html'
	};
}).directive('mainareaSplitter', ['$document',
function($document) {
	return {
		require : '^mainarea',
		restrict : 'EA',
		controller : function($scope){
			
		},
		link : function(scope, element, attrs, mainareaCtrl) {
			var startX = 0;
			var minWidth = attrs.minWidth || 350;
			var maxWidth = attrs.maxWidth || 900;

			element.css({
				height : '100%',
				cursor : 'e-resize',
				display : 'block'
			});

			element.on('mousedown', function(event) {
				// Prevent default dragging of selected content
				event.preventDefault();
				startX = event.pageX;
				$document.on('mousemove', mousemove);
				$document.on('mouseup', mouseup);
			});

			function mousemove(event) {
				var newX = event.pageX;
				var x = event.pageX;
				var nextWidth = x;
				if (nextWidth < minWidth) {
					nextWidth = minWidth;
				}
				if (nextWidth > maxWidth) {
					nextWidth = maxWidth;
				}

				mainareaCtrl.setNavbarWidth(nextWidth);
				console.log(nextWidth);
			}

			function mouseup() {
				$document.off('mousemove', mousemove);
				$document.off('mouseup', mouseup);
			}

		},
		templateUrl : 'resources/components/mainarea/mainarea-spiltter.html'
	};
}]).directive('mainareaContent', function() {
	return {
		require : '^mainarea',
		restrict : 'EA',
		controller : function($scope){
			
		}
	};
});
