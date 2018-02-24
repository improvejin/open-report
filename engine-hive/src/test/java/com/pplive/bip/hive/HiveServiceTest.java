package com.pplive.bip.hive;

import com.pplive.bip.report.ResultColumn;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;


@SpringBootTest
@RunWith(SpringRunner.class)
public class HiveServiceTest {

    private static String sql = "select ipvalue  from sm_play";

    @Autowired
    HiveService service;

    @Test
    public void testGetResultSchema() throws Exception{
        List<ResultColumn> s =  service.getSchema("select ipvalue  from sm_play where dt=180102");
        Assert.assertEquals(new ResultColumn("ipvalue", "string"), s.get(0));
    }

    @Test
    public void testGetQueryTable() throws Exception {
        Set<String> tables = service.getQueryTable(sql);
        Assert.assertEquals(1, tables.size());
        Assert.assertTrue(tables.contains("sm_play"));
    }

    @Ignore
    public void testCreateSchema()throws Exception {
        service.executeUpdate("create table jjt(a int)");
    }

}
