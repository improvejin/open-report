package com.pplove.bip.scheduler.oozie;

/**
 * Created by jiatingjin on 2018/1/9.
 */
public class WorkflowException extends RuntimeException {
    public WorkflowException(String message, Exception cause){
        super(message,cause);
    }
}
