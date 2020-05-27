/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.merchanttransactionsync.config.security;


import com.etz.merchanttransactionsync.model.syncdb.ClientMerchantDetails;
import com.etz.merchanttransactionsync.model.CustomUser;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.etz.merchanttransactionsync.service.ClientMerchantDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 *
 * @author afam.okonkwo
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    
    @Value("${app.data.oauth.user.clientId}")
    private String clientId;
    @Value("${app.data.oauth.user.clientSecret}")
    private String clientSecret;
    @Value("${app.data.oauth.user.redirectUris}")
    private String redirectUris;
    
    @Value("${app.data.oauth.user.private.key}")
    private String privateKey;
    @Value("${app.data.oauth.user.public.key}")
    private String publicKey;

    @Value("${spring.third-datasource.url}")
    private String url;
    @Value("${spring.third-datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.third-datasource.username}")
    private String username;

    @Value("${spring.third-datasource.password}")
    private String password;

    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientMerchantDetailsService clientMerchantDetailsService;



    //@Qualifier("syncDbDataSource")
    final DataSource syncDbDataSource;

    public  OAuth2AuthorizationServer(final  @Qualifier("syncDbDataSource") DataSource dataSource) {
        this.syncDbDataSource =dataSource;
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer authServerConfigurer) {
        authServerConfigurer.passwordEncoder(this.passwordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated")
                .allowFormAuthenticationForClients()
                //.addTokenEndpointAuthenticationFilter(filter);
                ;
        log.info("ClientId::{}", clientId);
                
    }
    
    @Override
    public void configure(final ClientDetailsServiceConfigurer clientDetailsConfig) throws Exception {
        //ClientMerchantDetails clientMerchantDetails  = clientMerchantDetailsService.findClientMerchantDetails(clientId);
        clientDetailsConfig.jdbc(syncDbDataSource).passwordEncoder(passwordEncoder);
                /*
                .inMemory()
                .withClient(clientMerchantDetails.getClientId())
                //.secret(passwordEncoder.encode(clientSecret))
                .redirectUris(clientMerchantDetails.getWebServerRedirectUri())
                .resourceIds(clientMerchantDetails.getResourceIds())
                .authorizedGrantTypes(clientMerchantDetails.getAuthorizedGrantTypes().split(",")[0])
                .authorities(clientMerchantDetails.getAuthorities())
                .accessTokenValiditySeconds(clientMerchantDetails.getAccessTokenValidity())
                .refreshTokenValiditySeconds(clientMerchantDetails.getRefreshTokenValidity())
                .scopes(clientMerchantDetails.getScope());

                 */

    }
    
    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                CustomUser user = (CustomUser)authentication.getPrincipal();
                Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
                
                if(user.getId() != null)
                    info.put("id", user.getId());
                if(user.getUsername()!= null)
                    info.put("userName", user.getUsername());
                List<String> authorities = Arrays.asList("CREATE_USER","VIEW_USER","DELETE_USER");
                info.putIfAbsent("authorities", authorities);
                info.putIfAbsent("iss", "com.etranzact.com");
                DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
                customAccessToken.setAdditionalInformation(info);
                System.out.println("Token Mapped Info :: "+info + " token-types:: " + accessToken.getTokenType() + " grant-types:: " + authentication.getOAuth2Request().getGrantType());
                accessToken = super.enhance(customAccessToken, authentication);
                return accessToken;
            }
        };
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        
        return converter;
    }
    
    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endPoints) {
        endPoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer())
                /**
                .pathMapping("/oauth/token", "/auth/token")
                .exceptionTranslator(exception -> {
                    if (exception instanceof OAuth2Exception) {
                        OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
                        return ResponseEntity
                                .status(oAuth2Exception.getHttpErrorCode())
                                .body(oAuth2Exception);
                    }  else {
                        throw exception;
                    }
                })
                 **/
         ;
        
    }
    
    
}
