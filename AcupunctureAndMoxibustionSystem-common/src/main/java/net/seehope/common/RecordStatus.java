package net.seehope.common;

public enum RecordStatus {
    OFF(0),UP(1);

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    RecordStatus(Integer status){
        this.status = status;
    }
    private Integer status;
}
