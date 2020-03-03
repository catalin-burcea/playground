package ro.cburcea.playground.springresilience4j.circuitbreaker;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class Resilience4JAutoConfigurationService {

    @Autowired
    private Resilience4JCircuitBreakerFactory cbFactory;

    @Autowired
    private MeterRegistry meterRegistry;

    public String remoteCallWithSuccess() {
        CircuitBreaker circuitBreaker = cbFactory.create("circuitbreaker1");
        Supplier<String> task = () -> "success message";

        return circuitBreaker.run(task);
    }

    public String remoteCallWithFallback() {
        CircuitBreaker circuitBreaker = cbFactory.create("circuitbreaker2");
        Supplier<String> task = () -> {
            throw new RuntimeException();
        };
        Function<Throwable, String> fallback = throwable -> "fallback message";

        return circuitBreaker.run(task, fallback);
    }

    public String remoteCallWithException() {
        CircuitBreaker circuitBreaker = cbFactory.create("circuitbreaker3");
        Supplier<String> task = () -> {
            throw new RuntimeException();
        };

        return circuitBreaker.run(task);
    }

    public String getMetrics() {
        StringBuffer result = new StringBuffer();
        meterRegistry.forEachMeter((meter) -> result.append(meter.getId()).append("</br>").append(meter.measure()).append("</br>").append("</br>"));
        return result.toString();
    }

}

