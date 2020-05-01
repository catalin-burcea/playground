package ro.cburcea.playground.springsecurity.oauth.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FrameworkEndpoint
@DependsOn("authorizationServerConfiguration")
public class RevokeTokenEndpoint {

    @Autowired
//    private JwtTokenStore tokenStore;
    private InMemoryTokenStore tokenStore;

    @DeleteMapping("/oauth/token")
    @ResponseBody
    public void revokeToken(@RequestParam("accessToken") String accessToken) {
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(accessToken);

        tokenStore.removeAccessToken(oAuth2AccessToken);
        tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());

    }
}