package com.note.flink.sql;

/**
 * @Author: Jary
 * @Date: 2021/10/18 2:39 下午
 */
public class KafkaTest {

    String ks = "CREATE TABLE PopulationUpdates ("
            + "  role STRING,"
            + "  user_type STRING,"
            + "  local_ping INT,"
            + "  line INT,"
            + "  server_rtt INT,"
            + "  classroom STRING,"
            + "  full_ping STRING,"
            + " timestamp Long"
            + ") WITH (" +
            "  'connector' = 'kafka'," +
            "  'topic' = 'app_inline_room_anticipation_ft_sql_test'," +
            "  'properties.bootstrap.servers' = 'localhost:9092'," +
            "  'properties.group.id' = 'testGroup'," +
            "  'scan.startup.mode' = 'earliest-offset'," +
            "  'format' = 'json'"
            + ")";

}
