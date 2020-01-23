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
import it.giunti.chimera.model.entity.Service;
import it.giunti.chimera.srvc.IdentitySrvc;
import it.giunti.chimera.srvc.ServiceSrvc;
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
	@Qualifier("serviceSrvc")
	private ServiceSrvc serviceSrvc;
	
	@PostMapping("/api05/find_provider_accounts")
	public List<ProviderAccountBean> findProviderAccounts(@Valid @RequestBody SocialInputBean input) {
		if (input != null) {
			Service service = serviceSrvc.findServiceByAccessKey(input.getAccessKey());
			if (service != null) {
				// ACCESS KEY EXISTS
				if (input.getIdentityUid() != null) {
					List<ProviderAccount> list = socialSrvc.findAccountsByIdentityUid(input.getIdentityUid());
					List<ProviderAccountBean> beanList = new ArrayList<ProviderAccountBean>();
					for (ProviderAccount entity:list) beanList.add(BeanConverter.toProviderAccountBean(entity));
					return beanList;
				}
				return new ArrayList<ProviderAccountBean>();
			}
			// ACCESS KEY FAILS
		}
		// NO INPUT
		ProviderAccountBean resultBean = new ProviderAccountBean();
		ErrorBean error = new ErrorBean();
		error.setCode(ErrorEnum.WRONG_ACCESS_KEY.getErrorCode());
		error.setMessage(ErrorEnum.WRONG_ACCESS_KEY.getErrorDescr());
		resultBean.setError(error);
		List<ProviderAccountBean> beanList = new ArrayList<ProviderAccountBean>();
		beanList.add(resultBean);
		return beanList;
	}
	
	@PostMapping("/api05/add_provider_account")
	public ProviderAccountBean addProviderAccount(@Valid @RequestBody SocialInputBean input) {
		if (input != null) {
			Service service = serviceSrvc.findServiceByAccessKey(input.getAccessKey());
			if (service != null) {
				// ACCESS KEY EXISTS
				Identity identity = identitySrvc.getIdentity(input.getIdentityUid());
				ErrorBean error = null;
				ProviderAccountBean resultBean = new ProviderAccountBean();
				try {
					ProviderAccount entity = socialSrvc.createProviderAccount(identity, input.getSocialId());
					resultBean = BeanConverter.toProviderAccountBean(entity);
				} catch (BusinessException e) {
					error = new ErrorBean();
					error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
					error.setMessage("Impossibile abbinare un nuovo account social");
				}
				resultBean.setError(error);
				return resultBean;
			}
			// ACCESS KEY FAILS
		}
		// NO INPUT
		ProviderAccountBean resultBean = new ProviderAccountBean();
		ErrorBean error = new ErrorBean();
		error.setCode(ErrorEnum.WRONG_ACCESS_KEY.getErrorCode());
		error.setMessage(ErrorEnum.WRONG_ACCESS_KEY.getErrorDescr());
		resultBean.setError(error);
		return resultBean;	
	}
	
	@PostMapping("/api05/remove_provider_account")
	public ValidationBean removeProviderAccount(@Valid @RequestBody SocialInputBean input) {
		if (input != null) {
			Service service = serviceSrvc.findServiceByAccessKey(input.getAccessKey());
			if (service != null) {
				// ACCESS KEY EXISTS
				ErrorBean error = null;
				try {
					ProviderAccount entity = socialSrvc.getAccountByIdentityUidAndSocialId(
							input.getIdentityUid(), input.getSocialId());
					socialSrvc.deleteProviderAccount(entity);
				} catch (BusinessException | EmptyResultException | DuplicateResultException e) {
					error = new ErrorBean();
					error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
					error.setMessage("Errore nell'eliminazione dell'account social");
				}
				ValidationBean resultBean = new ValidationBean();
				resultBean.setError(error);
				return resultBean;
			}
			// ACCESS KEY FAILS
		}
		// NO INPUT
		ValidationBean resultBean = new ValidationBean();
		ErrorBean error = new ErrorBean();
		error.setCode(ErrorEnum.WRONG_ACCESS_KEY.getErrorCode());
		error.setMessage(ErrorEnum.WRONG_ACCESS_KEY.getErrorDescr());
		resultBean.setError(error);
		return resultBean;
	}

}
