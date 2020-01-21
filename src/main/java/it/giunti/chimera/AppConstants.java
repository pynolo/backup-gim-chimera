package it.giunti.chimera;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppConstants {

	public static final String QUARTZ_CONFIG_FILE = "/quartz-jobs.xml";
	public static final String LOGGER_CONFIG_FILE = "/log4j-custom.xml";
	public static final String PROPERTY_FILE = "/gim.properties";
	//CHARSET
	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String CHARSET_ISO88591 = "ISO-8859-1";
	public static final String CHARSET_ISO885915 = "ISO-8859-15";
	public static final String[] CHARSET_LIST = {CHARSET_UTF8, CHARSET_ISO88591, CHARSET_ISO885915};
	
	//SERVLET PATTERN (non-API servlets)
	public static final String PATTERN_RESET_CACHE = "/reset_cache";
	
	//PARAMETERS  (non-API parameters)
	public static final String PARAM_IDENTITY_UID = "identity_uid";
	public static final Integer DEFAULT_IDENTITY_UID_START_VALUE = 1;
	public static final Integer DEFAULT_IDENTITY_UID_CODE_LENGTH = 7;//Senza contare il carattere di checksum
	
	//FORMATI
	public static final String PATTERN_ISO8601 = "yyyyMMdd'T'HHmmssZ";
	public static final NumberFormat FORMAT_INTEGER = NumberFormat.getNumberInstance(Locale.US);
	public static final DecimalFormat FORMAT_DOUBLE = new DecimalFormat("###.##");
	public static final SimpleDateFormat FORMAT_FILENAME_TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	public static final SimpleDateFormat FORMAT_TIMESTAMP_ISO8601 = new SimpleDateFormat(PATTERN_ISO8601);
	public static final SimpleDateFormat FORMAT_DATE_JSON = new SimpleDateFormat("yyyy-MM-dd");
	
	//CHANGE TYPES
	public static final String CHANGE_TYPE_UPDATE = "update";
	public static final String CHANGE_TYPE_REPLACE = "replace";
	public static final String CHANGE_TYPE_DELETE = "delete";
	
	//PROPERTY TYPES
	public static final String PROPERTY_TYPE_STRING = "string";
	public static final String PROPERTY_TYPE_NUMBER = "number";
	public static final String PROPERTY_TYPE_DATE = "date";
	
	//MESSAGGI
	public static final String MSG_EMPTY_RESULT = "La ricerca non ha corrispondenze";
}
