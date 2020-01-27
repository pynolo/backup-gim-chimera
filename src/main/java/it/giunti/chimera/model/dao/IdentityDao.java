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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import it.giunti.chimera.AppConstants;
import it.giunti.chimera.BusinessException;
import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.GiuntiCardModeEnum;
import it.giunti.chimera.IdentityPropertiesEnum;
import it.giunti.chimera.ValidationException;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.Province;
import it.giunti.chimera.util.PasswordUtil;
import it.giunti.chimera.util.QueryUtil;
import it.giunti.chimera.util.ValidationUtil;

@Repository("identityDao")
public class IdentityDao {

	private static final String emailRegExp = "^([\\w_!#\\$%&'\\*\\+\\-/=\\?\\^`\\{\\|\\}~\\.])+@([\\w\\-\\.]+\\.)+[\\w]{2,8}$";
	//private static final String emailRegExp = "^([a-zA-Z0-9_\\-\\.])+@([\\w\\-\\.]+\\.)+[A-Z]{2,6}$";
	private static final Pattern emailPattern = Pattern.compile(emailRegExp, Pattern.CASE_INSENSITIVE);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	@Qualifier("provinceDao")
	ProvinceDao provinceDao;
	
	public Identity selectById(int id) {
		return entityManager.find(Identity.class, id);
	}
	
	public Identity insert(Identity item) {
		entityManager.persist(item);
		return item;
	}
	
	public Identity update(Identity item) {
		Identity itemToUpdate = selectById(item.getId());
		itemToUpdate.setIdentityUid(item.getIdentityUid());
		itemToUpdate.setIdentityUidOld(item.getIdentityUidOld());
		itemToUpdate.setAddressProvinceId(item.getAddressProvinceId());
		itemToUpdate.setAddressStreet(item.getAddressStreet());
		itemToUpdate.setAddressTown(item.getAddressTown());
		itemToUpdate.setAddressZip(item.getAddressZip());
		itemToUpdate.setBirthDate(item.getBirthDate());
		itemToUpdate.setChangeTime(item.getChangeTime());
		itemToUpdate.setChangeType(item.getChangeType());
		itemToUpdate.setCodiceFiscale(item.getCodiceFiscale());
		itemToUpdate.setEmail(item.getEmail());
		itemToUpdate.setFirstName(item.getFirstName());
		itemToUpdate.setGiuntiCard(item.getGiuntiCard());
		itemToUpdate.setGiuntiCardMode(item.getGiuntiCardMode());
		itemToUpdate.setIdService(item.getIdService());
		itemToUpdate.setLastName(item.getLastName());
		itemToUpdate.setPartitaIva(item.getPartitaIva());
		itemToUpdate.setPasswordMd5(item.getPasswordMd5());
		itemToUpdate.setSex(item.getSex());
		itemToUpdate.setTelephone(item.getTelephone());
		itemToUpdate.setUserName(item.getUserName());
		itemToUpdate.setInterest(item.getInterest());
		itemToUpdate.setJob(item.getJob());
		itemToUpdate.setSchool(item.getSchool());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		Identity item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
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
	public Identity findByEmail(String email) throws DuplicateResultException {
		Identity result = null;
		String hql = "from Identity as up where " +
				"up.email like :s1 " +
				"order by up.id asc";
		Query q = entityManager.createQuery(hql);
		email = QueryUtil.escapeParam(email);
		q.setParameter("s1", email);
		List<Identity> upList = (List<Identity>) q.getResultList();
		if (upList != null) {
			if (upList.size() == 1) {
				result = upList.get(0);
			} else {
				if (upList.size() > 1)
					throw new DuplicateResultException("More rows in Identity have the same email");
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Identity findByUserName(String userName) 
			throws DuplicateResultException {
		Identity result = null;
		String hql = "from Identity as up where " +
				"up.userName like :s1 " +
				"order by up.id asc";
		Query q = entityManager.createQuery(hql);
		userName = QueryUtil.escapeParam(userName);
		q.setParameter("s1", userName);
		List<Identity> upList = (List<Identity>) q.getResultList();
		if (upList != null) {
			if (upList.size() == 1) {
				result = upList.get(0);
			} else {
				if (upList.size() > 1)
					throw new DuplicateResultException("More rows in Identity have the same userName");
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
			throws BusinessException, ValidationException {
		if (stringValue == null) stringValue = "";
		Object result = null;
		//Is mandatory
		if (stringValue.length() == 0) {
			if (property.isMandatory())	{
				throw new ValidationException("Campo obbligatorio");
			} else {
				return null;
			}
		}
		//Length
		if (property.getType().equals(AppConstants.PROPERTY_TYPE_STRING)) {
			stringValue = stringValue.trim();
			if (stringValue.length() > property.getStringLength()) {
				throw new ValidationException("Il campo supera i "+
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
			Province found = provinceDao.findByCode(stringValue.toUpperCase());
			if (found != null) {
				result = found.getCode();
			} else {
				throw new ValidationException("La provincia "+stringValue.toUpperCase()+" non esiste");
			}
		}
		//NASVITA
		if (property.equals(IdentityPropertiesEnum.NASCITA)) {
			try {
				result = (Date) AppConstants.FORMAT_DATE_JSON.parse(stringValue);
			} catch (ParseException e) {
				throw new ValidationException("Data di nascita non valida");
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
				throw new ValidationException("Modalita' card non valida");
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
	
	public String validateEmail(String email) throws ValidationException {
		return validateEmail(null, email);
	}
	
	public String validateEmail(String identityUid, String email)
			throws ValidationException {
		if (email == null) {
			new ValidationException("Email non valida");
		} else if (email.equals("")) new ValidationException("Email non valida");
		Matcher matcher = emailPattern.matcher(email);
		if (!matcher.matches()) {
			throw new ValidationException("Email non valida");
		}
		//Uniqueness verification
		Identity uProp = null;
		try {
			uProp = findByEmail(email);
		} catch (DuplicateResultException e) {
			//There are already many lines with that email!!
			throw new ValidationException("Email gia' assegnata", e);
		}
		if (uProp != null) {
			if (identityUid == null) {
				throw new ValidationException("Email gia' assegnata");// a "+uProp.getIdentityUid()+" ");
			} else {
				if (!uProp.getIdentityUid().equalsIgnoreCase(identityUid)) {
					throw new ValidationException("Email gia' assegnata");// a "+uProp.getIdentityUid()+" ");
				}
			}
		}
		return email;
	}
}
