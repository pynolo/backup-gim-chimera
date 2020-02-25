package it.giunti.chimera.api.v05;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.giunti.chimera.api.v05.bean.ProviderAccountBean;
import it.giunti.chimera.api.v05.bean.ProviderAccountListBean;
import it.giunti.chimera.api.v05.bean.SocialInputBean;
import it.giunti.chimera.api.v05.bean.ValidationBean;
import it.giunti.chimera.exception.Conflict409Exception;
import it.giunti.chimera.exception.NotFound404Exception;
import it.giunti.chimera.exception.Unauthorized401Exception;
import it.giunti.chimera.exception.UnprocessableEntity422Exception;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.ProviderAccount;
import it.giunti.chimera.service.FederationService;
import it.giunti.chimera.service.IdentityService;
import it.giunti.chimera.service.SocialService;

@RestController
@CrossOrigin(origins = "*")
public class SocialController {

	@Autowired
	@Qualifier("socialService")
	private SocialService socialService;
	@Autowired
	@Qualifier("identityService")
	private IdentityService identityService;
	@Autowired
	@Qualifier("federationService")
	private FederationService federationService;
	
	@Autowired
	@Qualifier("converter05Service")
	private Converter05Service converter05Service;
	
	@GetMapping("/api/05/find_provider_accounts/{identityUid}")
	public ProviderAccountListBean findProviderAccounts(@PathVariable(value = "identityUid") String identityUid) {
		ProviderAccountListBean resultBean = new ProviderAccountListBean();
		List<ProviderAccountBean> beanList = new ArrayList<ProviderAccountBean>();
		if (identityUid != null) {
			List<ProviderAccount> list = socialService.findAccountsByIdentityUid(identityUid);
			for (ProviderAccount entity:list) beanList.add(converter05Service.toProviderAccountBean(entity));
			resultBean.setProviderAccounts(beanList);
			return resultBean;
		}
		resultBean.setProviderAccounts(new ArrayList<ProviderAccountBean>());
		return resultBean;
	}
	
	@PostMapping("/api/05/add_provider_account")
	public ProviderAccountBean addProviderAccount(@Valid @RequestBody SocialInputBean input) 
			throws Unauthorized401Exception, UnprocessableEntity422Exception,
			Conflict409Exception, NotFound404Exception {
		ProviderAccountBean resultBean = new ProviderAccountBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		//Verifica diritti scrittura
		if (!fed.getCanUpdate()) {
			throw new Unauthorized401Exception("Non autorizzato");
		}
		try {
			Identity identity = identityService.getIdentity(input.getIdentityUid());
			ProviderAccount entity = socialService.createProviderAccount(identity.getId(), input.getSocialId());
			resultBean = converter05Service.toProviderAccountBean(entity);
		} catch (Exception e) {
			if (input != null)
				identityService.addLog(input.getIdentityUid(), fed.getId(),
					"/api/05/add_provider_account", input, e.getMessage());
			throw e;
		}
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), fed.getId(),
				"/api/05/add_provider_account", input, null);
		return resultBean;	
	}
	
	@PostMapping("/api/05/delete_provider_account")
	public ValidationBean deleteProviderAccount(@Valid @RequestBody SocialInputBean input) 
			throws Unauthorized401Exception, UnprocessableEntity422Exception,
			Conflict409Exception, NotFound404Exception {
		ValidationBean resultBean = new ValidationBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		//Verifica diritti scrittura
		if (!fed.getCanUpdate()) {
			throw new Unauthorized401Exception("Non autorizzato");
		}
		boolean success = false;
		try {
			ProviderAccount entity = socialService.getAccountByIdentityUidAndSocialId(
					input.getIdentityUid(), input.getSocialId());
			socialService.deleteProviderAccount(entity);
			success = true;
		} catch (Exception e) {
			if (input != null)
				identityService.addLog(input.getIdentityUid(), fed.getId(),
					"/api/05/add_provider_account", input, e.getMessage());
			throw e;
		}
		resultBean.setSuccess(success);
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), fed.getId(),
				"/api/05/delete_provider_account", input, null);
		return resultBean;
	}

}
