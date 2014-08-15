angular.module('clever.management.filters.pretty', []).filter('pretty', function(){
	return function(text){
		return prettyPrintOne(text);
	};
});