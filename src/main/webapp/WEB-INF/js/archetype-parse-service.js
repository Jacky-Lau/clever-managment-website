angular.module('clever.management.services.archetypeParse', []).service('archetypeParseService', [
function() {

	this.parseArchetype = function(archetype) {
		var header = this.parseHeader(archetype);
		var terminologies = this.parseTerminology(archetype);
		var definitions = this.parseDefinition(archetype);
		var languages = this.parseLanguage(archetype);
		return {
			header : header,
			terminologies : terminologies,
			definitions : definitions,
			languages : languages
		};
	};

	this.parseHeader = function(archetype) {
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
	};

	this.parseLanguage = function(archetype) {
		var languages = [];
		var originalLanguage = {
			code : archetype.original_language.code_string,
			terminology : archetype.original_language.terminology_id.value,
		};
		var descriptions = archetype.description.details;
		if (angular.isArray(descriptions)) {
			angular.forEach(descriptions, function(description) {
				languages.push({
					code : description.language.code_string,
					terminology : description.language.terminology_id.value
				});
			});
		} else {
			languages.push({
				code : descriptions.language.code_string,
				terminology : descriptions.language.terminology_id.value
			});
		}
		return {
			originalLanguage : originalLanguage,
			languages : languages
		};
	};

	this.parseTerminology = function(archetype) {
		var termDefinitions = archetype.ontology.term_definitions;
		var constrainDefinitions = archetype.ontology.constraint_definitions;
		return {
			terms : parseTermDefinition(termDefinitions),
			constraints : parseConstrainDefinition(constrainDefinitions)
		};
	};

	function parseTermDefinition(termDefinitions) {
		if (termDefinitions) {
			var terms = [];
			if (angular.isArray(termDefinitions)) {
				angular.forEach(termDefinitions, function(termDefinition) {
					var term = {
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
						term.items.push({
							code : code,
							text : text,
							description : description,
						});
					});
					terms.push(term);
				});
			} else {
				var term = {
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
					term.items.push({
						code : code,
						text : text,
						description : description,
					});
				});
				terms.push(term);
			}
			return terms;
		}
	}

	function parseConstrainDefinition(constraintDefinitions) {
		if (constraintDefinitions) {
			var constraints = [];
			if (angular.isArray(constraintDefinitions)) {
				angular.forEach(constraintDefinitions, function(constraintDefinition) {
					var constraint = {
						language : constraintDefinition._language,
						items : []
					};
					angular.forEach(constraintDefinition.items, function(definition) {
						var code, text, description;
						code = definition._code;
						angular.forEach(definition.items, function(value) {
							if (value._id == 'text') {
								text = value.__text;
							} else if (value._id == 'description') {
								description = value.__text;
							}
						});
						constraint.items.push({
							code : code,
							text : text,
							description : description,
						});
					});
					constraints.push(constraint);
				});
			} else {
				var constraint = {
					language : termDefinitions._language,
					items : []
				};
				angular.forEach(constraintDefinitions.items, function(definition) {
					var code, text, description;
					code = definition._code;
					angular.forEach(constraint.items, function(value) {
						if (value._id == 'text') {
							text = value.__text;
						} else if (value._id == 'description') {
							description = value.__text;
						}
					});
					constraint.items.push({
						code : code,
						text : text,
						description : description,
					});
				});
				constraints.push(constraint);
			}
			return constraints;
		}
	}


	this.parseDefinition = function(archetype) {
		var definitions = [];
		processNode(archetype.definition, definitions);
		return definitions;
	};

	function extractNode(node) {
		var type, attribute, code, occurrences, existence;
		type = node.rm_type_name;
		attribute = node.rm_attribute_name;
		if (node.node_id) {
			code = node.node_id;
		}
		var label, labelType;
		if (type) {
			labelType = 'type';
			label = type;
			occurrences = node.occurrences;
		} else if (attribute) {
			labelType = 'attribute';
			label = attribute;
			existence = node.existence;
		}
		return {
			label : {
				type : labelType,
				text : label,
				code : code,
				occurrences : occurrences,
				existence : existence
			}
		};
	}

	function processCodePhrase(codePhraseNode, nodes) {
		var labelType = 'codePhrase';
		var codeList;
		if(codePhraseNode.code_list){
			codeList = codePhraseNode.code_list;
		}
		if(codePhraseNode.reference){
			codeList = codePhraseNode.reference;
		}
		if (angular.isArray(codeList)) {
			angular.forEach(codeList, function(code) {
				nodes.push({
					label : {
						type : labelType,
						text : '',
						code : code
					}
				});
			});
		} else {
			nodes.push({
				label : {
					type : labelType,
					text : '',
					code : codeList
				}
			});
		}
	}

	function processNode(node, nodes) {
		if (angular.isArray(node)) {
			angular.forEach(node, function(value) {
				var extractedNode = extractNode(value);
				if (value.attributes) {
					extractedNode.children = [];
					processNode(value.attributes, extractedNode.children);
				}
				if (value.children) {
					extractedNode.children = [];
					processNode(value.children, extractedNode.children);
				}
				if (extractedNode.label.type == 'type' && extractedNode.label.text == 'CODE_PHRASE') {
					extractedNode.children = [];
					processCodePhrase(value.code_list, extractedNode.children);
				}
				nodes.push(extractedNode);
			});
		} else {
			var extractedNode = extractNode(node);
			if (node.attributes) {
				extractedNode.children = [];
				processNode(node.attributes, extractedNode.children);
			}
			if (node.children) {
				extractedNode.children = [];
				processNode(node.children, extractedNode.children);
			}
			if (extractedNode.label.type == 'type' && extractedNode.label.text == 'CODE_PHRASE') {
				extractedNode.children = [];
				processCodePhrase(node, extractedNode.children);
			}
			nodes.push(extractedNode);
		}
	}

}]);
