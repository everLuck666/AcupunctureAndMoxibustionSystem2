package net.seehope.common;

public enum  PlanStatus {
    FINISH(1),UNFINISH(0);
    PlanStatus(Integer status){
        this.status = status;
    }
    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer status;
}
