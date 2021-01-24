package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`symptom`")
public class Symptom implements Serializable {
    /**
     * 症状id
     */
    @Column(name = "`symptom_id`")
    private String symptomId;

    /**
     * 病因
     */
    @Column(name = "`reason`")
    private String reason;

    /**
     * 症状名称
     */
    @Column(name = "`symptom_name`")
    private String symptomName;

    private static final long serialVersionUID = 1L;

    /**
     * 获取症状id
     *
     * @return symptom_id - 症状id
     */
    public String getSymptomId() {
        return symptomId;
    }

    /**
     * 设置症状id
     *
     * @param symptomId 症状id
     */
    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    /**
     * 获取病因
     *
     * @return reason - 病因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置病因
     *
     * @param reason 病因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 获取症状名称
     *
     * @return symptom_name - 症状名称
     */
    public String getSymptomName() {
        return symptomName;
    }

    /**
     * 设置症状名称
     *
     * @param symptomName 症状名称
     */
    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }
}