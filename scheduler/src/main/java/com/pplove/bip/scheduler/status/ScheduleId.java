package com.pplove.bip.scheduler.status;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ScheduleId implements Serializable {
    @Column(nullable = false)
    private String name; //报表名字

    @Column(nullable = false)
    private int date; //执行时间

    public ScheduleId(){

    }

    public ScheduleId(String name, int date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleId that = (ScheduleId) o;

        if (date != that.date) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + date;
        return result;
    }
}
