package edu.zju.bme.clever.website.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.openehr.am.archetype.Archetype;
import org.openehr.am.serialize.ADLSerializer;
import org.openehr.rm.common.resource.ResourceDescription;
import org.openehr.rm.common.resource.ResourceDescriptionItem;
import org.openehr.rm.support.identification.ArchetypeID;
import org.springframework.web.multipart.MultipartFile;

import se.acode.openehr.parser.ADLParser;

public class ArchetypeUtil {
	private static Logger logger = Logger.getLogger(ArchetypeUtil.class.getName());
	
	private Archetype archetype = null;
	private String archetypeId = "";
	private String archetypeDescription = "";
	private String archetypeContent = "";

	public ArchetypeUtil(MultipartFile archetypeFile) {
		try {
			ADLParser parser = new ADLParser(archetypeFile.getInputStream(), "UTF-8");
			archetype = parser.parse();
			ArchetypeID archetypeIdNode = archetype.getArchetypeId();
			archetypeId = archetypeIdNode.toString();
			ResourceDescription rd = archetype.getDescription();
			List<ResourceDescriptionItem> lrd = rd.getDetails();
			for (ResourceDescriptionItem resourceDescriptionItem : lrd) {
				String language = resourceDescriptionItem.getLanguage().getCodeString();
				String purpose = resourceDescriptionItem.getPurpose();
				String use = resourceDescriptionItem.getUse();
				String keywords = getArchetypeKeywords(resourceDescriptionItem);
				String misuse = resourceDescriptionItem.getMisuse();
				String copyright = resourceDescriptionItem.getCopyright();
				
				archetypeDescription = archetypeDescription + 
						"language = " + ((language == null) ? "" : language) + "\n" +
						"purpose = " + ((purpose == null) ? "" : purpose) + "\n" +
						"use = " + ((use == null) ? "" : use) + "\n" +
						"keywords = " + ((keywords == null) ? "" : keywords) + "\n" +
						"misuse = " + ((misuse == null) ? "" : misuse) + "\n" +
						"copyright = " + ((copyright == null) ? "" : copyright) + "\n" +
						"\n";
			}
			ADLSerializer adlSerilizer = new ADLSerializer();
			archetypeContent = adlSerilizer.output(archetype);
		} catch (Throwable e) {
			archetype = null;
			archetypeId = "";
			archetypeDescription = "";
			archetypeContent = "";
			logger.error(e);
		}
	}

	public String getArchetypeId() {
		return archetypeId;
	}
	
	public String getArchetypeDescription() {
		return archetypeDescription;
	}
	
	public String getArchetypeContent() {	
		return archetypeContent;
	}
	
	protected String getArchetypeKeywords(ResourceDescriptionItem resourceDescriptionItem) {
		String keywords = "";
		try {
			for (String keyword : resourceDescriptionItem.getKeywords()) {
				keywords += keyword + " ";
			}			
		} catch (Exception e) {
			logger.error(e);
		}
		return keywords.trim();
	}
}
