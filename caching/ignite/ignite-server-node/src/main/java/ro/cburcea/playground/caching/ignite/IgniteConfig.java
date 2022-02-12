package ro.cburcea.playground.caching.ignite;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.*;
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
        igniteConfiguration.setIgniteInstanceName("my-ignite-server-node");

        TcpDiscoverySpi spi = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Collections.singletonList("localhost"));

        spi.setIpFinder(ipFinder);

        igniteConfiguration.setDiscoverySpi(spi);
        igniteConfiguration.setDataStorageConfiguration(dataStorageConfiguration());
        igniteConfiguration.setCacheConfiguration(myCache());

        return igniteConfiguration;
    }

    private DataStorageConfiguration dataStorageConfiguration() {
        DataStorageConfiguration storageCfg = new DataStorageConfiguration();

        storageCfg.setWalMode(WALMode.FSYNC);
        storageCfg.setWalSegmentSize(128 * 1024 * 1024);


        DataRegionConfiguration defaultRegion = new DataRegionConfiguration();
        defaultRegion.setName("Default_Region");
        defaultRegion.setInitialSize(10 * 1024 * 1024);
        defaultRegion.setMaxSize(100 * 1024 * 1024);

        storageCfg.setDefaultDataRegionConfiguration(defaultRegion);

        DataRegionConfiguration region2 = new DataRegionConfiguration();
        region2.setName("region2");
        region2.setInitialSize(20 * 1024 * 1024);
        region2.setMaxSize(40 * 1024 * 1024);
        region2.setPageEvictionMode(DataPageEvictionMode.RANDOM_2_LRU);
        region2.setPersistenceEnabled(true);

        storageCfg.setDataRegionConfigurations(region2);

        return storageCfg;
    }

    private CacheConfiguration myCache() {
        CacheConfiguration<Integer, Integer> myCache = new CacheConfiguration<>("myCache");
        myCache.setCacheMode(CacheMode.PARTITIONED);
        myCache.setBackups(2);
        myCache.setDataRegionName("region2");

        return myCache;
    }

}
