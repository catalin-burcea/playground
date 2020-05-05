package ro.cburcea.playground.springcloud.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MyRestController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String WEATHER_SERVICE = "weather-service";

    @RequestMapping("/client/success")
    public String success() {
        String result = this.restTemplate.getForObject("http://" + WEATHER_SERVICE + "/backend", String.class);
        return "Server Response :: " + result;
    }

    @RequestMapping("/client/weather")
    public String weather() {
        String result = this.restTemplate.getForObject("http://" + WEATHER_SERVICE + "/weather", String.class);
        return "Weather Service Response: " + result;
    }
}
