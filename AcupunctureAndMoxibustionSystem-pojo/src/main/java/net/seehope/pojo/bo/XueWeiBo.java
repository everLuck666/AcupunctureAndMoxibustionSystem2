package net.seehope.pojo.bo;

import io.swagger.annotations.ApiModelProperty;

public class XueWeiBo {
    @ApiModelProperty("第几天")
    private String day;
    @ApiModelProperty("诊疗方案的名字")
    private String treatProjectName;
    @ApiModelProperty("穴位的名字")
    private String pointName;
    @ApiModelProperty("诊疗的温度")
    private String temperature;
    @ApiModelProperty("治疗的时间")
    private String treatTime;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTreatProjectName() {
        return treatProjectName;
    }

    public void setTreatProjectName(String treatProjectName) {
        this.treatProjectName = treatProjectName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTreatTime() {
        return treatTime;
    }

    public void setTreatTime(String treatTime) {
        this.treatTime = treatTime;
    }
}
