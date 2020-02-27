package it.giunti.chimera.model.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.AppConstants;
import it.giunti.chimera.ChangeEnum;
import it.giunti.chimera.GiuntiCardModeEnum;
import it.giunti.chimera.IdentityPropertiesEnum;
import it.giunti.chimera.exception.UnprocessableEntity422Exception;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.util.PasswordUtil;
import it.giunti.chimera.util.QueryUtil;
import it.giunti.chimera.util.ValidationUtil;

@Repository("identityDao")
public class IdentityDao {

	private static final String emailRegExp = "^([\\w_!#\\$%&'\\*\\+\\-/=\\?\\^`\\{\\|\\}~\\.])+@([\\w\\-\\.]+\\.)+[\\w]{2,8}$";
	//private static final String emailRegExp = "^([a-zA-Z0-9_\\-\\.])+@([\\w\\-\\.]+\\.)+[A-Z]{2,6}$";
	private static final Pattern emailPattern = Pattern.compile(emailRegExp, Pattern.CASE_INSENSITIVE);
	private static final String provinciaRegExp = "^[A-Z]{2}$";
	private static final Pattern provinciaPattern = Pattern.compile(provinciaRegExp, Pattern.CASE_INSENSITIVE);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Identity selectById(int id) {
		return entityManager.find(Identity.class, id);
	}
	
	public Identity insert(Identity item) {
		item.setChangeTime(new Date());
		item.setChangeType(ChangeEnum.INSERT.getName());
		entityManager.persist(item);
		return item;
	}
	
