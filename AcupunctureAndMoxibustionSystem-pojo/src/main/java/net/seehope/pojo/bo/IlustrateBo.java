package net.seehope.pojo.bo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class IlustrateBo {
    @ApiModelProperty("症状的名字")
    private String symptomName;
    @ApiModelProperty("诊疗的名字")
    private String treatName;
    @ApiModelProperty("症状的原因")
    private String reason;
    @ApiModelProperty("诊疗的影响")
    private String effect;

    @ApiModelProperty("诊疗方案的描述")
    private String describe;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    private String path;

    private String create_Time;
    private String symptomId;
    private String treateId;

    public String getCreate_Time() {
        return create_Time;
    }

    public void setCreate_Time(String create_Time) {
        this.create_Time = create_Time;
    }

    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    public String getTreateId() {
        return treateId;
    }

    public void setTreateId(String treateId) {
        this.treateId = treateId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getTreatName() {
        return treatName;
    }

    public void setTreatName(String treatName) {
        this.treatName = treatName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
