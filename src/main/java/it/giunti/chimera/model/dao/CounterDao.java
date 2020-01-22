package it.giunti.chimera.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import it.giunti.chimera.AppConstants;
import it.giunti.chimera.model.entity.Counter;
import it.giunti.chimera.util.NumberBaseConverter;

@Repository("counterDao")
public class CounterDao {

	@PersistenceContext
	private EntityManager entityManager;

	public Counter selectById(int id) {
		return entityManager.find(Counter.class, id);
	}
	
	public Counter insert(Counter item) {
		entityManager.persist(item);
		return item;
	}
	
	public Counter update(Counter item) {
		Counter itemToUpdate = selectById(item.getId());
		itemToUpdate.setCkey(item.getCkey());
		itemToUpdate.setLocked(item.getLocked());
		itemToUpdate.setNumber(item.getNumber());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		Counter item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	
	public String generateIdentityUid() {
		String queryString = "from Counter ac where ac.ckey = :s1 ";
		Query q = entityManager.createQuery(queryString);
		q.setParameter("s1", AppConstants.PARAM_IDENTITY_UID);
		Counter cont = (Counter)q.getSingleResult();
		if (cont == null) {
			//se non esiste un cursore per le anagrafiche allora lo crea
			Counter ac = new Counter();
			ac.setCkey(AppConstants.PARAM_IDENTITY_UID);
			ac.setNumber(AppConstants.DEFAULT_IDENTITY_UID_START_VALUE);
			ac.setLocked(false);
			cont = insert(ac);
		}
		Integer progressivo = cont.getNumber();
		String code = NumberBaseConverter.toBase30(progressivo);
		if (code.length() < AppConstants.DEFAULT_IDENTITY_UID_CODE_LENGTH) {
			code = "000000000000000"+code;
			code = code.substring(code.length()-AppConstants.DEFAULT_IDENTITY_UID_CODE_LENGTH);
		}
		code = NumberBaseConverter.getChecksum30(code) + code;
		cont.setNumber(progressivo+1);
		update(cont);
		return code;
	}
	
}
