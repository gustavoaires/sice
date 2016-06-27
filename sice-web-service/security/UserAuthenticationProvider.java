package br.ufc.sice.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private AuthProxy proxy;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		boolean result = proxy.isValidUser(authentication.getPrincipal().toString(),
				authentication.getCredentials().toString());
		System.out.println("USERAUTHENTICATIONPROVIDER");
		if (result) {
			List<GrantedAuthority> grantedAuthorities =	new ArrayList<GrantedAuthority>();
			UserAuthenticationToken auth = 
					new UserAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(),
					grantedAuthorities);

			return auth;
		} else {
			throw new BadCredentialsException("Bad User Credentials.");
		}
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
