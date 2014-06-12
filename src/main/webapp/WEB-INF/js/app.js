angular.module('clever.management.directives', ['clever.management.directives.splitter', 'clever.management.directives.overview', 'clever.management.directives.filesModel', 'clever.management.directives.archetypeTreeview', 'angularBootstrapNavTree', 'toggle-switch']);
angular.module('clever.management.services', ['clever.management.services.fileUpload', 'clever.management.services.archetypeRetrieve']);
angular.module('clever.management.filters', ['clever.management.filters.pretty']);
angular.module('cleverManagementApp', ['ui.bootstrap', 'ngAnimate', 'clever.management.directives', 'clever.management.services', 'clever.management.filters', 'clever.management.config']);
