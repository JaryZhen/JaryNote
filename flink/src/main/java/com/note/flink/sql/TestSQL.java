package com.note.flink.sql;

import org.apache.flink.table.api.*;


/**
 * @Author: Jary
 * @Date: 2021/10/18 11:50 上午
 */
public class TestSQL {

    public static void main(String[] args) {

        EnvironmentSettings settings = EnvironmentSettings
                .newInstance()
                .useBlinkPlanner()
                .inStreamingMode()
                .build();

        TableEnvironment tableEnv = TableEnvironment.create(settings);
        //tableEnv = //TableEnvironment.create(EnvironmentSettings.newInstance().build());

        tableEnv.executeSql(SQL.kafkaSource)
                .print();

    }

    private static class SQL {
        static String kafkaSource = String.format(
                " CREATE TABLE KafkaTable (" +
                        "  `user_id` BIGINT," +
                        "  `item_id` BIGINT," +
                        "  `behavior` STRING," +
                        "  `ts` TIMESTAMP(3) METADATA FROM 'timestamp'" +
                        " ) WITH (" +
                        "  'connector' = 'kafka'," +
                        "  'topic' = 'app_inline_room_anticipation_ft_sql_test'," +
                        "  'properties.bootstrap.servers' = 'localhost:9092'," +
                        "  'properties.group.id' = 'testGroup'," +
                        "  'scan.startup.mode' = 'earliest-offset'," +
                        "  'format' = 'csv'" +
                        ")"
        );
    }
}
