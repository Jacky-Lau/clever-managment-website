package edu.zju.bme.clever.website.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.openehr.am.archetype.Archetype;
import org.openehr.am.archetype.constraintmodel.CObject;
import org.openehr.am.archetype.ontology.ArchetypeOntology;
import org.openehr.am.archetype.ontology.OntologyDefinitions;
import org.openehr.am.serialize.ADLSerializer;
import org.openehr.rm.common.resource.ResourceDescription;
import org.openehr.rm.common.resource.ResourceDescriptionItem;
import org.openehr.rm.support.identification.ArchetypeID;
import org.openehr.rm.datatypes.basic.ReferenceModelName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import se.acode.openehr.parser.ADLParser;
import edu.zju.bme.clever.website.dao.CommitSequenceDao;
import edu.zju.bme.clever.website.dao.UserDao;
import edu.zju.bme.clever.website.exception.ArchetypePersistException;
import edu.zju.bme.clever.website.model.entity.ArchetypeFile;
import edu.zju.bme.clever.website.model.entity.ArchetypeNode;
import edu.zju.bme.clever.website.model.entity.CommitSequence;
import edu.zju.bme.clever.website.model.entity.FileProcessResult;
import edu.zju.bme.clever.website.model.entity.User;
import edu.zju.bme.clever.website.service.ArchetypeExtractService;
import edu.zju.bme.clever.website.service.ArchetypePersistanceService;
import edu.zju.bme.clever.website.service.ArchetypeProviderService;
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
	@Resource(name = "archetypeExtractService")
	private ArchetypeExtractService archetypeExtractService;
	@Resource(name = "archetypeProviderService")
	private ArchetypeProviderService archetypeProviderService;

	@RequestMapping(value = "/archetypes", method = RequestMethod.POST)
	@ResponseBody
	public FileUploadResult uploadFiles(
			@RequestParam(value = "files", required = true) MultipartFile[] files,
			Authentication authentication) {
		String userName = ((UserDetails) authentication.getPrincipal())
				.getUsername();
		Map<String, Archetype> archetypes = new HashMap<String, Archetype>();
		List<String> archetypeContents = new ArrayList<String>();
		FileUploadResult result = new FileUploadResult();
		result.setSucceeded(true);

		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			long fileSize = file.getSize();
			this.logger.trace("Persisting file: {}, size: {}.", fileName,
					fileSize);
			try {
				ADLParser parser = new ADLParser(file.getInputStream(), "UTF-8");
				Archetype archetype = parser.parse();
				archetypes
						.put(archetype.getArchetypeId().getValue(), archetype);
				ADLSerializer serializer = new ADLSerializer();
				archetypeContents.add(serializer.output(archetype));
			} catch (Exception ex) {
				this.logger.debug("Parse file {} failed.",
						file.getOriginalFilename(), ex);
				result.setSucceeded(false);
				result.setMessage("Parse file " + file.getOriginalFilename()
						+ " failed.");
				return result;
			}
		}
		archetypeContents.addAll(this.archetypeProviderService
				.getAllArchetypeAdls());
		if (!this.archetypeValidationService
				.validateFeasibility(archetypeContents)) {
			this.logger.debug("Update schema on CLEVER failed");
			result.setSucceeded(false);
			result.setMessage("Update schema on CLEVER failed");
			return result;
		}
		try {
			this.archetypePersistanceService.saveArchetypes(
					new ArrayList<Archetype>(archetypes.values()), userName);
		} catch (ArchetypePersistException ex) {
			this.logger.debug("Persist archetypes failed.", ex);
			result.setSucceeded(false);
			result.setMessage("Persist archetypes failed, error: "
					+ ex.getMessage());
			return result;
		}
		return result;
	}

	// @RequestMapping(value = "/archetypes", method = RequestMethod.POST)
	// @ResponseBody
	// public FileUploadResult uploadSingleFile(
	// @RequestParam(value = "file", required = true) MultipartFile file,
	// @RequestParam(value = "commitSequenceId", required = true) Integer
	// commitSequenceId,
	// @RequestParam(value = "overwriteChange", defaultValue = "false") boolean
	// overwriteChange) {
	//
	// // Get commit sequence
	// if (commitSequenceId == null) {
	// this.logger.info("Commit sequence ID is null.");
	// throw new IllegalArgumentException("Commit sequence ID is null.");
	// }
	// CommitSequence commitSequence = this.archetypePersistanceService
	// .getCommitSequenceById(commitSequenceId);
	// // Get uploaded archetype
	// String fileName = file.getOriginalFilename();
	// long fileSize = file.getSize();
	// this.logger.info(
	// "Recieves file: {}, size: {}, commit sequence ID: {}.",
	// fileName, fileSize, commitSequenceId);
	// // Initialize file upload result
	// FileUploadResult result = new FileUploadResult();
	// result.setFileName(fileName);
	// String fileStatus = FileProcessResult.FileStatusConstant.UPLOADED;
	// String archetypeId = "";
	// String archetypeContent = "";
	// String purpose = "";
	// String keywords = "";
	// String use = "";
	// String originalLanguage = "";
	// // Parse the archetype
	// try {
	// ADLParser parser = new ADLParser(file.getInputStream(), "UTF-8");
	// Archetype archetype = parser.parse();
	// ArchetypeID archetypeIdNode = archetype.getArchetypeId();
	// archetypeId = archetypeIdNode.toString();
	// originalLanguage = archetype.getOriginalLanguage().getCodeString();
	// ResourceDescription resourceDescription = archetype
	// .getDescription();
	// List<ResourceDescriptionItem> resourceDescriptionItems =
	// resourceDescription
	// .getDetails();
	// for (ResourceDescriptionItem resourceDescriptionItem :
	// resourceDescriptionItems) {
	// if (resourceDescriptionItem.getLanguage().getCodeString()
	// .equals(originalLanguage)) {
	// if (resourceDescriptionItem.getPurpose() != null) {
	// purpose = resourceDescriptionItem.getPurpose();
	// }
	// if (resourceDescriptionItem.getUse() != null) {
	// use = resourceDescriptionItem.getUse();
	// }
	// if (resourceDescriptionItem.getKeywords() != null) {
	// keywords = String.join("|",
	// resourceDescriptionItem.getKeywords());
	// }
	// }
	// }
	// ADLSerializer adlSerilizer = new ADLSerializer();
	// archetypeContent = adlSerilizer.output(archetype);
	// } catch (Exception ex) {
	// this.logger.info("Parse file {} failed.", fileName, ex);
	// fileStatus = FileProcessResult.FileStatusConstant.INVALID;
	// result.setFileStatus(fileStatus);
	// return result;
	// }
	//
	// // Validate the archetype
	// // if (this.archetypeValidationService.validate(archetypeContent)) {
	// if (true) {
	// ArchetypeFile archetypeFileFromDB = this.archetypePersistanceService
	// .getArchetypeByName(archetypeId);
	// ArchetypeFile uploadedArchetypeFile = new ArchetypeFile();
	// uploadedArchetypeFile.setCommitSequence(commitSequence);
	// uploadedArchetypeFile.setModifyTime(Calendar.getInstance());
	// uploadedArchetypeFile.setContent(archetypeContent);
	// uploadedArchetypeFile.setName(archetypeId);
	// uploadedArchetypeFile.setKeywords(keywords);
	// uploadedArchetypeFile.setPurpose(purpose);
	// uploadedArchetypeFile.setUse(use);
	// uploadedArchetypeFile.setOriginalLanguage(originalLanguage);
	//
	// if (archetypeFileFromDB != null) {
	// if (archetypeFileFromDB.getContent()
	// .compareTo(archetypeContent) == 0) {
	// fileStatus = FileProcessResult.FileStatusConstant.EXISTED;
	// } else {
	// if (overwriteChange) {
	// // save the archetype
	// uploadedArchetypeFile
	// .setId(archetypeFileFromDB.getId());
	// this.archetypePersistanceService
	// .updateArchetypeFile(uploadedArchetypeFile);
	// } else {
	// fileStatus = FileProcessResult.FileStatusConstant.CHANGED;
	// }
	// }
	// } else {
	// // save the archetype
	// this.archetypePersistanceService
	// .saveArchetypeFile(uploadedArchetypeFile);
	// }
	// } else {
	// fileStatus = FileProcessResult.FileStatusConstant.INVALID;
	// }
	// result.setFileStatus(fileStatus);
	// return result;
	// }
	//
	// @RequestMapping(value = "/commitSequence", method = RequestMethod.GET)
	// @ResponseBody
	// public CommitSequence getCommitSequence() {
	// return this.archetypePersistanceService.getNewCommitSequence();
	// }
}
