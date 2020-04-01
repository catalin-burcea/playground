package ro.cburcea.playground.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(3)
public class URLSpecificFilter implements Filter {

    private final static Logger LOG = LoggerFactory.getLogger(URLSpecificFilter.class);

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