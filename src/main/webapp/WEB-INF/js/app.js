angular.module('clever.management.directives', ['clever.management.directives.splitter','clever.management.directives.filesModel']);
angular.module('clever.management.services', ['clever.management.services.fileUpload']);
angular.module('cleverManagementApp', [ 'ui.bootstrap', 'clever.management.directives', 'clever.management.services' ]);