package edu.zju.bme.clever.website.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.zju.bme.clever.website.dao.ArchetypeHostDao;
import edu.zju.bme.clever.website.dao.ArchetypeHostLayoutSettingDao;
import edu.zju.bme.clever.website.dao.ArchetypeTypeClassificationDao;
import edu.zju.bme.clever.website.dao.ArchetypeTypeClassificationLayoutScaleDao;
import edu.zju.bme.clever.website.dao.ArchetypeTypeDao;
import edu.zju.bme.clever.website.dao.ArchetypeTypeLayoutScaleDao;
import edu.zju.bme.clever.website.dao.ArchetypeTypeLayoutSettingDao;
import edu.zju.bme.clever.website.dao.UserDao;
import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.model.entity.ArchetypeHost;
import edu.zju.bme.clever.website.model.entity.ArchetypeHostLayoutSetting;
import edu.zju.bme.clever.website.model.entity.ArchetypeType;
import edu.zju.bme.clever.website.model.entity.ArchetypeTypeClassification;
import edu.zju.bme.clever.website.model.entity.ArchetypeTypeClassificationLayoutScale;
import edu.zju.bme.clever.website.model.entity.ArchetypeTypeLayoutScale;
import edu.zju.bme.clever.website.model.entity.ArchetypeTypeLayoutSetting;
import edu.zju.bme.clever.website.model.entity.User;
import edu.zju.bme.clever.website.view.entity.ArchetypeHostLayoutSettingInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeClassificationLayoutInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeLayoutInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeLayoutSettingInfo;

@Service("layoutService")
@Transactional
public class LayoutServiceImpl implements LayoutService {

	@Resource(name = "archetypeHostLayoutSettingDao")
	private ArchetypeHostLayoutSettingDao archetypeHostLayoutSettingDao;
	@Resource(name = "archetypeTypeLayoutSettingDao")
	private ArchetypeTypeLayoutSettingDao archetypeTypeLayoutSettingDao;
	@Resource(name = "archetypeHostDao")
	private ArchetypeHostDao archetypeHostDao;
	@Resource(name = "userDao")
	private UserDao userDao;
	@Resource(name = "archetypeTypeDao")
	private ArchetypeTypeDao archetypeTypeDao;
	@Resource(name = "archetypeTypeLayoutScaleDao")
	private ArchetypeTypeLayoutScaleDao typeLayoutScaleDao;
	@Resource(name = "archetypeTypeClassificationDao")
	private ArchetypeTypeClassificationDao archetypeTypeClassificationDao;
	@Resource(name = "archetypeTypeClassificationLayoutScaleDao")
	private ArchetypeTypeClassificationLayoutScaleDao classificationLayoutScaleDao;

	@Override
	public ArchetypeTypeClassificationLayoutInfo getClassificationLayoutByIdAndUserName(
			Integer classifcationId, String userName) throws LayoutException {
		User user = this.userDao.findUniqueByProperty("userName", userName);
		if (user == null) {
			throw new LayoutException("User '" + userName + "' does not exist.");
		}
		ArchetypeTypeClassification classifcation = this.archetypeTypeClassificationDao
				.findById(classifcationId);
		if (classifcation == null) {
			throw new LayoutException("Classifcation with id '"
					+ classifcationId + "' does not exist.");
		}
		List<ArchetypeTypeLayoutSettingInfo> settings = this.archetypeTypeLayoutSettingDao
				.findByProperty("user", user, "archetypeTypeClassification",
						classifcation)
				.stream()
				.map(setting -> {
					ArchetypeTypeLayoutSettingInfo info = new ArchetypeTypeLayoutSettingInfo();
					info.setArchetypeTypeId(setting.getArchetypeTypeId());
					info.setPositionX(setting.getPositionX());
					info.setPositionY(setting.getPositionY());
					return info;
				}).collect(Collectors.toList());
		List<ArchetypeTypeClassificationLayoutScale> scales = this.classificationLayoutScaleDao
				.findByProperty("user", user, "archetypeTypeClassification",
						classifcation);
		float scale;
		if (scales.size() >= 1) {
			throw new LayoutException(
					"More than one archetype type layout scale have found.");
		} else if (scales.size() == 0) {
			scale = 1;
		} else {
			scale = scales.get(0).getScale();
		}
		return new ArchetypeTypeClassificationLayoutInfo(settings,scale);
	}

