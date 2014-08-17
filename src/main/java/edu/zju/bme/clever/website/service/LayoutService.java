package edu.zju.bme.clever.website.service;

import java.util.List;

import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.view.entity.ArchetypeHostLayoutSettingInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeClassificationLayoutInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeLayoutInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeLayoutSettingInfo;

public interface LayoutService {

	public ArchetypeTypeClassificationLayoutInfo getClassificationLayoutByIdAndUserName(
			Integer classifcationId, String userName) throws LayoutException;

	public void updateClassificationLayoutByIdAndUserName(
			Integer classifcationId, String userName,
			ArchetypeTypeClassificationLayoutInfo layout) throws LayoutException;

	public ArchetypeTypeLayoutInfo getArchetypeTypeLatyoutByIdAndUserName(
			Integer typeId, String userName) throws LayoutException;

	public void updateArchetypeTypeLatyoutByIdAndUserName(Integer typeId,
			String userName, ArchetypeTypeLayoutInfo layout)
			throws LayoutException;

}
