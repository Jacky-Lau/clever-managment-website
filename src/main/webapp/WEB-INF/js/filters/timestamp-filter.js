angular.module('clever.management.filters.timestamp', []).filter('timestamp', function(){
	return function(text){
		var date = new Date();
		return text + '?' + date.getTime();
	};
});