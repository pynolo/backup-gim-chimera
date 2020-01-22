package it.giunti.chimera.api05;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.giunti.chimera.api05.IdentityController.IdentityInputBean;
import it.giunti.chimera.model.entity.IdentityService;

@RestController
@CrossOrigin(origins = "*")
public class FederationController {
	
	@Autowired
	@Qualifier("identityService")
	private IdentityService identityService;

	@PostMapping("/api05/find_changed_identities")
	public IdentityBean findChangedIdentities(@Valid @RequestBody IdentityInputBean input) {

	}

	@PostMapping("/api05/find_services")
	public IdentityBean findServices(@Valid @RequestBody IdentityInputBean input) {

	}
	
}
