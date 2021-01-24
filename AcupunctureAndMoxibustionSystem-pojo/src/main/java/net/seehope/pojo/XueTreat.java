package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`xue_treat`")
public class XueTreat implements Serializable {
    @Column(name = "`xue_id`")
    private Integer xueId;

    @Column(name = "`day`")
    private Integer day;

    @Column(name = "`treat_id`")
    private Integer treatId;

    private static final long serialVersionUID = 1L;

    /**
     * @return xue_id
     */
    public Integer getXueId() {
        return xueId;
    }

    /**
     * @param xueId
     */
    public void setXueId(Integer xueId) {
        this.xueId = xueId;
    }

    /**
     * @return day
     */
    public Integer getDay() {
        return day;
    }

    /**
     * @param day
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * @return treat_id
     */
    public Integer getTreatId() {
        return treatId;
    }

    /**
     * @param treatId
     */
    public void setTreatId(Integer treatId) {
        this.treatId = treatId;
    }
}