	@Override
	public void updateClassificationLayoutByIdAndUserName(
			Integer classifcationId, String userName,
			List<ArchetypeTypeLayoutSettingInfo> infos) throws LayoutException {
		User user = this.userDao.findUniqueByProperty("userName", userName);
		if (user == null) {
			throw new LayoutException("User '" + userName + "' does not exist.");
		}
		ArchetypeTypeClassification classifcation = this.archetypeTypeClassificationDao
				.findById(classifcationId);
		if (classifcation == null) {
			throw new LayoutException("Classifcation with id '"
					+ classifcationId + "' does not exist.");
		}
		Map<Integer, ArchetypeTypeLayoutSetting> settings = this.archetypeTypeLayoutSettingDao
				.findByProperty("user", user, "archetypeTypeClassification",
						classifcation)
				.stream()
				.collect(
						Collectors.toMap(
								setting -> setting.getArchetypeTypeId(),
								setting -> setting));
		for (ArchetypeTypeLayoutSettingInfo info : infos) {
			ArchetypeTypeLayoutSetting setting = settings.get(info
					.getArchetypeTypeId());
			if (setting == null) {
				setting = new ArchetypeTypeLayoutSetting();
				ArchetypeType archetypeType = this.archetypeTypeDao
						.findById(info.getArchetypeTypeId());
				if (archetypeType == null) {
					throw new LayoutException("Archetype type with id '"
							+ info.getArchetypeTypeId() + "' does not exist.");
				}
				setting.setArchetypeType(archetypeType);
				setting.setArchetypeTypeClassification(classifcation);
				setting.setUser(user);
			}
			setting.setPositionX(info.getPositionX());
			setting.setPositionY(info.getPositionY());
			this.archetypeTypeLayoutSettingDao.saveOrUpdate(setting);
		}
	}

	@Override
	public ArchetypeTypeLayoutInfo getArchetypeTypeLatyoutByIdAndUserName(
			Integer typeId, String userName) throws LayoutException {
		User user = this.userDao.findUniqueByProperty("userName", userName);
		if (user == null) {
			throw new LayoutException("User '" + userName + "' does not exist.");
		}
		ArchetypeType type = this.archetypeTypeDao.findById(typeId);
		if (type == null) {
			throw new LayoutException("Archetype type with id '" + typeId
					+ "' does not exist.");
		}
		List<ArchetypeHostLayoutSettingInfo> settings = this.archetypeHostLayoutSettingDao
				.findByProperty("user", user, "archetypeType", type)
				.stream()
				.map(setting -> {
					ArchetypeHostLayoutSettingInfo info = new ArchetypeHostLayoutSettingInfo();
					info.setArchetypeHostId(setting.getArchetypeHostId());
					info.setPositionX(setting.getPositionX());
					info.setPositionY(setting.getPositionY());
					return info;
				}).collect(Collectors.toList());
		List<ArchetypeTypeLayoutScale> scales = this.typeLayoutScaleDao
				.findByProperty("user", user, "archetypeType", type);
		float scale;
		if (scales.size() >= 1) {
			throw new LayoutException(
					"More than one archetype type layout scale have found.");
		} else if (scales.size() == 0) {
			scale = 1;
		} else {
			scale = scales.get(0).getScale();
		}
		return new ArchetypeTypeLayoutInfo(settings, scale);
	}

	@Override
	public void updateArchetypeTypeLatyoutByIdAndUserName(Integer typeId,
			String userName, List<ArchetypeHostLayoutSettingInfo> infos)
			throws LayoutException {
		User user = this.userDao.findUniqueByProperty("userName", userName);
		if (user == null) {
			throw new LayoutException("User '" + userName + "' does not exist.");
		}
		ArchetypeType type = this.archetypeTypeDao.findById(typeId);
		if (type == null) {
			throw new LayoutException("Archetype type with id '" + typeId
					+ "' does not exist.");
		}
		Map<Integer, ArchetypeHostLayoutSetting> settings = this.archetypeHostLayoutSettingDao
				.findByProperty("user", user, "archetypeType", type)
				.stream()
				.collect(
						Collectors.toMap(
								setting -> setting.getArchetypeHostId(),
								setting -> setting));
		for (ArchetypeHostLayoutSettingInfo info : infos) {
			ArchetypeHostLayoutSetting setting = settings.get(info
					.getArchetypeHostId());
			if (setting == null) {
				setting = new ArchetypeHostLayoutSetting();
				ArchetypeHost archetypeHost = this.archetypeHostDao
						.findById(info.getArchetypeHostId());
				if (archetypeHost == null) {
					throw new LayoutException("Archetype host with id '"
							+ info.getArchetypeHostId() + "' does not exist.");
				}
				setting.setArchetypeHost(archetypeHost);
				setting.setArchetypeType(type);
				setting.setUser(user);
			}
			setting.setPositionX(info.getPositionX());
			setting.setPositionY(info.getPositionY());
			this.archetypeHostLayoutSettingDao.saveOrUpdate(setting);
		}
	}

}
