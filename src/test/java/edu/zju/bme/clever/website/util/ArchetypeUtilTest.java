package edu.zju.bme.clever.website.util;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.openehr.am.archetype.Archetype;
import org.openehr.am.serialize.ADLSerializer;
import org.openehr.rm.common.resource.ResourceDescription;
import org.openehr.rm.common.resource.ResourceDescriptionItem;
import org.openehr.rm.support.identification.ArchetypeID;

import se.acode.openehr.parser.ADLParser;

public class ArchetypeUtilTest {

	@Test
	public void test() throws Exception{
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("edu/zju/bme/clever/website/adl/openEHR-DEMOGRAPHIC-PERSON.master_patient_index_modifyed_log.v1.adl");
		ADLParser parser = new ADLParser(is,
				"UTF-8");
		Archetype archetype = parser.parse();
		ArchetypeID archetypeIdNode = archetype.getArchetypeId();
		String archetypeId = archetypeIdNode.toString();
		ResourceDescription rd = archetype.getDescription();
		List<ResourceDescriptionItem> lrd = rd.getDetails();
		for (ResourceDescriptionItem resourceDescriptionItem : lrd) {
			String language = resourceDescriptionItem.getLanguage()
					.getCodeString();
			String purpose = resourceDescriptionItem.getPurpose();
			String use = resourceDescriptionItem.getUse();
			String keywords = getArchetypeKeywords(resourceDescriptionItem);
			String misuse = resourceDescriptionItem.getMisuse();
			String copyright = resourceDescriptionItem.getCopyright();

			String archetypeDescription = "language = "
					+ ((language == null) ? "" : language) + "\n"
					+ "purpose = " + ((purpose == null) ? "" : purpose)
					+ "\n" + "use = " + ((use == null) ? "" : use) + "\n"
					+ "keywords = " + ((keywords == null) ? "" : keywords)
					+ "\n" + "misuse = " + ((misuse == null) ? "" : misuse)
					+ "\n" + "copyright = "
					+ ((copyright == null) ? "" : copyright) + "\n" + "\n";
		}
		ADLSerializer adlSerilizer = new ADLSerializer();
		String archetypeContent = adlSerilizer.output(archetype);
	}
	
	protected String getArchetypeKeywords(
			ResourceDescriptionItem resourceDescriptionItem) {
		String keywords = "";
		try {
			for (String keyword : resourceDescriptionItem.getKeywords()) {
				keywords += keyword + " ";
			}
		} catch (Exception e) {
		}
		return keywords.trim();
	}

}
