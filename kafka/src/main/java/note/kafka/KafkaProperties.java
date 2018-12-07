package note.kafka;


public class KafkaProperties {
    //public static final String HOSTNAME = "192.168.1.55";
    public static final String HOSTNAME = "localhost";

    public static final int ZK_PORT = 2188;

    public static final String ZOOKEEPER_CONNECT = HOSTNAME + ":" + ZK_PORT;
    public static final int BROKER_ID = 1;

    public static final String TOPIC = "sd_dby_ping";
    public static final String TOPIC_V11_S = "s2";
    public static final String TOPIC_a ="Suct_Data";
    public static final String TOPIC_vipkid ="qos_ping_history";

    public static final int PORT = 9092;
    public static final String BOOTSTRAP_SERVERS=HOSTNAME+ ":" + PORT;
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";
    public static final String TOPIC_r = "test";
    public static final String BOOTSTRAP_SERVERS_REMOTE=
                    "10.0.10.185" + ":" + PORT+"," +
                    "10.0.10.184" + ":" + PORT+"," +
                    "10.0.9.186" + ":" + PORT+"," +
                    "10.0.9.185" + ":" + PORT+"," +
                    "10.0.9.184" + ":" + PORT;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 6 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";

    private KafkaProperties() {}


}
