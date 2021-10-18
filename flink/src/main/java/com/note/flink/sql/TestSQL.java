package com.note.flink.sql;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.JobID;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.calcite.shaded.com.google.common.base.Preconditions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;
import scala.Predef;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
