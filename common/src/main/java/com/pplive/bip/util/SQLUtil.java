package com.pplive.bip.util;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SQLUtil {

    public static Set<String> getQueryTables(String sql, String engine) {
        List<SQLStatement> statements =  SQLUtils.parseStatements(sql, engine);
        SchemaStatVisitor visitor = new SchemaStatVisitor();
        Set<String> tableNames = new HashSet();
        for(SQLStatement statement: statements) {
            statement.accept(visitor);
            visitor.getTables().keySet().forEach(t->{
                tableNames.add(t.getName());
            });
        }
        return tableNames;
    }
}
