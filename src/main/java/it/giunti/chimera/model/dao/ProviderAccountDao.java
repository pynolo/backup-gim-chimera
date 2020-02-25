package it.giunti.chimera.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import it.giunti.chimera.exception.Conflict409Exception;
import it.giunti.chimera.exception.NotFound404Exception;
import it.giunti.chimera.model.entity.Provider;
import it.giunti.chimera.model.entity.ProviderAccount;
import it.giunti.chimera.util.QueryUtil;

@Repository("providerAccountDao")
public class ProviderAccountDao {

	@PersistenceContext
	private EntityManager entityManager;
    @Autowired
    @Qualifier("providerDao")
    private ProviderDao providerDao;
    
	public ProviderAccount selectById(int id) {
		return entityManager.find(ProviderAccount.class, id);
	}
	
	public ProviderAccount insert(ProviderAccount item) {
		entityManager.persist(item);
		return item;
	}
	
	public ProviderAccount update(ProviderAccount item) {
		ProviderAccount itemToUpdate = selectById(item.getId());
		itemToUpdate.setAccountIdentifier(item.getAccountIdentifier());
		itemToUpdate.setIdIdentity(item.getIdIdentity());
		itemToUpdate.setLastModified(item.getLastModified());
		itemToUpdate.setProvider(item.getProvider());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		ProviderAccount item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public ProviderAccount findByProviderIdentifier(String pac4jPrefix, String accountIdentifier) {
		ProviderAccount result = null;
		String hql = "from ProviderAccount as pa where " +
				"pa.provider.casPrefix = :id1 and " +
				"pa.accountIdentifier = :id2 " +
				"order by pa.id asc";
		Query q = entityManager.createQuery(hql);
		pac4jPrefix = QueryUtil.escapeParam(pac4jPrefix);
		accountIdentifier = QueryUtil.escapeParam(accountIdentifier);
		q.setParameter("id1", pac4jPrefix);
		q.setParameter("id2", accountIdentifier);
		List<ProviderAccount> pList = (List<ProviderAccount>) q.getResultList();
		if (pList != null) {
			if (pList.size() >= 1) {
				result = pList.get(0);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderAccount> findByIdentity(Integer idIdentity) {
		List<ProviderAccount> result = null;
		String hql = "from ProviderAccount as pa where " +
				"pa.identity.id = :id1 " +
				"order by pa.id asc";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", idIdentity);
		List<ProviderAccount> pList = (List<ProviderAccount>) q.getResultList();
		if (pList != null) {
			result = pList;
		}
		return result;
	}
		
	@SuppressWarnings("unchecked")
	public List<ProviderAccount> findByIdentityAndProvider(Integer idIdentity, String pac4jPrefix) {
		String hql = "from ProviderAccount as pa where " +
				"pa.provider.casPrefix = :id1 and " +
				"pa.identity.id = :id2 " +
				"order by pa.id asc";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", pac4jPrefix);
		q.setParameter("id2", idIdentity);
		List<ProviderAccount> pList = (List<ProviderAccount>) q.getResultList();
		if (pList != null) {
			return pList;
		}
		return new ArrayList<ProviderAccount>();
	}
	
	public ProviderAccount createProviderAccount(Integer idIdentity, String pac4jPrefix, String accountIdentifier) 
			throws NotFound404Exception, Conflict409Exception {
		Provider provider = providerDao.findByCasPrefix(pac4jPrefix);
		if (provider == null) throw new NotFound404Exception("No provider identified by '"+pac4jPrefix+"'");
		
		//TEST 1
		//List<ProviderAccount> testList = findByProviderAndIdentifier(ses, pac4jPrefix, accountIdentifier);
		//if (testList.size() > 0) throw new BusinessException("One or more ProviderAccount correspond to "+pac4jPrefix+"#"+accountIdentifier);
		//TEST 2
		List<ProviderAccount> testList = findByIdentityAndProvider(idIdentity, pac4jPrefix);
		if (testList.size() > 0) throw new Conflict409Exception("A ProviderAccount for "+pac4jPrefix+" and id_identity "+idIdentity+" already exists");
		
		ProviderAccount result = new ProviderAccount();
		result.setAccountIdentifier(accountIdentifier);
		result.setIdIdentity(idIdentity);
		result.setLastModified(new Date());
		result.setProvider(provider);
		insert(result);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public void deleteProviderAccount(Integer idIdentity, String pac4jPrefix, String accountIdentifier) 
			throws NotFound404Exception {
		ProviderAccount toDelete = null;
		String hql = "from ProviderAccount as pa where " +
				"pa.provider.casPrefix = :id1 and " +
				"pa.accountIdentifier = :id2 and " +
				"pa.identity.id = :id3 " +
				"order by pa.id asc";
		Query q = entityManager.createQuery(hql);
		pac4jPrefix = QueryUtil.escapeParam(pac4jPrefix);
		accountIdentifier = QueryUtil.escapeParam(accountIdentifier);
		q.setParameter("id1", pac4jPrefix);
		q.setParameter("id2", accountIdentifier);
		q.setParameter("id3", idIdentity);
		List<ProviderAccount> pList = (List<ProviderAccount>) q.getResultList();
		if (pList != null) {
			if (pList.size() >= 1) {
				toDelete = pList.get(0);
			} else {
				throw new NotFound404Exception("No rows in ProviderAccount to delete");
			}
		}
		delete(toDelete.getId());
	}
	
}
