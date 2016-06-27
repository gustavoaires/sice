package br.ufc.sice.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -1092219614309982278L;
    private final Object principal;
    private Object credentials;
    
    public UserAuthenticationToken(Object principal, Object credentials,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
        System.out.println("USERAUTHENTICATIONTOKEN");
    }

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return this.credentials;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return this.principal;
	}

}
