package edu.zju.bme.clever.website.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.openehr.am.archetype.Archetype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import se.acode.openehr.parser.ADLParser;
import edu.zju.bme.clever.website.model.entity.ArchetypeFile;
import edu.zju.bme.clever.website.model.entity.FileProcessResult;
import edu.zju.bme.clever.website.model.entity.FileProcessResult.FileStatusConstant;
import edu.zju.bme.clever.website.service.ArchetypeValidationService;

@Controller
public class FileValidateController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "archetypeValidationService")
	private ArchetypeValidationService archetypeValidationService;

	@RequestMapping(value = "/archetypes/validation", method = RequestMethod.POST)
	@ResponseBody
	public List<FileProcessResult> validateFiles(
			@RequestParam(value = "files", required = true) MultipartFile[] files) {
		final Map<String, Archetype> archetypes = new HashMap<String, Archetype>();
		final Map<String, FileProcessResult> results = new HashMap<String, FileProcessResult>();
		final List<FileProcessResult> allResults = new ArrayList<FileProcessResult>();
		Arrays.asList(files)
				.forEach(
						file -> {
							String fileName = file.getOriginalFilename();
							long fileSize = file.getSize();
							this.logger.trace("Validating file: {}, size: {}.",
									fileName, fileSize);
							FileProcessResult result = new FileProcessResult();
							result.setName(fileName);
							result.setStatus(FileProcessResult.FileStatusConstant.DEFAULT);
							allResults.add(result);
							try {
								ADLParser parser = new ADLParser(file
										.getInputStream(), "UTF-8");
								Archetype archetype = parser.parse();
								archetypes.put(archetype.getArchetypeId()
										.getValue(), archetype);
								results.put(archetype.getArchetypeId()
										.getValue(), result);
							} catch (Exception ex) {
								this.logger.debug("Parse file {} failed.",
										file.getOriginalFilename(), ex);
								result.setStatus(FileProcessResult.FileStatusConstant.INVALID);
								result.setMessage("Archetype parse failed, error: "
										+ ex.getMessage());
							}
						});
		this.archetypeValidationService
				.validateConsistency(archetypes, results);
		results.values()
				.stream()
				.filter(result -> result.getStatus().compareTo(
						FileStatusConstant.DEFAULT) == 0)
				.forEach(
						result -> {
							result.setStatus(FileProcessResult.FileStatusConstant.VALID);
						});
		return allResults;
	}
}
