package it.giunti.chimera.api05;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.giunti.chimera.BusinessException;
import it.giunti.chimera.DuplicateResultException;
import it.giunti.chimera.ErrorEnum;
import it.giunti.chimera.api05.bean.AccessKeyValidationBean;
import it.giunti.chimera.api05.bean.ErrorBean;
import it.giunti.chimera.api05.bean.IdentityBean;
import it.giunti.chimera.api05.bean.IdentityConsentBean;
import it.giunti.chimera.api05.bean.IdentityHistoryBean;
import it.giunti.chimera.api05.bean.ParametersBean;
import it.giunti.chimera.api05.bean.ValidationBean;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.service.FederationService;
import it.giunti.chimera.service.IdentityService;
import it.giunti.chimera.util.PasswordUtil;

@RestController
@CrossOrigin(origins = "*")
public class IdentityController {

	@Autowired
	@Qualifier("identityService")
	private IdentityService identityService;
	@Autowired
	@Qualifier("federationService")
	private FederationService federationService;
	@Autowired
	@Qualifier("converter05Service")
	private Converter05Service converter05Service;
	
	private List<String> findUidHistory(String identityUid) {
		List<Identity> replacedList = identityService
				.findIdentityByReplacingUid(identityUid);
		List<String> uidList = new ArrayList<String>();
		for (Identity replaced:replacedList) uidList.add(replaced.getIdentityUid());
		return uidList;
	}
	
