package edu.zju.bme.clever.website.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "/archetype/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getArchetypeById(@PathVariable Integer id){
		return this.archetypeProviderService.getArchetypeXmlById(id);
	}
	
	@RequestMapping(value = "/archetype/name/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String getArchetypeByName(@PathVariable String name){
		return this.archetypeProviderService.getArchetypeXmlByName(name);
	}
	
}
