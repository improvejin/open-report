package com.pplove.bip.report;


/**
 * Created by jiatingjin on 2017/12/27.
 */
public class ResultColumn {
    public String dataType; //java.sql.types
    public String name;

    public ResultColumn(String name, String dataType) {
        this.dataType = dataType;
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResultColumn that = (ResultColumn) o;

        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = dataType != null ? dataType.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
