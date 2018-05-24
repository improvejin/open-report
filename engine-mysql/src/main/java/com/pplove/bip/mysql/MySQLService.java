package com.pplove.bip.mysql;


import com.pplove.bip.engine.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@Service
public class MySQLService extends EngineService {

    @Autowired
    private DataSource dataSource;


    public ResultSetMetaData getMetaData(String sql) throws SQLException{
        Connection conn = dataSource.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSetMetaData metaData = statement.getMetaData();
        conn.close();
        return metaData;
    }


    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
