package edu.zju.bme.clever.website.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.service.LayoutService;
import edu.zju.bme.clever.website.view.entity.ArchetypeHostLayoutSettingInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeLayoutSettingInfo;

@Controller
public class LayoutController {

	@Resource(name = "layoutService")
	private LayoutService layoutService;

	final private static String DEFAULT_USER = "admin";

	@RequestMapping(value = "/classification/id/{id}/layout", method = RequestMethod.GET)
	@ResponseBody
	public List<ArchetypeTypeLayoutSettingInfo> getClassificationLayoutById(
			@PathVariable Integer id, Authentication authentication) {
		String userName = Optional.ofNullable(authentication)
				.map(authen -> (UserDetails) authen.getPrincipal())
				.map(principal -> principal.getUsername()).orElse(DEFAULT_USER);
		try {
			List<ArchetypeTypeLayoutSettingInfo> infos = this.layoutService
					.getClassificationLayoutByIdAndUserName(id, userName);
			if (infos.size() == 0) {
				infos = this.layoutService
						.getClassificationLayoutByIdAndUserName(id,
								DEFAULT_USER);
			}
			return infos;
		} catch (LayoutException ex) {
			return null;
		}
	}

	@RequestMapping(value = "/archetypeType/id/{id}/layout", method = RequestMethod.GET)
	@ResponseBody
	public List<ArchetypeHostLayoutSettingInfo> getArchetypeTypeLayoutById(
			@PathVariable Integer id, Authentication authentication) {
		String userName = Optional.ofNullable(authentication)
				.map(authen -> (UserDetails) authen.getPrincipal())
				.map(principal -> principal.getUsername()).orElse(DEFAULT_USER);
		try {
			List<ArchetypeHostLayoutSettingInfo> infos = this.layoutService
					.getArchetypeTypeLatyoutByIdAndUserName(id, userName);
			if (infos.size() == 0) {
				infos = this.layoutService
						.getArchetypeTypeLatyoutByIdAndUserName(id,
								DEFAULT_USER);
			}
			return infos;
		} catch (LayoutException ex) {
			return null;
		}
	}

	@RequestMapping(value = "/classification/id/{id}/layout", method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResult updateClassificationLayoutById(
			@PathVariable Integer id, Authentication authentication,
			@RequestBody List<ArchetypeTypeLayoutSettingInfo> infos) {
		FileUploadResult result = new FileUploadResult();
		result.setSucceeded(true);
		String userName = Optional.ofNullable(authentication)
				.map(authen -> (UserDetails) authen.getPrincipal())
				.map(principal -> principal.getUsername()).orElse(DEFAULT_USER);
		try {
			this.layoutService.updateClassificationLayoutByIdAndUserName(id,
					userName, infos);
		} catch (LayoutException ex) {
			result.setSucceeded(false);
			result.setMessage(ex.getMessage());
			return result;
		}
		return result;
	}

	@RequestMapping(value = "/archetypeType/id/{id}/layout", method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResult getArchetypeTypeLayoutById(
			@PathVariable Integer id, Authentication authentication,
			@RequestBody List<ArchetypeHostLayoutSettingInfo> infos) {
		FileUploadResult result = new FileUploadResult();
		result.setSucceeded(true);
		String userName = Optional.ofNullable(authentication)
				.map(authen -> (UserDetails) authen.getPrincipal())
				.map(principal -> principal.getUsername()).orElse(DEFAULT_USER);
		try {
			this.layoutService.updateArchetypeTypeLatyoutByIdAndUserName(id,
					userName, infos);
		} catch (LayoutException ex) {
			result.setSucceeded(false);
			result.setMessage(ex.getMessage());
			return result;
		}
		return result;
	}
}
