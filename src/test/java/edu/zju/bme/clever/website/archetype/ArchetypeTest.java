package edu.zju.bme.clever.website.archetype;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.openehr.am.archetype.Archetype;
import org.openehr.am.archetype.constraintmodel.CComplexObject;
import org.openehr.am.archetype.constraintmodel.CObject;
import org.openehr.am.archetype.ontology.ArchetypeOntology;
import org.openehr.am.serialize.ADLSerializer;
import org.openehr.rm.common.resource.ResourceDescription;
import org.openehr.rm.common.resource.ResourceDescriptionItem;
import org.openehr.rm.support.identification.ArchetypeID;

import edu.zju.bme.clever.website.model.entity.ArchetypeFile;
import edu.zju.bme.clever.website.model.entity.ArchetypeNode;
import edu.zju.bme.clever.website.model.entity.ArchetypeRelationship;
import edu.zju.bme.clever.website.model.entity.FileProcessResult;
import edu.zju.bme.clever.website.service.ArchetypeExtractService;
import edu.zju.bme.clever.website.service.ArchetypeExtractServiceImpl;
import edu.zju.bme.clever.website.util.ArchetypeLeafNodeRmTypeAttributeMap;
import se.acode.openehr.parser.ADLParser;
import se.acode.openehr.parser.ParseException;

public class ArchetypeTest {

	// @Test
	public void test() throws ParseException, Exception {
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
						"edu/zju/bme/clever/website/adl/openEHR-EHR-COMPOSITION.visit.v3.adl");
		Archetype visitArchetype = new ADLParser(visitIs, "UTF-8").parse();

		Map<String, Archetype> archetypes = new HashMap<String, Archetype>();
		archetypes.put(patientArchetype.getArchetypeId().getValue(),
				patientArchetype);
		archetypes.put(visitArchetype.getArchetypeId().getValue(),
				visitArchetype);

		ArchetypeExtractService srv = new ArchetypeExtractServiceImpl();

		Map<String, ArchetypeFile> archetypeFiles = new HashMap<String, ArchetypeFile>();
		archetypeFiles.put(patientArchetype.getArchetypeId().getValue(),
				srv.extractArchetype(patientArchetype));
		archetypeFiles.put(visitArchetype.getArchetypeId().getValue(),
				srv.extractArchetype(visitArchetype));

		List<ArchetypeRelationship> relations = srv.extractArchetypeRelations(
				archetypes, archetypeFiles);
		// ArchetypeFile file = srv.extractArchetype(archetype);
	}

	public void testArchetypeValidate() throws ParseException, Exception {
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
						"edu/zju/bme/clever/website/adl/openEHR-EHR-COMPOSITION.visit.v3.adl");
		Archetype visitArchetype = new ADLParser(visitIs, "UTF-8").parse();

		Map<Archetype, FileProcessResult> archetypes = new HashMap<Archetype, FileProcessResult>();

	}

	@Test
	public void testIsLeafNode() throws Exception {
		InputStream patientIs = this
				.getClass()
				.getClassLoader()
				.getResourceAsStream(
						"edu/zju/bme/clever/website/adl/openEHR-DEMOGRAPHIC-PERSON.patient.v1.adl");
		Archetype patientArchetype = new ADLParser(patientIs, "UTF-8").parse();
		patientArchetype
				.getPathNodeMap()
				.forEach(
						(key, value) -> {
							final int total = 0;
							if (ArchetypeLeafNodeRmTypeAttributeMap
									.isElementNode(value)) {
								if (value instanceof CComplexObject) {
									CComplexObject element = (CComplexObject) value;
									element.getAttributes()
											.forEach(
													attribute -> attribute
															.getChildren()
															.forEach(
																	type -> System.out
																			.println(type
																					.getRmTypeName())));
								}
							}
						});
	}

}
