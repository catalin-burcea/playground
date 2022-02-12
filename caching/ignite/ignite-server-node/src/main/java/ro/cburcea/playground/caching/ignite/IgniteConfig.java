package ro.cburcea.playground.caching.ignite;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@Slf4j
public class IgniteConfig {

    @Bean(destroyMethod = "close")
    public Ignite igniteInstance(IgniteConfiguration igniteConfiguration) {
        Ignite ignite = Ignition.start(igniteConfiguration);
        ignite.cluster().state(ClusterState.ACTIVE);
        ignite.cluster().baselineAutoAdjustEnabled(true);
        ignite.cluster().baselineAutoAdjustTimeout(30000);
        ignite.log().info("Ignite server node started!");
        return ignite;
    }

    @Bean
    public IgniteConfiguration igniteConfiguration() {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setClientMode(false);
        igniteConfiguration.setLifecycleBeans(new IgniteLifecycleBean());
        igniteConfiguration.setGridLogger(new Slf4jLogger());

        TcpDiscoverySpi spi = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Collections.singletonList("localhost"));

        spi.setIpFinder(ipFinder);

        igniteConfiguration.setDiscoverySpi(spi);
        return igniteConfiguration;
    }

    private DataStorageConfiguration dataStorageConfiguration() {
        DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
        DataRegionConfiguration dataRegionConfiguration = new DataRegionConfiguration();
        dataRegionConfiguration.setPersistenceEnabled(true);
        return dataStorageConfiguration;
    }

}
