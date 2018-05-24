package com.pplove.bip.report.rest;

import org.springframework.cloud.netflix.feign.FeignClient;


@FeignClient(name = "engine-mysql-service", path = "/mysql")
public interface MySQLEngineClient extends IEngineRestClient {

}
