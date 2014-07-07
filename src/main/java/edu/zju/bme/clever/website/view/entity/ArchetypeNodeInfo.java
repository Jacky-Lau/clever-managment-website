package edu.zju.bme.clever.website.view.entity;

public class ArchetypeNodeInfo {

	public enum Type {
		Add("Add"), Modify("Modify");

		private final String name;

		public String getName() {
			return name;
		}

		public String toString() {
			return name;
		}

		Type(String name) {
			this.name = name;
		}
	}

	private Type type;
	private String previousName;
	private String currentName;
	private String rmType;
	private String nodePath;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getPreviousName() {
		return previousName;
	}

	public void setPreviousName(String previousName) {
		this.previousName = previousName;
	}

	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public String getRmType() {
		return rmType;
	}

	public void setRmType(String rmType) {
		this.rmType = rmType;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

}
