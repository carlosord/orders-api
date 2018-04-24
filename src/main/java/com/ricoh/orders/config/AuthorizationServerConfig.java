package com.ricoh.orders.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	static final String CLIEN_ID = "client";
	static final String CLIENT_SECRET = "secret";
	static final String GRANT_TYPE_PASSWORD = "password";
	static final String AUTHORIZATION_CODE = "authorization_code";
    static final String IMPLICIT = "implicit";
	static final String SCOPE_READ = "read";
	static final String SCOPE_WRITE = "write";
	
	@Value("${security.oauth.client_id}")
	private String client_id;
	
    @Value("${security.oauth.secret}")
	private String secret;

	@Value("${security.oauth.grant_type_password}")
	private String grant_type_password;

	@Value("${security.oauth.grant_type_authorization_code}")
	private String grant_type_authorization_code;

	@Value("${security.oauth.grant_type_implicit}")
	private String grant_type_implicit;

	@Value("${security.oauth.scope_read}")
	private String scope_read;
	
	@Value("${security.oauth.scope_write}")
	private String scope_write;
	
	@Value("${security.oauth.signing_key}")
	private String signing_key;
	
	@Value("${security.oauth.access_token_validity}")
	private int access_token_validity;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter()).authenticationManager(authenticationManager);
	}
	
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient(client_id)
            .secret(secret)
            .authorizedGrantTypes(grant_type_password, grant_type_authorization_code, grant_type_implicit)
            .scopes(scope_read, scope_write)
            .accessTokenValiditySeconds(access_token_validity);
    }

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signing_key);
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(false);
		defaultTokenServices.setTokenEnhancer(accessTokenConverter());
		return defaultTokenServices;
	}
	
}
