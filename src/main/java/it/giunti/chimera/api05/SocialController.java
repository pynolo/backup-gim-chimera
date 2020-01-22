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
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.ProviderAccount;
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
	
	@PostMapping("/api05/find_provider_accounts")
	public List<ProviderAccountBean> findProviderAccounts(@Valid @RequestBody SocialInputBean input) {
		if (input != null) {
			if (input.getIdentityUid() != null) {
				List<ProviderAccount> list = socialService.findAccountsByIdentityUid(input.getIdentityUid());
				List<ProviderAccountBean> beanList = new ArrayList<ProviderAccountBean>();
				for (ProviderAccount entity:list) beanList.add(BeanConverter.toProviderAccountBean(entity));
				return beanList;
			}
		}
		return new ArrayList<ProviderAccountBean>();
	}
	
	@PostMapping("/api05/add_provider_account")
	public ProviderAccountBean addProviderAccount(@Valid @RequestBody SocialInputBean input) {
		Identity identity = identityService;
		ProviderAccount entity = socialService.createProviderAccount(identity, input.getSocialId());
		ProviderAccountBean resultBean = BeanConverter.toProviderAccountBean(entity);
		return resultBean;
	}
	
	@PostMapping("/api05/remove_provider_account")
	public ValidationBean removeProviderAccount(@Valid @RequestBody SocialInputBean input) {
		ErrorBean error = null;
		try {
			ProviderAccount entity = socialService.getAccountByIdentityUidAndSocialId(
					input.getIdentityUid(), input.getSocialId());
			socialService.deleteProviderAccount(entity);
		} catch (BusinessException | EmptyResultException | DuplicateResultException e) {
			error = new ErrorBean();
			error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
			error.setMessage("Errore nell'eliminazione dell'account social");
		}
		ValidationBean resultBean = new ValidationBean();
		resultBean.setError(error);
	}

	
	// Input Beans
	
	
	public class SocialInputBean {
		private String accessKey = null;
		private String identityUid = null;
		private String socialId = null;
		
		public String getAccessKey() {
			return accessKey;
		}
		public void setAccessKey(String accessKey) {
			this.accessKey = accessKey;
		}
		public String getIdentityUid() {
			return identityUid;
		}
		public void setIdentityUid(String identityUid) {
			this.identityUid = identityUid;
		}
		public String getSocialId() {
			return socialId;
		}
		public void setSocialId(String socialId) {
			this.socialId = socialId;
		}
	}
}
