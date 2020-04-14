package ro.playground.netflix.eureka.serviceclient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Profile("rest-template")
@RestController
public class RestTemplateController {

    private static final String TARGET_SERVICE = "spring-cloud-eureka-client";
    private static final String TARGET_SERVICE_URL_FORMAT = "http://%s:%s/greeting";

    @Autowired
    private EurekaClient eurekaClient;

    @RequestMapping("/greeting")
    public String greeting() {

        Application application = eurekaClient.getApplication(TARGET_SERVICE);
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String hostname = instanceInfo.getHostName();
        int port = instanceInfo.getPort();
        Map<String, String> metadata = instanceInfo.getMetadata();

        RestTemplate restTemplate = new RestTemplate();
        final String url = String.format(TARGET_SERVICE_URL_FORMAT, hostname, port);
        System.out.println("Eureka Client URL: " + url);

        return restTemplate.getForObject(url, String.class);
    }
}
