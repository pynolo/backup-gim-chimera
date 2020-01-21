package it.giunti.chimera;

public enum GiuntiCardModeEnum {

	MST("Master"),
	SLV("Slave");
	
	private String description;
	
	private GiuntiCardModeEnum(String description) {
		this.description=description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
