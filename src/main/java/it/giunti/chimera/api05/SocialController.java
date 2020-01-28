package it.giunti.chimera.api05;

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
import it.giunti.chimera.api05.bean.ErrorBean;
import it.giunti.chimera.api05.bean.ProviderAccountBean;
import it.giunti.chimera.api05.bean.SocialInputBean;
import it.giunti.chimera.api05.bean.ValidationBean;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.ProviderAccount;
import it.giunti.chimera.srvc.FederationSrvc;
import it.giunti.chimera.srvc.IdentitySrvc;
import it.giunti.chimera.srvc.SocialSrvc;

@RestController
@CrossOrigin(origins = "*")
public class SocialController {

	@Autowired
	@Qualifier("socialSrvc")
	private SocialSrvc socialSrvc;
	@Autowired
	@Qualifier("identitySrvc")
	private IdentitySrvc identitySrvc;
	@Autowired
	@Qualifier("federationSrvc")
	private FederationSrvc federationSrvc;
	
	@Autowired
	@Qualifier("converterApi05Srvc")
	private ConverterApi05Srvc converterApi05Srvc;
	
	@PostMapping("/api05/find_provider_accounts")
	public List<ProviderAccountBean> findProviderAccounts(@Valid @RequestBody SocialInputBean input) {
		List<ProviderAccountBean> beanList = new ArrayList<ProviderAccountBean>();
		ErrorBean error = federationSrvc.checkAccessKeyAndNull(input);
		if (error == null) {
			if (input.getIdentityUid() != null) {
				List<ProviderAccount> list = socialSrvc.findAccountsByIdentityUid(input.getIdentityUid());
				for (ProviderAccount entity:list) beanList.add(converterApi05Srvc.toProviderAccountBean(entity));
				return beanList;
			}
			return new ArrayList<ProviderAccountBean>();
		}
		ProviderAccountBean bean = new ProviderAccountBean();
		bean.setError(error);
		beanList.add(bean);
		return beanList;
	}
	
	@PostMapping("/api05/add_provider_account")
	public ProviderAccountBean addProviderAccount(@Valid @RequestBody SocialInputBean input) {
		ProviderAccountBean resultBean = new ProviderAccountBean();
		ErrorBean error = federationSrvc.checkAccessKeyAndNull(input);
		if (error == null) {
			Identity identity = identitySrvc.getIdentity(input.getIdentityUid());
			try {
				ProviderAccount entity = socialSrvc.createProviderAccount(identity.getId(), input.getSocialId());
				resultBean = converterApi05Srvc.toProviderAccountBean(entity);
			} catch (BusinessException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage("Impossibile abbinare un nuovo account social");
			}
		}
		resultBean.setError(error);
		return resultBean;	
	}
	
	@PostMapping("/api05/delete_provider_account")
	public ValidationBean deleteProviderAccount(@Valid @RequestBody SocialInputBean input) {
		ValidationBean resultBean = new ValidationBean();
		ErrorBean error = federationSrvc.checkAccessKeyAndNull(input);
		if (error == null) {
			try {
				ProviderAccount entity = socialSrvc.getAccountByIdentityUidAndSocialId(
						input.getIdentityUid(), input.getSocialId());
				socialSrvc.deleteProviderAccount(entity);
			} catch (BusinessException | EmptyResultException | DuplicateResultException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage("Errore nell'eliminazione dell'account social");
			}
		}
		resultBean.setError(error);
		return resultBean;
	}

}
