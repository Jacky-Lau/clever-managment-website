package edu.zju.bme.clever.website.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.openehr.am.archetype.Archetype;
import org.openehr.am.archetype.constraintmodel.CObject;
import org.openehr.am.archetype.ontology.OntologyDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.bme.clever.service.CleverService;
import edu.zju.bme.clever.website.dao.ArchetypeFileDao;
import edu.zju.bme.clever.website.dao.ArchetypeHostDao;
import edu.zju.bme.clever.website.model.entity.ArchetypeFile;
import edu.zju.bme.clever.website.model.entity.ArchetypeHost;
import edu.zju.bme.clever.website.model.entity.ArchetypeNode;
import edu.zju.bme.clever.website.model.entity.ArchetypeNodeChangeLog;
import edu.zju.bme.clever.website.model.entity.ArchetypeRelationship;
import edu.zju.bme.clever.website.model.entity.FileProcessResult;
import edu.zju.bme.clever.website.util.ArchetypeLeafNodeRmTypeAttributeMap;

@Service("archetypeValidationService")
@Transactional
public class ArchetypeValidationServiceImpl implements
		ArchetypeValidationService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "cleverValidationClient")
	private CleverService cleverValidationClient;
	@Resource(name = "archetypeHostDao")
	private ArchetypeHostDao archetypeHostDao;
	@Resource(name = "archetypeFileDao")
	private ArchetypeFileDao archetypeFileDao;

	@Override
	public boolean validateFeasibility(List<String> archetypes) {
		int stopResult = cleverValidationClient.stop();
		if (stopResult != 0) {
			this.logger.trace(
					"Archetype validation client stop failed, result: {}.",
					stopResult);
			return false;
		}
		int reconfigureResult = cleverValidationClient.reconfigure(archetypes,
				null);
		if (reconfigureResult != 0) {
			this.logger
					.trace("Archetype validation client reconfigure failed, result: {}.",
							reconfigureResult);
			return false;
		}
		int startResult = cleverValidationClient.start();
		if (startResult != 0) {
			this.logger.trace(
					"Archetype validation client start failed, result: {}.",
					startResult);
			return false;
		}
		return true;
	}

	@Override
	public void validateConsistency(Map<String, Archetype> archetypes,
			Map<String, FileProcessResult> results) {
		archetypes
				.forEach((archetypeId, archetype) -> {
					Optional<ArchetypeFile> archetypeFile = Optional
							.ofNullable(this.archetypeFileDao
									.findUniqueByProperty("name", archetype
											.getArchetypeId().getValue()));
					FileProcessResult result = results.get(archetypeId);
					archetypeFile.ifPresent(file -> {
						result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
						result.setMessage(result.getMessage()
								+ "Archetype already exists. ");
					});
					String originalLanguage = archetype.getOriginalLanguage()
							.getCodeString();
					String archetypeHostName = archetype.getArchetypeId()
							.base();
					Optional<ArchetypeHost> archetypeHost = Optional
							.ofNullable(this.archetypeHostDao
									.findUniqueByProperty("name",
											archetypeHostName));
					if (archetypeHost.isPresent()) {
						String latestVersion = archetypeHost.get()
								.getLatestVersion();
						String nextVersion = "v"
								+ (Integer.valueOf(latestVersion.replace("v",
										"")) + 1);
						if (archetype.getArchetypeId().versionID()
								.compareTo(nextVersion) != 0) {
							result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
							result.setMessage(result.getMessage()
									+ "Archetype version should be "
									+ nextVersion + ". ");
						}
					} else {
						if (archetype.getArchetypeId().versionID()
								.compareTo("v1") != 0) {
							result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
							result.setMessage(result.getMessage()
									+ "Archetype version should be v1. ");
						}
					}
					// Validate node
					Map<String, ArchetypeNode> existedNodes = archetypeHost
							.map(host -> host
									.getArchetypeNodeMap(ArchetypeNode::getNodePath))
							.orElse(new HashMap<String, ArchetypeNode>());
					for (CObject node : archetype.getPathNodeMap().values()) {
						if (node.getRmTypeName() != null
								&& node.getParent() != null
								&& ArchetypeLeafNodeRmTypeAttributeMap
										.isLeafNode(node.getRmTypeName(), node
												.getParent()
												.getRmAttributeName())) {
							String code = this.getNodeIdFromNodePath(node
									.path());
							String aliasName = Optional
									.ofNullable(
											archetype.getOntology()
													.termDefinition(
															"annotation", code))
									.map(term -> term.getItem("Column_name"))
									.orElse(null);
							if (aliasName == null) {
								result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
								result.setMessage(result.getMessage()
										+ "Column name for " + node.path()
										+ " is empty. ");
							}
							if (existedNodes.containsKey(node.path())) {
								// Node already exists
								ArchetypeNode existedNode = existedNodes
										.get(node.path());
								if (aliasName.compareTo(existedNode
										.getAliasName()) != 0) {
									result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
									result.setMessage(result.getMessage()
											+ "Column name for "
											+ node.path()
											+ " is not allowed for change. The original column name is "
											+ existedNode.getAliasName() + ". ");
								}
							}
						}
					}
					Set<String> nodePathes = archetype.getPathNodeMap()
							.values().stream().map(node -> node.path())
							.collect(Collectors.toSet());
					for (String nodePath : existedNodes.keySet()) {
						if (!nodePathes.contains(nodePath)) {
							result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
							result.setMessage(result.getMessage()
									+ "Missing node " + nodePath + ". ");
						}
					}
					// Validate annotation
					OntologyDefinitions annotation = archetype
							.getOntology()
							.getTermDefinitionsList()
							.stream()
							.filter(definition -> definition.getLanguage()
									.equals("annotation")).findFirst().get();
					annotation
							.getDefinitions()
							.forEach(
									term -> {
										String oneToMany = term
												.getItem(ArchetypeRelationship.RelationType.OneToMany
														.toString());
										if (oneToMany != null) {
											boolean contained = false;
											for (String key : archetypes
													.keySet()) {
												if (key.contains(oneToMany)) {
													contained = true;
													break;
												}
											}
											if (!contained
													&& this.archetypeHostDao
															.findUniqueByProperty(
																	"name",
																	oneToMany) == null) {
												result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
												result.setMessage(result
														.getMessage()
														+ "Missing archetype "
														+ oneToMany
														+ " refered in term "
														+ term.getCode() + ". ");
											}
										}
										String manyToOne = term
												.getItem(ArchetypeRelationship.RelationType.ManyToOne
														.toString());
										if (manyToOne != null) {
											boolean contained = false;
											for (String key : archetypes
													.keySet()) {
												if (key.contains(manyToOne)) {
													contained = true;
													break;
												}
											}
											if (!contained
													&& this.archetypeHostDao
															.findUniqueByProperty(
																	"name",
																	manyToOne) == null) {
												result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
												result.setMessage(result
														.getMessage()
														+ "Missing archetype "
														+ manyToOne
														+ " refered in term "
														+ term.getCode() + ". ");
											}
										}
									});
				});
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
