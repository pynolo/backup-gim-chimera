package it.giunti.chimera.service;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.giunti.chimera.model.dao.UserDao;
import it.giunti.chimera.model.entity.User;

@Service("authService")
public class AuthService {
	private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	@Qualifier("userDao")
	UserDao userDao;
	
	@Value("${giunti.ldap.host}")
	private String ldapHost;
	@Value("${giunti.ldap.domain}")
	private String ldapDomain;
	@Value("${giunti.ldap.baseDn}")
	private String ldapBaseDn;
	@Value("${giunti.ldap.principal}")
	private String ldapPrincipal;
	@Value("${giunti.ldap.credential}")
	private String ldapCredential;
	@Value("${giunti.ldap.attributeForUser}")
	private String ldapAttributeForUser;
	@Value("${giunti.ldap.attributeForName}")
	private String ldapAttributeForName;
	@Value("${giunti.ldap.attributeForMail}")
	private String ldapAttributeForMail;
	
	@Transactional
	public void authenticate(String username, String password) throws AuthenticationException {
		String errorString = null;
		//Search on DB
		User u = userDao.findByUserName(username);
		if (u != null) {
			// You specify in the authenticate user the attributes that you want returned.
			// Some companies use standard attributes <like 'description' to hold an employee ID.
			Attributes att = null;
			try {
				att = authenticateLdapUser(username, password,
						ldapDomain, ldapHost, ldapBaseDn);
			} catch (NamingException e) {
				LOG.debug(username+" non presente in ldap");
				errorString = username+" non presente in ldap";
			}
			//Se l'utente è nel DB verifica:
			//Password ldap corretta altrimenti password DB corretta
			if (att == null) {
				errorString = "Password errata";
			}
			//String name = att.get(ATTRIBUTE_FOR_NAME).toString();
			//name = name.substring(ATTRIBUTE_FOR_NAME.length()+2);
		} else {
			errorString = username+" non autorizzato";
		}
		if (errorString != null) throw new AuthenticationException(errorString);
	}

	@Transactional
	private Attributes authenticateLdapUser(String userName, String password,
			String domain, String host, String baseDn) throws NamingException {
		String returnedAtts[] = { ldapAttributeForUser, ldapAttributeForName, ldapAttributeForMail };
		String searchFilter = "(&(objectClass=user)(" + ldapAttributeForUser
				+ "=" + userName + "))";
		Attributes result = null;
		// Create the search controls
		SearchControls searchCtls = new SearchControls();
		searchCtls.setReturningAttributes(returnedAtts);
		// Specify the search scope
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		String searchBase = baseDn;
		Hashtable<String, String> environment = new Hashtable<String, String>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		// Using standard Port, check your instalation
		environment.put(Context.PROVIDER_URL, "ldap://" + host + ":389");
		environment.put(Context.SECURITY_AUTHENTICATION, "simple");
		environment.put(Context.SECURITY_PRINCIPAL, ldapPrincipal);//username + "@" + domain);
		environment.put(Context.SECURITY_CREDENTIALS, ldapCredential);//password);
		LdapContext ldapCtx = null;
		//Acquisisce il context
		ldapCtx = new InitialLdapContext(environment, null);
		//Ottiene il dn dell'utente cercato
		NamingEnumeration<SearchResult> answer = ldapCtx.search(searchBase,
				searchFilter, searchCtls);
		String dn = null;
		while (answer.hasMoreElements()) {
			SearchResult sr = answer.next();
			dn = sr.getName()+","+baseDn;
			result = sr.getAttributes();
			if (result == null) {
				throw new NamingException("Could not connect to the directory");
			}
		}
		ldapCtx.close();
		if (dn == null) {
			throw new NamingException("Could not connect to the directory");
		}
        // Authenticate
		environment.put(Context.SECURITY_AUTHENTICATION, "simple");
		environment.put(Context.SECURITY_PRINCIPAL, dn);
		environment.put(Context.SECURITY_CREDENTIALS, password);

        try {
			ldapCtx = new InitialLdapContext(environment, null);
			ldapCtx.close();
		} catch (NamingException e) {
			throw new NamingException("User "+ userName + " could not be found on the directory");
		}
        return result;
	}
	
}
