package com.pplive.bip.mysql;

import com.pplive.bip.report.AlterColumn;
import com.pplive.bip.report.ResultColumn;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@SpringBootTest
@RunWith(SpringRunner.class)
public class MySQLControllerTest {

    @Autowired
    MySQLController controller;

    @Test
    public void testGetResultSchema()throws Exception {
        controller.getResultSchema("select id as id1 from sys_users");
    }

    @Test
    public void testGetQueryTable() {
        Set<String> tables = controller.getQueryTable("select id as id1 from sys_users");
        Assert.assertTrue(tables.contains("sys_users"));
    }

    @Ignore
    public void testCreateSchema()throws Exception {
        List<ResultColumn> columns = new ArrayList<>();
        columns.add(new ResultColumn("a", "BIGINT"));
        columns.add(new ResultColumn("b", "VARCHAR"));
        controller.createResultTable("jjt", columns);
    }

    @Ignore
    public void testAlterResultTable()throws Exception {
        List<ResultColumn> c1 = new ArrayList<>();
        List<ResultColumn> c2 = new ArrayList<>();
        c1.add(new ResultColumn("a", "BOOLEAN"));
        c1.add(new ResultColumn("b", "BOOLEAN"));
        c2.add(new ResultColumn("e", "BOOLEAN"));
        c2.add(new ResultColumn("f", "BOOLEAN"));
        AlterColumn alter = new AlterColumn(c2, c1);
        controller.alterResultTable("jjt", alter);
    }


}
