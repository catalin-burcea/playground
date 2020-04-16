package ro.cburcea.playground.springcloud.ribbon.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;

/**
 * A default configuration can be provided for all Ribbon Clients by using the @RibbonClients annotation and registering a default configuration
 */
//@RibbonClients(defaultConfiguration = DefaultRibbonConfig.class)
public class RibbonClientDefaultConfigurationTestsConfig {

    public static class BazServiceList extends ConfigurationBasedServerList {

        public BazServiceList(IClientConfig config) {
            super.initWithNiwsConfig(config);
        }

    }

}

//@Configuration
class DefaultRibbonConfig {

    @Bean
    public IRule ribbonRule() {
        return new BestAvailableRule();
    }

    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }

//    @Bean
//    public ServerList<Server> ribbonServerList(IClientConfig config) {
//        return new RibbonClientDefaultConfigurationTestsConfig.BazServiceList(config);
//    }

    @Bean
    public ServerListSubsetFilter serverListFilter() {
        return new ServerListSubsetFilter();
    }

}