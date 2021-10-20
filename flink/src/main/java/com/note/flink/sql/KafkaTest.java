package com.note.flink.sql;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.module.ModuleManager;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertThat;


/**
 * @Author: Jary
 * @Date: 2021/10/18 2:39 下午
 */
public class KafkaTest {
    private static final Logger LOG = LoggerFactory.getLogger(ModuleManager.class);

    static String source_kafka = "CREATE TABLE kafkaSource ("
            + "  `timestamp-type` STRING METADATA VIRTUAL,"
            + "  `timestamp` TIMESTAMP(3) METADATA,"
            + "  `leader-epoch` INT METADATA VIRTUAL,"
            + "  `headers` MAP<STRING, BYTES> METADATA,"
            + "  `partition` INT METADATA VIRTUAL,"
            + "  `topic` STRING METADATA VIRTUAL,"
            + "   `offset` BIGINT METADATA VIRTUAL,"
            + "  role STRING,"
            + "  user_type STRING,"
            + "  local_ping INT,"
            + "  line INT,"
            + "  server_rtt INT,"
            + "  classroom STRING,"
            + "  full_ping STRING,"
            + " `ts` timestamp(3)"
           // + "  WATERMARK FOR `ts` AS `ts` - INTERVAL '1' SECOND"
            + ") WITH (" +
            "  'connector' = 'kafka'," +
            "  'topic' = 'app_inline_room_anticipation_ft_sql_test'," +
            "  'properties.bootstrap.servers' = 'localhost:9092'," +
            "  'properties.group.id' = 'testGroup'," +
            "  'scan.startup.mode' = 'latest-offset'," +
            "  'format' = 'json'"
            + ")";

    static String sink_kafka = "CREATE TABLE kafkaSink ("
            // + " `offset` BIGINT,"
            + "  role STRING,"
            + "  user_type STRING,"
            + "  local_ping INT,"
            + "  line INT,"
            + "  server_rtt INT,"
            + "  classroom STRING,"
            + "  full_ping STRING,"
            + "  `ts` timestamp(3)"
            + ") WITH (" +
            "  'connector' = 'kafka'," +
            "  'topic' = 'app_inline_room_anticipation_ft_sql_test_sink'," +
            "  'properties.bootstrap.servers' = 'localhost:9092',"
            + " 'format' = 'json'"
            + ")";

    static String sink_kafka_Prim = "CREATE TABLE kafkaSink ("
            // + " `offset` BIGINT,"
            + "  classroom STRING,"
            + "  total bigint,"
            + "  PRIMARY KEY (classroom) NOT ENFORCED"
            + ") WITH (" +
            "  'connector' = 'upsert-kafka'," +
            "  'topic' = 'app_inline_room_anticipation_ft_sql_test_sink'," +
            "  'properties.bootstrap.servers' = 'localhost:9092',"
            + " 'key.format' = 'json',"
            + " 'value.format' = 'json'"
            + ")";

    static String sink_print =
            "CREATE TABLE kafkaSink ( role STRING, " +
                    " user_type STRING, " +
                    " local_ping INT, " +
                    " line INT,  server_rtt INT, " +
                    " classroom STRING, " +
                    " full_ping STRING, " +
                    " `ts` timestamp(3)" +
                    ") WITH ( " +
                    " 'connector' = 'print'" +
                    ")";

    static String insert = " insert into kafkaSink " +
            "select role, user_type, local_ping, line, server_rtt, classroom, full_ping, ts " +
            "from kafkaSource ";

    static String insert_kafka_Prim =
            " insert into kafkaSink " +
            "select classroom, count(*) as total " +
            "from kafkaSource"
            + " group by classroom";

    static StreamExecutionEnvironment env;
    static StreamTableEnvironment tEnv;

    public static void main(String[] args) throws Exception {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        tEnv = StreamTableEnvironment.create(env);
        LOG.info("asdfasdfa");
        System.out.println(source_kafka);
        System.out.println(sink_print);
        System.out.println(insert);
        test1();
    }

    public static void test1() throws Exception {
        tEnv.executeSql(source_kafka);
        tEnv.executeSql(sink_kafka_Prim);
        tEnv.executeSql(insert_kafka_Prim);
        //env.execute();

    }
}
