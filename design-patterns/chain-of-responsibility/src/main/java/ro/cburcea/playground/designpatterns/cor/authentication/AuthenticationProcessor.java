package ro.cburcea.playground.designpatterns.cor.authentication;

public abstract class AuthenticationProcessor {

    // next element in chain or responsibility
    public AuthenticationProcessor nextProcessor;

    public AuthenticationProcessor(AuthenticationProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract boolean isAuthorized(AuthenticationProvider authProvider);
}