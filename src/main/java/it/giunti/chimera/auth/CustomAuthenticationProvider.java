package it.giunti.chimera.auth;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import it.giunti.chimera.model.entity.Federation;
import it.giunti.chimera.service.FederationService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Qualifier("federationService")
	private FederationService federationService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();

		Federation fed = federationService.findFederationByUid(username);
		
		if (fed != null) {
			if (fed.getAccessKey().equals(password)) {
				return new UsernamePasswordAuthenticationToken(
						fed.getFederationUid(), fed.getAccessKey(), Collections.EMPTY_LIST);
			}
		}
		throw new BadCredentialsException("Invalid username or password");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
