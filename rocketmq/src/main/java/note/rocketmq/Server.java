package note.rocketmq;

import org.apache.rocketmq.broker.BrokerController;
import org.apache.rocketmq.broker.BrokerStartup;
import org.apache.rocketmq.common.BrokerConfig;

/**
 * @Author: Jary
 * @Date: 2021/5/19 11:43 AM
 */
public class Server {
    public static void main(String[] args) {
        BrokerStartup startup = new BrokerStartup();
        BrokerController ser = new BrokerController();

        BrokerConfig config = BrokerStartup.createBrokerController()
        startup
        config.setBrokerId();

        Serve

    }
}
