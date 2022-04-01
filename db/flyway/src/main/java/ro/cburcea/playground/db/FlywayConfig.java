package ro.cburcea.playground.db;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public Flyway flyway() {
        final Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration")
                .callbacks(new FlywayCallback())
                .load();
        flyway.migrate();
        return flyway;
    }

}