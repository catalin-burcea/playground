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

    @RequestMapping("/client/success")
    public String success() {
        String result = this.restTemplate.getForObject("http://" + clientName + "/backend", String.class);
        return "Server Response :: " + result;
    }

    @RequestMapping("/client/timeout")
    public String retry() {
        String result = this.restTemplate.getForObject("http://" + clientName + "/timeout", String.class);
        return "Server Response :: " + result;
    }
}
