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

import it.giunti.chimera.api05.bean.ErrorBean;
import it.giunti.chimera.api05.bean.FederationBean;
import it.giunti.chimera.api05.bean.IdentityFinderBean;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.srvc.FederationSrvc;

@RestController
@CrossOrigin(origins = "*")
public class FederationController {
	
	@Autowired
	@Qualifier("federationSrvc")
	private FederationSrvc federationSrvc;
	
	@PostMapping("/api05/find_services")
	public List<FederationBean> findServices(@Valid @RequestBody IdentityFinderBean input) {
		List<FederationBean> beanList = new ArrayList<FederationBean>();
		ErrorBean error = federationSrvc.checkAccessKeyAndNull(input);
		if (error == null) {
			List<Federation> fedList = federationSrvc.findAllFederations();
			for (Federation fed:fedList) {
				FederationBean bean = new FederationBean();
				bean.setName(fed.getName());
				bean.setFederationUid(fed.getFederationUid());
				beanList.add(bean);
			}
			return beanList;
		}
		FederationBean bean = new FederationBean();
		bean.setError(error);
		return beanList;
	}
	
//	@PostMapping("/api05/find_changed_identities")
//	public IdentityBean findChangedIdentities(@Valid @RequestBody IdentityFinderBean input) {
//
//	}

}
