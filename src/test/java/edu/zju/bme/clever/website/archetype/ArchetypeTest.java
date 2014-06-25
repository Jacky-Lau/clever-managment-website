package edu.zju.bme.clever.website.archetype;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.openehr.am.archetype.Archetype;
import org.openehr.am.archetype.constraintmodel.CObject;
import org.openehr.am.archetype.ontology.ArchetypeOntology;
import org.openehr.am.serialize.ADLSerializer;
import org.openehr.rm.common.resource.ResourceDescription;
import org.openehr.rm.common.resource.ResourceDescriptionItem;
import org.openehr.rm.support.identification.ArchetypeID;

import edu.zju.bme.clever.website.entity.ArchetypeFile;
import edu.zju.bme.clever.website.entity.ArchetypeNode;
import edu.zju.bme.clever.website.service.ArchetypeInfoExtractServiceImpl;
import se.acode.openehr.parser.ADLParser;
import se.acode.openehr.parser.ParseException;

public class ArchetypeTest {

	@Test
	public void test() throws ParseException, Exception {
		InputStream is = this
				.getClass()
				.getClassLoader()
				.getResourceAsStream(
						"edu/zju/bme/clever/website/adl/openEHR-DEMOGRAPHIC-PERSON.patient.v1.adl");
		ADLParser parser = new ADLParser(is, "UTF-8");
		Archetype archetype = parser.parse();
		ArchetypeInfoExtractServiceImpl srv = new ArchetypeInfoExtractServiceImpl();
		ArchetypeFile file = srv.extractArchetype(archetype);
	}

}
