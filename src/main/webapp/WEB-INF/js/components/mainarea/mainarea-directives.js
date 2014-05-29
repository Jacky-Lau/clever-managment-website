angular.module('clever.management.directives.mainarea', []).directive('mainarea', function() {
	return {
		restrict : 'EA',
		controller : mainareaCtrl,
		link : function(scope, element, attrs) {
			element.addClass('hbox');
		},
	};
}).directive('mainareaNavbar', function() {
	return {
		require : '^mainarea',
		transclude : true,
		restrict : 'EA',
		controller : mainareaNavbarCtrl,
		templateUrl : 'resources/components/mainarea/mainarea-navbar.html'
	};
}).directive('mainareaSplitter', ['$document',
function($document) {
	return {
		require : '^mainarea',
		restrict : 'EA',
		controller : mainareaSplitterCtrl,
		link : function(scope, element, attrs, mainareaCtrl) {
			var startX = 0;
			var minWidth = attrs.minWidth || 335;
			var maxWidth = attrs.maxWidth || 966;

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
		controller : mainareaContentCtrl
	};
});
