angular.module('clever.management.directives', ['clever.management.directives.splitter', 'clever.management.directives.overview', 'clever.management.directives.filesModel', 'clever.management.directives.headerTab', 'clever.management.directives.terminologyTab', 'clever.management.directives.definitionTab', 'angularBootstrapNavTree', 'toggle-switch']);
angular.module('clever.management.services', ['clever.management.services.fileUpload', 'clever.management.services.archetypeRetrieve', 'clever.management.services.archetypeParse']);
angular.module('clever.management.filters', ['clever.management.filters.pretty']);
angular.module('cleverManagementApp', ['ui.bootstrap', 'clever.management.directives', 'clever.management.services', 'clever.management.filters', 'clever.management.config']);
