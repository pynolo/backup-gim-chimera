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
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.model.entity.Service;
import it.giunti.chimera.srvc.IdentitySrvc;
import it.giunti.chimera.srvc.ServiceSrvc;
import it.giunti.chimera.util.PasswordUtil;

@RestController
@CrossOrigin(origins = "*")
public class IdentityController {

	@Autowired
	@Qualifier("identitySrvc")
	private IdentitySrvc identitySrvc;
	@Autowired
	@Qualifier("serviceSrvc")
	private ServiceSrvc serviceSrvc;
	
	private ErrorBean checkInputBean(IdentityInputBean input) {
		ErrorBean error = new ErrorBean();
		if (input != null) {
			Service service = serviceSrvc.findServiceByAccessKey(input.getAccessKey());
			if (service != null) {	
				// ACCESS KEY EXISTS
				return null;
			}
			// ACCESS KEY FAILS
			error.setCode(ErrorEnum.WRONG_ACCESS_KEY.getErrorCode());
			error.setMessage(ErrorEnum.WRONG_ACCESS_KEY.getErrorDescr());
		} else {
			// NO INPUT
			error.setCode(ErrorEnum.EMPTY_PARAMETER.getErrorCode());
			error.setMessage("La richiesta e' priva di contenuto");
		}
		return error;	
	}
	
	@PostMapping("/api05/authenticate")
	public IdentityBean authenticate(@Valid @RequestBody IdentityInputBean input) {
		IdentityBean resultBean = new IdentityBean();
		ErrorBean error = checkInputBean(input);
		if (error == null) {
			//BODY
			try {
				Identity entity = identitySrvc.getIdentityByEmail(input.getEmail());
				if (entity != null) {
					// Identity found
					String passwordMd5 = PasswordUtil.md5(input.getPassword());
					if (entity.getPasswordMd5().equals(passwordMd5)) {
						resultBean = BeanConverter.toIdentityBean(entity);
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
	public IdentityBean getIdentity(@Valid @RequestBody IdentityInputBean input) {
		IdentityBean resultBean = new IdentityBean();
		ErrorBean error = checkInputBean(input);
		if (error == null) {
			//BODY
			Identity entity = identitySrvc.getIdentity(input.getIdentityUid());
			if (entity != null) {
				resultBean = BeanConverter.toIdentityBean(entity);
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
	public IdentityBean getIdentityByEmail(@Valid @RequestBody IdentityInputBean input) {
		IdentityBean resultBean = new IdentityBean();
		ErrorBean error = checkInputBean(input);
		if (error == null) {
			try {
				Identity entity = identitySrvc.getIdentityByEmail(input.getEmail());
				if (entity != null) {
					resultBean = BeanConverter.toIdentityBean(entity);
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
	public IdentityBean getIdentityBySocialId(@Valid @RequestBody IdentityInputBean input) {
		IdentityBean resultBean = new IdentityBean();
		ErrorBean error = checkInputBean(input);
		if (error == null) {
			try {
				Identity entity = identitySrvc.getIdentityBySocialId(input.getSocialId());
				if (entity != null) {
					resultBean = BeanConverter.toIdentityBean(entity);
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
		Map<String, String> errMap;
		try {
			errMap = BeanValidator.validateIdentityBean(input);
			if (errMap.isEmpty()) {
				resultBean.setSuccessfulValidation(true);
			} else {
				resultBean.setSuccessfulValidation(false);
				resultBean.setWarnings(errMap);
			}
		} catch (BusinessException e) {
			ErrorBean error = new ErrorBean();
			error.setCode(ErrorEnum.INTERNAL_ERROR.getErrorCode());
			error.setMessage(ErrorEnum.INTERNAL_ERROR.getErrorDescr());
			resultBean.setError(error);
		}
		return resultBean;
	}
	
	@PostMapping("/api05/validate_new_identity")
	public ValidationBean validateNewIdentity(@Valid @RequestBody IdentityBean input) {
		if (input.getIdentityUid() != null) {
			ValidationBean resultBean = new ValidationBean();
			ErrorBean error = new ErrorBean();
			error.setCode(ErrorEnum.WRONG_PARAMETER_VALUE.getErrorCode());
			error.setMessage("Una nuova identity non può avere identityUid");
			resultBean.setError(error);
		}
		return validateUpdatingIdentity(input);
	}
	
	@PostMapping("/api05/update_identity")
	public ValidationBean updateIdentity(@Valid @RequestBody IdentityBean input) {
		ValidationBean resultBean = validateUpdatingIdentity(input);
		if (resultBean.getError() != null) {
			Identity identity = BeanConverter.toIdentity(input);
			identity = identitySrvc.addOrUpdateIdentity(identity, false);
		}
		return resultBean;
	}

	@PostMapping("/api05/add_identity")
	public ValidationBean addIdentity(@Valid @RequestBody IdentityBean input) {
		ValidationBean resultBean = validateNewIdentity(input);
		if (resultBean.getError() != null) {
			Identity identity = BeanConverter.toIdentity(input);
			identity = identitySrvc.addOrUpdateIdentity(identity, false);
		}
		return resultBean;
	}
	
//	@PostMapping("/api05/update_identity_consent")
//	public IdentityBean updateIdentityConsent(@Valid @RequestBody IdentityInputBean input) {
//
//	}
//	
//	@PostMapping("/api05/replace_identity")
//	public IdentityBean replaceIdentity(@Valid @RequestBody IdentityInputBean input) {
//
//	}
//	
//	@PostMapping("/api05/delete_identity")
//	public IdentityBean deleteIdentity(@Valid @RequestBody IdentityInputBean input) {
//
//	}

}
