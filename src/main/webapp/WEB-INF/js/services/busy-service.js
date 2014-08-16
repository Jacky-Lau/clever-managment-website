angular.module('clever.management.services.busy', []).service('busyService', function($q) {
	var self = this;
	var defer = $q.defer();
	this.isBusy = false;

	this.setBusy = function(isBusy, busyHint) {
		var busyHint = busyHint || 'iLoading';
		self.isBusy = isBusy;
		defer.notify({
			isBusy : self.isBusy,
			busyHint : busyHint,
		});
	};

	this.observeIsBusy = function() {
		return defer.promise;
	};

});
