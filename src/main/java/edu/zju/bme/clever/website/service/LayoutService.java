package edu.zju.bme.clever.website.service;

import java.util.List;

import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.view.entity.ArchetypeHostLayoutSettingInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeLayoutSettingInfo;

public interface LayoutService {

	public List<ArchetypeTypeLayoutSettingInfo> getClassificationLayoutByIdAndUserName(
			Integer classifcationId, String userName) throws LayoutException;

	public void updateClassificationLayoutByIdAndUserName(
			Integer classifcationId, String userName,
			List<ArchetypeTypeLayoutSettingInfo> infos) throws LayoutException;

	public List<ArchetypeHostLayoutSettingInfo> getArchetypeTypeLatyoutByIdAndUserName(
			Integer typeId, String userName) throws LayoutException;

	public void updateArchetypeTypeLatyoutByIdAndUserName(Integer typeId,
			String userName, List<ArchetypeHostLayoutSettingInfo> infos)
			throws LayoutException;

}
