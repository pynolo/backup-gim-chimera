package it.giunti.chimera.util;

import org.apache.commons.lang3.StringUtils;

public class QueryUtil {
	
	public static String clearParam(String param, boolean allowWildcards) {
		if (param != null) {
			param = param.trim();
			if (param.equals("")) {
				param = null;
			} else {
				param = escapeParam(param);
				if (allowWildcards) {
					param = StringUtils.replace(param, "*", "%");
				}
			}
		}
		return param;
	}
	
	public static String escapeParam(String param) {
		if (param != null) {
			param = StringUtils.replace(param, "%", "\\%");
			param = StringUtils.replace(param, "_", "\\_");
		}
		return param;
	}
}
