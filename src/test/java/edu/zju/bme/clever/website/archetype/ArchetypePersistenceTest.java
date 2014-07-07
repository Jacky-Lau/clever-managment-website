package edu.zju.bme.clever.website.archetype;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openehr.am.archetype.Archetype;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import se.acode.openehr.parser.ADLParser;
import se.acode.openehr.parser.ParseException;
import edu.zju.bme.clever.website.model.entity.FileProcessResult;
import edu.zju.bme.clever.website.service.ArchetypePersistanceService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:applicationContext-test.xml")
public class ArchetypePersistenceTest {
	
	@Resource(name = "archetypePersistanceService")
	private ArchetypePersistanceService archetypePersistanceService;

	@Test
	public void test() throws ParseException, Exception{
		InputStream patientIs = this
				.getClass()
				.getClassLoader()
				.getResourceAsStream(
						"edu/zju/bme/clever/website/adl/openEHR-DEMOGRAPHIC-PERSON.patient.v1.adl");
		Archetype patientArchetype = new ADLParser(patientIs, "UTF-8").parse();
		InputStream visitIs = this
				.getClass()
				.getClassLoader()
				.getResourceAsStream(
						"edu/zju/bme/clever/website/adl/openEHR-EHR-COMPOSITION.visit.v1.adl");
		Archetype visitArchetype = new ADLParser(visitIs, "UTF-8").parse();
		
		List<Archetype> archetypes = new ArrayList<Archetype>();
		archetypes.add(visitArchetype);
		archetypes.add(patientArchetype);
		
		this.archetypePersistanceService.saveArchetypes(archetypes, "admin");
	}

}
