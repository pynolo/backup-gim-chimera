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

import it.giunti.chimera.BusinessException;
import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.EmptyResultException;
import it.giunti.chimera.model.entity.Identities;
import it.giunti.chimera.model.entity.ProviderAccounts;
import it.giunti.chimera.model.entity.Providers;
import it.giunti.chimera.util.QueryUtil;

@Repository("providerAccountsDao")
public class ProviderAccountsDao {

	@PersistenceContext
	private EntityManager entityManager;
    @Autowired
    @Qualifier("providersDao")
    private ProvidersDao providersDao;
    
	public ProviderAccounts selectById(int id) {
		return entityManager.find(ProviderAccounts.class, id);
	}
	
	public ProviderAccounts insert(ProviderAccounts item) {
		entityManager.persist(item);
		return item;
	}
	
	public ProviderAccounts update(ProviderAccounts item) {
		ProviderAccounts itemToUpdate = selectById(item.getId());
		itemToUpdate.setAccountIdentifier(item.getAccountIdentifier());
		itemToUpdate.setIdentity(item.getIdentity());
		itemToUpdate.setLastModified(item.getLastModified());
		itemToUpdate.setProvider(item.getProvider());
		entityManager.merge(itemToUpdate);
		entityManager.flush();
		return item;
	}

	public void delete(int id) {
		ProviderAccounts item = selectById(id);
		entityManager.merge(item);
		entityManager.remove(item);
		entityManager.flush();
	}
	
	@SuppressWarnings("unchecked")
	public ProviderAccounts findByProviderIdentifier(String pac4jPrefix, String accountIdentifier) 
			throws EmptyResultException, DuplicateResultException {
		ProviderAccounts result = null;
		String hql = "from ProviderAccounts as pa where " +
				"pa.provider.casPrefix = :id1 and " +
				"pa.accountIdentifier = :id2 " +
				"order by pa.id asc";
		Query q = entityManager.createQuery(hql);
		pac4jPrefix = QueryUtil.escapeParam(pac4jPrefix);
		accountIdentifier = QueryUtil.escapeParam(accountIdentifier);
		q.setParameter("id1", pac4jPrefix);
		q.setParameter("id2", accountIdentifier);
		List<ProviderAccounts> pList = (List<ProviderAccounts>) q.getResultList();
		if (pList != null) {
			if (pList.size() == 1) {
				result = pList.get(0);
			} else {
				if (pList.size() == 0)
					throw new EmptyResultException("No rows in ProviderAccounts corresponds to "+pac4jPrefix+"#"+accountIdentifier);
				if (pList.size() > 1)
					throw new DuplicateResultException("More rows in ProviderAccounts corresponds to "+pac4jPrefix+"#"+accountIdentifier);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProviderAccounts> findByIdentity(Integer idIdentity) 
			throws EmptyResultException, DuplicateResultException {
		List<ProviderAccounts> result = null;
		String hql = "from ProviderAccounts as pa where " +
				"pa.identity.id = :id1 " +
				"order by pa.id asc";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", idIdentity);
		List<ProviderAccounts> pList = (List<ProviderAccounts>) q.getResultList();
		if (pList != null) {
			result = pList;
		}
		return result;
	}
		
	@SuppressWarnings("unchecked")
	public List<ProviderAccounts> findByIdentityAndProvider(Integer idIdentity, String pac4jPrefix) {
		String hql = "from ProviderAccounts as pa where " +
				"pa.provider.casPrefix = :id1 and " +
				"pa.identity.id = :id2 " +
				"order by pa.id asc";
		Query q = entityManager.createQuery(hql);
		q.setParameter("id1", pac4jPrefix);
		q.setParameter("id2", idIdentity);
		List<ProviderAccounts> pList = (List<ProviderAccounts>) q.getResultList();
		if (pList != null) {
			return pList;
		}
		return new ArrayList<ProviderAccounts>();
	}
	
	public ProviderAccounts createProviderAccount(Identities identity, String pac4jPrefix, String accountIdentifier) 
			throws BusinessException {
		Providers provider;
		try {
			provider = providersDao.findByCasPrefix(pac4jPrefix);
		} catch (EmptyResultException e) {
			throw new BusinessException(e.getMessage());
		} catch (DuplicateResultException e) {
			throw new BusinessException(e.getMessage());
		}
		if (provider == null) throw new BusinessException("No provider identified by '"+pac4jPrefix+"'");
		
		//TEST 1
		//List<ProviderAccounts> testList = findByProviderAndIdentifier(ses, pac4jPrefix, accountIdentifier);
		//if (testList.size() > 0) throw new BusinessException("One or more ProviderAccounts correspond to "+pac4jPrefix+"#"+accountIdentifier);
		//TEST 2
		List<ProviderAccounts> testList = findByIdentityAndProvider(identity.getId(), pac4jPrefix);
		if (testList.size() > 0) throw new BusinessException("A ProviderAccount for "+pac4jPrefix+" and user_uid "+identity.getIdentityUid()+" already exists");
		
		ProviderAccounts result = new ProviderAccounts();
		result.setAccountIdentifier(accountIdentifier);
		result.setIdentity(identity);
		result.setLastModified(new Date());
		result.setProvider(provider);
		insert(result);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public void deleteProviderAccount(Integer idIdentity, String pac4jPrefix, String accountIdentifier) 
			throws EmptyResultException, DuplicateResultException {
		ProviderAccounts toDelete = null;
		String hql = "from ProviderAccounts as pa where " +
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
		List<ProviderAccounts> pList = (List<ProviderAccounts>) q.getResultList();
		if (pList != null) {
			if (pList.size() == 1) {
				toDelete = pList.get(0);
			} else {
				if (pList.size() == 0)
					throw new EmptyResultException("No rows in ProviderAccounts to delete");
				if (pList.size() > 1)
					throw new DuplicateResultException("More rows in ProviderAccounts corresponds to given user");
			}
		}
		delete(toDelete.getId());
	}
	
}
