package it.giunti.chimera.api05;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import it.giunti.chimera.AppConstants;
import it.giunti.chimera.BusinessException;
import it.giunti.chimera.IdentityPropertiesEnum;
import it.giunti.chimera.model.entity.Identity;

public class BeanValidator {

	public static Map<String,String> validateIdentityBean(IdentityBean bean) throws BusinessException {
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
							}
						}
					}
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new BusinessException(e.getMessage(), e);
		}
		return errMap;
	}
}
