package ro.cburcea.playground.springboot.runners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * mvn spring-boot:run -Dspring-boot.run.arguments=--customArgument=custom
 * mvn clean package spring-boot:repackage
 * <p>
 * Once we pass the development phase and we want to progress towards bringing our application to production, we need to package our application.
 * Unfortunately, if we are working with a jar package, the basic Maven package goal doesn't include any of the external dependencies.
 * To circumvent this limitation, we need to leverage the Maven Spring Boot plugin repackage goal to run our jar/war as a stand-alone application.
 */
@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SpringBootConsoleApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(SpringBootConsoleApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : CommandLineRunner");

        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }
    }

}