package ro.cburcea.playground.springsecurity.securitycontext;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
