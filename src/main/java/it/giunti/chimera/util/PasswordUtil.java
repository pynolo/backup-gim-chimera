package it.giunti.chimera.util;

public class PasswordUtil {

	public static String md5(String input) {
		String md5 = null;
		if (null == input) return null;
		DefaultPasswordEncoder dpe = new DefaultPasswordEncoder("MD5");
		md5 = dpe.encode(input);
		return md5;
	}
	
}
