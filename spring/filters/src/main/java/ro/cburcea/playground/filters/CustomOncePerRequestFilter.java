package ro.cburcea.playground.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.cburcea.playground.filters.config.SimpleBean;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * As of Servlet 3.0, a filter may be invoked as part of a REQUEST or ASYNC dispatches that occur in separate threads.
 * A filter can be configured in web.xml whether it should be involved in async dispatches. However, in some cases servlet containers assume different default configuration.
 * Therefore sub-classes can override the method shouldNotFilterAsyncDispatch() to declare statically if they should indeed be invoked, once,
 * during both types of dispatches in order to provide thread initialization, logging, security, and so on.
 * This mechanism complements and does not replace the need to configure a filter in web.xml with dispatcher types.
 */
@WebFilter(filterName = "customOncePerRequestFilter", urlPatterns = "/*", dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ERROR})
public class CustomOncePerRequestFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomOncePerRequestFilter.class);

    @Autowired
    private SimpleBean simpleBean; //injected

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        LOG.info("Request URI is: {}", httpServletRequest.getRequestURI());
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        LOG.info("Response Status Code is: {}", httpServletResponse.getStatus());
    }

    @Override
    protected boolean isAsyncDispatch(HttpServletRequest request) {
        return super.isAsyncDispatch(request);
    }

    /**
     * Subclasses may use isAsyncDispatch(HttpServletRequest) to determine when a filter is invoked as part of an async dispatch,
     * and use isAsyncStarted(HttpServletRequest) to determine when the request has been placed in async mode and therefore the current dispatch won't be the last one for the given request.
     */
    @Override
    protected boolean isAsyncStarted(HttpServletRequest request) {
        return super.isAsyncStarted(request);
    }
}