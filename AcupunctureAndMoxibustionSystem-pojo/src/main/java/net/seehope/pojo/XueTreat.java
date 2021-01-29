package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`xue_treat`")
public class XueTreat implements Serializable {
    @Column(name = "`xue_id`")
    private String xueId;

    @Column(name = "`day`")
    private String  day;

    @Column(name = "`treat_id`")
    private String treatId;

    public String getXueId() {
        return xueId;
    }

    public void setXueId(String xueId) {
        this.xueId = xueId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTreatId() {
        return treatId;
    }

    public void setTreatId(String treatId) {
        this.treatId = treatId;
    }
}