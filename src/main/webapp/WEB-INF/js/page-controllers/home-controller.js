function HomeCtrl($scope) {
	$scope.slides = [];
	$scope.carouselHeight = ($scope.windowHeight - 100) / 3 * 2;
	$scope.interval = 5000;
	$scope.slides.push({
		image : '/clever-management-website/img/slide-1.png',
		text : 'Clinical Large Easy Variety Elastic Repository'
	});
	$scope.slides.push({
		image : '/clever-management-website/img/Letter-A-icon.png',
		text : '1'
	});
	$scope.slides.push({
		image : '/clever-management-website/img/Letter-A-icon.png',
		text : '2'
	});
}
