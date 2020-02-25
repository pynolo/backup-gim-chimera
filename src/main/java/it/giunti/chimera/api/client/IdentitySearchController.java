package it.giunti.chimera.api.client;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.giunti.chimera.exception.NotFound404Exception;
import it.giunti.chimera.model.entity.Identity;
import it.giunti.chimera.service.IdentityService;

@RestController
@CrossOrigin(origins = "*")
public class IdentitySearchController {

	@Autowired
	@Qualifier("identityService")
	private IdentityService identityService;

	@PostMapping("/api/client/search_identity")
	public List<IdentitySearchBean> searchIdentity(@Valid @RequestBody SearchBean input) 
			throws NotFound404Exception {
		if (input == null) throw new NotFound404Exception("Empty search params");
		
		List<Identity> iList = identityService.findIdentityByProperties(
				input.getIdentityUid(),
				input.getEmail(),
				input.getLastName(),
				input.getFirstName(), 
				input.getAddress(),
				input.getProvId(),
				input.getZip(),
				input.getPhone(),
				input.getCodiceFiscale(),
				input.getPartitaIva());
		List<IdentitySearchBean> beanList = new ArrayList<IdentitySearchBean>();
		for (Identity i:iList) {
			IdentitySearchBean isb = new IdentitySearchBean(i);
			beanList.add(isb);
		}
		return beanList;
	}
	
}
