package edu.zju.bme.clever.website.service;

import java.util.List;

import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeInfo;
import edu.zju.bme.clever.website.view.entity.ClassificationBriefInfo;

public interface ClassificationService {

	public List<ClassificationBriefInfo> getAllClassifications();

	public ClassificationBriefInfo getClassificationBriefInfoById(
			Integer classificationId);
}
