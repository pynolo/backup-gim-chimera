package it.giunti.chimera.api05;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		if (input != null) {
			Service service = serviceSrvc.findServiceByAccessKey(input.getAccessKey());
			if (service != null) {	
				// ACCESS KEY EXISTS
				return null;
			}
			// ACCESS KEY FAILS
		}
		// NO INPUT
		ErrorBean error = new ErrorBean();
		error.setCode(ErrorEnum.WRONG_ACCESS_KEY.getErrorCode());
		error.setMessage(ErrorEnum.WRONG_ACCESS_KEY.getErrorDescr());
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
	
//	@PostMapping("/api05/get_identity_by_social_id")
//	public IdentityBean getIdentityBySocialId(@Valid @RequestBody IdentityInputBean input) {
//
//	}
//	
//	@PostMapping("/api05/validate_identity_data")
//	public IdentityBean validateIdentityData(@Valid @RequestBody IdentityInputBean input) {
//
//	}
//	
//	@PostMapping("/api05/create_identity")
//	public IdentityBean createIdentity(@Valid @RequestBody IdentityInputBean input) {
//
//	}
//	
//	@PostMapping("/api05/update_identity")
//	public IdentityBean updateIdentity(@Valid @RequestBody IdentityInputBean input) {
//
//	}
//	
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
