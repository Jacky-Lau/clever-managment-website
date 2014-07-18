package edu.zju.bme.clever.website.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.zju.bme.clever.website.exception.AppLibraryPersistException;
import edu.zju.bme.clever.website.model.entity.Application;
import edu.zju.bme.clever.website.service.AppLibraryService;

@Controller
public class AppLibraryController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "appLibraryService")
	private AppLibraryService appLibraryService;

	@RequestMapping(value = "/applications", method = RequestMethod.GET)
	@ResponseBody
	public List<Application> getAllApps() {
		return this.appLibraryService.getAllApplications();
	}

	@RequestMapping(value = "/application/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Application getApplicationById(@PathVariable Integer id) {
		return this.appLibraryService.getApplicationById(id);
	}

	@RequestMapping(value = "/application/id/{id}", method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResult updateApplicationById(
			@PathVariable Integer id,
			@RequestParam(value = "img", required = false) MultipartFile img,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "url", required = true) String url) {
		FileUploadResult result = new FileUploadResult();
		result.setSucceeded(true);
		try {
			this.appLibraryService.updateApplication(id, name, description,
					url, img);
		} catch (AppLibraryPersistException ex) {
			result.setSucceeded(false);
			result.setMessage(ex.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/application/id/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteApplicationById(@PathVariable Integer id) {
		this.appLibraryService.deleteApplicationById(id);
	}

	@RequestMapping(value = "/application", method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResult uploadNewApplication(
			@RequestParam(value = "img", required = true) MultipartFile img,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "url", required = true) String url) {
		FileUploadResult result = new FileUploadResult();
		result.setSucceeded(true);
		try {
			this.appLibraryService.saveApplication(name, description, url, img);
		} catch (AppLibraryPersistException ex) {
			result.setSucceeded(false);
			result.setMessage(ex.getMessage());
		}
		return result;
	}
}
