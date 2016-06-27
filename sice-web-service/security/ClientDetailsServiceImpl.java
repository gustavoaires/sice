package br.ufc.sice.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

	private String id;
	private String secretKey;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		if (clientId.equals(id)) {
			List<String> authorizedGrantTypes = new ArrayList<String>();
			authorizedGrantTypes.add("password");
			authorizedGrantTypes.add("refresh_token");
			authorizedGrantTypes.add("client_credentials");
			System.out.println("CLIENTEDETAILS");
			BaseClientDetails clientDetails = new BaseClientDetails();
			clientDetails.setClientId(id);
			clientDetails.setClientSecret(secretKey);
			clientDetails.setAuthorizedGrantTypes(authorizedGrantTypes);

			return clientDetails;
		} else {
			throw new NoSuchClientException("No client recognized with id: " + clientId);
		}
	}
	
	public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getSecretKey() {
        return secretKey;
    }
 
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
