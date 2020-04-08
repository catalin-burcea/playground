package ro.cburcea.playground.springsecurity.oauth.authserver.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The token info endpoint, also sometimes called the introspection endpoint, likely requires some kind of client authentication,
 * either Basic or Bearer. Generally speaking, the bearer token in the SecurityContext won’t suffice since that is tied to the user.
 * Instead, you’ll need to specify credentials that represent this client.
 *
 * By default, this will use Basic authentication, using the configured credentials, to authenticate against the token info endpoint.
 */
@FrameworkEndpoint
class IntrospectionEndpoint {

    private TokenStore tokenStore;

    IntrospectionEndpoint(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @PostMapping("/introspection")
    @ResponseBody
    public Map<String, Object> introspect(@RequestParam("token") String token) {
        OAuth2AccessToken accessToken = this.tokenStore.readAccessToken(token);
        Map<String, Object> attributes = new HashMap<>();
        if (accessToken == null || accessToken.isExpired()) {
            attributes.put("active", false);
            return attributes;
        }

        OAuth2Authentication authentication = this.tokenStore.readAuthentication(token);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        attributes.put("active", true);
        attributes.put("exp", accessToken.getExpiration().getTime());
        attributes.put("scope", accessToken.getScope().stream().collect(Collectors.joining(" ")));
        attributes.put("sub", authentication.getName());
        attributes.put("username", user.getUsername());

        return attributes;
    }
}
