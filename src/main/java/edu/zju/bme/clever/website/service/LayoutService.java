package edu.zju.bme.clever.website.service;

import java.util.List;

import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.view.entity.LayoutInfo;
import edu.zju.bme.clever.website.view.entity.LayoutSettingInfo;

public interface LayoutService {

	public List<LayoutInfo> getAllLayouts();

	public void updateLayout(Integer id, List<LayoutSettingInfo> infos)
			throws LayoutException;

	public List<LayoutSettingInfo> getLayoutById(Integer id);
}
