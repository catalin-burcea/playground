package ro.cburcea.playground.caching.ignite;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cluster.ClusterState;
import org.apache.ignite.configuration.*;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.Collections;

@Configuration
@Slf4j
public class IgniteConfig {

    private static final String NATIVE_PERSISTENCE_REGION = "NativePersistenceRegion";
    private static final String DEFAULT_REGION = "DefaultRegion";

    @Bean(destroyMethod = "close")
    public Ignite igniteInstance(IgniteConfiguration igniteConfiguration) {
        Ignite ignite = Ignition.start(igniteConfiguration);
        ignite.cluster().state(ClusterState.ACTIVE);
        ignite.cluster().baselineAutoAdjustEnabled(true);
        ignite.cluster().baselineAutoAdjustTimeout(30000);

        CacheConfiguration<Integer, Integer> cacheTemplate = new CacheConfiguration<>("myCacheTemplate");
        cacheTemplate.setCacheMode(CacheMode.PARTITIONED);
        cacheTemplate.setBackups(1);
        ignite.addCacheConfiguration(cacheTemplate);

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

        setTcpDiscoverySpi(igniteConfiguration);
        igniteConfiguration.setDataStorageConfiguration(dataStorageConfiguration());
        igniteConfiguration.setCacheConfiguration(myCache());

        return igniteConfiguration;
    }

    private void setTcpDiscoverySpi(IgniteConfiguration igniteConfiguration) {
        TcpDiscoverySpi spi = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Collections.singletonList("localhost"));

        spi.setIpFinder(ipFinder);

        igniteConfiguration.setDiscoverySpi(spi);
    }

    private DataStorageConfiguration dataStorageConfiguration() {
        DataStorageConfiguration storageCfg = new DataStorageConfiguration();

        storageCfg.setWalMode(WALMode.FSYNC);
        storageCfg.setWalSegmentSize(128 * 1024 * 1024);

        DataRegionConfiguration defaultRegion = new DataRegionConfiguration();
        defaultRegion.setName(DEFAULT_REGION);
        defaultRegion.setInitialSize(10 * 1024 * 1024);
        defaultRegion.setMaxSize(100 * 1024 * 1024);

        storageCfg.setDefaultDataRegionConfiguration(defaultRegion);

        DataRegionConfiguration nativePersistenceDataRegion = new DataRegionConfiguration();
        nativePersistenceDataRegion.setName(NATIVE_PERSISTENCE_REGION);
        nativePersistenceDataRegion.setInitialSize(20 * 1024 * 1024);
        nativePersistenceDataRegion.setMaxSize(40 * 1024 * 1024);
        // by default, eviction is disabled and can lead to Out Of Memory Error
        nativePersistenceDataRegion.setPageEvictionMode(DataPageEvictionMode.RANDOM_2_LRU);
        nativePersistenceDataRegion.setPersistenceEnabled(true);

        storageCfg.setDataRegionConfigurations(nativePersistenceDataRegion);

        return storageCfg;
    }

    private CacheConfiguration myCache() {
        CacheConfiguration<Integer, Integer> myCache = new CacheConfiguration<>("myCache");

        myCache.setDataRegionName(NATIVE_PERSISTENCE_REGION);
        myCache.setRebalanceMode(CacheRebalanceMode.SYNC);
        myCache.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);

        myCache.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        myCache.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.FIVE_MINUTES));
        myCache.setEagerTtl(true);

        return myCache;
    }

}
