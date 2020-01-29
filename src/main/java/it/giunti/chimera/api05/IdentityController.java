package it.giunti.chimera.api05;

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
import it.giunti.chimera.api05.bean.ParametersBean;
import it.giunti.chimera.api05.bean.ValidationBean;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.srvc.FederationSrvc;
import it.giunti.chimera.srvc.IdentitySrvc;
import it.giunti.chimera.util.PasswordUtil;

@RestController
@CrossOrigin(origins = "*")
public class IdentityController {

	@Autowired
	@Qualifier("identitySrvc")
	private IdentitySrvc identitySrvc;
	@Autowired
	@Qualifier("federationSrvc")
	private FederationSrvc federationSrvc;
	@Autowired
	@Qualifier("converterApi05Srvc")
	private ConverterApi05Srvc converterApi05Srvc;
	
	@PostMapping("/api05/authenticate")
	public IdentityBean authenticate(@Valid @RequestBody ParametersBean input) {
		IdentityBean resultBean = new IdentityBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			//BODY
			try {
				Identity entity = identitySrvc.getIdentityByEmail(input.getEmail());
				if (entity != null) {
					// Identity found
					String passwordMd5 = PasswordUtil.md5(input.getPassword());
					if (entity.getPasswordMd5().equals(passwordMd5)) {
						resultBean = converterApi05Srvc.toIdentityBean(entity);
						federationSrvc.addOrUpdateIdentityFederation(
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
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			//BODY
			Identity entity = identitySrvc.getIdentity(input.getIdentityUid());
			if (entity != null) {
				resultBean = converterApi05Srvc.toIdentityBean(entity);
				return resultBean;
			}
			error = new ErrorBean();
			error.setCode(ErrorEnum.DATA_NOT_FOUND.getErrorCode());
			error.setMessage(ErrorEnum.DATA_NOT_FOUND.getErrorDescr());
		}
		resultBean.setError(error);
		return resultBean;	
	}

	@PostMapping("/api05/get_identity_by_email")
	public IdentityBean getIdentityByEmail(@Valid @RequestBody ParametersBean input) {
		IdentityBean resultBean = new IdentityBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			try {
				Identity entity = identitySrvc.getIdentityByEmail(input.getEmail());
				if (entity != null) {
					resultBean = converterApi05Srvc.toIdentityBean(entity);
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
	
	@PostMapping("/api05/get_identity_by_social_id")
	public IdentityBean getIdentityBySocialId(@Valid @RequestBody ParametersBean input) {
		IdentityBean resultBean = new IdentityBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			try {
				Identity entity = identitySrvc.getIdentityBySocialId(input.getSocialId());
				if (entity != null) {
					resultBean = converterApi05Srvc.toIdentityBean(entity);
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
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
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
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
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
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
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
				converterApi05Srvc.persistIntoIdentity(input);
				success = true;
			}
		}
		resultBean.setSuccess(success);
		resultBean.setError(error);
		return resultBean;
	}

	@PostMapping("/api05/add_identity")
	public ValidationBean addIdentity(@Valid @RequestBody IdentityBean input) {
		ValidationBean resultBean = new ValidationBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
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
				converterApi05Srvc.persistIntoIdentity(input);
				success = true;
			}
		}
		resultBean.setSuccess(success);
		resultBean.setError(error);
		return resultBean;
	}
	
	@PostMapping("/api05/update_identity_consent")
	public ValidationBean updateIdentityConsent(@Valid @RequestBody IdentityConsentBean input) {
		ValidationBean resultBean = new ValidationBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
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
					converterApi05Srvc.persistIntoConsent(input);
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
		return resultBean;
	}
	
	@PostMapping("/api05/delete_identity")
	public ValidationBean deleteIdentity(@Valid @RequestBody ParametersBean input) {
		ValidationBean resultBean = new ValidationBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		//Verifica diritti scrittura
		if (!akBean.getFederation().getCanUpdate()) {
			error.setCode(ErrorEnum.UNAUTHORIZED.getErrorCode());
			error.setMessage(ErrorEnum.UNAUTHORIZED.getErrorDescr());
		}
		boolean success = false;
		if (error == null) {
			try {
				identitySrvc.deleteIdentity(input.getIdentityUid());
				success = true;
			} catch (BusinessException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.DATA_NOT_FOUND.getErrorCode());
				error.setMessage(e.getMessage());
			}
		}
		resultBean.setSuccess(success);
		resultBean.setError(error);
		return resultBean;
	}
	
	@PostMapping("/api05/replace_identity")
	public IdentityBean replaceIdentity(@Valid @RequestBody ParametersBean input) {
		IdentityBean resultBean = new IdentityBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationSrvc.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		//Verifica diritti scrittura
		if (!akBean.getFederation().getCanMerge()) {
			error.setCode(ErrorEnum.UNAUTHORIZED.getErrorCode());
			error.setMessage(ErrorEnum.UNAUTHORIZED.getErrorDescr());
		}
		if (error == null) {
			try {
				identitySrvc.replaceIdentity(input.getRedundantIdentityUid(), input.getFinalIdentityUid());
			} catch (BusinessException e) {
				error = new ErrorBean();
				error.setCode(ErrorEnum.DATA_NOT_FOUND.getErrorCode());
				error.setMessage(e.getMessage());
			}
		}
		resultBean.setError(error);
		return resultBean;
	}
	
}
