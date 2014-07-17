var prefix = '/clever-management-website';
var config = {
	'WEBSITE_DOMAIN' : prefix,
	'ARCHETYPE_LIST_URL' : prefix + '/archetypes',
	'COMMIT_SEQUENCE_URL' : prefix + '/commitSequence',
	'ARCHETYPE_UPLOAD_URL' : prefix + '/archetypes',
	'ARCHETYPE_VALIDATE_URL' : prefix + '/archetypes/validation',
	'ARCHETYPE_BY_ID_URL' : prefix + '/archetype/id/',
	'ARCHETYPE_BY_NAME_URL' : prefix + '/archetype/name/',
	'LATEST_VERSION_ARCHETYPE_LIST_URL' : prefix + '/archetypes/latest',
	'DEPLOYED_ARCHETYPE_LIST_URL' : prefix + '/archetypes/deployed',
	'ARCHETYPE_DEPLOY_URL' : prefix + '/archetypes/actions/deploy',
	'ARCHETYPE_BRIEF_INFO_URL' : prefix + '/archetypes/briefInfo',
	'AUTHENTICATION_URL' : prefix + '/authentication',
	'APPLICATION_BY_ID_URL' : prefix + '/application/id/',
	'APPLICATIONS_URL' : prefix + '/applications',
	'APPLICATION_UPLOAD_URL' : prefix + '/application',
	'TEMP_URL' : prefix + '/temp',
};
var configModel = angular.module('clever.management.config', []);
angular.forEach(config, function(value, key) {
	configModel.constant(key, value);
});
