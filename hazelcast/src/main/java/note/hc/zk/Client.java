package note.hc.zk;


import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.Member;
import com.hazelcast.instance.GroupProperty;
import com.hazelcast.zookeeper.ZookeeperDiscoveryProperties;
import com.hazelcast.zookeeper.ZookeeperDiscoveryStrategyFactory;
import org.apache.curator.test.TestingServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Jary on 2018/1/10 0010.
 */
public class Client {

    public static void main(String[] args) {
        String zookeeperURL = "kafka1:2222";
        final int HAZELCAST_BASE_PORT = 9999;

        ClientConfig config = new ClientConfig();
        //config.getNetworkConfig().getAwsConfig().setEnabled(false);
        config.setProperty(GroupProperty.DISCOVERY_SPI_ENABLED, "true");
        List add = new ArrayList();
        add.add("localhost:"+HAZELCAST_BASE_PORT);

        config.getNetworkConfig().setAddresses(add);

        DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(new ZookeeperDiscoveryStrategyFactory());
        discoveryStrategyConfig.addProperty(ZookeeperDiscoveryProperties.ZOOKEEPER_URL.key(),zookeeperURL);
        discoveryStrategyConfig.addProperty(ZookeeperDiscoveryProperties.ZOOKEEPER_PATH.key(), "/hazelcast2");
        discoveryStrategyConfig.addProperty(ZookeeperDiscoveryProperties.GROUP.key(), "id");
        config.getNetworkConfig().getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);


            HazelcastInstance instance  = HazelcastClient.newHazelcastClient(config);
        IMap map = instance.getMap("customers");
        System.out.println("Map Size:" + map.size());

            Iterator<Member> ite  = instance.getCluster().getMembers().iterator();
            while (ite.hasNext()){

                Member m = ite.next();
                System.out.println(m.getAddress()+"  "+m.getSocketAddress().toString());

            }

    }

}
