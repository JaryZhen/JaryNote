package com.note.flink.sql;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.CloseableIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Author: Jary
 * @Date: 2021/10/18 2:39 下午
 */
public class KafkaTest2 {
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
            "  'topic' = 'app_inline_room_anticipation_ft_sql_test_sink'," +
            "  'properties.bootstrap.servers' = 'localhost:9092'," +
            "  'properties.group.id' = 'testGroupSink'," +
            "  'scan.startup.mode' = 'latest-offset'," +
            "  'format' = 'avro'"
            + ")";


    static StreamExecutionEnvironment env;
    static StreamTableEnvironment tEnv;

    public static void main(String[] args) throws Exception {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
        tEnv = StreamTableEnvironment.create(env);
        System.out.println(ks);
        test1();
    }

    public static void test1() throws Exception {
        tEnv.executeSql(ks);

        String sql = "SELECT * FROM kafkaSource";
        Table table = tEnv.sqlQuery(sql);
        table.printSchema();
        DataStream<Row> resultDs = tEnv.toChangelogStream(table);
        resultDs.print();
        env.execute();
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
