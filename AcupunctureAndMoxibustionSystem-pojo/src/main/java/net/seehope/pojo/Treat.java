package net.seehope.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`treat`")
public class Treat implements Serializable {
    @Column(name = "`treat_id`")
    private String treatId;

    @Column(name = "`symptom_id`")
    private String symptomId;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "`create_time`")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return treat_id
     */
    public String getTreatId() {
        return treatId;
    }

    /**
     * @param treatId
     */
    public void setTreatId(String treatId) {
        this.treatId = treatId;
    }

    /**
     * @return symptom_id
     */
    public String getSymptomId() {
        return symptomId;
    }

    /**
     * @param symptomId
     */
    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }
}