	@PostMapping("/api05/authenticate")
	public IdentityHistoryBean authenticate(@Valid @RequestBody ParametersBean input) {
		IdentityHistoryBean resultBean = new IdentityHistoryBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			//BODY
			try {
				Identity entity = identityService.getIdentityByEmail(input.getEmail());
				if (entity != null) {
					// Identity found
					String passwordMd5 = PasswordUtil.md5(input.getPassword());
					if (entity.getPasswordMd5().equals(passwordMd5)) {
						//Returns identityUid
						resultBean.setIdentityUid(entity.getIdentityUid());
						//Returns old uid's list
						resultBean.setReplacedIdentityUids(
								findUidHistory(entity.getIdentityUid()));
						//Updates federation info
						federationService.addOrUpdateIdentityFederation(
								entity.getId(), akBean.getFederation().getId());
						return resultBean;
					}
					// Wrong password
				} 
				//Identity not found
				error = new ErrorBean();
				error.setCode(ErrorEnum.WRONG_PARAMETER_VALUE.getErrorCode());
				error.setMessage("Le credenziali non sono corrette. ");
			} catch (DuplicateResultException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage("Risultato non univoco per "+input.getEmail()+". ");
			}
		}
		resultBean.setError(error);
		return resultBean;
	}

	@PostMapping("/api05/get_identity")
	public IdentityBean getIdentity(@Valid @RequestBody ParametersBean input) {
		IdentityBean resultBean = new IdentityBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			//BODY
			Identity entity = identityService.getIdentity(input.getIdentityUid());
			if (entity != null) {
				resultBean = converter05Service.toIdentityBean(entity);
				return resultBean;
			}
			error = new ErrorBean();
			error.setCode(ErrorEnum.DATA_NOT_FOUND.getErrorCode());
			error.setMessage(ErrorEnum.DATA_NOT_FOUND.getErrorDescr());
		}
		resultBean.setError(error);
		return resultBean;	
	}

	@PostMapping("/api05/find_identity_uid_by_email")
	public IdentityHistoryBean findIdentityUidByEmail(@Valid @RequestBody ParametersBean input) {
		IdentityHistoryBean resultBean = new IdentityHistoryBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			try {
				Identity entity = identityService.getIdentityByEmail(input.getEmail());
				if (entity != null) {
					//Returns identityUid
					resultBean.setIdentityUid(entity.getIdentityUid());
					//Returns old uid's list
					resultBean.setReplacedIdentityUids(
							findUidHistory(entity.getIdentityUid()));
					//Updates federation info
					federationService.addOrUpdateIdentityFederation(
							entity.getId(), akBean.getFederation().getId());
					return resultBean;
				}
				error = new ErrorBean();
				error.setCode(ErrorEnum.DATA_NOT_FOUND.getErrorCode());
				error.setMessage(ErrorEnum.DATA_NOT_FOUND.getErrorDescr());
			} catch (DuplicateResultException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage("Risultato non univoco per "+input.getEmail()+". ");
			}
		}
		resultBean.setError(error);
		return resultBean;	
	}
	
	@PostMapping("/api05/find_identity_uid_by_social_id")
	public IdentityHistoryBean findIdentityUidBySocialId(@Valid @RequestBody ParametersBean input) {
		IdentityHistoryBean resultBean = new IdentityHistoryBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			try {
				Identity entity = identityService.getIdentityBySocialId(input.getSocialId());
				if (entity != null) {
					//Returns identityUid
					resultBean.setIdentityUid(entity.getIdentityUid());
					//Returns old uid's list
					resultBean.setReplacedIdentityUids(
							findUidHistory(entity.getIdentityUid()));
					//Updates federation info
					federationService.addOrUpdateIdentityFederation(
							entity.getId(), akBean.getFederation().getId());
					return resultBean;
				}
				error = new ErrorBean();
				error.setCode(ErrorEnum.DATA_NOT_FOUND.getErrorCode());
				error.setMessage(ErrorEnum.DATA_NOT_FOUND.getErrorDescr());
			} catch (BusinessException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage(e.getMessage()+" ");
			} catch (DuplicateResultException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage("Risultato non univoco per "+input.getEmail()+". ");
			}
		}
		resultBean.setError(error);
		return resultBean;	
	}
	
	@PostMapping("/api05/validate_updating_identity")
	public ValidationBean validateUpdatingIdentity(@Valid @RequestBody IdentityBean input) {
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
			Map<String, String> errMap;
			try {
				errMap = BeanValidator.validateIdentityBean(input);
				if (errMap.isEmpty()) {
					success = true;
				} else {
					resultBean.setWarnings(errMap);
				}
			} catch (BusinessException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage(ErrorEnum.INTERNAL_ERROR.getErrorDescr());
			}
		}
		resultBean.setError(error);
		resultBean.setSuccess(success);
		return resultBean;
	}
	
	@PostMapping("/api05/validate_new_identity")
	public ValidationBean validateNewIdentity(@Valid @RequestBody IdentityBean input) {
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
			if (input != null) {
				if (input.getIdentityUid() != null) {
					error = new ErrorBean();
					error.setCode(ErrorEnum.WRONG_PARAMETER_VALUE.getErrorCode());
					error.setMessage("Una nuova identity non può avere identityUid");
					resultBean.setError(error);
				} else {
					return validateUpdatingIdentity(input);
				}
			}
		}
		resultBean.setSuccess(success);
		resultBean.setError(error);
		return resultBean;
	}
	
	@PostMapping("/api05/update_identity")
	public ValidationBean updateIdentity(@Valid @RequestBody IdentityBean input) {
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
			resultBean = validateUpdatingIdentity(input);
			if (resultBean.getError() != null) {
				/*Identity updatedIdentity =*/
				converter05Service.persistIntoIdentity(input);
				success = true;
			}
		}
		resultBean.setSuccess(success);
		resultBean.setError(error);
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), akBean.getFederation().getId(),
				"/api05/update_identity", input, error);
		return resultBean;
	}

	@PostMapping("/api05/add_identity")
	public ValidationBean addIdentity(@Valid @RequestBody IdentityBean input) {
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
			resultBean = validateNewIdentity(input);
			if (resultBean.getError() != null) {
				/*Identity identity */
				converter05Service.persistIntoIdentity(input);
				success = true;
			}
		}
		resultBean.setSuccess(success);
		resultBean.setError(error);
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), akBean.getFederation().getId(),
				"/api05/add_identity", input, error);
		return resultBean;
	}
	
	@PostMapping("/api05/update_identity_consent")
	public ValidationBean updateIdentityConsent(@Valid @RequestBody IdentityConsentBean input) {
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
			Map<String, String> errMap;
			try {
				errMap = BeanValidator.validateConsentBean(input);
				if (errMap.isEmpty()) {
					//Actually adds or updates
					/*IdentityConsent updatedConsent =*/
					converter05Service.persistIntoConsent(input);
					success = true;
				} else {
					resultBean.setWarnings(errMap);
				}
			} catch (BusinessException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
				error.setMessage(ErrorEnum.INTERNAL_ERROR.getErrorDescr());
			}
		}
		resultBean.setSuccess(success);
		resultBean.setError(error);
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), akBean.getFederation().getId(),
				"/api05/update_identity_consent", input, error);
		return resultBean;
	}
	
	@PostMapping("/api05/delete_identity")
	public ValidationBean deleteIdentity(@Valid @RequestBody ParametersBean input) {
		ValidationBean resultBean = new ValidationBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		//Verifica diritti scrittura
		if (!akBean.getFederation().getCanDelete()) {
			error.setCode(ErrorEnum.UNAUTHORIZED.getErrorCode());
			error.setMessage(ErrorEnum.UNAUTHORIZED.getErrorDescr());
		}
		boolean success = false;
		if (error == null) {
			try {
				identityService.deleteIdentity(input.getIdentityUid());
				success = true;
			} catch (BusinessException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.DATA_NOT_FOUND.getErrorCode());
				error.setMessage(e.getMessage());
			}
		}
		resultBean.setSuccess(success);
		resultBean.setError(error);
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), akBean.getFederation().getId(),
				"/api05/delete_identity", input, error);
		return resultBean;
	}
	
	@PostMapping("/api05/replace_identity")
	public IdentityBean replaceIdentity(@Valid @RequestBody ParametersBean input) {
		IdentityBean resultBean = new IdentityBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		//Verifica diritti scrittura
		if (!akBean.getFederation().getCanReplace()) {
			error.setCode(ErrorEnum.UNAUTHORIZED.getErrorCode());
			error.setMessage(ErrorEnum.UNAUTHORIZED.getErrorDescr());
		}
		if (error == null) {
			try {
				identityService.replaceIdentity(input.getRedundantIdentityUid(), input.getFinalIdentityUid());
			} catch (BusinessException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.DATA_NOT_FOUND.getErrorCode());
				error.setMessage(e.getMessage());
			}
		}
		resultBean.setError(error);
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), akBean.getFederation().getId(),
				"/api05/replace_identity", input, error);
		return resultBean;
	}
	
}
