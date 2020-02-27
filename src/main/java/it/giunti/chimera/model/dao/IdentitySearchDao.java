package it.giunti.chimera.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.exception.NotFound404Exception;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.util.QueryUtil;

@Repository("identitySearchDao")
public class IdentitySearchDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public List<Identity> findIdentityByProperties(int maxResults, String identityUid, String email,
			String lastName, String firstName, String addressStreet, String addressZip,
			String addressTown, String addressProvinceId, String telephone, String codiceFiscale,
			String partitaIva) 
					throws NotFound404Exception {
		int count = 0;
		Map<String, String> paramMap = new HashMap<String, String>();
		String qs = "from Identity as i where ";
		if (identityUid != null) {
			if (identityUid.length() > 3) {
				count++;
				qs+="i.identityUid = :id1 and ";
				paramMap.put("id1", identityUid);
			}
		}
		if (email != null) {
			if (email.length() > 3) {
				count++;
				qs+="i.email = :s1 and ";
				paramMap.put("s1", email);
			}
		}
		if (lastName != null) {
			if (lastName.length() > 3) {
				count++;
				qs+="i.lastName like :s2 and ";
				lastName = lastName.replaceFirst("\\*", "%");
				paramMap.put("s2", lastName);
			}
		}
		if (firstName != null) {
			if (firstName.length() > 3) {
				count++;
				qs+="i.firstName like :s3 and ";
				firstName = firstName.replaceFirst("\\*", "%");
				paramMap.put("s3", firstName);
			}
		}
		if (addressStreet != null) {
			if (addressStreet.length() > 3) {
				count++;
				qs+="i.addressStreet like :s4 and ";
				addressStreet = addressStreet.replaceFirst("\\*", "%");
				paramMap.put("s4", addressStreet);
			}
		}
		if (addressTown != null) {
			if (addressTown.length() > 3) {
				count++;
				qs+="i.addressTown like :s5 and ";
				addressTown = addressTown.replaceFirst("\\*", "%");
				paramMap.put("s5", addressTown);
			}
		}
		if (addressProvinceId != null) {
			if (addressProvinceId.length() == 2) {
				count++;
				qs+="i.addressProvinceId = :s6 and ";
				paramMap.put("s6", addressProvinceId);
			}
		}
		if (addressZip != null) {
			if (addressZip.length() > 3) {
				count++;
				qs+="i.addressZip = :s7 and ";
				paramMap.put("s7", addressZip);
			}
		}
		if (telephone != null) {
			if (telephone.length() > 3) {
				count++;
				qs+="i.telephone = :s8 and ";
				paramMap.put("s8", telephone);
			}
		}
		if (codiceFiscale != null) {
			if (codiceFiscale.length() > 3) {
				count++;
				qs+="i.codiceFiscale = :s9 and ";
				paramMap.put("s9", codiceFiscale);
			}
		}
		if (partitaIva != null) {
			if (partitaIva.length() > 3) {
				count++;
				qs+="i.partitaIva = :s10 and ";
				paramMap.put("s10", partitaIva);
			}
		}
		qs += "i.deletionTime is null " +
				"order by i.id desc";
		if (count > 0) {
			Query q = entityManager.createQuery(qs);
			for (String key:paramMap.keySet()) {
				String value = paramMap.get(key);
				value = QueryUtil.escapeParam(value);
				q.setParameter(key, value);
			}
			q.setMaxResults(maxResults);
			List<Identity> iList = (List<Identity>) q.getResultList();
			return iList;
		} else {
			throw new NotFound404Exception("I parametri di ricerca sono vuoti o troppo corti");
		}
	}
		
}
