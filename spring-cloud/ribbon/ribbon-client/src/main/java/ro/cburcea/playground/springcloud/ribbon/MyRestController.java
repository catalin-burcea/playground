package ro.cburcea.playground.springcloud.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MyRestController {

    @Autowired
    private RestTemplate restTemplate;

    private String clientName = "demoserver";

    @RequestMapping("/client/frontend")
    public String hi() {
        String randomString = this.restTemplate.getForObject("http://" + clientName + "/backend", String.class);
        return "Server Response :: " + randomString;
    }
}
