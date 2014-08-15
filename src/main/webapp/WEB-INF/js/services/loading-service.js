angular.module('clever.management.services.loading', []).service('loadingService', function($q) {
	var self = this;
	var defer = $q.defer();
	this.isLoading = false;

	this.setLoading = function(isLoading) {
		self.isLoading = isLoading;
		defer.notify(self.isLoading);
	};
	this.observeIsLoading = function() {
		return defer.promise;
	};
});
