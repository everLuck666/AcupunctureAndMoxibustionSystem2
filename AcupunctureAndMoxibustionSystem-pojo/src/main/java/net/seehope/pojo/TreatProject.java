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
    @Column(name = "`describe`")
    private String describe;

    /**
     * 穴位名称
     */
    @Column(name = "`point_name`")
    private String pointName;

    /**
     * 针灸温度
     */
    @Column(name = "`temperature`")
    private String temperature;

    /**
     * 治疗时间
     */
    @Column(name = "`treat_time`")
    private String treatTime;

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
     * @return describe - 描述
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * 设置描述
     *
     * @param describe 描述
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /**
     * 获取穴位名称
     *
     * @return point_name - 穴位名称
     */
    public String getPointName() {
        return pointName;
    }

    /**
     * 设置穴位名称
     *
     * @param pointName 穴位名称
     */
    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    /**
     * 获取针灸温度
     *
     * @return temperature - 针灸温度
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * 设置针灸温度
     *
     * @param temperature 针灸温度
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * 获取治疗时间
     *
     * @return treat_time - 治疗时间
     */
    public String getTreatTime() {
        return treatTime;
    }

    /**
     * 设置治疗时间
     *
     * @param treatTime 治疗时间
     */
    public void setTreatTime(String treatTime) {
        this.treatTime = treatTime;
    }
}