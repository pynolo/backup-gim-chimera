package it.giunti.chimera.api05;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.giunti.chimera.ErrorEnum;
import it.giunti.chimera.api05.bean.AccessKeyValidationBean;
import it.giunti.chimera.api05.bean.ChangedIdentitiesBean;
import it.giunti.chimera.api05.bean.ErrorBean;
import it.giunti.chimera.api05.bean.FederationListBean;
import it.giunti.chimera.api05.bean.IdentityBean;
import it.giunti.chimera.api05.bean.ParametersBean;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.service.FederationService;

@RestController
@CrossOrigin(origins = "*")
public class FederationController {
	
	@Autowired
	@Qualifier("federationService")
	private FederationService federationService;
	
	@Autowired
	@Qualifier("converterApi05Srvc")
	private ConverterApi05Srvc converterApi05Srvc;
	
	@PostMapping("/api05/find_federations")
	public FederationListBean findServices(@Valid @RequestBody ParametersBean input) {
		FederationListBean resultBean = new FederationListBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			List<FederationListBean.FederationBean> beanList = 
					new ArrayList<FederationListBean.FederationBean>();
			List<Federation> fedList = federationService.findAllFederations();
			for (Federation fed:fedList) {
				FederationListBean.FederationBean bean = resultBean.new FederationBean();
				bean.setName(fed.getName());
				bean.setFederationUid(fed.getFederationUid());
				beanList.add(bean);
			}
			resultBean.setFederations(beanList);
			return resultBean;
		}
		resultBean.setError(error);
		return resultBean;
	}
	
	@PostMapping("/api05/find_changed_identities")
	public ChangedIdentitiesBean findChangedIdentities(@Valid @RequestBody ParametersBean input) {
		String currentTimestamp = new Long(new Date().getTime()).toString();
		ChangedIdentitiesBean resultBean = new ChangedIdentitiesBean();
		//Verifica accessKey
		AccessKeyValidationBean akBean = federationService.checkAccessKeyAndNull(input);
		ErrorBean error = akBean.getError();
		if (error == null) {
			if (input.getStartTimestamp() != null) {
				try {
					Long start = Long.parseLong(input.getStartTimestamp());
					List<Identity> iList = federationService.findChangedIdentities(start);
					List<IdentityBean> beanList = new ArrayList<IdentityBean>();
					for (Identity identity:iList) {
						IdentityBean bean = converterApi05Srvc.toIdentityBean(identity);
						beanList.add(bean);
					}
					resultBean.setIdentities(beanList);
					resultBean.setCurrentTimestamp(currentTimestamp);
					return resultBean;
				} catch (NumberFormatException e) {
					error = new ErrorBean();
					error.setCode(ErrorEnum.WRONG_PARAMETER_VALUE.getErrorCode());
					error.setMessage("currentTimestamp non e' un numero");
				}
			}
			error = new ErrorBean();
			error.setCode(ErrorEnum.EMPTY_PARAMETER.getErrorCode());
			error.setMessage("currentTimestamp non ha un valore");
		}
		resultBean.setError(error);
		return resultBean;
	}

}
