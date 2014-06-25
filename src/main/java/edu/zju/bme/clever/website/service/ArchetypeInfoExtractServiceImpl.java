package edu.zju.bme.clever.website.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openehr.am.archetype.Archetype;
import org.openehr.am.archetype.constraintmodel.CObject;
import org.openehr.am.archetype.ontology.ArchetypeOntology;
import org.openehr.am.archetype.ontology.OntologyDefinitions;
import org.openehr.am.serialize.ADLSerializer;
import org.openehr.rm.common.resource.ResourceDescription;
import org.openehr.rm.common.resource.ResourceDescriptionItem;
import org.openehr.rm.datatypes.basic.ReferenceModelName;
import org.openehr.rm.support.identification.ArchetypeID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.zju.bme.clever.website.entity.ArchetypeFile;
import edu.zju.bme.clever.website.entity.ArchetypeNode;

public class ArchetypeInfoExtractServiceImpl {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ReferenceModelName[] filterRmType = {
			ReferenceModelName.DV_TEXT, ReferenceModelName.DV_COUNT,
			ReferenceModelName.DV_QUANTITY, ReferenceModelName.DV_DATE_TIME };
	private final List<String> filterRmTypeNames;

	public ArchetypeInfoExtractServiceImpl() {
		this.filterRmTypeNames = Arrays.asList(this.filterRmType).stream()
				.map(rm -> new String(rm.getName()))
				.collect(Collectors.toList());
	}

	public ArchetypeFile extractArchetype(Archetype archetype) {
		try {
			final ArchetypeFile archetypeFile = new ArchetypeFile();
			String purpose = "";
			String keywords = "";
			String use = "";
			final String archetypeId = archetype.getArchetypeId().getValue();
			final String rmName = archetype.getArchetypeId().rmName();
			final String rmEntity = archetype.getArchetypeId().rmEntity();
			final String originalLanguage = archetype.getOriginalLanguage()
					.getCodeString();
			final String concept = archetype.getConcept();
			final String conceptName = archetype
					.getConceptName(originalLanguage);
			Optional<ResourceDescriptionItem> optionalItem = archetype
					.getDescription()
					.getDetails()
					.stream()
					.filter(item -> item.getLanguage().getCodeString()
							.equals(originalLanguage)).findFirst();
			if (optionalItem.isPresent()) {
				ResourceDescriptionItem resourceDescriptionItem = optionalItem
						.get();
				if (resourceDescriptionItem.getPurpose() != null) {
					purpose = resourceDescriptionItem.getPurpose();
				}
				if (resourceDescriptionItem.getUse() != null) {
					use = resourceDescriptionItem.getUse();
				}
				if (resourceDescriptionItem.getKeywords() != null) {
					keywords = String.join("|",
							resourceDescriptionItem.getKeywords());
				}
			}
			ADLSerializer adlSerilizer = new ADLSerializer();
			final String archetypeContent = adlSerilizer.output(archetype);
			archetypeFile.setModifyTime(Calendar.getInstance());
			archetypeFile.setContent(archetypeContent);
			archetypeFile.setName(archetypeId);
			archetypeFile.setKeywords(keywords);
			archetypeFile.setPurpose(purpose);
			archetypeFile.setUse(use);
			archetypeFile.setOriginalLanguage(originalLanguage);
			archetypeFile.setConcept(concept);
			archetypeFile.setConceptName(conceptName);
			archetypeFile.setRmName(rmName);
			archetypeFile.setRmEntity(rmEntity);

			Map<String, CObject> pathNodeMap = archetype.getPathNodeMap();
			final ArchetypeOntology ontology = archetype.getOntology();

			pathNodeMap.forEach((path, node) -> {
				if (this.filterRmTypeNames.contains(node.getRmTypeName())) {
					ArchetypeNode archetypeNode = new ArchetypeNode();
					archetypeNode.setNodePath(path);
					archetypeNode.setRmType(node.getRmTypeName());
					final String nodeId = this.getNodeIdFromNodePath(path);
					if (nodeId != "") {
						archetypeNode.setCode(nodeId);
						archetypeNode.setNodeName(ontology.termDefinition(
								originalLanguage, nodeId).getText());
					}
					archetypeFile.addArchetypeNode(archetypeNode);
				}
			});

			return archetypeFile;
		} catch (Exception ex) {
			this.logger.debug("Extract archetype {} failed.", archetype
					.getArchetypeId().getValue(), ex);
			return null;
		}
	}

	private String getNodeIdFromNodePath(String nodePath) {
		Pattern pattern = Pattern.compile("\\[(\\w+)\\]");
		Matcher matcher = pattern.matcher(nodePath);
		String nodeId = "";
		while (matcher.find()) {
			nodeId = matcher.group(matcher.groupCount());
		}
		return nodeId;
	}

}
