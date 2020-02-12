package it.giunti.chimera.api.v05;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.giunti.chimera.BusinessException;
import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.EmptyResultException;
import it.giunti.chimera.ErrorEnum;
import it.giunti.chimera.api.v05.bean.AccessKeyValidationBean;
import it.giunti.chimera.api.v05.bean.ErrorBean;
import it.giunti.chimera.api.v05.bean.ProviderAccountBean;
import it.giunti.chimera.api.v05.bean.ProviderAccountListBean;
import it.giunti.chimera.api.v05.bean.SocialInputBean;
import it.giunti.chimera.api.v05.bean.ValidationBean;
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
	
	@PostMapping("/api/05/find_provider_accounts")
	public ProviderAccountListBean findProviderAccounts(@Valid @RequestBody SocialInputBean input) {
		ProviderAccountListBean resultBean = new ProviderAccountListBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			List<ProviderAccountBean> beanList = new ArrayList<ProviderAccountBean>();
			if (input.getIdentityUid() != null) {
				List<ProviderAccount> list = socialService.findAccountsByIdentityUid(input.getIdentityUid());
				for (ProviderAccount entity:list) beanList.add(converter05Service.toProviderAccountBean(entity));
				resultBean.setProviderAccounts(beanList);
				return resultBean;
			}
			resultBean.setProviderAccounts(new ArrayList<ProviderAccountBean>());
			return resultBean;
		}
		resultBean.setError(error);
		return resultBean;
	}
	
	@PostMapping("/api/05/add_provider_account")
	public ProviderAccountBean addProviderAccount(@Valid @RequestBody SocialInputBean input) {
		ProviderAccountBean resultBean = new ProviderAccountBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		//Verifica diritti scrittura
		if (!akBean.getFederation().getCanUpdate()) {
			error.setCode(ErrorEnum.UNAUTHORIZED.getErrorCode());
			error.setMessage(ErrorEnum.UNAUTHORIZED.getErrorDescr());
		}
		if (error == null) {
			Identity identity = identityService.getIdentity(input.getIdentityUid());
			try {
				ProviderAccount entity = socialService.createProviderAccount(identity.getId(), input.getSocialId());
				resultBean = converter05Service.toProviderAccountBean(entity);
			} catch (BusinessException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage("Impossibile abbinare un nuovo account social");
			}
		}
		resultBean.setError(error);
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), akBean.getFederation().getId(),
				"/api/05/add_provider_account", input, error);
		return resultBean;	
	}
	
	@PostMapping("/api/05/delete_provider_account")
	public ValidationBean deleteProviderAccount(@Valid @RequestBody SocialInputBean input) {
		ValidationBean resultBean = new ValidationBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		//Verifica diritti scrittura
		if (!akBean.getFederation().getCanUpdate()) {
			error.setCode(ErrorEnum.UNAUTHORIZED.getErrorCode());
			error.setMessage(ErrorEnum.UNAUTHORIZED.getErrorDescr());
		}
		boolean success = false;
		if (error == null) {
			try {
				ProviderAccount entity = socialService.getAccountByIdentityUidAndSocialId(
						input.getIdentityUid(), input.getSocialId());
				socialService.deleteProviderAccount(entity);
				success = true;
			} catch (BusinessException | EmptyResultException | DuplicateResultException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage("Errore nell'eliminazione dell'account social");
			}
		}
		resultBean.setSuccess(success);
		resultBean.setError(error);
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), akBean.getFederation().getId(),
				"/api/05/delete_provider_account", input, error);
		return resultBean;
	}

}
