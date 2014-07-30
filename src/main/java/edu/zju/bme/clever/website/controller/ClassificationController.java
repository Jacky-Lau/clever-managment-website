package edu.zju.bme.clever.website.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.zju.bme.clever.website.exception.LayoutException;
import edu.zju.bme.clever.website.service.ClassificationService;
import edu.zju.bme.clever.website.view.entity.ArchetypeTypeInfo;
import edu.zju.bme.clever.website.view.entity.ClassificationBriefInfo;

@Controller
public class ClassificationController {

	@Resource(name = "classificationService")
	private ClassificationService classificationService;

	@RequestMapping(value = "/classifications", method = RequestMethod.GET)
	@ResponseBody
	public List<ClassificationBriefInfo> getAllClassificationInfos() {
		return this.classificationService.getAllClassifications();
	}

	@RequestMapping(value = "/classification/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ClassificationBriefInfo getClassificationById(
			@PathVariable Integer id) {
		return this.classificationService.getClassificationBriefInfoById(id);
	}
	
}
