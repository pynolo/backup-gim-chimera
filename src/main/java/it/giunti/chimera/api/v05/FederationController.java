package it.giunti.chimera.api.v05;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import it.giunti.chimera.api.v05.bean.ChangedIdentitiesBean;
import it.giunti.chimera.api.v05.bean.FederationListBean;
import it.giunti.chimera.api.v05.bean.IdentityBean;
import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.mvc.UnprocessableEntity422Exception;
import it.giunti.chimera.service.FederationService;

@RestController
@CrossOrigin(origins = "*")
public class FederationController {
	
	@Autowired
	@Qualifier("federationService")
	private FederationService federationService;
	
	@Autowired
	@Qualifier("converter05Service")
	private Converter05Service converter05Service;
	
	@GetMapping("/api/05/find_federations")
	public FederationListBean findServices() {
		FederationListBean resultBean = new FederationListBean();
		//Verifica accessKey
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
	
	@GetMapping("/api/05/find_changed_identities/{startTimestamp}")
	public ChangedIdentitiesBean findChangedIdentities(@PathVariable(value = "startTimestamp") String startTimestamp) 
			throws UnprocessableEntity422Exception {
		String currentTimestamp = new Long(new Date().getTime()).toString();
		ChangedIdentitiesBean resultBean = new ChangedIdentitiesBean();
		
		if (startTimestamp != null) {
			try {
				Long start = Long.parseLong(startTimestamp);
				List<Identity> iList = federationService.findChangedIdentities(start);
				List<IdentityBean> beanList = new ArrayList<IdentityBean>();
				for (Identity identity:iList) {
					IdentityBean bean = converter05Service.toIdentityBean(identity);
					beanList.add(bean);
				}
				resultBean.setIdentities(beanList);
				resultBean.setCurrentTimestamp(currentTimestamp);
				return resultBean;
			} catch (NumberFormatException e) {
				throw new UnprocessableEntity422Exception("currentTimestamp non e' un numero");
			}
		}
		throw new UnprocessableEntity422Exception("currentTimestamp non ha un valore");
	}

}
