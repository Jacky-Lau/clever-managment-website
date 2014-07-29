package edu.zju.bme.clever.website.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.openehr.am.archetype.Archetype;
import org.openehr.am.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import se.acode.openehr.parser.ADLParser;
import edu.zju.bme.clever.service.CleverService;
import edu.zju.bme.clever.website.dao.ArchetypeFileDao;
import edu.zju.bme.clever.website.dao.ArchetypeHostDao;
import edu.zju.bme.clever.website.dao.ArchetypeNodeChangeLogDao;
import edu.zju.bme.clever.website.dao.ArchetypeNodeDao;
import edu.zju.bme.clever.website.dao.ArchetypeRelationshipDao;
import edu.zju.bme.clever.website.dao.ArchetypeTypeClassificationDao;
import edu.zju.bme.clever.website.dao.ArchetypeTypeDao;
import edu.zju.bme.clever.website.dao.HistoriedArchetypeFileDao;
import edu.zju.bme.clever.website.model.entity.ArchetypeFile;
import edu.zju.bme.clever.website.model.entity.ArchetypeNode;
import edu.zju.bme.clever.website.model.entity.ArchetypeNodeChangeLog;
import edu.zju.bme.clever.website.model.entity.ArchetypeType;
import edu.zju.bme.clever.website.model.entity.ArchetypeTypeClassification;
import edu.zju.bme.clever.website.view.entity.*;

