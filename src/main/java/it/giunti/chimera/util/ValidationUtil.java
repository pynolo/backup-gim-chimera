package it.giunti.chimera.util;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.giunti.chimera.ValidationException;

public class ValidationUtil {

	private static final String eanRegExp = "^[0-9]{13}$";
	private static final Pattern eanPattern = Pattern.compile(eanRegExp, Pattern.CASE_INSENSITIVE);
	public final static String REGEX_CODFISC =  "^[a-zA-Z]{6}[0-9]{2}[abcdehlmprstABCDEHLMPRST]{1}[0-9]{2}([a-zA-Z]{1}[0-9]{3})[a-zA-Z]{1}$";
	public final static String REGEX_P_IVA = "^[0-9]{11}$";
	
	public static String validateEan(String stringValue)
			throws ValidationException {
		Matcher matcher = eanPattern.matcher(stringValue);
		if (!matcher.matches()) {
			throw new ValidationException("Ean non valido");
		}
		return stringValue;
	}
	
	public static String validateCodiceFiscale(String codFis)
			throws ValidationException {
		if (codFis == null) {
			new ValidationException("Codice fiscale non valido");
		}
		boolean isValid = isValidCodFisc(codFis);
		if (!isValid) {
			throw new ValidationException("Codice fiscale non valido");
		}
		return codFis;
	}
	
	public static String validatePartitaIva(String pIva)
			throws ValidationException {
		if (pIva == null) {
			new ValidationException("Partita iva non valida");
		}
		boolean isValid = isValidPIva(pIva);
		if (!isValid) {
			throw new ValidationException("Partita iva non valida");
		}
		return pIva;
	}
	
	// CIN VALIDATION
	
		private final static char[] elencoPari = {'0','1','2','3','4','5','6','7','8','9','A','B',
				'C','D','E','F','G','H','I','J','K','L','M','N',
				'O','P','Q','R','S','T','U','V','W','X','Y','Z'
		};
		         
		private final static int[] elencoDispari= {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13,
				15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16,
				10, 22, 25, 24, 23
		};

		public static boolean isValidPIva(String pi) {
			if (pi == null) return false;
			boolean pIvaOk = pi.matches(REGEX_P_IVA);
			if (pIvaOk) pIvaOk = verifyCinPIva(pi);
			return pIvaOk;
		}
		
		public static boolean isValidCodFisc(String codFisc) {
			if (codFisc == null) return false;
			boolean codFiscOk = codFisc.matches(REGEX_CODFISC);
			if (codFiscOk) codFiscOk = verifyCinCodFisc(codFisc);
			boolean pIvaOk = isValidPIva(codFisc);
			return codFiscOk || pIvaOk;
		}

		private static boolean verifyCinPIva(String pi) {
			boolean isValid = false;
			int zeroChar = "0".charAt(0);
			if( pi != null ) {
				if( pi.length() == 11 ) {
					int s = 0;
					for(int i = 0; i <= 9; i += 2 )
						s += pi.charAt(i) - zeroChar;
					for(int i = 1; i <= 9; i += 2 ){
						int c = 2*( pi.charAt(i) - zeroChar);
						if( c > 9 ) c = c - 9;
						s += c;
					}
					int modulo = (10 - s%10)%10;
					int num = pi.charAt(10) - zeroChar;
					if(modulo == num) isValid = true;
				}
			}
			return isValid;
		}
		
		private static boolean verifyCinCodFisc(String codFisc) {
			boolean isValid = false;
			if (codFisc != null) {
				if (codFisc.length() == 16) {
					//Calcolo CIN
					String str = codFisc.toUpperCase().substring(0, 15);
					int pari=0;
					int dispari=0;
			
					for(int i=0; i<str.length(); i++) {
						char ch = str.charAt(i);// i-esimo carattere della stringa
						// Il primo carattere e' il numero 1 non 0
						if((i+1) % 2 == 0) {
							int index = Arrays.binarySearch(elencoPari,ch);
							pari += (index >= 10) ? index-10 : index;
						} else {
							int index = Arrays.binarySearch(elencoPari,ch);
							dispari += elencoDispari[index];
						}
					}
			
					int controllo = (pari+dispari) % 26;
					controllo += 10;
					String cin = elencoPari[controllo]+"";
					if (codFisc.substring(15).equals(cin)) isValid = true;
				}
			}
			return isValid;
		}
}
