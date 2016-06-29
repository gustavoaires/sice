package br.ufc.sice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import br.ufc.sice.oauth.SiceUserApprovalHandler;

@Configuration
public class OAuth2ServerConfig {

	private static final String SICE_RESOURCE_ID = "sice";

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			System.out.println("OAuth2ServerConfig --- ResourceServerConfiguration --- configure");
			resources.resourceId(SICE_RESOURCE_ID).stateless(false);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			System.out.println("OAuth2ServerConfig --- ResourceServerConfiguration --- configure");
			// @formatter:off
			http
				// Since we want the protected resources to be accessible in the UI as well we need 
				// session creation to be allowed (it's disabled by default in 2.0.6)
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//			.and()
				.requestMatchers().antMatchers("/user/**", "/oauth/users/**", "/oauth/clients/**", "/event")
			.and()
				.authorizeRequests()
//					.antMatchers("/me").access("#oauth2.hasScope('read')")
				//user
					.antMatchers("/user/insert").access("#oauth2.hasScope('trust') or (!#oauth2.isOAuth() and hasRole('ADMIN'))")
					.antMatchers("/user/load").access("#oauth2.hasScope('read') and (!#oauth2.isOAuth() and (hasRole('ADMIN') or hasRole('USER')))")
				//event
					.antMatchers("/event/insert").access("#oauth2.hasScope('trust') or (!#oauth2.isOAuth() and hasRole('ADMIN'))")
					.antMatchers("/event/insertweb").access("#oauth2.hasScope('trust') or (!#oauth2.isOAuth() and hasRole('ADMIN'))")
					.antMatchers("/event/load").access("#oauth2.hasScope('read') and (!#oauth2.isOAuth() and (hasRole('ADMIN') or hasRole('USER')))")
					.antMatchers("/event/load_filter").access("#oauth2.hasScope('read') and (!#oauth2.isOAuth() and (hasRole('ADMIN') or hasRole('USER')))")
					.antMatchers("/event/summary").access("#oauth2.hasScope('read') and (!#oauth2.isOAuth() and (hasRole('ADMIN') or hasRole('USER')))")
				//participation
					.antMatchers("/participation/insert").access("#oauth2.hasScope('trust') or (!#oauth2.isOAuth() and hasRole('ADMIN'))")
				//subevent
					.antMatchers("/subevent/insert").access("#oauth2.hasScope('trust') or (!#oauth2.isOAuth() and hasRole('ADMIN'))")
					.antMatchers("/subevent/insertweb").access("#oauth2.hasScope('trust') or (!#oauth2.isOAuth() and hasRole('ADMIN'))")
					.antMatchers("/subevent/load").access("#oauth2.hasScope('read') and (!#oauth2.isOAuth() and (hasRole('ADMIN') or hasRole('USER')))")
					.antMatchers("/subevent/get_participante").access("#oauth2.hasScope('trust') or (!#oauth2.isOAuth() and hasRole('ADMIN'))")
				//regex
					.regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
						.access("#oauth2.clientHasRole('ADMIN') and (hasRole('USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
					.regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
						.access("#oauth2.clientHasRole('ADMIN') and (hasRole('USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
					.regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
						.access("#oauth2.clientHasRole('ADMIN') and #oauth2.isClient() and #oauth2.hasScope('read')");
			// @formatter:on
		}

	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

		@Autowired
		private TokenStore tokenStore;

		@Autowired
		private UserApprovalHandler userApprovalHandler;

		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			System.out.println("OAuth2ServerConfig --- AuthorizationServerConfiguration --- configure");
			// @formatter:off
			clients.inMemory()
			        .withClient("my-trusted-client-with-secret")
			            .authorizedGrantTypes("password", /*"authorization_code",*/ "refresh_token", "implicit")
			            .authorities("ADMIN")
			            .scopes(/*"read", "write", */"trust")
			            .secret("sicesecret")
			           .accessTokenValiditySeconds(3000000)
			        .and()
		            .withClient("my-less-trusted-client")
			            .authorizedGrantTypes(/*"password", "authorization_code",*/ "implicit"/*, "refresh_token"*/)
			            .authorities("USER")
			            .scopes("read");
//					.withClient("sice")
//			 			.resourceIds(SICE_RESOURCE_ID)
//			 			.authorizedGrantTypes("authorization_code", "implicit")
//			 			.authorities("ROLE_CLIENT")
//			 			.scopes("read", "write")
//			 			.secret("secret")
//			 		.and()
//			 		.withClient("sice-with-redirect")
//			 			.resourceIds(SICE_RESOURCE_ID)
//			 			.authorizedGrantTypes("authorization_code", "implicit")
//			 			.authorities("ROLE_CLIENT")
//			 			.scopes("read", "write")
//			 			.secret("secret")
//			 			.redirectUris(tonrRedirectUri)
//			 		.and()
//		 		    .withClient("my-client-with-registered-redirect")
//	 			        .resourceIds(SICE_RESOURCE_ID)
//	 			        .authorizedGrantTypes("authorization_code", "client_credentials")
//	 			        .authorities("ROLE_CLIENT")
//	 			        .scopes("read", "trust")
//	 			        .redirectUris("http://anywhere?key=value")
//		 		    .and()
//	 		        .withClient("my-trusted-client")
// 			            .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
// 			            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
// 			            .scopes("read", "write", "trust")
// 			            .secret("sicesecret")
// 			            .accessTokenValiditySeconds(3000000)
//     		        .and()
//		            .withClient("my-less-trusted-autoapprove-client")
//		                .authorizedGrantTypes("implicit")
//		                .authorities("ROLE_CLIENT")
//		                .scopes("read", "write", "trust")
//		                .autoApprove(true);
			// @formatter:on
		}

		@Bean
		public TokenStore tokenStore() {
			System.out.println("OAuth2ServerConfig --- AuthorizationServerConfiguration --- tokenStore");
			return new InMemoryTokenStore();
		}

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			System.out.println("OAuth2ServerConfig --- AuthorizationServerConfiguration --- configure");
			endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler)
					.authenticationManager(authenticationManager);
		}

		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			System.out.println("OAuth2ServerConfig --- AuthorizationServerConfiguration --- configure");
			oauthServer.realm(SICE_RESOURCE_ID + "/client");
		}

	}

	protected static class Stuff {

		@Autowired
		private ClientDetailsService clientDetailsService;

		@Autowired
		private TokenStore tokenStore;

		@Bean
		public ApprovalStore approvalStore() throws Exception {
			System.out.println("OAuth2ServerConfig --- Stuff --- approvalStore");
			TokenApprovalStore store = new TokenApprovalStore();
			store.setTokenStore(tokenStore);
			return store;
		}

		@Bean
		@Lazy
		@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
		public SiceUserApprovalHandler userApprovalHandler() throws Exception {
			System.out.println("OAuth2ServerConfig --- Stuff --- userApprovalHandler");
			SiceUserApprovalHandler handler = new SiceUserApprovalHandler();
			handler.setApprovalStore(approvalStore());
			handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
			handler.setClientDetailsService(clientDetailsService);
			handler.setUseApprovalStore(true);
			return handler;
		}
	}

}