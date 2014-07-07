package edu.zju.bme.clever.website.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.openehr.am.archetype.Archetype;
import org.openehr.am.archetype.constraintmodel.CObject;
import org.openehr.am.archetype.ontology.ArchetypeTerm;
import org.openehr.am.archetype.ontology.OntologyDefinitions;
import org.openehr.am.serialize.ADLSerializer;
import org.openehr.rm.datatypes.basic.ReferenceModelName;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.bme.clever.website.dao.ArchetypeFileDao;
import edu.zju.bme.clever.website.dao.ArchetypeHostDao;
import edu.zju.bme.clever.website.dao.ArchetypeNodeChangeLogDao;
import edu.zju.bme.clever.website.dao.ArchetypeNodeDao;
import edu.zju.bme.clever.website.dao.ArchetypeRelationshipDao;
import edu.zju.bme.clever.website.dao.CommitSequenceDao;
import edu.zju.bme.clever.website.dao.HistoriedArchetypeFileDao;
import edu.zju.bme.clever.website.dao.UserDao;
import edu.zju.bme.clever.website.exception.ArchetypePersistenceException;
import edu.zju.bme.clever.website.model.entity.ArchetypeFile;
import edu.zju.bme.clever.website.model.entity.ArchetypeHost;
import edu.zju.bme.clever.website.model.entity.ArchetypeNode;
import edu.zju.bme.clever.website.model.entity.ArchetypeNodeChangeLog;
import edu.zju.bme.clever.website.model.entity.ArchetypeRelationship;
import edu.zju.bme.clever.website.model.entity.CommitSequence;
import edu.zju.bme.clever.website.model.entity.FileProcessResult;
import edu.zju.bme.clever.website.model.entity.HistoriedArchetypeFile;
import edu.zju.bme.clever.website.model.entity.User;

