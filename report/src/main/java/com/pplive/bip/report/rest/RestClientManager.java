package com.pplive.bip.report.rest;

import com.pplive.bip.report.Engine;
import com.pplive.bip.exception.EngineNotSupportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestClientManager {

    private static MySQLEngineClient mySQLEngine;

    private static HiveEngineClient hiveEngine;


    @Autowired
    public RestClientManager(MySQLEngineClient mysql, HiveEngineClient hive) {
        RestClientManager.mySQLEngine = mysql;
        RestClientManager.hiveEngine = hive;
    }

    public static IEngineRestClient getRestClient(Engine engine) {
        switch (engine) {
            case HIVE:
                return hiveEngine;
            case MYSQL:
                return mySQLEngine;
            default:
                throw new EngineNotSupportException(engine.toString());
        }
    }
}
