package net.seehope.common;

public enum  MedicalRecordType {
    DELETE("-1"),UNDELETE("1"),ALL("3"),SPACE("4");//这个状态表示没有这一条数据

    MedicalRecordType(String status){
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