	public Identity update(Identity item, ChangeEnum changeType) {
		Identity itemToUpdate = selectById(item.getId());
		itemToUpdate.setIdentityUid(item.getIdentityUid());
		itemToUpdate.setReplacedByUid(item.getReplacedByUid());
		itemToUpdate.setAddressProvinceId(item.getAddressProvinceId());
		itemToUpdate.setAddressStreet(item.getAddressStreet());
		itemToUpdate.setAddressTown(item.getAddressTown());
		itemToUpdate.setAddressZip(item.getAddressZip());
		itemToUpdate.setBirthDate(item.getBirthDate());
		itemToUpdate.setChangeTime(new Date());
		itemToUpdate.setChangeType(changeType.getName());
		itemToUpdate.setCodiceFiscale(item.getCodiceFiscale());
		itemToUpdate.setEmail(item.getEmail());
		itemToUpdate.setFirstName(item.getFirstName());
		//itemToUpdate.setGiuntiCard(item.getGiuntiCard());
		//itemToUpdate.setGiuntiCardMode(item.getGiuntiCardMode());
		itemToUpdate.setLastName(item.getLastName());
		itemToUpdate.setPartitaIva(item.getPartitaIva());
		itemToUpdate.setPasswordMd5(item.getPasswordMd5());
		itemToUpdate.setSex(item.getSex());
		itemToUpdate.setTelephone(item.getTelephone());
		itemToUpdate.setUserName(item.getUserName());
		itemToUpdate.setInterest(item.getInterest());
		itemToUpdate.setJob(item.getJob());
		itemToUpdate.setSchool(item.getSchool());
		itemToUpdate.setDeletionTime(item.getDeletionTime());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void logicalDelete(int id) {
		Date now = new Date();
		//Commented physical deletion
		//Identity item = selectById(id);
		//entityManager.merge(item);
		//entityManager.remove(item);
		//entityManager.flush();
		// LOGICAL DELETION
		Identity item = selectById(id);
		//item.setIdentityUid();
		//item.setReplacedByUid(replacedByUid); KEEPS replacedByUid value!!
		item.setAddressProvinceId(null);
		item.setAddressStreet(null);
		item.setAddressTown(null);
		item.setAddressZip(null);
		item.setBirthDate(null);
		item.setChangeTime(now);
		item.setChangeType(ChangeEnum.DELETE.getName());
		item.setCodiceFiscale(null);
		item.setEmail(null);
		item.setFirstName(null);
		//item.setGiuntiCard(null);
		//item.setGiuntiCardMode(null);
		item.setLastName(null);
		item.setPartitaIva(null);
		item.setPasswordMd5("x");
		item.setSex(null);
		item.setTelephone(null);
		item.setUserName(null);
		item.setInterest(null);
		item.setJob(null);
		item.setSchool(null);
		item.setDeletionTime(now);
		entityManager.merge(item);
		entityManager.flush();
	}
	
	
	@SuppressWarnings("unchecked")
	public Identity findByIdentityUid(String identityUid) {
		String hql = "from Identity as up where " +
				"up.identityUid = :uid1 " +
				"order by up.id asc";
		Query q = entityManager.createQuery(hql);
		identityUid = QueryUtil.escapeParam(identityUid);
		q.setParameter("uid1", identityUid);
		List<Identity> upList = (List<Identity>) q.getResultList();
		if (upList != null) {
			if (upList.size() > 0) {
				return upList.get(0);
			} else {
				return null;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Identity> findByReplacingUid(String replacedByUid) {
		String hql = "from Identity as idt where " +
				"idt.replacedByUid = :uid1 " +
				"order by idt.id asc";
		Query q = entityManager.createQuery(hql);
		replacedByUid = QueryUtil.escapeParam(replacedByUid);
		q.setParameter("uid1", replacedByUid);
		List<Identity> idtList = (List<Identity>) q.getResultList();
		return idtList;
	}
	
	@SuppressWarnings("unchecked")
	public Identity findByEmail(String email) {
		Identity result = null;
		String hql = "from Identity as up where " +
				"up.email like :s1 " +
				"order by up.id asc";
		Query q = entityManager.createQuery(hql);
		email = QueryUtil.escapeParam(email);
		q.setParameter("s1", email);
		List<Identity> upList = (List<Identity>) q.getResultList();
		if (upList != null) {
			if (upList.size() >= 1) {
				result = upList.get(0);
			} 
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Identity findByUserName(String userName) {
		Identity result = null;
		String hql = "from Identity as up where " +
				"up.userName like :s1 " +
				"order by up.id asc";
		Query q = entityManager.createQuery(hql);
		userName = QueryUtil.escapeParam(userName);
		q.setParameter("s1", userName);
		List<Identity> upList = (List<Identity>) q.getResultList();
		if (upList != null) {
			if (upList.size() >= 1) {
				result = upList.get(0);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Identity> findAll(int offset, int pageSize) {
		String hql = "from Identity as up order by up.id asc";
		Query q = entityManager.createQuery(hql);
		q.setFirstResult(offset);
		q.setMaxResults(pageSize);
		List<Identity> upList = (List<Identity>) q.getResultList();
		if (upList != null) {
			return upList;
		}
		return new ArrayList<Identity>();
	}
	
	@SuppressWarnings("unchecked")
	public List<Identity> findByChangeTime(Date startDt) {
		String hql = "from Identity as up where "+
				"up.changeTime >= :dt1 "+
				"order by up.changeTime asc";
		Query q = entityManager.createQuery(hql);
		q.setParameter("dt1", startDt);
		List<Identity> upList = (List<Identity>) q.getResultList();
		return upList;
	}
	
	
	//Validation
	
	public Object validateAndCast(IdentityPropertiesEnum property,
			String stringValue, String identityUid, boolean verification)
			throws UnprocessableEntity422Exception {
		if (stringValue == null) stringValue = "";
		Object result = null;
		//Is mandatory
		if (stringValue.length() == 0) {
			if (property.isMandatory())	{
				throw new UnprocessableEntity422Exception("Campo obbligatorio");
			} else {
				return null;
			}
		}
		//Length
		if (property.getType().equals(AppConstants.PROPERTY_TYPE_STRING)) {
			stringValue = stringValue.trim();
			if (stringValue.length() > property.getStringLength()) {
				throw new UnprocessableEntity422Exception("Il campo supera i "+
						property.getStringLength()+" caratteri");
			}
		}
		
		result = stringValue;
		//Conversion
		
		//PASSWORD
		if (property.equals(IdentityPropertiesEnum.PASSWORD)) {
			String pwdMd5 = PasswordUtil.md5(stringValue);
			result = pwdMd5;
		}
		//EMAIL
		if (property.equals(IdentityPropertiesEnum.EMAIL)) {
			if (verification && (identityUid == null)) {
				//Check regex and email absolute uniqueness
				result = validateEmail(stringValue);
			} else {
				//Check regex and email uniqueness only if different from existing identityUid email
				result = validateEmail(identityUid, stringValue);
			}
		}
		//SESSO
		if (property.equals(IdentityPropertiesEnum.SESSO)) {
			if ("FMfm".contains(stringValue)) {
				result = stringValue.toUpperCase();
			} else {
				result = null;
			}
		}
		//COD_PROVINCIA
		if (property.equals(IdentityPropertiesEnum.COD_PROVINCIA)) {
			Matcher matcher = provinciaPattern.matcher(stringValue);
			if (!matcher.matches()) {
				throw new UnprocessableEntity422Exception("Provincia non valida");
			}
		}
		//NASVITA
		if (property.equals(IdentityPropertiesEnum.NASCITA)) {
			try {
				result = (Date) AppConstants.FORMAT_DATE_JSON.parse(stringValue);
			} catch (ParseException e) {
				throw new UnprocessableEntity422Exception("Data di nascita non valida");
			}
		}
		//TELEFONO
		if (property.equals(IdentityPropertiesEnum.TELEFONO)) {
			//Possible format verification?
			result = stringValue;
		}
		//GIUNTI_CARD
		if (property.equals(IdentityPropertiesEnum.GIUNTI_CARD)) {
			result = ValidationUtil.validateEan(stringValue);
		}
		//GIUNTI_CARD_MODE
		if (property.equals(IdentityPropertiesEnum.GIUNTI_CARD_MODE)) {
			GiuntiCardModeEnum mode;
			try {
				mode = GiuntiCardModeEnum.valueOf(stringValue.toUpperCase());
				result = mode.name();
			} catch (Exception e) {
				throw new UnprocessableEntity422Exception("Modalita' card non valida");
			}				
		}
		//CODICE_FISCALE
		if (property.equals(IdentityPropertiesEnum.CODICE_FISCALE)) {
			//Check removed (needs country info)
			//result = validateCodiceFiscale(stringValue);
			result = stringValue;
		}
		//PARTITA IVA
		if (property.equals(IdentityPropertiesEnum.PARTITA_IVA)) {
			//Check removed (needs country info)
			//result = validatePartitaIva(stringValue);
			result = stringValue;
		}
		
		return result;
	}
	
	public String validateEmail(String email) throws UnprocessableEntity422Exception {
		return validateEmail(null, email);
	}
	
	public String validateEmail(String identityUid, String email)
			throws UnprocessableEntity422Exception {
		if (email == null) {
			new UnprocessableEntity422Exception("Email non valida");
		} else if (email.equals("")) new UnprocessableEntity422Exception("Email non valida");
		Matcher matcher = emailPattern.matcher(email);
		if (!matcher.matches()) {
			throw new UnprocessableEntity422Exception("Email non valida");
		}
		//Uniqueness verification
		Identity uProp = findByEmail(email);
		if (uProp != null) {
			if (identityUid == null) {
				throw new UnprocessableEntity422Exception("Email gia' assegnata");// a "+uProp.getIdentityUid()+" ");
			} else {
				if (!uProp.getIdentityUid().equalsIgnoreCase(identityUid)) {
					throw new UnprocessableEntity422Exception("Email gia' assegnata");// a "+uProp.getIdentityUid()+" ");
				}
			}
		}
		return email;
	}
		
}
