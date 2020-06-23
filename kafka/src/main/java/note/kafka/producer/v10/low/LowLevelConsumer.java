package note.kafka.producer.v10.low;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.message.MessageAndOffset;
import note.kafka.KafkaProperties;

import java.nio.ByteBuffer;
import java.util.*;

public class LowLevelConsumer {
    private List<String> m_replicaBrokers = new ArrayList<String>();

    public static void main(String args[]) {
        LowLevelConsumer example = new LowLevelConsumer();
        String topic = KafkaProperties.TOPIC;
        int partition = 0;
        List<String> hosts = new ArrayList<String>();
        hosts.add(LowProperties.HOSTNAME);

        int port = LowProperties.KAFKA_SERVER_PORT;
        try {
              /*SimpleConsumer(
                        val host: String,
                        val port: Int,
                        val soTimeout: Int,
                        val bufferSize: Int,
                        val clientId: String)
                        */
            example.run(hosts , port, topic, partition);
        } catch (Exception e) {
            System.out.println("Oops:" + e);
            e.printStackTrace();
        }
    }


    public LowLevelConsumer() {
        m_replicaBrokers = new ArrayList<String>();
    }

    public void run(List<String> hosts,  int a_port, String a_topic, int a_partition) throws Exception {
        // find the meta data about the topic and partition we are interested in
        //
        PartitionMetadata metadata = findLeader(hosts, a_port, a_topic, a_partition);
        System.out.println("1.ȡleader "+ metadata.toString());
        if (metadata == null) {
            System.out.println("Can't find metadata for Topic and Partition. Exiting");
            return;
        }
        if (metadata.leader() == null) {
            System.out.println("Can't find Leader for Topic and Partition. Exiting");
            return;
        }
        String leadBroker = metadata.leader().host();

        String clientName = "Client_" + a_topic + "_" + a_partition;
        System.out.println(clientName);
        System.out.println("---Broker----Partition---Leader");

        SimpleConsumer consumer = new SimpleConsumer(leadBroker, a_port, 100000, 64 * 1024, clientName);
        long readOffset = getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.EarliestTime(), clientName);
        System.out.println("---topic -- partition--offerset--"+readOffset);

        long a_maxReads = 10000L;
        int numErrors = 0;
        while (a_maxReads > 0) {
            if (consumer == null) {
                consumer = new SimpleConsumer(leadBroker, a_port, 100000, 64 * 1024, clientName);
            }
            FetchRequest req = new FetchRequestBuilder()
                    .clientId(clientName)
                    .addFetch(a_topic, a_partition, readOffset, 100000) // Note: this fetchSize of 100000 might need to be increased if large batches are written to Kafka
                    .build();
            FetchResponse fetchResponse = consumer.fetch(req);

            // if get the data from this fech
            if (fetchResponse.hasError()) {
                numErrors++;
                // Something went wrong!
                short code = fetchResponse.errorCode(a_topic, a_partition);
                System.out.println("Error fetching data from the Broker:" + leadBroker + " Reason: " + code);
                if (numErrors > 5) break;
                if (code == ErrorMapping.OffsetOutOfRangeCode()) {
                    // We asked for an invalid offset. For simple case ask for the last element to reset
                    readOffset = getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.LatestTime(), clientName);
                    continue;
                }
                consumer.close();
                consumer = null;
                leadBroker = findNewLeader(leadBroker, a_topic, a_partition, a_port);
                continue;
            }
            numErrors = 0;

            long numRead = 0;
            System.out.println("- topic ---ĳһpartion----");
            for (MessageAndOffset messageAndOffset : fetchResponse.messageSet(a_topic, a_partition)) {
                long currentOffset = messageAndOffset.offset();
                if (currentOffset < readOffset) {
                    System.out.println("Found an old offset: " + currentOffset + " Expecting: " + readOffset);
                    continue;
                }
                readOffset = messageAndOffset.nextOffset();
                ByteBuffer payload = messageAndOffset.message().payload();

                byte[] bytes = new byte[payload.limit()];
                payload.get(bytes);
                System.out.println(String.valueOf(messageAndOffset.offset()) + ": " + new String(bytes, "UTF-8"));
                numRead++;
                a_maxReads--;
            }

