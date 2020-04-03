package ro.cburcea.playground.springboot.admin.server;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.notify.LoggingNotifier;
import de.codecentric.boot.admin.server.notify.RemindingNotifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@Configuration
public class LoggingNotifierConfiguration {
    private final InstanceRepository repository;

    public LoggingNotifierConfiguration(InstanceRepository repository) {
        this.repository = repository;
    }

    @Bean
    public LoggingNotifier notifier() {
        return new LoggingNotifier(repository);
    }

    @Primary
    @Bean(initMethod = "start", destroyMethod = "stop")
    public RemindingNotifier remindingNotifier() {
        RemindingNotifier remindingNotifier = new RemindingNotifier(notifier(), repository);
        remindingNotifier.setReminderPeriod(Duration.ofSeconds(10));
        remindingNotifier.setCheckReminderInverval(Duration.ofSeconds(15));
        return remindingNotifier;
    }
}