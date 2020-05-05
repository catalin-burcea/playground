package ro.cburcea.playground.springcloud.ribbon.config;

/**
 * A default configuration can be provided for all Ribbon Clients by using the @RibbonClients annotation and registering a default configuration
 */
//@RibbonClients(defaultConfiguration = DefaultRibbonConfig.class)
//public class RibbonClientDefaultConfigurationTestsConfig {
//
//    public static class DefaultServiceList extends ConfigurationBasedServerList {
//
//        public DefaultServiceList(IClientConfig config) {
//            super.initWithNiwsConfig(config);
//        }
//
//    }
//
//}
//
//@Configuration
//class DefaultRibbonConfig {
//
//    @Bean
//    public IRule ribbonRule() {
//        return new BestAvailableRule();
//    }
//
//    @Bean
//    public IPing ribbonPing() {
//        return new PingUrl();
//    }
//
//    @Bean
//    public ServerList<Server> ribbonServerList(IClientConfig config) {
//        return new RibbonClientDefaultConfigurationTestsConfig.DefaultServiceList(config);
//    }
//
//    @Bean
//    public ServerListSubsetFilter serverListFilter() {
//        return new ServerListSubsetFilter();
//    }
//
//}