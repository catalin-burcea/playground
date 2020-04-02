package ro.cburcea.playground.springboot.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

public class MySecondCompositeHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.down().build();
    }

}