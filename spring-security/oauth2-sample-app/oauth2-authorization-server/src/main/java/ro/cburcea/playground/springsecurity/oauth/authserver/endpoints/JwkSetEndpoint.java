package ro.cburcea.playground.springsecurity.oauth.authserver.endpoints;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;


@FrameworkEndpoint
public class JwkSetEndpoint {

    public static final String RSA_KEY_1 = "rsa-key1";
    private KeyPair keyPair;

    JwkSetEndpoint(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).keyUse(KeyUse.SIGNATURE).keyID(RSA_KEY_1).build();
        return new JWKSet(key).toJSONObject();
    }
}