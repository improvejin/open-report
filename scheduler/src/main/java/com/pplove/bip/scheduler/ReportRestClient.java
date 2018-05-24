package com.pplove.bip.scheduler;

import com.pplove.bip.report.SchedulableReport;
import com.pplove.bip.report.SchedulableReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiatingjin on 2018/1/2.
 */
@Component
public class ReportRestClient {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    private static final Map<String, SchedulableReport> empty = new HashMap<>();

    Map<String, SchedulableReport> getHourlyReports() {
        return empty;
    }

    Map<String, SchedulableReport> getDailyReports() {
        return empty;
    }

    Map<String, SchedulableReport> getWeeklyReports() {
        return empty;
    }

    Map<String, SchedulableReport> getMonthlyReports() {
        return empty;
    }

    Map<String, SchedulableReport> getYearlyReports() {
        return empty;
    }
}
