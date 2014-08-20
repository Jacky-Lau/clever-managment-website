package edu.zju.bme.clever.website.util;

import java.util.HashMap;
import java.util.Map;

import org.openehr.rm.datatypes.basic.ReferenceModelName;

public class ArchetypeLeafNodeRmTypeAttributeMap {

	private static final Map<String, String> FilterRmTypeNames = new HashMap<String, String>();

	static {
		FilterRmTypeNames.put(ReferenceModelName.DV_TEXT.getName(), "value");
		FilterRmTypeNames.put(ReferenceModelName.DV_COUNT.getName(),
				"magnitude");
		FilterRmTypeNames.put(ReferenceModelName.DV_QUANTITY.getName(),
				"magnitude");
		FilterRmTypeNames.put(ReferenceModelName.DV_DATE_TIME.getName(),
				"value");
		//FilterRmTypeNames.put(ReferenceModelName.CLUSTER.getName(), "items");
	}

	public static String getAttribute(String rmType) {
		return FilterRmTypeNames.get(rmType);
	}

	public static boolean containsRmType(String rmType) {
		return FilterRmTypeNames.containsKey(rmType);
	}

	public static boolean isLeafNode(String rmType, String rmAttribute) {
		if (FilterRmTypeNames.containsKey(rmType)) {
			if (FilterRmTypeNames.get(rmType).equals(rmAttribute)) {
				return true;
			}
		}
		return false;
	}
}
