package edu.zju.bme.clever.website.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.zju.bme.clever.service.CleverService;
import edu.zju.bme.clever.website.service.ArchetypeProviderService;

@Controller
public class ArchetypeDeployController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "cleverClient")
	private CleverService cleverClient;
	@Resource(name = "archetypeProviderService")
	private ArchetypeProviderService archetypeProviderService;

	@RequestMapping(value = "/archetypes/actions/deploy", method = RequestMethod.GET)
	@ResponseBody
	public boolean deployArchetypes(Authentication authentication) {
		String userName = ((UserDetails) authentication.getPrincipal())
				.getUsername();
		this.logger.info("Deploy called by user {}.", userName);
		List<String> archetypes = this.archetypeProviderService
				.getAllLatestVersionArchetypeAdls();
		try {
			int stopResult = cleverClient.stop();
			if (stopResult != 0) {
				this.logger.trace(
						"Archetype clever client stop failed, result: {}.",
						stopResult);
				return false;
			}
			int reconfigureResult = cleverClient.reconfigure(archetypes, null);
			if (reconfigureResult != 0) {
				this.logger
						.trace("Archetype clever client reconfigure failed, result: {}.",
								reconfigureResult);
				return false;
			}
			int startResult = cleverClient.start();
			if (startResult != 0) {
				this.logger.trace(
						"Archetype clever client start failed, result: {}.",
						startResult);
				return false;
			}
		} catch (Throwable ex) {
			this.logger.info("Invoke clever service failed, error:{}.",
					ex.getMessage(), ex);
			return false;
		}
		return true;
	}
}
