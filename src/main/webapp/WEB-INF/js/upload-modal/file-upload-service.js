angular.module('clever.management.services.fileUpload', []).service('fileUploadService', ['$http',
function($http) {
	this.uploadSingleFileToUrl = function(file, commitSequence, overwriteChange, uploadUrl) {
		file.status = 'UPLOADING';
		var formData = new FormData();
		formData.append('file', file.file);
		formData.append('commitSequenceId', commitSequence.id);
		formData.append('overwriteChange', overwriteChange);
		$http.post(uploadUrl, formData, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(function(result) {
			file.status = result.fileStatus;
		}).error(function() {
			file.status = 'FAILED';
		});
	};

	this.getCommitSequence = function(url) {
		return $http.get(url).then(function(response) {
			return response.data;
		});
	};
}]);
