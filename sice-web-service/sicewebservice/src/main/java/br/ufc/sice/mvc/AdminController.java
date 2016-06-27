package br.ufc.sice.mvc;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import br.ufc.sice.oauth.SiceUserApprovalHandler;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AdminController {

	private ConsumerTokenServices tokenServices;

	private TokenStore tokenStore;

	private SiceUserApprovalHandler userApprovalHandler;

	@RequestMapping("/oauth/cache_approvals")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void startCaching() throws Exception {
		System.out.println("AdminController --- startCaching");
		userApprovalHandler.setUseApprovalStore(true);
	}

	@RequestMapping("/oauth/uncache_approvals")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void stopCaching() throws Exception {
		System.out.println("AdminController --- stopCaching");
		userApprovalHandler.setUseApprovalStore(false);
	}

	@RequestMapping("/oauth/clients/{client}/users/{user}/tokens")
	@ResponseBody
	public Collection<OAuth2AccessToken> listTokensForUser(@PathVariable String client, @PathVariable String user,
			Principal principal) throws Exception {
		System.out.println("AdminController --- listTokensForUser");
		checkResourceOwner(user, principal);
		return enhance(tokenStore.findTokensByClientIdAndUserName(client, user));
	}

	@RequestMapping(value = "/oauth/users/{user}/tokens/{token}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> revokeToken(@PathVariable String user, @PathVariable String token, Principal principal)
			throws Exception {
		System.out.println("AdminController --- revokeToken");
		checkResourceOwner(user, principal);
		if (tokenServices.revokeToken(token)) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping("/oauth/clients/{client}/tokens")
	@ResponseBody
	public Collection<OAuth2AccessToken> listTokensForClient(@PathVariable String client) throws Exception {
		System.out.println("AdminController --- listTokensForClient");
		return enhance(tokenStore.findTokensByClientId(client));
	}

	private Collection<OAuth2AccessToken> enhance(Collection<OAuth2AccessToken> tokens) {
		System.out.println("AdminController --- enhance");
		Collection<OAuth2AccessToken> result = new ArrayList<OAuth2AccessToken>();
		for (OAuth2AccessToken prototype : tokens) {
			DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(prototype);
			OAuth2Authentication authentication = tokenStore.readAuthentication(token);
			if (authentication == null) {
				continue;
			}
			String clientId = authentication.getOAuth2Request().getClientId();
			if (clientId != null) {
				Map<String, Object> map = new HashMap<String, Object>(token.getAdditionalInformation());
				map.put("client_id", clientId);
				token.setAdditionalInformation(map);
				result.add(token);
			}
		}
		return result;
	}

	private void checkResourceOwner(String user, Principal principal) {
		if (principal instanceof OAuth2Authentication) {
			System.out.println("AdminController --- checkResourceOwner");
			OAuth2Authentication authentication = (OAuth2Authentication) principal;
			if (!authentication.isClientOnly() && !user.equals(principal.getName())) {
				throw new AccessDeniedException(String.format("User '%s' cannot obtain tokens for user '%s'",
						principal.getName(), user));
			}
		}
	}

	/**
	 * @param userApprovalHandler the userApprovalHandler to set
	 */
	public void setUserApprovalHandler(SiceUserApprovalHandler userApprovalHandler) {
		System.out.println("AdminController --- setUserApprovalHandler");
		this.userApprovalHandler = userApprovalHandler;
	}

	/**
	 * @param tokenServices the consumerTokenServices to set
	 */
	public void setTokenServices(ConsumerTokenServices tokenServices) {
		System.out.println("AdminController --- setTokenServices");
		this.tokenServices = tokenServices;
	}

	/**
	 * @param tokenStore the tokenStore to set
	 */
	public void setTokenStore(TokenStore tokenStore) {
		System.out.println("AdminController --- setTokenStore");
		this.tokenStore = tokenStore;
	}

}