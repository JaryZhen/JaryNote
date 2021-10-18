package com.note.flink.sql;

import lombok.Data;

/**
 * @author zhuhuipei
 * @Description:
 * @date 2020-08-21
 * @time 02:10
 */
@Data
public class JobRunParam {
    /**
     * sql语句目录
     */
    private String sqlPath;

    /**
     * 任务类型
     */
    private JobTypeEnum jobTypeEnum;

}
