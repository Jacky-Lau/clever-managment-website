package edu.zju.bme.clever.website.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.openehr.am.archetype.Archetype;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.zju.bme.clever.website.service.ArchetypeProviderService;
import edu.zju.bme.clever.website.view.entity.ArchetypeBriefInfo;

@Controller
public class ArchetypeProviderController {
	
	@Resource(name="archetypeProviderService")
	private ArchetypeProviderService archetypeProviderService;
	
//	@RequestMapping(value = "/archetypes", method = RequestMethod.GET)
//	@ResponseBody
//	public List<ArchetypeBriefInfo> getArchetypeList(){
//		return this.archetypeProviderService.getArchetypeList();
//	}
	
	@RequestMapping(value = "/archetype/id/{id}.xml", method = RequestMethod.GET)
	@ResponseBody
	public String getArchetypeXmlById(@PathVariable Integer id){
		return this.archetypeProviderService.getArchetypeXmlById(id);
	}
	
	@RequestMapping(value = "/archetype/name/{name}.xml", method = RequestMethod.GET)
	@ResponseBody
	public String getArchetypeXmlByName(@PathVariable String name){
		return this.archetypeProviderService.getArchetypeXmlByName(name);
	}
	
	@RequestMapping(value = "/archetype/id/{id}.adl", method = RequestMethod.GET)
	@ResponseBody
	public String getArchetypeAdlById(@PathVariable Integer id){
		return this.archetypeProviderService.getArchetypeAdlById(id);
	}
	
	@RequestMapping(value = "/archetype/name/{name}.adl", method = RequestMethod.GET)
	@ResponseBody
	public String getArchetypeAdlByName(@PathVariable String name){
		return this.archetypeProviderService.getArchetypeAdlByName(name);
	}
	
	@RequestMapping(value = "/archetype/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Archetype getArchetypeById(@PathVariable Integer id){
		return this.archetypeProviderService.getArchetypeById(id);
	}
	
	@RequestMapping(value = "/archetype/name/{name}", method = RequestMethod.GET)
	@ResponseBody
	public Archetype getArchetypeByName(@PathVariable String name){
		return this.archetypeProviderService.getArchetypeByName(name);
	}
	
	@RequestMapping(value = "/archetypes/deployed", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getDeployedArchetypeIds(){
		return this.archetypeProviderService.getDeployedArchetypeIds();
	}
	
	@RequestMapping(value = "/archetypes/all", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAllArchetypeIds(){
		return this.archetypeProviderService.getAllArchetypeIds();	
	}
	
	@RequestMapping(value = "/archetypes/briefInfo", method = RequestMethod.GET)
	@ResponseBody
	public ArchetypeBriefInfo getAllArchetypesBriefInfo(){
		return this.archetypeProviderService.getArchetypeBriefInfo();
	}
	
}
