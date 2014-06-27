package edu.zju.bme.clever.website.service;

import java.util.List;

public interface ArchetypeValidationService {
	public boolean validate(String archetype);

	public boolean validate(List<String> archetype);
}
