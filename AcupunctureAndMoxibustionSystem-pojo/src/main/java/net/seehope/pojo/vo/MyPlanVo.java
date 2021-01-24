package net.seehope.pojo.vo;

import javax.xml.crypto.Data;

public class MyPlanVo {
    //诊疗方案的名称
    private String name;
    //诊疗状态
    private int status;
    //诊疗天数
    private String date;

    public String getCreateTime() {
        return createTime;
    }

    /**
     * 诊疗进度
     */
    private String process;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    //一次诊疗的时间
    private String totalTime;
    //创建时间
    private String createTime;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }
}
