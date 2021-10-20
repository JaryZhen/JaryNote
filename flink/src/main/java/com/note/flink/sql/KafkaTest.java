package com.note.flink.sql;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.CloseableIterator;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;


/**
 * @Author: Jary
 * @Date: 2021/10/18 2:39 下午
 */
public class KafkaTest {
    static String ks = "CREATE TABLE kafkaSource ("
            + "  `timestamp-type` STRING METADATA VIRTUAL,"
            + "  `timestamp` TIMESTAMP(3) METADATA,"
            + "  `leader-epoch` INT METADATA VIRTUAL,"
            + "  `headers` MAP<STRING, BYTES> METADATA,"
            + "  `partition` INT METADATA VIRTUAL,"
            + "  `topic` STRING METADATA VIRTUAL,"
            + "     `offset` BIGINT METADATA VIRTUAL,"
            + "  role STRING,"
            + "  user_type STRING,"
            + "  local_ping INT,"
            + "  line INT,"
            + "  server_rtt INT,"
            + "  classroom STRING,"
            + "  full_ping STRING,"
            + " `timestamp` BIGINT"
            + ") WITH (" +
            "  'connector' = 'kafka'," +
            "  'topic' = 'app_inline_room_anticipation_ft_sql_test'," +
            "  'properties.bootstrap.servers' = 'localhost:9092'," +
            "  'properties.group.id' = 'testGroup'," +
            "  'scan.startup.mode' = 'earliest-offset'," +
            "  'format' = 'json'"
            + ")";

    static String ksink = "CREATE TABLE kafkaSink ("
            // + " `offset` BIGINT,"
            + "  role STRING,"
            + "  user_type STRING,"
            + "  local_ping INT,"
            + "  line INT,"
            + "  server_rtt INT,"
            + "  classroom STRING,"
            + "  full_ping STRING,"
            + "  `timestamp` BIGINT"
            + ") WITH (" +
            "  'connector' = 'kafka'," +
            "  'topic' = 'app_inline_room_anticipation_ft_sql_test_sink'," +
            "  'properties.bootstrap.servers' = 'localhost:9092'" +
            ")";

    static String print =
            "CREATE TABLE kafkaSink ( role STRING, " +
                    " user_type STRING, " +
                    " local_ping INT, " +
                    " line INT,  server_rtt INT, " +
                    " classroom STRING, " +
                    " full_ping STRING, " +
                    " `timestamp` BIGINT" +
                    ") WITH ( " +
                    " 'connector' = 'print'" +
                    ")";

    static String insert = " insert into kafkaSink " +
            "select role, user_type, local_ping, line, server_rtt, classroom, full_ping, timestamp " +
            "from kafkaSource";

    static StreamExecutionEnvironment env;
    static StreamTableEnvironment tEnv;
    public static void main(String[] args) throws Exception {
         env = StreamExecutionEnvironment.getExecutionEnvironment();
         tEnv = StreamTableEnvironment.create(env);
        System.out.println(ks);
        System.out.println(ksink);
        System.out.println(insert);
        test1();
    }

    public static void test1() throws Exception {
        tEnv.executeSql(ks);
        String sql = "SELECT * FROM kafkaSource";
        Table table = tEnv.sqlQuery(sql);
        table.printSchema();
        DataStream<Row> resultDs = tEnv.toChangelogStream(table);
        resultDs.print();
        //table.execute();

        //TableResult outputTable = tEnv.executeSql(print);
        // tEnv.executeSql(insert);  //从结果表查数据，转存到输出表
        // tEnv.executeSql("insert into output_kafka select * from "+tableResult);  //从结果表查数据，转存到输出表

        env.execute();
    }
    public static void test() throws Exception {
       /* final String createTable =
                String.format(
                        "CREATE TABLE kafka (\n"
                                + "  `physical_1` STRING,\n"
                                + "  `physical_2` INT,\n"
                                // metadata fields are out of order on purpose
                                // offset is ignored because it might not be deterministic
                                + "  `timestamp-type` STRING METADATA VIRTUAL,\n"
                                + "  `timestamp` TIMESTAMP(3) METADATA,\n"
                                + "  `leader-epoch` INT METADATA VIRTUAL,\n"
                                + "  `headers` MAP<STRING, BYTES> METADATA,\n"
                                + "  `partition` INT METADATA VIRTUAL,\n"
                                + "  `topic` STRING METADATA VIRTUAL,\n"
                                + "  `physical_3` BOOLEAN\n"
                                + ") WITH (\n"
                                + "  'connector' = 'kafka',\n"
                                + "  'topic' = '%s',\n"
                                + "  'properties.bootstrap.servers' = '%s',\n"
                                + "  'properties.group.id' = '%s',\n"
                                + "  'scan.startup.mode' = 'earliest-offset',\n"
                                + "  %s\n"
                                + ")");
*/
        tEnv.executeSql(ks);

        String initialValues =
                "INSERT INTO kafka\n"
                        + "VALUES\n"
                        + " ('data 1', 1, TIMESTAMP '2020-03-08 13:12:11.123', MAP['k1', X'C0FFEE', 'k2', X'BABE'], TRUE),\n"
                        + " ('data 2', 2, TIMESTAMP '2020-03-09 13:12:11.123', CAST(NULL AS MAP<STRING, BYTES>), FALSE),\n"
                        + " ('data 3', 3, TIMESTAMP '2020-03-10 13:12:11.123', MAP['k1', X'10', 'k2', X'20'], TRUE)";
        //tEnv.executeSql(initialValues).await();


        final List<Row> result = collectRows(tEnv.sqlQuery("SELECT * FROM kafkaSource"), 3);

        System.out.println(result.toArray());
    }

    public static List<Row> collectRows(Table table, int expectedSize) throws Exception {
        final TableResult result = table.execute();
        final List<Row> collectedRows = new ArrayList<>();
        try (CloseableIterator<Row> iterator = result.collect()) {
            while (collectedRows.size() < expectedSize && iterator.hasNext()) {
                collectedRows.add(iterator.next());
            }
        }
        result.getJobClient()
                .ifPresent(
                        jc -> {
                            try {
                                jc.cancel().get(5, TimeUnit.SECONDS);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        });

        return collectedRows;
    }
}
