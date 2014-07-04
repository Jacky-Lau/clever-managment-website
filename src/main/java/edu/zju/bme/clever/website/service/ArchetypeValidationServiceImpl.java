package edu.zju.bme.clever.website.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.openehr.am.archetype.Archetype;
import org.openehr.am.archetype.ontology.OntologyDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.bme.clever.service.CleverService;
import edu.zju.bme.clever.website.dao.ArchetypeFileDao;
import edu.zju.bme.clever.website.dao.ArchetypeHostDao;
import edu.zju.bme.clever.website.entity.ArchetypeFile;
import edu.zju.bme.clever.website.entity.ArchetypeHost;
import edu.zju.bme.clever.website.entity.ArchetypeRelationship;
import edu.zju.bme.clever.website.entity.FileProcessResult;

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
	public void validateConsistency(Map<Archetype, FileProcessResult> archetypes) {
		Map<String, Archetype> archetypeMap = archetypes
				.keySet()
				.stream()
				.collect(
						Collectors.toMap(archetype -> archetype
								.getArchetypeId().getValue(),
								archetype -> archetype));
		archetypes
				.forEach((archetype, result) -> {
					ArchetypeFile archetypeFile = this.archetypeFileDao.findUniqueByProperty("name", archetype.getArchetypeId().getValue());
					if(archetypeFile!=null){
						result.setStatus(FileProcessResult.FileStatusConstant.EXISTED);
						result.setMessage("Archetype already exists.");
						return;
					}
					String archetypeHostName = archetype.getArchetypeId()
							.qualifiedRmEntity()
							+ "."
							+ archetype.getArchetypeId().conceptName();
					ArchetypeHost archetypeHost = this.archetypeHostDao
							.findUniqueByProperty("name", archetypeHostName);
					if (archetypeHost != null) {
						String latestVersion = archetypeHost.getLatestVersion();
						String nextVersion = "v"
								+ (Integer.valueOf(latestVersion.replace("v",
										"")) + 1);
						if (archetype.getArchetypeId().versionID()
								.compareTo(nextVersion) != 0) {
							result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
							result.setMessage("Archetype version should be "
									+ nextVersion + ".");
							return;
						}
					} else {
						if (archetype.getArchetypeId().versionID()
								.compareTo("v1") != 0) {
							result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
							result.setMessage("Archetype version should be v1.");
							return;
						}
					}
					OntologyDefinitions termDefinitions = archetype
							.getOntology()
							.getTermDefinitionsList()
							.stream()
							.filter(definition -> definition.getLanguage()
									.equals(archetype.getOriginalLanguage()
											.getCodeString())).findFirst()
							.get();
					termDefinitions
							.getDefinitions()
							.forEach(
									term -> {
										String oneToMany = term
												.getItem(ArchetypeRelationship.RelationType.OneToMany
														.toString());
										if (oneToMany != null) {
											if (!archetypeMap
													.containsKey(oneToMany)
													&& this.archetypeFileDao
															.findUniqueByProperty(
																	"name",
																	oneToMany) == null) {
												result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
												result.setMessage("Missing archetype "
														+ oneToMany
														+ " refered in term "
														+ term.getCode() + ".");
												return;
											}
										}
										String manyToOne = term
												.getItem(ArchetypeRelationship.RelationType.ManyToOne
														.toString());
										if (manyToOne != null) {
											if (!archetypeMap
													.containsKey(manyToOne)
													&& this.archetypeFileDao
															.findUniqueByProperty(
																	"name",
																	manyToOne) == null) {
												result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
												result.setMessage("Missing archetype "
														+ manyToOne
														+ " refered in term "
														+ term.getCode() + ".");
												return;
											}
										}
									});
					result.setStatus(FileProcessResult.FileStatusConstant.VALID);
				});
	}
}
