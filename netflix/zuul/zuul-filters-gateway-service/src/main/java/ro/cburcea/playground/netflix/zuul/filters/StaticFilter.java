package ro.cburcea.playground.netflix.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.filters.StaticResponseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Allows the generation of responses from Zuul itself, instead of forwarding the request to an origin.
 */
@Component
public class StaticFilter extends StaticResponseFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public Object uri() {
        return Pattern.compile("/static.*");
    }

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public String responseBody() {
        if (RequestContext.getCurrentContext().getRequest().getRequestURI().endsWith(".svg")) {

            RequestContext.getCurrentContext().getResponse().setContentType("image/svg+xml");

            try {
                InputStream inputStream = resourceLoader.getResource("classpath:static/example.svg").getInputStream();
                String result = new BufferedReader(new InputStreamReader(inputStream))
                        .lines()
                        .collect(Collectors.joining("\n"));
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }

        } else {

            return "Static content";

        }
    }
}