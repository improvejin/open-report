package com.pplove.bip.report;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * 模拟浏览器发送Rest请求
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetReportById() {
        Map<String, Object> r = restTemplate.getForObject("/report/frequency/daily", Map.class);
//        System.out.println(r.get("data"));
    }
}
