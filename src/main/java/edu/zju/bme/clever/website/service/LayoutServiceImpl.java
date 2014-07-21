package edu.zju.bme.clever.website.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.bme.clever.website.dao.ArchetypeHostDao;
import edu.zju.bme.clever.website.dao.LayoutDao;
import edu.zju.bme.clever.website.dao.LayoutSettingDao;
import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.model.entity.ArchetypeHost;
import edu.zju.bme.clever.website.model.entity.Layout;
import edu.zju.bme.clever.website.model.entity.LayoutSetting;
import edu.zju.bme.clever.website.view.entity.LayoutInfo;
import edu.zju.bme.clever.website.view.entity.LayoutSettingInfo;

@Service("layoutService")
@Transactional
public class LayoutServiceImpl implements LayoutService {

	@Resource(name = "layoutDao")
	private LayoutDao layoutDao;
	@Resource(name = "layoutSettingDao")
	private LayoutSettingDao layoutSettingDao;
	@Resource(name = "archetypeHostDao")
	private ArchetypeHostDao archetypeHostDao;

	@Override
	public List<LayoutInfo> getAllLayouts() {
		return this.layoutDao.selectAll().stream().map(layout -> {
			LayoutInfo info = new LayoutInfo();
			info.setId(layout.getId());
			info.setName(layout.getName());
			return info;
		}).collect(Collectors.toList());
	}

	@Override
	public void updateLayout(Integer id, List<LayoutSettingInfo> infos)
			throws LayoutException {
		Layout layout = this.layoutDao.findById(id);
		if (layout == null) {
			throw new LayoutException("Layout with id '" + id
					+ "' does not exist.");
		}
		Map<Integer, LayoutSetting> settings = layout
				.getLayoutSettingMap(setting -> setting.getArchetypeHost()
						.getId());
		for (LayoutSettingInfo info : infos) {
			LayoutSetting setting = settings.get(info.getArchetypeHostId());
			if(setting ==null){
				setting = new LayoutSetting();
				ArchetypeHost archetypeHost = this.archetypeHostDao.findById(info
						.getArchetypeHostId());
				if (archetypeHost == null) {
					throw new LayoutException("Archetype host with id '"
							+ info.getArchetypeHostId() + "' does not exist.");
				}
				setting.setArchetypeHost(archetypeHost);
				setting.setLayout(layout);
			}
			setting.setPositionX(info.getPositionX());
			setting.setPositionY(info.getPositionY());
			this.layoutSettingDao.saveOrUpdate(setting);
		}
		this.layoutDao.update(layout);
	}

	@Override
	public List<LayoutSettingInfo> getLayoutById(Integer id) {
		Layout layout = this.layoutDao.findById(id);
		return layout.getLayoutSettings().stream().map(setting -> {
			LayoutSettingInfo settingInfo = new LayoutSettingInfo();
			settingInfo.setPositionX(setting.getPositionX());
			settingInfo.setPositionY(setting.getPositionY());
			settingInfo.setArchetypeHostId(setting.getArchetypeHost().getId());
			return settingInfo;
		}).collect(Collectors.toList());
	}
}
