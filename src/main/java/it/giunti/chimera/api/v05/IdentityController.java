package it.giunti.chimera.api.v05;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import it.giunti.chimera.api.v05.bean.IdentityBean;
import it.giunti.chimera.api.v05.bean.IdentityConsentBean;
import it.giunti.chimera.api.v05.bean.IdentityHistoryBean;
import it.giunti.chimera.api.v05.bean.ParametersBean;
import it.giunti.chimera.api.v05.bean.ValidationBean;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.mvc.Conflict409Exception;
import it.giunti.chimera.mvc.Internal418Exception;
import it.giunti.chimera.mvc.NotFound404Exception;
import it.giunti.chimera.mvc.Unauthorized401Exception;
import it.giunti.chimera.mvc.UnprocessableEntity422Exception;
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
	
	@PostMapping("/api/05/authenticate")
	public IdentityHistoryBean authenticate(@Valid @RequestBody ParametersBean input) 
			throws Unauthorized401Exception {
		IdentityHistoryBean resultBean = new IdentityHistoryBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		//BODY
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
						entity.getId(), fed.getId());
				return resultBean;
			}
		}
		throw new Unauthorized401Exception("Le credenziali non sono corrette. ");
	}

	@GetMapping("/api/05/get_identity/{identityUid}")
	public IdentityBean getIdentity(@PathVariable(value = "identityUid") String identityUid) 
			throws NotFound404Exception {
		IdentityBean resultBean = new IdentityBean();
		
		Identity entity = identityService.getIdentity(identityUid);
		if (entity != null) {
			resultBean = converter05Service.toIdentityBean(entity);
			return resultBean;
		}
		throw new NotFound404Exception("Non trovato");
	}

	@GetMapping("/api/05/find_identity_uid_by_email/{email}")
	public IdentityHistoryBean findIdentityUidByEmail(@PathVariable(value = "email") String email) 
			throws NotFound404Exception {
		IdentityHistoryBean resultBean = new IdentityHistoryBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		Identity entity = identityService.getIdentityByEmail(email);
		if (entity != null) {
			//Returns identityUid
			resultBean.setIdentityUid(entity.getIdentityUid());
			//Returns old uid's list
			resultBean.setReplacedIdentityUids(
					findUidHistory(entity.getIdentityUid()));
			//Updates federation info
			federationService.addOrUpdateIdentityFederation(
					entity.getId(), fed.getId());
			return resultBean;
		}
		throw new NotFound404Exception("Non trovato");
	}
	
	@GetMapping("/api/05/find_identity_uid_by_social_id/{socialId}")
	public IdentityHistoryBean findIdentityUidBySocialId(@PathVariable(value = "socialId") String socialId) 
			throws NotFound404Exception, Conflict409Exception, UnprocessableEntity422Exception {
		IdentityHistoryBean resultBean = new IdentityHistoryBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		Identity entity = identityService.getIdentityBySocialId(socialId);
		if (entity != null) {
			//Returns identityUid
			resultBean.setIdentityUid(entity.getIdentityUid());
			//Returns old uid's list
			resultBean.setReplacedIdentityUids(
					findUidHistory(entity.getIdentityUid()));
			//Updates federation info
			federationService.addOrUpdateIdentityFederation(
					entity.getId(), fed.getId());
			return resultBean;
		}
		throw new NotFound404Exception("Non trovato");
	}
	
	@PostMapping("/api/05/validate_updating_identity")
	public ValidationBean validateUpdatingIdentity(@Valid @RequestBody IdentityBean input) 
			throws Unauthorized401Exception, Internal418Exception {
		ValidationBean resultBean = new ValidationBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		//Verifica diritti scrittura
		if (!fed.getCanUpdate()) {
			throw new Unauthorized401Exception("Non autorizzato");
		}
		boolean success = false;
		Map<String, String> errMap;
		errMap = BeanValidator.validateIdentityBean(input);
		if (errMap.isEmpty()) {
			success = true;
		} else {
			resultBean.setWarnings(errMap);
		}
		resultBean.setSuccess(success);
		return resultBean;
	}
	
	@PostMapping("/api/05/validate_new_identity")
	public ValidationBean validateNewIdentity(@Valid @RequestBody IdentityBean input) 
			throws Unauthorized401Exception, UnprocessableEntity422Exception, Internal418Exception {
		ValidationBean resultBean = new ValidationBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		//Verifica diritti scrittura
		if (!fed.getCanUpdate()) {
			throw new Unauthorized401Exception("Non autorizzato");
		}
		boolean success = false;
		if (input != null) {
			if (input.getIdentityUid() != null) {
				throw new UnprocessableEntity422Exception("Una nuova identity non può avere identityUid");
			}
			return validateUpdatingIdentity(input);
		}
		resultBean.setSuccess(success);
		return resultBean;
	}
	
	@PostMapping("/api/05/update_identity")
	public ValidationBean updateIdentity(@Valid @RequestBody IdentityBean input) 
			throws Unauthorized401Exception, Internal418Exception {
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
			Identity output = new Identity();
			resultBean = validateUpdatingIdentity(input);
			if (resultBean.getSuccess()) {
				/*Identity updatedIdentity =*/
				output = converter05Service.persistIntoIdentity(input);
				success = true;
			}
			resultBean.setSuccess(success);
			resultBean.setAssignedIdentityUid(output.getIdentityUid());
		} catch (Internal418Exception e) {
			identityService.addLog(input.getIdentityUid(), fed.getId(),
					"/api/05/update_identity", input, e.getMessage());
			throw e;
		}
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), fed.getId(),
				"/api/05/update_identity", input, null);
		return resultBean;
	}

	@PostMapping("/api/05/add_identity")
	public ValidationBean addIdentity(@Valid @RequestBody IdentityBean input) 
			throws Unauthorized401Exception, Internal418Exception, UnprocessableEntity422Exception {
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
			Identity output = new Identity();
			resultBean = validateNewIdentity(input);
			if (resultBean.getSuccess()) {
				/*Identity identity */
				output = converter05Service.persistIntoIdentity(input);
				success = true;
			}
			resultBean.setSuccess(success);
			resultBean.setAssignedIdentityUid(output.getIdentityUid());
		} catch (Exception e) {
			identityService.addLog(input.getIdentityUid(), fed.getId(),
					"/api/05/add_identity", input, e.getMessage());
			throw e;
		}
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), fed.getId(),
				"/api/05/add_identity", input, null);
		return resultBean;
	}
	
	@PostMapping("/api/05/update_identity_consent")
	public ValidationBean updateIdentityConsent(@Valid @RequestBody IdentityConsentBean input) 
			throws Unauthorized401Exception, NotFound404Exception {
		ValidationBean resultBean = new ValidationBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		//Verifica diritti scrittura
		if (!fed.getCanUpdate()) {
			throw new Unauthorized401Exception("Non autorizzato");
		}
		boolean success = false;
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
			resultBean.setSuccess(success);
		} catch (Exception e) {
			identityService.addLog(input.getIdentityUid(), fed.getId(),
					"/api/05/update_identity_consent", input, e.getMessage());
			throw e;
		}
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), fed.getId(),
				"/api/05/update_identity_consent", input, null);
		return resultBean;
	}
	
	@PostMapping("/api/05/delete_identity")
	public ValidationBean deleteIdentity(@Valid @RequestBody ParametersBean input) 
			throws Unauthorized401Exception, NotFound404Exception {
		ValidationBean resultBean = new ValidationBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		//Verifica diritti scrittura
		if (!fed.getCanDelete()) {
			throw new Unauthorized401Exception("Non autorizzato");
		}
		boolean success = false;
		try {
			identityService.deleteIdentity(input.getIdentityUid());
			success = true;
		} catch (Exception e) {
			identityService.addLog(input.getIdentityUid(), fed.getId(),
					"/api/05/delete_identity", input, e.getMessage());
			throw e;
		}
		resultBean.setSuccess(success);
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), fed.getId(),
				"/api/05/delete_identity", input, null);
		return resultBean;
	}
	
	@PostMapping("/api/05/replace_identity")
	public IdentityBean replaceIdentity(@Valid @RequestBody ParametersBean input) 
			throws Unauthorized401Exception, UnprocessableEntity422Exception {
		IdentityBean resultBean = new IdentityBean();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String federationUid = authentication.getName();
		Federation fed = federationService.findFederationByUid(federationUid);
		
		//Verifica diritti scrittura
		if (!fed.getCanReplace()) {
			throw new Unauthorized401Exception("Non autorizzato");
		}
		try {
			identityService.replaceIdentity(input.getRedundantIdentityUid(), input.getFinalIdentityUid());
		} catch (Exception e) {
			identityService.addLog(input.getIdentityUid(), fed.getId(),
					"/api/05/replace_identity", input, e.getMessage());
			throw e;
		}
		//LOG
		if (input != null)
			identityService.addLog(input.getIdentityUid(), fed.getId(),
				"/api/05/replace_identity", input, null);
		return resultBean;
	}
	
}