@Service("archetypePersistanceService")
@Transactional
public class ArchetypePersistanceServiceImpl implements
		ArchetypePersistanceService {

	@Resource(name = "commitSequenceDao")
	private CommitSequenceDao commitSequenceDao;
	@Resource(name = "archetypeFileDao")
	private ArchetypeFileDao archetypeFileDao;
	@Resource(name = "historiedArchetypeFileDao")
	private HistoriedArchetypeFileDao historiedArchetypeFileDao;
	@Resource(name = "archetypeNodeDao")
	private ArchetypeNodeDao archetypeNodeDao;
	@Resource(name = "archetypeRelationshipDao")
	private ArchetypeRelationshipDao archetypeRelationshipDao;
	@Resource(name = "archetypeHostDao")
	private ArchetypeHostDao archetypeHostDao;
	@Resource(name = "archetypeNodeChangeLogDao")
	private ArchetypeNodeChangeLogDao archetypeNodeChangeLogDao;
	@Resource(name = "userDao")
	private UserDao userDao;

	private final Map<String, String> filterRmTypeNames = new HashMap<String, String>();

	public ArchetypePersistanceServiceImpl() {
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

	private boolean isLeafNode(String rmType, String rmAttribute) {
		if (this.filterRmTypeNames.containsKey(rmType)) {
			if (this.filterRmTypeNames.get(rmType).equals(rmAttribute)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional(rollbackFor = { ArchetypePersistenceException.class })
	public void saveArchetypes(List<Archetype> archetypes, String submitter)
			throws ArchetypePersistenceException {

		Map<String, ArchetypeFile> archetypeFiles = new HashMap<String, ArchetypeFile>();

		// Persist commit sequence
		User user = this.userDao.findUniqueByProperty("userName", submitter);
		if (user == null) {
			throw new ArchetypePersistenceException("User " + submitter
					+ " not found.");
		}
		CommitSequence commitSequence = new CommitSequence();
		commitSequence.setSubmitter(user);
		this.commitSequenceDao.save(commitSequence);

		for (Archetype archetype : archetypes) {
			ArchetypeFile archetypeFileFromDB = this.archetypeFileDao
					.findUniqueByProperty("name", archetype.getArchetypeId()
							.getValue());
			if (archetypeFileFromDB != null) {
				throw new ArchetypePersistenceException("Archetype "
						+ archetype.getArchetypeId().getValue()
						+ " already exists.");
			}
			ADLSerializer serializer = new ADLSerializer();
			String archetypeContent = "";
			try {
				archetypeContent = serializer.output(archetype);
			} catch (IOException e) {
				throw new ArchetypePersistenceException("Serialize archetype "
						+ archetype.getArchetypeId().getValue() + " failed.");
			}

			ArchetypeFile archetypeFile = new ArchetypeFile();
			String version = archetype.getArchetypeId().versionID();
			String originalLanguage = archetype.getOriginalLanguage()
					.getCodeString();

			// Persist archetype host
			String archetypeHostName = archetype.getArchetypeId()
					.qualifiedRmEntity()
					+ "."
					+ archetype.getArchetypeId().conceptName();
			ArchetypeHost archetypeHost = this.archetypeHostDao
					.findUniqueByProperty("name", archetypeHostName);
			if (archetypeHost != null) {
				String latestVersion = archetypeHost.getLatestVersion();
				String nextVersion = "v"
						+ (Integer.valueOf(latestVersion.replace("v", "")) + 1);
				if (version.compareTo(nextVersion) != 0) {
					throw new ArchetypePersistenceException("Archetype "
							+ archetype.getArchetypeId().getValue()
							+ " version should be " + nextVersion + ".");
				}
				archetypeHost.setLatestVersion(nextVersion);
				archetypeHost.setModifyTime(Calendar.getInstance());
			} else {
				if (version.compareTo("v1") != 0) {
					throw new ArchetypePersistenceException("Archetype "
							+ archetype.getArchetypeId().getValue()
							+ " version should be v1.");
				}
				archetypeHost = new ArchetypeHost();
				archetypeHost.setLatestVersion("v1");
				archetypeHost.setModifyTime(Calendar.getInstance());
				archetypeHost.setName(archetypeHostName);
				archetypeHost
						.setRmEntity(archetype.getArchetypeId().rmEntity());
				archetypeHost.setRmName(archetype.getArchetypeId().rmName());
				archetypeHost.setRmOriginator(archetype.getArchetypeId()
						.rmOriginator());
				archetypeHost.setConceptName(archetype.getArchetypeId()
						.conceptName());
			}
			this.archetypeHostDao.saveOrUpdate(archetypeHost);

			// Add new archetype file
			archetypeFile.setArchetypeHost(archetypeHost);
			archetypeFile.setCommitSequence(commitSequence);
			archetypeFile.setName(archetype.getArchetypeId().getValue());
			archetypeFile.setConceptName(archetype.getArchetypeId()
					.conceptName());
			archetypeFile.setOriginalLanguage(originalLanguage);
			archetypeFile.setRmEntity(archetype.getArchetypeId().rmEntity());
			archetypeFile.setRmName(archetype.getArchetypeId().rmName());
			archetypeFile.setRmOriginator(archetype.getArchetypeId()
					.rmOriginator());
			archetypeFile.setContent(archetypeContent);
			archetypeFile.setVersion(version);
			archetype
					.getDescription()
					.getDetails()
					.stream()
					.filter(item -> item
							.getLanguage()
							.getCodeString()
							.equals(archetype.getOriginalLanguage()
									.getCodeString()))
					.findFirst()
					.ifPresent(
							item -> {
								if (item.getPurpose() != null) {
									archetypeFile.setPurpose(item.getPurpose());
								}
								if (item.getUse() != null) {
									archetypeFile.setUse(item.getUse());
								}
								if (item.getKeywords() != null) {
									archetypeFile.setKeywords(String.join("|",
											item.getKeywords()));
								}
							});
			this.archetypeFileDao.save(archetypeFile);
			archetypeFiles.put(archetype.getArchetypeId().getValue(),
					archetypeFile);

			// Persist archetype nodes
			Map<String, ArchetypeNode> existedNodes = archetypeHost
					.getArchetypeNodeMap(ArchetypeNode::getNodePath);
			for (CObject node : archetype.getPathNodeMap().values()) {
				if (node.getRmTypeName() != null
						&& node.getParent() != null
						&& this.isLeafNode(node.getRmTypeName(), node
								.getParent().getRmAttributeName())) {
					if (existedNodes.containsKey(node.path())) {
						ArchetypeNode existedNode = existedNodes.get(node
								.path());
						String code = existedNode.getCode();
						String nodeName = archetype.getOntology()
								.termDefinition(originalLanguage, code)
								.getText();
						if (nodeName
								.compareTo(existedNode.getCurrentNodeName()) != 0) {
							// Add new archetype node change log
							ArchetypeNodeChangeLog log = new ArchetypeNodeChangeLog();
							log.setArchetypeHost(archetypeHost);
							log.setRmType(existedNode.getRmType());
							log.setPreviousNodeName(existedNode
									.getCurrentNodeName());
							log.setPreviousVersion(existedNode
									.getCurrentVersion());
							log.setCurrentNodeName(nodeName);
							log.setCurrentVersion(version);
							log.setNodePath(node.path());
							this.archetypeNodeChangeLogDao.save(log);
							// Update archetype node
							existedNode.setCurrentNodeName(nodeName);
							existedNode.setCurrentVersion(version);
							this.archetypeNodeDao.update(existedNode);
						}
					} else {
						// Add new archetype node
						ArchetypeNode newNode = new ArchetypeNode();
						newNode.setArchetypeHost(archetypeHost);
						String code = this.getNodeIdFromNodePath(node.path());
						String nodeName = archetype.getOntology()
								.termDefinition(originalLanguage, code)
								.getText();
						newNode.setCode(code);
						newNode.setCurrentNodeName(nodeName);
						newNode.setCurrentVersion(version);
						newNode.setOriginalNodeName(nodeName);
						newNode.setOriginalVersion(version);
						newNode.setNodePath(node.path());
						newNode.setRmType(node.getRmTypeName());
						this.archetypeNodeDao.save(newNode);
					}
				}
			}
		}

		// Persist archetype relationships after all archetypes have saved into
		// database
		for (Archetype archetype : archetypes) {
			OntologyDefinitions annotations = archetype
					.getOntology()
					.getTermDefinitionsList()
					.stream()
					.filter(definition -> definition.getLanguage().equals(
							"annotation"))
					.findFirst().get();
			for (ArchetypeTerm item : annotations.getDefinitions()) {
				ArchetypeFile sourceArchetypeFile = null, destinationArchetypeFile = null;
				if (item.getItem(ArchetypeRelationship.RelationType.OneToMany
						.toString()) != null) {
					sourceArchetypeFile = archetypeFiles.get(archetype
							.getArchetypeId().getValue());
					String archetypeName = item
							.getItem(ArchetypeRelationship.RelationType.OneToMany
									.toString());
					destinationArchetypeFile = archetypeFiles
							.get(archetypeName);
					if (destinationArchetypeFile == null) {
						destinationArchetypeFile = this.archetypeFileDao
								.findUniqueByProperty("name", archetypeName);
					}
					if (destinationArchetypeFile == null) {
						throw new ArchetypePersistenceException(
								"Miss archetype " + archetypeName + ".");
					}
				}
				if (item.getItem(ArchetypeRelationship.RelationType.ManyToOne
						.toString()) != null) {
					destinationArchetypeFile = archetypeFiles.get(archetype
							.getArchetypeId().getValue());
					String archetypeName = item
							.getItem(ArchetypeRelationship.RelationType.ManyToOne
									.toString());
					sourceArchetypeFile = archetypeFiles.get(archetypeName);
					if (sourceArchetypeFile == null) {
						sourceArchetypeFile = this.archetypeFileDao
								.findUniqueByProperty("name", archetypeName);
					}
					if (sourceArchetypeFile == null) {
						throw new ArchetypePersistenceException(
								"Miss archetype " + archetypeName + ".");
					}
				}
				if (sourceArchetypeFile != null
						&& destinationArchetypeFile != null) {
					String hql = "from ArchetypeRelationship as r where r.sourceArchetypeHost.id = :sourceId and r.destinationArchetypeHost.id = :destinationId and r.relationType = :relationType";
					Map<String, Object> variables = new HashMap<String, Object>();
					variables.put("sourceId", sourceArchetypeFile
							.getArchetypeHost().getId());
					variables.put("destinationId", destinationArchetypeFile
							.getArchetypeHost().getId());
					variables.put("relationType",
							ArchetypeRelationship.RelationType.OneToMany);
					List result = this.archetypeRelationshipDao.findByHQL(hql,
							variables);
					if (result.size() == 0) {
						// Add new relationship
						ArchetypeRelationship relationship = new ArchetypeRelationship();
						relationship
								.setRelationType(ArchetypeRelationship.RelationType.OneToMany);
						relationship.setSourceArchetypeHost(sourceArchetypeFile
								.getArchetypeHost());
						relationship
								.setDestinationArchetypeHost(destinationArchetypeFile
										.getArchetypeHost());
						this.archetypeRelationshipDao.save(relationship);
					} else if (result.size() > 1) {
						throw new ArchetypePersistenceException(
								"More than one relationships were found.");
					}
				}
			}
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
