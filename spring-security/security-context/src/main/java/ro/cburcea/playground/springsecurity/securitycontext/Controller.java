package ro.cburcea.playground.springsecurity.securitycontext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class Controller {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @GetMapping("/username1")
    public String username1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return String.format("hello %s from secured endpoint1", currentPrincipalName);
    }

    @GetMapping("/username2")
    public String username2(Principal principal) {
        return String.format("hello %s from secured endpoint2", principal.getName());
    }

    @GetMapping("/username3")
    public String username3(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return String.format("hello %s from secured endpoint3. Your authorities: %s", authentication.getName(), userDetails.getAuthorities());
    }

    @GetMapping("/username4")
    public String username4(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return String.format("hello %s from secured endpoint4", principal.getName());
    }

    @GetMapping("/username5")
    public String username5() {
        return String.format("hello %s from secured endpoint5", authenticationFacade.getAuthentication().getName());
    }

    //Annotation that is used to resolve {@link Authentication#getPrincipal()} to a method argument.
    @GetMapping("/username6")
    public String username6(@AuthenticationPrincipal Principal principal) {
        return String.format("hello %s from secured endpoint6", principal.getName());
    }

}
