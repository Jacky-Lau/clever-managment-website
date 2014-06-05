package edu.zju.bme.clever.website.controller;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.openehr.am.archetype.Archetype;
import org.openehr.am.serialize.ADLSerializer;
import org.openehr.rm.common.resource.ResourceDescription;
import org.openehr.rm.common.resource.ResourceDescriptionItem;
import org.openehr.rm.support.identification.ArchetypeID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import se.acode.openehr.parser.ADLParser;
import edu.zju.bme.clever.website.dao.CommitSequenceDao;
import edu.zju.bme.clever.website.entity.ArchetypeFile;
import edu.zju.bme.clever.website.entity.CommitSequence;
import edu.zju.bme.clever.website.service.ArchetypePersistanceService;
import edu.zju.bme.clever.website.service.ArchetypeValidationService;
import edu.zju.bme.clever.website.util.ArchetypeUtil;
import edu.zju.bme.clever.website.util.FileUtil;

@Controller
public class FileUploadController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "archetypePersistanceService")
	private ArchetypePersistanceService archetypePersistanceService;
	@Resource(name = "archetypeValidationService")
	private ArchetypeValidationService archetypeValidationService;

	@RequestMapping(value = "/archetypeFile", method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResult uploadSingleFile(
			@RequestParam(value = "file", required = true) MultipartFile file,
			@RequestParam(value = "commitSequenceId", required = true) Integer commitSequenceId,
			@RequestParam(value = "overwriteChange", defaultValue = "false") boolean overwriteChange) {

		// Get commit sequence
		if (commitSequenceId == null) {
			this.logger.info("Commit sequence ID is null.");
			throw new IllegalArgumentException("Commit sequence ID is null.");
		}
		CommitSequence commitSequence = this.archetypePersistanceService
				.getCommitSequenceById(commitSequenceId);
		// Get uploaded archetype
		String fileName = file.getOriginalFilename();
		long fileSize = file.getSize();
		this.logger.info(
				"Recieves file: {}, size: {}, commit sequence ID: {}.",
				fileName, fileSize, commitSequenceId);
		// Initialize file upload result
		FileUploadResult result = new FileUploadResult();
		result.setFileName(fileName);
		String fileStatus = FileUploadStatusConstant.UPLOADED;
		String archetypeId = "";
		String archetypeContent = "";
		String zhPurpose = "";
		String zhKeywords = "";
		String zhUse = "";
		// Parse the archetype
		try {
			ADLParser parser = new ADLParser(file.getInputStream(), "UTF-8");
			Archetype archetype = parser.parse();
			ArchetypeID archetypeIdNode = archetype.getArchetypeId();
			archetypeId = archetypeIdNode.toString();
			ResourceDescription resourceDescription = archetype
					.getDescription();
			List<ResourceDescriptionItem> resourceDescriptionItems = resourceDescription
					.getDetails();
			for (ResourceDescriptionItem resourceDescriptionItem : resourceDescriptionItems) {
				if (resourceDescriptionItem.getLanguage().getCodeString()
						.equals("zh")) {
					if (resourceDescriptionItem.getPurpose() != null) {
						zhPurpose = resourceDescriptionItem.getPurpose();
					}
					if (resourceDescriptionItem.getUse() != null) {
						zhUse = resourceDescriptionItem.getUse();
					}
					if (resourceDescriptionItem.getKeywords() != null) {
						zhKeywords = String.join("|",
								resourceDescriptionItem.getKeywords());
					}
				}
			}
			ADLSerializer adlSerilizer = new ADLSerializer();
			archetypeContent = adlSerilizer.output(archetype);
		} catch (Exception ex) {
			this.logger.info("Persist file {} failed.", ex);
			fileStatus = FileUploadStatusConstant.INVALID;
			result.setFileStatus(fileStatus);
			return result;
		}

		// Validate the archetype
		if (this.archetypeValidationService.validate(archetypeContent)) {
			ArchetypeFile archetypeFileFromDB = this.archetypePersistanceService
					.getArchetypeByName(archetypeId);
			ArchetypeFile uploadedArchetypeFile = new ArchetypeFile();
			uploadedArchetypeFile.setCommitSequence(commitSequence);
			uploadedArchetypeFile.setModifyTime(Calendar.getInstance());
			uploadedArchetypeFile.setContent(archetypeContent);
			uploadedArchetypeFile.setName(archetypeId);
			uploadedArchetypeFile.setKeywords(zhKeywords);
			uploadedArchetypeFile.setPurpose(zhPurpose);
			uploadedArchetypeFile.setUse(zhUse);

			if (archetypeFileFromDB != null) {
				if (archetypeFileFromDB.getContent()
						.compareTo(archetypeContent) == 0) {
					fileStatus = FileUploadStatusConstant.EXISTED;
				} else {
					if (overwriteChange) {
						// save the archetype
						uploadedArchetypeFile
								.setId(archetypeFileFromDB.getId());
						this.archetypePersistanceService
								.updateArchetypeFile(uploadedArchetypeFile);
					} else {
						fileStatus = FileUploadStatusConstant.CHANGED;
					}
				}
			} else {
				// save the archetype
				this.archetypePersistanceService
						.saveArchetypeFile(uploadedArchetypeFile);
			}
		} else {
			fileStatus = FileUploadStatusConstant.INVALID;
		}
		result.setFileStatus(fileStatus);
		return result;
	}

	@RequestMapping(value = "/commitSequence", method = RequestMethod.GET)
	@ResponseBody
	public CommitSequence getCommitSequence() {
		return this.archetypePersistanceService.getNewCommitSequence();
	}

	public class FileUploadResult {
		private String fileName;
		private String fileStatus;

		public FileUploadResult() {

		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getFileStatus() {
			return fileStatus;
		}

		public void setFileStatus(String fileStatus) {
			this.fileStatus = fileStatus;
		}

	}

	public class FileUploadStatusConstant {
		public static final String UPLOADED = "UPLOADED";
		public static final String EXISTED = "EXISTED";
		public static final String CHANGED = "CHANGED";
		public static final String INVALID = "INVALID";
	}
}
