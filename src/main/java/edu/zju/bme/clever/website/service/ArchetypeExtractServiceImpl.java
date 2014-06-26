package edu.zju.bme.clever.website.service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openehr.am.archetype.Archetype;
import org.openehr.am.archetype.constraintmodel.CObject;
import org.openehr.am.archetype.ontology.ArchetypeOntology;
import org.openehr.am.archetype.ontology.ArchetypeTerm;
import org.openehr.am.archetype.ontology.OntologyDefinitions;
import org.openehr.am.serialize.ADLSerializer;
import org.openehr.rm.common.resource.ResourceDescription;
import org.openehr.rm.common.resource.ResourceDescriptionItem;
import org.openehr.rm.datatypes.basic.ReferenceModelName;
import org.openehr.rm.support.identification.ArchetypeID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.zju.bme.clever.website.entity.ArchetypeFile;
import edu.zju.bme.clever.website.entity.ArchetypeNode;
import edu.zju.bme.clever.website.entity.ArchetypeRelation;

@Service("archetypeExtractService")
public class ArchetypeExtractServiceImpl implements ArchetypeExtractService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Map<String, String> filterRmTypeNames = new HashMap<String, String>();

	public ArchetypeExtractServiceImpl() {
		this.filterRmTypeNames.put(ReferenceModelName.DV_TEXT.getName(),
				"value");
		this.filterRmTypeNames.put(ReferenceModelName.DV_COUNT.getName(),
				"magnitude");
		this.filterRmTypeNames.put(ReferenceModelName.DV_QUANTITY.getName(),
				"magnitude");
		this.filterRmTypeNames.put(ReferenceModelName.DV_DATE_TIME.getName(),
				"value");
		this.filterRmTypeNames.put(ReferenceModelName.CLUSTER.getName(),
				"items");
	}

	@Override
	public ArchetypeFile extractArchetype(Archetype archetype) {
		try {
			final ArchetypeFile archetypeFile = new ArchetypeFile();
			final Map<String, String> archetypeAttributes = new ConcurrentHashMap<String, String>();
			archetypeAttributes.put("archetypeId", archetype.getArchetypeId()
					.getValue());
			archetypeAttributes.put("rmName", archetype.getArchetypeId()
					.rmName());
			archetypeAttributes.put("rmEntity", archetype.getArchetypeId()
					.rmEntity());
			archetypeAttributes.put("originalLanguage", archetype
					.getOriginalLanguage().getCodeString());
			archetypeAttributes.put("concept", archetype.getConcept());
			archetypeAttributes.put("conceptName",
					archetype.getConceptName(archetypeAttributes
							.get("originalLanguage")));
			archetype
					.getDescription()
					.getDetails()
					.stream()
					.filter(item -> item
							.getLanguage()
							.getCodeString()
							.equals(archetypeAttributes.get("originalLanguage")))
					.findFirst()
					.ifPresent(
							item -> {
								if (item.getPurpose() != null) {
									archetypeAttributes.put("purpose",
											item.getPurpose());
								}
								if (item.getUse() != null) {
									archetypeAttributes.put("use",
											item.getUse());
								}
								if (item.getKeywords() != null) {
									archetypeAttributes.put("keywords", String
											.join("|", item.getKeywords()));
								}
							});

			ADLSerializer adlSerilizer = new ADLSerializer();
			archetypeAttributes.put("archetypeContent",
					adlSerilizer.output(archetype));
			archetypeFile.setModifyTime(Calendar.getInstance());
			archetypeFile.setContent(archetypeAttributes
					.get("archetypeContent"));
			archetypeFile.setName(archetypeAttributes.get("archetypeId"));
			archetypeFile.setKeywords(archetypeAttributes.get("keywords"));
			archetypeFile.setPurpose(archetypeAttributes.get("purpose"));
			archetypeFile.setUse(archetypeAttributes.get("use"));
			archetypeFile.setOriginalLanguage(archetypeAttributes
					.get("originalLanguage"));
			archetypeFile.setConcept(archetypeAttributes.get("concept"));
			archetypeFile
					.setConceptName(archetypeAttributes.get("conceptName"));
			archetypeFile.setRmName(archetypeAttributes.get("rmName"));
			archetypeFile.setRmEntity(archetypeAttributes.get("rmEntity"));

			Map<String, CObject> pathNodeMap = archetype.getPathNodeMap();
			final ArchetypeOntology ontology = archetype.getOntology();

			pathNodeMap.forEach((path, node) -> {
				if (this.filterRmTypeNames.containsKey(node.getRmTypeName())) {
					if (this.filterRmTypeNames.get(node.getRmTypeName())
							.equals(node.getParent().getRmAttributeName())) {
						ArchetypeNode archetypeNode = new ArchetypeNode();
						archetypeNode.setNodePath(path);
						archetypeNode.setRmType(node.getRmTypeName());
						String nodeId = this.getNodeIdFromNodePath(path);
						if (nodeId != "") {
							archetypeNode.setCode(nodeId);
							archetypeNode.setNodeName(ontology
									.termDefinition(
											archetypeAttributes
													.get("originalLanguage"),
											nodeId).getText());
						}
						archetypeFile.addArchetypeNode(archetypeNode);
					}
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

	@Override
	public List<ArchetypeRelation> extractArchetypeRelations(
			final Map<String, Archetype> archetypes,
			final Map<String, ArchetypeFile> archetypeFiles) {
		List<ArchetypeRelation> relations = new ArrayList<ArchetypeRelation>();
		final Map<String, OntologyDefinitions> termDefinitionMap = archetypes
				.entrySet().stream()
				.map(entry -> {
					Archetype archetype = entry.getValue();
					OntologyDefinitions terms = archetype
							.getOntology()
							.getTermDefinitionsList()
							.stream()
							.filter(definition -> definition.getLanguage()
									.equals(archetype.getOriginalLanguage()
											.getCodeString())).findFirst()
							.get();
					return new AbstractMap.SimpleEntry<String, OntologyDefinitions>(
							archetype.getArchetypeId().getValue(), terms);
				})
				.collect(
						Collectors
								.toMap(Map.Entry::getKey, Map.Entry::getValue));
		// Max iteration times = n*n
		termDefinitionMap.forEach((archetypeName, termDefinitions) -> {
			termDefinitions
					.getDefinitions()
					.stream()
					.filter(item -> item
							.getItem(ArchetypeRelation.RelationType.OneToMany
									.toString()) != null)
					.forEach(item -> {
						try{
							String targetArchetypeName = item
									.getItem(ArchetypeRelation.RelationType.OneToMany
											.toString());
							String mappedNodePath = item.getItem("mappedBy");
							Archetype targetArchetype = archetypes.get(targetArchetypeName);
							String mappedNodeId = this.getNodeIdFromNodePath(mappedNodePath);
							ArchetypeTerm targetTerm = targetArchetype.getOntology().termDefinition(targetArchetype.getOriginalLanguage()
									.getCodeString(), mappedNodeId);
							String inverseArchetypeName = targetTerm.getItem(ArchetypeRelation.RelationType.ManyToOne
									.toString());
							if(inverseArchetypeName.equals(archetypeName)){
								ArchetypeRelation relation = new ArchetypeRelation();
								ArchetypeFile sourceArchetypeFile = archetypeFiles.get(archetypeName);
								ArchetypeFile definitionArchetypeFile = archetypeFiles.get(targetArchetypeName);
								relation.setSourceArchetypeFile(sourceArchetypeFile);
								relation.setDestinationArchetypeFile(definitionArchetypeFile);
								relation.setSourceArchetypeNode(sourceArchetypeFile.getArchetypeNodeMap(ArchetypeNode::getCode).get(item.getCode()));
								relation.setDestinationArchetypeNode(definitionArchetypeFile.getArchetypeNodeMap(ArchetypeNode::getCode).get(targetTerm.getCode()));
								relations.add(relation);
							}else{
								this.logger.debug("OneToMany and ManyToOne relation does not match between {} and {}",archetypeName,inverseArchetypeName);
								throw new Exception("OneToMany and ManyToOne relation does not match.");
							}
						}catch(Exception ex){
							this.logger.debug("Parse archetype relation failed.", ex);
							throw new RuntimeException(ex);
						}
					});
			;
		});
		return relations;
	}
}
