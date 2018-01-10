package note.hc.zk;


import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.GroupProperty;
import com.hazelcast.zookeeper.ZookeeperDiscoveryProperties;
import com.hazelcast.zookeeper.ZookeeperDiscoveryStrategyFactory;

/**
 * Created by Jary on 2018/1/10 0010.
 */
public class Client {

    public static void main(String[] args) {
        String zookeeperURL = "localhost:2181";

        ClientConfig config = new ClientConfig();
        config.setProperty(GroupProperty.DISCOVERY_SPI_ENABLED, "true");
        config.getNetworkConfig().getAwsConfig().setEnabled(false);

        DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(new ZookeeperDiscoveryStrategyFactory());
        discoveryStrategyConfig.addProperty(ZookeeperDiscoveryProperties.ZOOKEEPER_URL.key(),zookeeperURL);
        discoveryStrategyConfig.addProperty(ZookeeperDiscoveryProperties.ZOOKEEPER_PATH.key(), "/hazelcast");
        discoveryStrategyConfig.addProperty(ZookeeperDiscoveryProperties.GROUP.key(), "{clusterId}");
        config.getNetworkConfig().getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);

        HazelcastInstance instance  = HazelcastClient.newHazelcastClient(config);

    }
}
