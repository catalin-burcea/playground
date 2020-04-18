package ro.cburcea.playground.springcloud.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    @Autowired
    MyService myService;

    @RequestMapping("/client/success")
    public String success() {
        return myService.success();
    }

    @RequestMapping("/client/timeout")
    public String retry() {
        return myService.retry();
    }
}
