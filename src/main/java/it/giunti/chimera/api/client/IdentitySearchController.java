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

	@PostMapping("/api/client/searchidentity")
	public List<IdentityResultBean> searchIdentity(@Valid @RequestBody SearchBean input) 
			throws NotFound404Exception {
		if (input == null) throw new NotFound404Exception("Empty search params");
		
		List<Identity> iList = identityService.findIdentityByProperties(
				input.getMaxResults(),
				input.getIdentityUid(),
				input.getEmail(),
				input.getLastName(),
				input.getFirstName(), 
				input.getAddressStreet(),
				input.getAddressZip(),
				input.getAddressTown(),
				input.getAddressProvinceId(),
				input.getTelephone(),
				input.getCodiceFiscale(),
				input.getPartitaIva());
		List<IdentityResultBean> beanList = new ArrayList<IdentityResultBean>();
		for (Identity i:iList) {
			IdentityResultBean isb = new IdentityResultBean(i);
			beanList.add(isb);
		}
		return beanList;
	}
	
}
