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

import it.giunti.chimera.api05.bean.ChangedIdentitiesBean;
import it.giunti.chimera.api05.bean.ErrorBean;
import it.giunti.chimera.api05.bean.FederationListBean;
import it.giunti.chimera.api05.bean.IdentityFinderBean;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.srvc.FederationSrvc;

@RestController
@CrossOrigin(origins = "*")
public class FederationController {
	
	@Autowired
	@Qualifier("federationSrvc")
	private FederationSrvc federationSrvc;
	
	@PostMapping("/api05/find_federations")
	public FederationListBean findServices(@Valid @RequestBody IdentityFinderBean input) {
		FederationListBean resultBean = new FederationListBean();
		ErrorBean error = federationSrvc.checkAccessKeyAndNull(input);
		if (error == null) {
			List<FederationListBean.FederationBean> beanList = 
					new ArrayList<FederationListBean.FederationBean>();
			List<Federation> fedList = federationSrvc.findAllFederations();
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
	public ChangedIdentitiesBean findChangedIdentities(@Valid @RequestBody IdentityFinderBean input) {
		String currentTimestamp = new Long(new Date().getTime()).toString();
		ChangedIdentitiesBean resultBean = new ChangedIdentitiesBean();
		resultBean.setCurrentTimestamp(currentTimestamp);
		//TODO
		return resultBean;
	}

}
