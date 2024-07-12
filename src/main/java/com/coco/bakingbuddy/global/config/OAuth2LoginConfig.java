package com.coco.bakingbuddy.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
@Slf4j
@Configuration
@RequiredArgsConstructor
public class OAuth2LoginConfig {
    //    client-id: 1000199336931-ttf2apdad8hk3fs0m932qhkmt5ehmvc3.apps.googleusercontent.com
//    client-secret: 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
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    String secretId;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    String redirectUri;
    //    private final Oredirect-uriAuth2ClientProperties properties;
    private String baseUrl = "https://baking-buddy-image-6q5ymuc2ha-du.a.run.app";
//    private String baseUrl = "http://localhost:8080";

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
    }

    private ClientRegistration googleClientRegistration() {
//        OAuth2ClientProperties.Registration googleRegistration = this.properties.getRegistration().get("google");
        log.info("client={}", clientId);
        log.info("secretId={}", secretId);
        log.info("redirectUri={}", redirectUri);
        return ClientRegistration.withRegistrationId("google")
                .clientId(clientId)
                .clientSecret(secretId)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
                .redirectUri(baseUrl + redirectUri)
                .scope("openid", "profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v4/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .clientName("Google")
                .build();
    }
}