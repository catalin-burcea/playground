package ro.cburcea.playground.springcloud.netflix.archaius.jdbc;

import com.netflix.config.DynamicConfiguration;
import com.netflix.config.FixedDelayPollingScheduler;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.config.sources.JDBCConfigurationSource;
import org.apache.commons.configuration.AbstractConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public AbstractConfiguration addApplicationPropertiesJdbcSource() {
        PolledConfigurationSource source = new JDBCConfigurationSource(dataSource,
                        "select distinct key, value from properties",
                        "key",
                        "value");
        return new DynamicConfiguration(source, new FixedDelayPollingScheduler());
    }
}
