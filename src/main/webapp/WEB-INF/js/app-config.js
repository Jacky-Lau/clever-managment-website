var config = {
	'ARCHETYPE_LIST_URL' : '/clever-management-website/archetypes',
	'COMMIT_SEQUENCE_URL' : '/clever-management-website/commitSequence',
	'ARCHETYPE_UPLOAD_URL' : '/clever-management-website/archetype',
	'ARCHETYPE_BY_ID_URL' : '/clever-management-website/archetype/id/',
	'ARCHETYPE_BY_NAME_URL' : '/clever-management-website/archetype/name/',
};
var configModel = angular.module('clever.management.config', []);
angular.forEach(config, function(value, key) {
	configModel.constant(key, value);
});
