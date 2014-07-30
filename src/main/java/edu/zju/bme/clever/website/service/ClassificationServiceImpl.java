package edu.zju.bme.clever.website.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.zju.bme.clever.website.dao.ArchetypeRelationshipDao;
import edu.zju.bme.clever.website.dao.ArchetypeTypeClassificationDao;
import edu.zju.bme.clever.website.dao.ArchetypeTypeDao;
import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.model.entity.ArchetypeType;
import edu.zju.bme.clever.website.model.entity.ArchetypeTypeClassification;
import edu.zju.bme.clever.website.view.entity.ArchetypeHostInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeRelationshipInfo;
import edu.zju.bme.clever.website.view.entity.ClassificationBriefInfo;

@Service("classificationService")
public class ClassificationServiceImpl implements ClassificationService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "archetypeTypeDao")
	private ArchetypeTypeDao archetypeTypeDao;
	@Resource(name = "archetypeTypeClassificationDao")
	private ArchetypeTypeClassificationDao archetypeTypeClassificationDao;
	@Resource(name = "archetypeRelationshipDao")
	private ArchetypeRelationshipDao archetypeRelationshipDao;


	@Override
	public List<ClassificationBriefInfo> getAllClassifications() {
		return this.archetypeTypeClassificationDao
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
		ArchetypeTypeClassification classification = this.archetypeTypeClassificationDao
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
								hostInfo.setRmEntity(host.getRmEntity());
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
