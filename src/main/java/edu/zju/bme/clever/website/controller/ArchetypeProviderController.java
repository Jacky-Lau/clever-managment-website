package edu.zju.bme.clever.website.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.zju.bme.clever.website.entity.ArchetypeBriefInfo;
import edu.zju.bme.clever.website.service.ArchetypeProviderService;

@Controller
public class ArchetypeProviderController {
	
	@Resource(name="archetypeProviderService")
	private ArchetypeProviderService archetypeProviderService;
	
	@RequestMapping(value = "/archetypeList", method = RequestMethod.GET)
	@ResponseBody
	public List<ArchetypeBriefInfo> getArchetypeList(){
		return this.archetypeProviderService.getArchetypeList();
	}
	
	
	
}
