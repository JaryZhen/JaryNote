package note.hc.zk;

import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.GroupProperty;
import com.hazelcast.zookeeper.ZookeeperDiscoveryProperties;
import com.hazelcast.zookeeper.ZookeeperDiscoveryStrategyFactory;

import java.util.Map;

/**
 * Created by Jary on 2018/1/10 0010.
 */
public class Server {
    public static void main(String[] args) {

        String zookeeperURL = "kafka1:2222";
        final int HAZELCAST_BASE_PORT = 9999;

        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.setProperty(GroupProperty.DISCOVERY_SPI_ENABLED, "true");
        config.getNetworkConfig().setPort(HAZELCAST_BASE_PORT);

        DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(new ZookeeperDiscoveryStrategyFactory());
        discoveryStrategyConfig.addProperty(ZookeeperDiscoveryProperties.ZOOKEEPER_URL.key(), zookeeperURL);
        discoveryStrategyConfig.addProperty(ZookeeperDiscoveryProperties.ZOOKEEPER_PATH.key(), "/hazelcast2");
        discoveryStrategyConfig.addProperty(ZookeeperDiscoveryProperties.GROUP.key(), "id");

        config.getNetworkConfig().getJoin().getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);

        HazelcastInstance instance =  Hazelcast.newHazelcastInstance(config);
        Map<Integer, String> mapCustomers = instance.getMap("customers");
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");
    }
}
