package note.kafka;


public class KafkaProperties {
    //public static final String HOSTNAME = "192.168.1.55";
    public static final String HOSTNAME = "localhost"; //

    public static final int ZK_PORT = 22822;

    public static final String ZOOKEEPER_CONNECT = HOSTNAME + ":" + ZK_PORT;
    public static final int BROKER_ID = 1;

    public static final String TOPIC = "timeline-router-log1";
    public static final String TOPIC_V11_S = "s2";
    public static final String TOPIC_a ="user_platform_stage_3306";


    public static final int KAFKA_SERVER_PORT = 9088;
    public static final String BOOTSTRAP_SERVERS=HOSTNAME+ ":" +KAFKA_SERVER_PORT;
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";
    public static final String TOPIC_r = "test";
    public static final String BOOTSTRAP_SERVERS_REMOTE="172.24.4.184"+ ":" +KAFKA_SERVER_PORT;

    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 6 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC_vk = "app_inline_room_anticipation_ft_sql_test";

    private KafkaProperties() {}


}
