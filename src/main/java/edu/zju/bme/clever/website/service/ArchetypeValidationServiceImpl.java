package edu.zju.bme.clever.website.service;

import org.springframework.stereotype.Service;

@Service("archetypeValidationService")
public class ArchetypeValidationServiceImpl implements ArchetypeValidationService{
	@Override
	public boolean validate(String archetype){
		return true;
	}
}
