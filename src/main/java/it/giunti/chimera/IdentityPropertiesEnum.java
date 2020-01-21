package it.giunti.chimera;

public enum IdentityPropertiesEnum {

	MODIFICA("modifica", "updateDate",
			AppConstants.PROPERTY_TYPE_DATE, null, false, true),
	EMAIL("email", "email",
			AppConstants.PROPERTY_TYPE_STRING, 256, false, false),
	USERNAME("username", "userName",
			AppConstants.PROPERTY_TYPE_STRING, 256, false, true),
	PASSWORD("password", "passwordMd5",
			AppConstants.PROPERTY_TYPE_STRING, 64, false, true),
	NOME("nome", "firstName",
			AppConstants.PROPERTY_TYPE_STRING, 32, false, false),
	COGNOME("cognome", "lastName",
			AppConstants.PROPERTY_TYPE_STRING, 32, false, false),
	SESSO("sesso", "sex",
			AppConstants.PROPERTY_TYPE_STRING, 1, false, false),
	NASCITA("nascita", "birthDate",
			AppConstants.PROPERTY_TYPE_DATE, null, false, false),
	INDIRIZZO("indirizzo", "addressStreet",
			AppConstants.PROPERTY_TYPE_STRING, 16, false, false),
	CAP("cap","addressZip",
			AppConstants.PROPERTY_TYPE_STRING, 8, false, false),
	COD_PROVINCIA("codProvincia", "addressProvinceId",
			AppConstants.PROPERTY_TYPE_STRING, 4, false, false),
	LOCALITA("localita", "addressTown",
			AppConstants.PROPERTY_TYPE_STRING, 64, false, false),
	TELEFONO("telefono", "telephone",
			AppConstants.PROPERTY_TYPE_STRING, 32, false, false),
	GIUNTI_CARD("giuntiCard", "giuntiCard",
			AppConstants.PROPERTY_TYPE_STRING, 13, false, false),
	GIUNTI_CARD_MODE("giuntiCardMode", "giuntiCardMode",
			AppConstants.PROPERTY_TYPE_STRING, 4, false, false),
	CODICE_FISCALE("codiceFiscale", "codiceFiscale",
			AppConstants.PROPERTY_TYPE_STRING, 16, false, false),
	PARTITA_IVA("partitaIva", "partitaIva",
			AppConstants.PROPERTY_TYPE_STRING, 16, false, false);
	
	private String jsonName; //the name showing in json object
	private String ormName; //the name of the corresponding db column
	private String type; //<“string”|“number”|“autocomplete”>
	private Integer stringLength; //length if string type
	private boolean mandatory; //mandatory
	private boolean system; //campo sola lettura o nascosto
	
	private IdentityPropertiesEnum(String jsonName, String ormName, String type,
			Integer stringLength, boolean mandatory, boolean system) {
		this.jsonName=jsonName;
		this.ormName=ormName;
		this.type=type;
		this.stringLength=stringLength;
		this.mandatory=mandatory;
		this.system=system;
	}

	public String getJsonName() {
		return jsonName;
	}

	public String getOrmName() {
		return ormName;
	}

	public String getType() {
		return type;
	}

	public Integer getStringLength() {
		return stringLength;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public boolean isSystem() {
		return system;
	}

}
