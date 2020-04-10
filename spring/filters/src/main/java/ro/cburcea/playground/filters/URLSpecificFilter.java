package ro.cburcea.playground.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ro.cburcea.playground.filters.config.SimpleBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class URLSpecificFilter implements Filter {

    @Autowired
    private SimpleBean simpleBean; //null

    private static final Logger LOG = LoggerFactory.getLogger(URLSpecificFilter.class);

    @Override
    public void init(final FilterConfig filterConfig) {
        LOG.info("Initializing url-specific filter :{}", this);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        LOG.info("URL-specific filter - Logging Request {} : {}", req.getMethod(), req.getRequestURI());
        chain.doFilter(request, response);
        LOG.info("URL-specific filter - Logging Response :{}", res.getContentType());
    }

    @Override
    public void destroy() {
        LOG.warn("Destructing url-specific filter :{}", this);
    }

}