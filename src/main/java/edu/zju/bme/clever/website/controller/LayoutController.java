package edu.zju.bme.clever.website.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.xerces.impl.dv.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.service.LayoutService;
import edu.zju.bme.clever.website.view.entity.ArchetypeHostLayoutSettingInfo;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeLayoutSettingInfo;

@Controller
public class LayoutController {

	@Resource(name = "layoutService")
	private LayoutService layoutService;
	@Resource
	private ServletContext servletContext;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	final private static String DEFAULT_USER = "admin";
	private static final String OUTLINE_FOLDER_PATH = "/upload/img/outline/";

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
				.map(principal -> principal.getUsername()).orElse(null);
		if (userName == null) {
			result.setSucceeded(false);
			result.setMessage("You have not logged in.");
			return result;
		}
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
	public FileUploadResult updateArchetypeTypeLayoutById(
			@PathVariable Integer id, Authentication authentication,
			@RequestBody List<ArchetypeHostLayoutSettingInfo> infos) {
		FileUploadResult result = new FileUploadResult();
		result.setSucceeded(true);
		String userName = Optional.ofNullable(authentication)
				.map(authen -> (UserDetails) authen.getPrincipal())
				.map(principal -> principal.getUsername()).orElse(null);
		if (userName == null) {
			result.setSucceeded(false);
			result.setMessage("You have not logged in.");
			return result;
		}
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

	@RequestMapping(value = "/archetypeType/id/{id}/outline", method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResult updateArchetypeTypeOutlineById(
			@PathVariable Integer id, Authentication authentication,
			@RequestBody String outline) {
		FileUploadResult result = new FileUploadResult();
		result.setSucceeded(true);
		String userName = Optional.ofNullable(authentication)
				.map(authen -> (UserDetails) authen.getPrincipal())
				.map(principal -> principal.getUsername()).orElse(null);
		if (userName == null) {
			result.setSucceeded(false);
			result.setMessage("You have not logged in.");
			return result;
		}
		String outlineFolderUrl = servletContext.getRealPath("/WEB-INF"
				+ OUTLINE_FOLDER_PATH);
		File outlineFolder = new File(outlineFolderUrl);
		if (!outlineFolder.exists()) {
			outlineFolder.mkdir();
		}
		File userFolder = new File(outlineFolderUrl + "/" + userName);
		// create user folder if not exists
		if (!userFolder.exists()) {
			userFolder.mkdir();
		}
		File outlineFile = new File(userFolder, "type-" + id + ".png");
		Pattern pattern = Pattern.compile("data:image/png;base64,(.*)$");
		Matcher matcher = pattern.matcher(outline);
		String code = null;
		while (matcher.find()) {
			code = matcher.group(1);
		}
		if (code == null) {
			result.setSucceeded(false);
			result.setMessage("Save image failed.");
			return result;
		}
		byte[] data = Base64.decode(code);
		try (OutputStream stream = new FileOutputStream(outlineFile)) {
			stream.write(data);
			stream.close();
		} catch (IOException ex) {
			result.setSucceeded(false);
			result.setMessage("Save image failed, error: " + ex.getMessage());
			return result;
		}
		return result;
	}
}
