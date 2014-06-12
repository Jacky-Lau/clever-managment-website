angular.module('clever.management.directives.archetypeTreeview', []).directive('archetypeTreeview', [
function() {
	return {
		restrict : 'EA',
		scope : {
			archetypeXml : '=',
		},
		templateUrl : 'js/archetype-treeview/archetype-treeview.html',
		link : function(scope, element, attrs) {
			scope.treeControl = {};
			
			scope.isExpandedAll = false;

			scope.$watch('isExpandedAll', function(newValue, oldValue) {
				if (newValue) {
					if(scope.treeControl.expand_all){
						scope.treeControl.expand_all();
					}				
				} else {
					if(scope.treeControl.collapse_all){
						scope.treeControl.collapse_all();
					}			
				}
			}); 
			
			scope.$watch('archetypeXml', function(archetypeXml) {
				if (archetypeXml) {
					var x2js = new X2JS();
					var archetype = x2js.xml_str2json(archetypeXml).archetype;
					var ontology = parseOntology(archetype);
					scope.treeData = parseDefinition(archetype, ontology);
				}
			});

			function parseOntology(archetype) {
				var ontology = [];
				angular.forEach(archetype.ontology.term_definitions.items, function(definition) {
					var id, text, description;
					id = definition._code;
					angular.forEach(definition.items, function(value) {
						if (value._id == 'text') {
							text = value.__text;
						} else if (value._id == 'description') {
							description = value.__text;
						}
					});
					ontology.push({
						id : id,
						text : text,
						description : description,
					});
				});
				return ontology;
			}

			function getOntologyById(id, ontology) {
				var matchedOntology;
				angular.forEach(ontology, function(value) {
					if (value.id == id) {
						matchedOntology = value;
					}
				});
				return matchedOntology;
			}

			function extractNode(node, ontology) {
				var type, attribute, nodeOntology;
				type = node.rm_type_name;
				attribute = node.rm_attribute_name;
				if (node.node_id) {
					nodeOntology = getOntologyById(node.node_id, ontology);
				}
				// return {
				// type : type,
				// attribute : attribute,
				// ontology : ontology
				// };
				var label, labelType;
				if (type) {
					labelType = 'type';
					label = type;
				}
				else if (attribute) {
					labelType = 'attribute';
					label =  attribute;
				}
				return {
					label : {
							type : labelType,
							text : label,
							ontology : nodeOntology
						}			
				};
			}

			function processAttributes(attributes, nodes, ontology) {
				if (angular.isArray(attributes)) {
					angular.forEach(attributes, function(value) {
						var node = extractNode(value, ontology);
						if (value.children) {
							node.children = [];
							processType(value.children, node.children, ontology);
						}
						nodes.push(node);
					});
				} else {
					var node = extractNode(attributes, ontology);
					if (attributes.children) {
						node.children = [];
						processType(attributes.children, node.children, ontology);
					}
					nodes.push(node);
				}
			}

			function processType(type, nodes, ontology) {
				if (angular.isArray(type)) {
					angular.forEach(type, function(value) {
						var node = extractNode(value, ontology);
						if (value.attributes) {
							node.children = [];
							processAttributes(value.attributes, node.children, ontology);
						}
						nodes.push(node);
					});
				} else {
					var node = extractNode(type, ontology);
					if (type.attributes) {
						node.children = [];
						processAttributes(type.attributes, node.children, ontology);
					}
					nodes.push(node);
				}
			}

			function parseDefinition(archetype, ontology) {
				var definitions = [];
				processType(archetype.definition, definitions, ontology);
				return definitions;
			}

		}
	};
}]);