            if (numRead == 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        }
        if (consumer != null) consumer.close();
    }


    public static long getLastOffset(SimpleConsumer consumer,
                                     String topic,
                                     int partition,
                                     long whichTime,
                                     String clientName) {
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();

        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));

        kafka.javaapi.OffsetRequest request = new kafka.javaapi.OffsetRequest(
                requestInfo,
                kafka.api.OffsetRequest.CurrentVersion(),
                clientName);

        OffsetResponse response = consumer.getOffsetsBefore(request);

        if (response.hasError()) {
            System.out.println("Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partition));
            return 0;
        }
        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }

    private String findNewLeader(String host, String a_topic, int a_partition, int a_port) throws Exception {
        for (int i = 0; i < 3; i++) {
            boolean goToSleep = false;
            PartitionMetadata metadata = findLeader(m_replicaBrokers, a_port, a_topic, a_partition);
            if (metadata == null) {
                goToSleep = true;
            } else if (metadata.leader() == null) {
                goToSleep = true;
            } else if (host.equalsIgnoreCase(metadata.leader().host()) && i == 0) {
                // first time through if the leader hasn't changed give ZooKeeper a second to recover
                // second time, assume the broker did recover before failover, or it was a non-Broker issue
                //
                goToSleep = true;
            } else {
                return metadata.leader().host();
            }
            if (goToSleep) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        }
        System.out.println("Unable to find new leader after Broker failure. Exiting");
        throw new Exception("Unable to find new leader after Broker failure. Exiting");
    }

    // -Broker-Partition---Leader
    private PartitionMetadata findLeader(List<String> hosts, int a_port, String a_topic, int a_partition) {
        PartitionMetadata returnMetaData = null;
        loop:
        for (String host : hosts) {
            SimpleConsumer consumer = null;
            try {

                consumer = new SimpleConsumer(host, a_port, 100000, 64 * 1024, "leaderLookup");
                List<String> topics = Collections.singletonList(a_topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);

                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        if (part.partitionId() == a_partition) {
                            returnMetaData = part;
                            break loop;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error communicating with Broker [" + host + "] to find Leader for [" + a_topic
                        + ", " + a_partition + "] Reason: " + e);
            } finally {
                if (consumer != null) consumer.close();
            }
        }
        if (returnMetaData != null) {
            m_replicaBrokers.clear();
            for (kafka.cluster.BrokerEndPoint replica : returnMetaData.replicas()) {
                m_replicaBrokers.add(replica.host());
            }
        }
        return returnMetaData;
    }

    public static class LowProperties {
        //public static final String HOSTNAME = "192.168.1.55";
        public static final String HOSTNAME = "localhost";

        public static final int ZK_PORT = 2222;

        public static final String ZOOKEEPER_CONNECT = HOSTNAME + ":" + ZK_PORT;

        public static final int BROKER_ID = 1;

        public static final String TOPIC = "paser1";
        public static final int KAFKA_SERVER_PORT = 9092;
        public static final String BOOTSTRAP_SERVERS=HOSTNAME+ ":" +KAFKA_SERVER_PORT;
        public static final String CLIENT_ID = "SimpleConsumerDemoClient";
        public static final String TOPIC_r = "test";
        public static final String BOOTSTRAP_SERVERS_REMOTE="172.24.4.184"+ ":" +KAFKA_SERVER_PORT;
        public static final int KAFKA_PRODUCER_BUFFER_SIZE = 6 * 1024;
        public static final int CONNECTION_TIMEOUT = 100000;
        public static final String TOPIC2 = "topic2";
        public static final String TOPIC3 = "topic3";

        private LowProperties() {}


    }
}
