package com.pplive.bip.report;

import com.pplive.bip.report.rest.MySQLEngineClient;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FeignClientTest {

    @Autowired
    private MySQLEngineClient mysql;

    @Ignore
    public void getQueryTableTest() {
        Set<String> t = mysql.getQueryTable("a");
        System.out.println(t);
    }
}
