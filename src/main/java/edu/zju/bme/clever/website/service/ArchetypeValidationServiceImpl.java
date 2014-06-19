package edu.zju.bme.clever.website.service;

import java.util.Arrays;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.zju.bme.clever.service.CleverService;

@Service("archetypeValidationService")
public class ArchetypeValidationServiceImpl implements
		ArchetypeValidationService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "cleverValidationClient")
	private CleverService cleverValidationClient;

	@Override
	public boolean validate(String archetype) {
		int stopResult = cleverValidationClient.stop();
		if (stopResult != 0) {
			this.logger.trace(
					"Archetype validation client stop failed, result: {}.",
					stopResult);
			return false;
		}
		int reconfigureResult = cleverValidationClient.reconfigure(
				Arrays.asList(archetype), null);
		if (reconfigureResult != 0) {
			this.logger
					.trace("Archetype validation client reconfigure failed, result: {}.",
							reconfigureResult);
			return false;
		}
		int startResult = cleverValidationClient.start();
		if (startResult != 0) {
			this.logger.trace(
					"Archetype validation client start failed, result: {}.",
					startResult);
			return false;
		}
		return true;
	}
}
