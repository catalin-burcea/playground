package ro.cburcea.playground.designpatterns.cor;

import org.junit.jupiter.api.Test;
import ro.cburcea.playground.designpatterns.cor.authentication.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChainOfResponsibilityIntegrationTest {

    private static AuthenticationProcessor getChainOfAuthProcessor() {

        AuthenticationProcessor oAuthProcessor = new OAuthProcessor(null);
        return new UsernamePasswordProcessor(oAuthProcessor);
    }

    @Test
    public void givenOAuthProvider_whenCheckingAuthorized_thenSuccess() {
        AuthenticationProcessor authProcessorChain = getChainOfAuthProcessor();
        boolean isAuthorized = authProcessorChain.isAuthorized(new OAuthTokenProvider());
        assertTrue(isAuthorized);
    }

    @Test
    public void givenUsernamePasswordProvider_whenCheckingAuthorized_thenSuccess() {
        AuthenticationProcessor authProcessorChain = getChainOfAuthProcessor();
        boolean isAuthorized = authProcessorChain.isAuthorized(new UsernamePasswordProvider());
        assertTrue(isAuthorized);
    }

    @Test
    public void givenSamlAuthProvider_whenCheckingAuthorized_thenFailure() {
        AuthenticationProcessor authProcessorChain = getChainOfAuthProcessor();
        boolean isAuthorized = authProcessorChain.isAuthorized(new SamlAuthenticationProvider());
        assertTrue(!isAuthorized);
    }

}