package com.pplive.bip.report;

import com.pplive.bip.report.domain.Report;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * 使用内存数据库测试，不会影响测试数据库
 * 如使用测试数据库，应该使用rollback机制
 */
@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class ReportRepositoryTest {

    @Autowired
    private ReportRepository reportRepository;

    @Test
    public void testSave(){
        Report r = new Report();
        r.setName("testx");
        r.setOwner("jjt");
        r.setDomain("plt");
        r.setEngine(Engine.HIVE);
        r.setStorePlace(Engine.MYSQL);
        r.setFrequency(Frequency.DAILY);
        r.setQl("select");
        Map<String, String> ext  = new HashMap();
        ext.put(ExtAttribute.SELF_DEPENDENT, Boolean.TRUE.toString());
        r.setExt(ext);
        Set<String> d = new HashSet<>();
        d.add("b");
        r.setDependencies(d);
        r.setResultTable("a");
        Set<String> q = new HashSet<>();
        q.add("a");
        r.setQueryTables(q);
        reportRepository.save(r);
    }

}
