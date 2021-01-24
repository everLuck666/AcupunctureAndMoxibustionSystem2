package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`treat_project`")
public class TreatProject implements Serializable {
    @Column(name = "`treat_id`")
    private String treatId;

    /**
     * 诊疗效果
     */
    @Column(name = "`effect`")
    private String effect;

    /**
     * 总时间
     */
    @Column(name = "`total_time`")
    private String totalTime;

    /**
     * 描述
     */
    @Column(name = "`treat_describe`")
    private String treatDescribe;

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
     * 获取诊疗效果
     *
     * @return effect - 诊疗效果
     */
    public String getEffect() {
        return effect;
    }

    /**
     * 设置诊疗效果
     *
     * @param effect 诊疗效果
     */
    public void setEffect(String effect) {
        this.effect = effect;
    }

    /**
     * 获取总时间
     *
     * @return total_time - 总时间
     */
    public String getTotalTime() {
        return totalTime;
    }

    /**
     * 设置总时间
     *
     * @param totalTime 总时间
     */
    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * 获取描述
     *
     * @return treat_describe - 描述
     */
    public String getTreatDescribe() {
        return treatDescribe;
    }

    /**
     * 设置描述
     *
     * @param treatDescribe 描述
     */
    public void setTreatDescribe(String treatDescribe) {
        this.treatDescribe = treatDescribe;
    }
}