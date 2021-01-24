package net.seehope.pojo.vo;

import net.seehope.pojo.XueWei;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class SymptomInformationVo {
    /**
     * 病因
     */

    private String reason;

    /**
     * 症状名称
     */

    private String symptonName;

    /**
     * 诊疗效果
     */

    private String effect;

    /**
     * 描述
     */

    private String describe;

    /**
     * 需要按的穴位
     */

    private List<XueWei> xueWeiList;

    private String totalDay;

    public String getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(String totalDay) {
        this.totalDay = totalDay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSymptonName() {
        return symptonName;
    }

    public void setSymptonName(String symptonName) {
        this.symptonName = symptonName;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<XueWei> getXueWeiList() {
        return xueWeiList;
    }

    public void setXueWeiList(List<XueWei> xueWeiList) {
        this.xueWeiList = xueWeiList;
    }
}
