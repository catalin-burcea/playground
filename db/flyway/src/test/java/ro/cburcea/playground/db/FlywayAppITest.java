package ro.cburcea.playground.db;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FlywayCallbackTestConfig.class)
public class FlywayAppITest {

    private static final Logger log = LoggerFactory.getLogger(FlywayAppITest.class);

    @Autowired
    private DataSource dataSource;

    @Test
    public void migrateWithNoCallbacks() {
        logTestBoundary("migrateWithNoCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration")
                .load();
        flyway.migrate();
    }

    @Test
    public void migrateWithJavaCallbacks() {
        logTestBoundary("migrateWithJavaCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration")
                .callbacks(new FlywayCallback())
                .load();
        flyway.migrate();
    }

    @Test
    public void migrateWithSqlCallbacks() {
        logTestBoundary("migrateWithSqlCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration", "db/callback")
                .load();
        flyway.migrate();
    }

    @Test
    public void migrateWithSqlAndJavaCallbacks() {
        logTestBoundary("migrateWithSqlAndJavaCallbacks");
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("db/migration", "db/callback")
                .callbacks(new FlywayCallback())
                .load();
        flyway.migrate();
    }

    private void logTestBoundary(String testName) {
        System.out.println("\n");
        log.info("> " + testName);
    }

}
