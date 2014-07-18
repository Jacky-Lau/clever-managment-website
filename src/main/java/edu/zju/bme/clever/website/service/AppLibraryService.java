package edu.zju.bme.clever.website.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.zju.bme.clever.website.exception.AppLibraryPersistException;
import edu.zju.bme.clever.website.model.entity.Application;

public interface AppLibraryService {

	public List<Application> getAllApplications();

	public Application getApplicationById(Integer id);

	public void saveApplication(String name, String description, String url,
			MultipartFile img) throws AppLibraryPersistException;

	public void updateApplication(Integer id, String name, String description,
			String url, MultipartFile img) throws AppLibraryPersistException;
	
	public void deleteApplicationById(Integer id);
}
