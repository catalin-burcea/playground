package ro.cburcea.playground.spring.hikari;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartupRunner implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(AppStartupRunner.class);

    @Autowired
    private CityRepository cityRepository;

    @Override
    public void run(ApplicationArguments args) {
        LOG.info("EXECUTING : ApplicationRunner");
        LOG.info("City: {}", cityRepository.findByName("Bucharest").getName());
    }
}