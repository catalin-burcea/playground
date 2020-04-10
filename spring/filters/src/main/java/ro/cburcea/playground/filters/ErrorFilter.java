package ro.cburcea.playground.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ro.cburcea.playground.filters.config.SimpleBean;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "errorFilter", urlPatterns = "/*", dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ERROR})
public class ErrorFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ErrorFilter.class);

    @Autowired
    private SimpleBean simpleBean; //injected

    @Override
    public void init(final FilterConfig filterConfig) {
        LOG.info("Initializing error filter :{}", this);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        LOG.info("Error filter: request:{}", req.getRequestURI());
        chain.doFilter(request, response);
        LOG.info("Error filter: response");
    }

    @Override
    public void destroy() {
        LOG.warn("Destructing error filter :{}", this);
    }
}