function ArchetypeDisplayCtrl($scope, archetypeParseService) {

	$scope.selectedTab = 'Header';
	
	$scope.selectTab = function(tab) {
		$scope.selectedTab = tab;
	};

	$scope.isExpandedAll = false;

	$scope.$watch('isExpandedAll', function(newValue, oldValue) {
		if (newValue) {
			if ($scope.treeControl.expand_all) {
				$scope.treeControl.expand_all();
			}
		} else {
			if ($scope.treeControl.collapse_all) {
				$scope.treeControl.collapse_all();
			}
		}
	});

	$scope.treeData = [];
	$scope.languages = [];
	$scope.treeControl = {};

	$scope.init = function(tab) {
		$scope.title = tab.title;

		tab.xmlPromise.then(function(xml) {
			$scope.xmlText = xml;
			var x2js = new X2JS();
			var archetype = x2js.xml_str2json(xml).archetype;
			var parsedResult = archetypeParseService.parseArchetype(archetype);
			$scope.terminologies = parsedResult.terminologies;
			$scope.originalLanguage = parsedResult.languages.originalLanguage;
			$scope.treeData = parsedResult.definitions;
			$scope.header = parsedResult.header;
			$scope.languages = parsedResult.languages.languages;
			$scope.selectedLanguage = $scope.originalLanguage;
		});

		tab.adlPromise.then(function(adl) {
			$scope.adlText = adl;
		});
	};
	
	$scope.isDropdownOpened = false;

	$scope.$watch('selectedLanguage', function(language) {
		if (language) {
			var currentTerm, currentConstraint;
			if ($scope.terminologies.terms) {
				angular.forEach($scope.terminologies.terms, function(term) {
					if (term.language == language.code) {
						currentTerm = term;
					}
				});
			}
			if ($scope.terminologies.constraints) {
				angular.forEach($scope.terminologies.constraints, function(constaint) {
					if (constaint.language == language.code) {
						currentConstraint = constaint;
					}
				});
			}
			$scope.currentTerminology = {
				term : currentTerm,
				constraint : currentConstraint
			};
			angular.forEach($scope.header.descriptions, function(description) {
				if (description.language == language.code) {
					$scope.currentDescription = description;
				}
			});
		}
	}); 

	$scope.selectLanguage = function(language) {
		$scope.selectedLanguage = language;
		$scope.isDropdownOpened = false;
	};
	
	$scope.selectDefinitionItem = function(definitionItem){
		$scope.selectedDefinitionItem = definitionItem.label;
	};

}
