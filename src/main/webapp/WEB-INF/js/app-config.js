var config = {
	'ARCHETYPE_LIST_URL' : '/clever-management-website/archetypes',
	'COMMIT_SEQUENCE_URL' : '/clever-management-website/commitSequence',
	'ARCHETYPE_UPLOAD_URL' : '/clever-management-website/archetypes',
	'ARCHETYPE_VALIDATE_URL' : '/clever-management-website/archetypes/validation',
	'ARCHETYPE_BY_ID_URL' : '/clever-management-website/archetype/id/',
	'ARCHETYPE_BY_NAME_URL' : '/clever-management-website/archetype/name/',
	'ALL_ARCHETYPE_LIST_URL' : '/clever-management-website/archetypes/all',
	'DEPLOYED_ARCHETYPE_LIST_URL' : '/clever-management-website/archetypes/deployed',
	'ARCHETYPE_DEPLOY_URL' : '/clever-management-website/archetypes/actions/deploy',
	'ARCHETYPE_BRIEF_INFO_URL' : '/clever-management-website/archetypes/briefInfo',
};
var configModel = angular.module('clever.management.config', []);
angular.forEach(config, function(value, key) {
	configModel.constant(key, value);
});
