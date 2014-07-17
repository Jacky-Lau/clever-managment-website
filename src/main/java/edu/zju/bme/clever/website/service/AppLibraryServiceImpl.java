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
import org.springframework.web.multipart.MultipartFile;

import edu.zju.bme.clever.website.dao.ApplicationDao;
import edu.zju.bme.clever.website.model.entity.Application;

@Service("appLibraryService")
public class AppLibraryServiceImpl implements AppLibraryService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "applicationDao")
	private ApplicationDao applicationDao;
	@Resource
	private ServletContext servletContext;

	private static final String IMG_PATH_PREFIX = "/upload/img/app/";

	@Override
	public List<Application> getAllApplications() {
		return this.applicationDao.selectAll();
	}

	@Override
	public void saveApplication(String name, String description, String url,
			MultipartFile img) {
		String appFolderUrl = servletContext.getRealPath("/WEB-INF"
				+ IMG_PATH_PREFIX);
		File imgFile = new File(appFolderUrl + "/" + name);
		try {
			img.transferTo(imgFile);
		} catch (IllegalStateException | IOException ex) {
			this.logger.info("Save application {} img failed.", name, ex);
			return;
		}
		Application application = new Application();
		application.setName(name);
		application.setDescription(description);
		application.setUrl(url);
		application.setImgPath(IMG_PATH_PREFIX + name);
		this.applicationDao.save(application);
	}

	@Override
	public void updateApplication(Integer id, String name, String description,
			String url, MultipartFile img) {
		Application application = this.applicationDao.findById(id);
		application.setDescription(description);
		application.setName(name);
		application.setUrl(url);
		File imgFile = new File(servletContext.getRealPath("/WEB-INF"
				+ application.getUrl()));
		try {
			img.transferTo(imgFile);
		} catch (IllegalStateException | IOException ex) {
			this.logger.info("Update application {} img failed.", name, ex);
			return;
		}
		this.applicationDao.update(application);
	}

	@Override
	public Application getApplicationById(Integer id) {
		return this.applicationDao.findById(id);
	}
}
