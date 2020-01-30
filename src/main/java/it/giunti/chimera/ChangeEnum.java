package it.giunti.chimera;

public enum ChangeEnum {
	INSERT("insert"),
	UPDATE("update"),
	DELETE("delete"),
	REPLACE("replace");
	
	private String name;
	
	private ChangeEnum(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
