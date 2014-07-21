package edu.zju.bme.clever.website.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.zju.bme.clever.website.dao.ApplicationDao;
import edu.zju.bme.clever.website.exception.AppLibraryPersistException;
import edu.zju.bme.clever.website.model.entity.Application;

@Service("appLibraryService")
@Transactional
public class AppLibraryServiceImpl implements AppLibraryService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "applicationDao")
	private ApplicationDao applicationDao;
	@Resource
	private ServletContext servletContext;

	private static final String APP_FOLDER_PATH = "/upload/img/app/";

	@Override
	public List<Application> getAllApplications() {
		return this.applicationDao.selectAll();
	}

	@Override
	public void saveApplication(String name, String description, String url,
			MultipartFile img) throws AppLibraryPersistException {
		if (this.applicationDao.findUniqueByProperty("name", name) != null) {
			throw new AppLibraryPersistException("Application " + name
					+ " already exists.");
		}
		String appFolderUrl = servletContext.getRealPath("/WEB-INF"
				+ APP_FOLDER_PATH);
		File imgFile = new File(appFolderUrl + "/" + name);
		try {
			img.transferTo(imgFile);
		} catch (IllegalStateException | IOException ex) {
			this.logger.info("Save application {} img failed.", name, ex);
			throw new AppLibraryPersistException("Save application " + name
					+ " img failed, error: " + ex.getMessage());
		}
		Application application = new Application();
		application.setName(name);
		application.setDescription(description);
		application.setUrl(url);
		application.setImgPath(APP_FOLDER_PATH + name);
		this.applicationDao.save(application);
	}

	@Override
	public void updateApplication(Integer id, String name, String description,
			String url, MultipartFile img) throws AppLibraryPersistException {
		Application application = this.applicationDao.findById(id);
		application.setDescription(description);
		application.setName(name);
		application.setUrl(url);
		if (img != null) {
			File oldImgFile = new File(servletContext.getRealPath("/WEB-INF"
					+ application.getImgPath()));
			oldImgFile.delete();
			File newImgFile = new File(servletContext.getRealPath("/WEB-INF"
					+ APP_FOLDER_PATH + name));
			try {
				img.transferTo(newImgFile);
			} catch (IllegalStateException | IOException ex) {
				this.logger.info("Update application {} img failed.", name, ex);
				throw new AppLibraryPersistException("Update application "
						+ name + " img failed, error: " + ex.getMessage());
			}
			application.setImgPath(APP_FOLDER_PATH + name);
		}
		this.applicationDao.update(application);
	}

	@Override
	public Application getApplicationById(Integer id) {
		return this.applicationDao.findById(id);
	}
	
	@Override
	public void deleteApplicationById(Integer id){
		Application application = this.applicationDao.findById(id);
		File imgFile = new File(servletContext.getRealPath("/WEB-INF"
				+ APP_FOLDER_PATH + application.getName()));
		imgFile.delete();
		this.applicationDao.delete(application);
	}
}
