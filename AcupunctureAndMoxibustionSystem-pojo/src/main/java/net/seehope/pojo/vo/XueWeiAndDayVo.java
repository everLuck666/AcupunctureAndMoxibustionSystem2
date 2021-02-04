package net.seehope.pojo.vo;

import net.seehope.pojo.XueWei;

import java.util.List;

public class XueWeiAndDayVo {
    private List<XueWei> xueWeiList;
    private String day;

    public List<XueWei> getXueWeiList() {
        return xueWeiList;
    }

    public void setXueWeiList(List<XueWei> xueWeiList) {
        this.xueWeiList = xueWeiList;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
