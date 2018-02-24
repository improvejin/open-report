package com.pplive.bip.report.rest;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by jiatingjin on 2017/12/28.
 */
@FeignClient(name = "engine-hive-service", path = "/hive")
public interface HiveEngineClient extends IEngineRestClient {

}
