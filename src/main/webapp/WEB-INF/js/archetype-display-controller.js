function ArchetypeDisplayCtrl($scope) {

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
			$scope.terminologies = parseTerminologies(archetype);
			$scope.originalLanguage = archetype.original_language.code_string;
			$scope.treeData = parseDefinitions(archetype);
			$scope.header = parseHeader(archetype);
			$scope.selectLanguage($scope.originalLanguage);
		});

		tab.adlPromise.then(function(adl) {
			$scope.adlText = adl;
		});
	};
	
	$scope.isDropdownOpened = false;

	$scope.selectLanguage = function(language) {
		if ($scope.selectedLanguage != language) {
			$scope.selectedLanguage = language;
			angular.forEach($scope.terminologies, function(terminology) {
				if (terminology.language == language) {
					$scope.currentTerminology = terminology;
				}
			});
			angular.forEach($scope.header.descriptions,function(description){
				if(description.language == language){
					$scope.currentDescription = description;
				}
			});
		}
		$scope.isDropdownOpened = false;
	};

	function parseHeader(archetype) {
		var header = {};
		header.concept = archetype.concept;
		header.id = archetype.archetype_id.value;
		header.descriptions = [];
		var details = archetype.description.details;
		if (angular.isArray(details)) {
			angular.forEach(details, function(detail) {
				var description = {
					copyright : detail.copyright,
					miuse : detail.miuse,
					purpose : detail.purpose,
					use : detail.use,
					language : detail.language.code_string
				};
				header.descriptions.push(description);
			});
		} else {
			var description = {
				copyright : details.copyright,
				miuse : details.miuse,
				purpose : details.purpose,
				use : details.use,
				language : details.language.code_string
			};
			header.descriptions.push(description);
		}
		return header;
	}

	function parseTerminologies(archetype) {
		var terminologies = [];
		var termDefinitions = archetype.ontology.term_definitions;
		if (angular.isArray(termDefinitions)) {
			angular.forEach(termDefinitions, function(termDefinition) {
				$scope.languages.push(termDefinition._language);
				var terminology = {
					language : termDefinition._language,
					items : []
				};
				angular.forEach(termDefinition.items, function(definition) {
					var code, text, description;
					code = definition._code;
					angular.forEach(definition.items, function(value) {
						if (value._id == 'text') {
							text = value.__text;
						} else if (value._id == 'description') {
							description = value.__text;
						}
					});
					terminology.items.push({
						code : code,
						text : text,
						description : description,
					});
				});
				terminologies.push(terminology);
			});
		} else {
			$scope.languages.push(termDefinitions._language);
			var terminology = {
				language : termDefinitions._language,
				items : []
			};
			angular.forEach(termDefinitions.items, function(definition) {
				var code, text, description;
				code = definition._code;
				angular.forEach(definition.items, function(value) {
					if (value._id == 'text') {
						text = value.__text;
					} else if (value._id == 'description') {
						description = value.__text;
					}
				});
				terminology.items.push({
					code : code,
					text : text,
					description : description,
				});
			});
			terminologies.push(terminology);
		}
		return terminologies;
	}

	function extractNode(node) {
		var type, attribute, code;
		type = node.rm_type_name;
		attribute = node.rm_attribute_name;
		if (node.node_id) {
			code = node.node_id;
		}
		var label, labelType;
		if (type) {
			labelType = 'type';
			label = type;
		} else if (attribute) {
			labelType = 'attribute';
			label = attribute;
		}
		return {
			label : {
				type : labelType,
				text : label,
				code : code
			}
		};
	}

	function processAttributes(attributes, nodes) {
		if (angular.isArray(attributes)) {
			angular.forEach(attributes, function(value) {
				var node = extractNode(value);
				if (value.children) {
					node.children = [];
					processType(value.children, node.children);
				}
				nodes.push(node);
			});
		} else {
			var node = extractNode(attributes);
			if (attributes.children) {
				node.children = [];
				processType(attributes.children, node.children);
			}
			nodes.push(node);
		}
	}

	function processType(type, nodes) {
		if (angular.isArray(type)) {
			angular.forEach(type, function(value) {
				var node = extractNode(value);
				if (value.attributes) {
					node.children = [];
					processAttributes(value.attributes, node.children);
				}
				nodes.push(node);
			});
		} else {
			var node = extractNode(type);
			if (type.attributes) {
				node.children = [];
				processAttributes(type.attributes, node.children);
			}
			nodes.push(node);
		}
	}

	function parseDefinitions(archetype) {
		var definitions = [];
		processType(archetype.definition, definitions);
		return definitions;
	}

}
