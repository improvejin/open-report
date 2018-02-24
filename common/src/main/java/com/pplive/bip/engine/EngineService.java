package com.pplive.bip.engine;



import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EngineService {

    public abstract DataSource getDataSource();

    public int executeUpdate(String sql) throws SQLException {
        Connection conn = this.getDataSource().getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        int c = statement.executeUpdate();
        conn.close();
        return c;
    }

    public List<Map<String, Object> > executeQuery(String sql) throws SQLException{
        Connection conn = this.getDataSource().getConnection();
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map<String, Object> > rows = new ArrayList<Map<String, Object>>();
        while (rs.next()) {
            Map<String, Object> m = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                Object value = rs.getObject(columnName);
                m.put(columnName, value);
            }
            rows.add(m);
        }
        rs.close();
        conn.close();
        return rows;
    }
}
