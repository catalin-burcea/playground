package ro.cburcea.playground.caching.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class IgniteConfig {

    @Bean(destroyMethod = "close")
    public Ignite igniteInstance(IgniteConfiguration igniteConfiguration) {
        Ignite ignite = Ignition.start(igniteConfiguration);
        ignite.cluster().state(ClusterState.ACTIVE);
        ignite.log().info("Ignite client node started!");
        return ignite;
    }

    @Bean
    public IgniteConfiguration igniteConfiguration() {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(true);
        igniteConfiguration.setLifecycleBeans(new IgniteLifecycleBean());
        igniteConfiguration.setGridLogger(new Slf4jLogger());

        TcpDiscoverySpi spi = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Collections.singletonList("localhost"));

        spi.setIpFinder(ipFinder);

        igniteConfiguration.setDiscoverySpi(spi);

        return igniteConfiguration;
    }

}