@Service("archetypeProviderService")
@Transactional
public class ArchetypeProviderServiceImpl implements ArchetypeProviderService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "archetypeFileDao")
	private ArchetypeFileDao archetypeFileDao;
	@Resource(name = "archetypeRelationshipDao")
	private ArchetypeRelationshipDao archetypeRelationshipDao;
	@Resource(name = "archetypeHostDao")
	private ArchetypeHostDao archetypeHostDao;
	@Resource(name = "archetypeTypeDao")
	private ArchetypeTypeDao archetypeTypeDao;
	@Resource(name = "archetypeTypeClassificationDao")
	private ArchetypeTypeClassificationDao archetypeTypeClassifictionDao;
	@Resource(name = "cleverClient")
	private CleverService cleverClient;

	@Override
	public ArchetypeBriefInfo getArchetypeBriefInfo() {
		final ArchetypeBriefInfo archetypeBriefInfo = new ArchetypeBriefInfo();
		// get all relationships
		this.archetypeRelationshipDao
				.selectAll()
				.forEach(
						relationship -> {
							ArchetypeRelationshipInfo relationshipInfo = new ArchetypeRelationshipInfo();
							relationshipInfo.setRelationType(relationship
									.getRelationType());
							relationshipInfo
									.setSourceArchetypeHostId(relationship
											.getSourceArchetypeHostId());
							relationshipInfo
									.setDestinationArchetypeHostId(relationship
											.getDestinationArchetypeHostId());
							archetypeBriefInfo.getArchetypeRelationshipInfos()
									.add(relationshipInfo);
						});
		this.archetypeHostDao
				.selectAll()
				.forEach(
						host -> {
							final ArchetypeHostInfo hostInfo = new ArchetypeHostInfo();
							final Map<String, ArchetypeInfo> archetypeInfoIndexByVersion = new HashMap<String, ArchetypeInfo>();
							hostInfo.setConceptName(host.getConceptName());
							hostInfo.setName(host.getName());
							hostInfo.setRmEntity(host.getRmEntity());
							hostInfo.setRmName(host.getRmName());
							hostInfo.setRmOriginator(host.getRmOriginator());
							hostInfo.setId(host.getId());
							host.getArchetypeFiles()
									.forEach(
											file -> {
												ArchetypeInfo info = new ArchetypeInfo();
												info.setId(file.getId());
												info.setName(file.getName());
												info.setVersion(file
														.getVersion());

												hostInfo.getArchetypeInfos()
														.add(info);
												archetypeInfoIndexByVersion
														.put(file.getVersion(),
																info);
											});
							host.getArchetypeNodes()
									.stream()
									.collect(
											Collectors
													.groupingBy(ArchetypeNode::getOriginalVersion))
									.forEach(
											(version, nodes) -> {
												final ArchetypeInfo info = archetypeInfoIndexByVersion
														.get(version);
												nodes.forEach(node -> {
													ArchetypeNodeInfo nodeInfo = new ArchetypeNodeInfo();
													nodeInfo.setType(ArchetypeNodeInfo.Type.Add);
													nodeInfo.setPreviousName(node
															.getOriginalNodeName());
													nodeInfo.setCurrentName(node
															.getOriginalNodeName());
													nodeInfo.setRmType(node
															.getRmType());
													nodeInfo.setNodePath(node
															.getNodePath());

													info.getArchetypeNodeInfos()
															.add(nodeInfo);
												});
											});
							host.getArchetypeNodeChangeLogs()
									.stream()
									.collect(
											Collectors
													.groupingBy(ArchetypeNodeChangeLog::getCurrentVersion))
									.forEach(
											(version, logs) -> {
												final ArchetypeInfo info = archetypeInfoIndexByVersion
														.get(version);
												logs.forEach(log -> {
													ArchetypeNodeInfo nodeInfo = new ArchetypeNodeInfo();
													nodeInfo.setType(ArchetypeNodeInfo.Type.Modify);
													nodeInfo.setCurrentName(log
															.getCurrentNodeName());
													nodeInfo.setPreviousName(log
															.getPreviousNodeName());
													nodeInfo.setRmType(log
															.getRmType());
													nodeInfo.setNodePath(log
															.getNodePath());

													info.getArchetypeNodeInfos()
															.add(nodeInfo);
												});
											});
							archetypeBriefInfo.getArchetypeHostInfos().add(
									hostInfo);
						});
		return archetypeBriefInfo;
	}

	@Override
	public ArchetypeBriefInfo getArchetypeBriefInfoByTypeId(Integer typeId) {
		final ArchetypeBriefInfo archetypeBriefInfo = new ArchetypeBriefInfo();
		// get all relationships
		this.archetypeRelationshipDao
				.selectAll()
				.forEach(
						relationship -> {
							ArchetypeRelationshipInfo relationshipInfo = new ArchetypeRelationshipInfo();
							relationshipInfo.setRelationType(relationship
									.getRelationType());
							relationshipInfo
									.setSourceArchetypeHostId(relationship
											.getSourceArchetypeHostId());
							relationshipInfo
									.setDestinationArchetypeHostId(relationship
											.getDestinationArchetypeHostId());
							archetypeBriefInfo.getArchetypeRelationshipInfos()
									.add(relationshipInfo);
						});
		ArchetypeType type = this.archetypeTypeDao.findById(typeId);
		if (type == null) {
			this.logger.info("Cannot find archetype type with id: {}.", typeId);
			return null;
		}
		// get all archetype hosts
		type.getArchetypeHosts()
				.forEach(
						host -> {
							final ArchetypeHostInfo hostInfo = new ArchetypeHostInfo();
							final Map<String, ArchetypeInfo> archetypeInfoIndexByVersion = new HashMap<String, ArchetypeInfo>();
							hostInfo.setConceptName(host.getConceptName());
							hostInfo.setName(host.getName());
							hostInfo.setRmEntity(host.getRmEntity());
							hostInfo.setRmName(host.getRmName());
							hostInfo.setRmOriginator(host.getRmOriginator());
							hostInfo.setId(host.getId());
							host.getArchetypeFiles()
									.forEach(
											file -> {
												ArchetypeInfo info = new ArchetypeInfo();
												info.setId(file.getId());
												info.setName(file.getName());
												info.setVersion(file
														.getVersion());

												hostInfo.getArchetypeInfos()
														.add(info);
												archetypeInfoIndexByVersion
														.put(file.getVersion(),
																info);
											});
							host.getArchetypeNodes()
									.stream()
									.collect(
											Collectors
													.groupingBy(ArchetypeNode::getOriginalVersion))
									.forEach(
											(version, nodes) -> {
												final ArchetypeInfo info = archetypeInfoIndexByVersion
														.get(version);
												nodes.forEach(node -> {
													ArchetypeNodeInfo nodeInfo = new ArchetypeNodeInfo();
													nodeInfo.setType(ArchetypeNodeInfo.Type.Add);
													nodeInfo.setPreviousName(node
															.getOriginalNodeName());
													nodeInfo.setCurrentName(node
															.getOriginalNodeName());
													nodeInfo.setRmType(node
															.getRmType());
													nodeInfo.setNodePath(node
															.getNodePath());

													info.getArchetypeNodeInfos()
															.add(nodeInfo);
												});
											});
							host.getArchetypeNodeChangeLogs()
									.stream()
									.collect(
											Collectors
													.groupingBy(ArchetypeNodeChangeLog::getCurrentVersion))
									.forEach(
											(version, logs) -> {
												final ArchetypeInfo info = archetypeInfoIndexByVersion
														.get(version);
												logs.forEach(log -> {
													ArchetypeNodeInfo nodeInfo = new ArchetypeNodeInfo();
													nodeInfo.setType(ArchetypeNodeInfo.Type.Modify);
													nodeInfo.setCurrentName(log
															.getCurrentNodeName());
													nodeInfo.setPreviousName(log
															.getPreviousNodeName());
													nodeInfo.setRmType(log
															.getRmType());
													nodeInfo.setNodePath(log
															.getNodePath());

													info.getArchetypeNodeInfos()
															.add(nodeInfo);
												});
											});
							archetypeBriefInfo.getArchetypeHostInfos().add(
									hostInfo);
						});
		return archetypeBriefInfo;
	}

	@Override
	public String getArchetypeXmlByName(String name) {
		ArchetypeFile file = this.archetypeFileDao.findUniqueByProperty("name",
				name);
		return this.serializeArchetype(this.parseArchetype(file.getContent()));
	}

	@Override
	public String getArchetypeXmlById(Integer id) {
		ArchetypeFile file = this.archetypeFileDao.findById(id);
		return this.serializeArchetype(this.parseArchetype(file.getContent()));
	}

	@Override
	public String getArchetypeAdlByName(String name) {
		ArchetypeFile file = this.archetypeFileDao.findUniqueByProperty("name",
				name);
		return file.getContent();
	}

	@Override
	public String getArchetypeAdlById(Integer id) {
		ArchetypeFile file = this.archetypeFileDao.findById(id);
		return file.getContent();
	}

	protected Archetype parseArchetype(String archetype) {
		ADLParser parser = new ADLParser(archetype);
		try {
			return parser.parse();
		} catch (Exception ex) {
			this.logger.info("Parse archetype failed.", ex);
		}
		return null;
	}

	protected String serializeArchetype(Archetype archetype) {
		if (archetype == null) {
			throw new IllegalArgumentException("Archetype is null.");
		}
		XMLSerializer xmlSerializer = new XMLSerializer();
		try {
			String output = xmlSerializer.output(archetype);
			// this.logger.trace(output);
			return output;
		} catch (Exception ex) {
			this.logger.info("Serialize archetype failed.", ex);
		}
		return null;
	}

	@Override
	public Archetype getArchetypeByName(String name) {
		ArchetypeFile file = this.archetypeFileDao.findUniqueByProperty("name",
				name);
		return this.parseArchetype(file.getContent());
	}

	@Override
	public Archetype getArchetypeById(Integer id) {
		ArchetypeFile file = this.archetypeFileDao.findById(id);
		return this.parseArchetype(file.getContent());
	}

	@Override
	public List<String> getAllArchetypeAdls() {
		return this.archetypeFileDao.selectAll().stream().map(file -> {
			return file.getContent();
		}).collect(Collectors.toList());
	}

	@Override
	public List<String> getAllLatestVersionArchetypeAdls() {
		return this.archetypeHostDao
				.selectAll()
				.stream()
				.map(host -> {
					return host
							.getArchetypeFiles()
							.stream()
							.filter(file -> file.getVersion().equals(
									host.getLatestVersion())).findFirst().get()
							.getContent();
				}).collect(Collectors.toList());
	}

	@Override
	public List<String> getAllArchetypeIds() {
		return this.archetypeFileDao.selectAll().stream().map(file -> {
			return file.getName();
		}).collect(Collectors.toList());
	}

	@Override
	public List<String> getAllLatestVersionArchetypeIds() {
		return this.archetypeHostDao
				.selectAll()
				.stream()
				.map(host -> {
					return host
							.getArchetypeFiles()
							.stream()
							.filter(file -> file.getVersion().equals(
									host.getLatestVersion())).findFirst().get()
							.getName();
				}).collect(Collectors.toList());
	}

	@Override
	public List<String> getDeployedArchetypeIds() {
		return this.cleverClient.getArchetypeIds();
	}

	@Override
	public List<ClassificationBriefInfo> getAllClassifications() {
		return this.archetypeTypeClassifictionDao
				.selectAll()
				.stream()
				.map(classification -> {
					ClassificationBriefInfo info = new ClassificationBriefInfo();
					info.setId(classification.getId());
					info.setName(classification.getName());
					return info;
				}).collect(Collectors.toList());
	}

	@Override
	public ClassificationBriefInfo getClassificationBriefInfoById(
			Integer classificationId) {
		ClassificationBriefInfo info = new ClassificationBriefInfo();
		ArchetypeTypeClassification classification = this.archetypeTypeClassifictionDao
				.findById(classificationId);
		if (classification == null) {
			this.logger.info("Cannot find classification with id: {}.",
					classificationId);
			return null;
		}
		final Map<Integer, Integer> hostIdTypeIdIndex = new HashMap<Integer, Integer>();
		info.setId(classificationId);
		info.setName(classification.getName());
		info.setArchetypeTypeInfos(classification
				.getTypes()
				.stream()
				.map(type -> {
					final ArchetypeTypeInfo typeInfo = new ArchetypeTypeInfo();
					typeInfo.setName(type.getName());
					typeInfo.setId(type.getId());
					typeInfo.setArchetypeHostInfos(type
							.getArchetypeHosts()
							.stream()
							.map(host -> {
								hostIdTypeIdIndex.put(host.getId(),
										typeInfo.getId());
								ArchetypeHostInfo hostInfo = new ArchetypeHostInfo();
								hostInfo.setId(host.getId());
								hostInfo.setName(host.getName());
								hostInfo.setConceptName(host.getConceptName());
								return hostInfo;
							}).collect(Collectors.toList()));
					return typeInfo;
				}).collect(Collectors.toList()));
		final Map<String, ArchetypeTypeRelationshipInfo> relationships = new HashMap<String, ArchetypeTypeRelationshipInfo>();
		this.archetypeRelationshipDao
				.selectAll()
				.forEach(
						relationship -> {
							Integer sourceTypeId = hostIdTypeIdIndex
									.get(relationship
											.getSourceArchetypeHostId());
							Integer destinationTypeId = hostIdTypeIdIndex
									.get(relationship
											.getDestinationArchetypeHostId());
							if (sourceTypeId == null
									|| destinationTypeId == null
									|| sourceTypeId == destinationTypeId) {
								return;
							}

							String key = relationship.getRelationType() + "-"
									+ sourceTypeId + "-" + destinationTypeId;
							ArchetypeTypeRelationshipInfo relationshipInfo = relationships
									.get(key);
							if (relationshipInfo == null) {
								relationshipInfo = new ArchetypeTypeRelationshipInfo();
								relationshipInfo.setRelationType(relationship
										.getRelationType());
								relationshipInfo
										.setSourceArchetypeTypeId(sourceTypeId);
								relationshipInfo
										.setDestinationArchetypeTypeId(destinationTypeId);
								relationships.put(key, relationshipInfo);
							}
							relationshipInfo.setWeight(relationshipInfo
									.getWeight() + 1);
						});
		info.setArchetypeTypeRelationshipInfos(new ArrayList<ArchetypeTypeRelationshipInfo>(
				relationships.values()));
		return info;
	}
}
