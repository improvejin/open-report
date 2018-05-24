package com.pplove.bip.scheduler.status;

/**
 * Created by jiatingjin on 2018/1/18.
 */
public enum  ScheduleStatus {
    SUBMITTED,
    RUNNING,
    IMPORTING,
    IMPORT_ERR,
    ERROR,
    SKIP, //提交了，前置依赖错误未执行
    SUCCESS
}
