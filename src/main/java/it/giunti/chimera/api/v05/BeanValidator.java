package it.giunti.chimera.api.v05;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import it.giunti.chimera.AppConstants;
import it.giunti.chimera.IdentityPropertiesEnum;
import it.giunti.chimera.api.v05.bean.IdentityBean;
import it.giunti.chimera.api.v05.bean.IdentityConsentBean;
import it.giunti.chimera.exception.Internal418Exception;
import it.giunti.chimera.model.entity.Identity;

public class BeanValidator {

	public static Map<String,String> validateIdentityBean(IdentityBean bean) 
			throws Internal418Exception {
		Map<String,String> errMap = new HashMap<String, String>();
		
		//FILTER Identity fields by IdentityPropertiesEnum
		Method[] methods = Identity.class.getDeclaredMethods();
		try {
			for (IdentityPropertiesEnum property:IdentityPropertiesEnum.values()) {
				for (Method method:methods) {
					if (method.getName().equalsIgnoreCase("get"+property.getOrmName())) {
						
						//This method is among IdentityPropertiesEnum
						Object objValue = method.invoke(bean);
						//Empty?
						if (property.isMandatory() && objValue == null) {
							errMap.put(property.getOrmName(), "Valore obbligatorio");
						} else {
							//STRING
							if (property.getType().equals(AppConstants.PROPERTY_TYPE_STRING)) {
								String value = (String) objValue;
								if (property.isMandatory() && value.length() == 0) {
									errMap.put(property.getOrmName(), "Valore obbligatorio");
								}
								if (value.length() > property.getStringLength()) {
									errMap.put(property.getOrmName(), "'"+property.getOrmName()+"' deve essere inferiore a "+property.getStringLength()+" caratteri");
								}
								//TODO
								//verify against lookup tables
							}
						}
					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new Internal418Exception(e.getMessage(), e);
		}
		return errMap;
	}
	
	public static Map<String,String> validateConsentBean(IdentityConsentBean bean) {
		Map<String,String> errMap = new HashMap<String, String>();
		if (bean.getRange() == null) errMap.put("range", "Valore obbligatorio");
		//TODO
		//verify against lookup tables
		if (bean.getTos() == null) {
			errMap.put("tos", "Valore obbligatorio");
		} else {
			if (bean.getTosDate() == null) errMap.put("tosDate", "Valore obbligatorio");
		}
		if (bean.getMarketing() == null) {
			errMap.put("marketing", "Valore obbligatorio");
		} else {
			if (bean.getMarketingDate() == null) errMap.put("marketingDate", "Valore obbligatorio");
		}
		if (bean.getProfiling() == null) errMap.put("profiling", "Valore obbligatorio");
		return errMap;
	}
		
}
