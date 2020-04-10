package ro.cburcea.playground.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;
import ro.cburcea.playground.filters.config.SimpleBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "customGenericFilterBean", urlPatterns = "/*")
public class CustomGenericFilterBean extends GenericFilterBean {

    private static final Logger LOG = LoggerFactory.getLogger(CustomGenericFilterBean.class);

    @Autowired
    private SimpleBean simpleBean; //injected

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        LOG.info("Request  {} : {}", req.getMethod(), req.getRequestURI());
        chain.doFilter(request, response);
        LOG.info("Response :{}", res.getContentType());
    }

    @Override
    public void destroy() {
        LOG.warn("Destructing filter :{}", this);
    }

}