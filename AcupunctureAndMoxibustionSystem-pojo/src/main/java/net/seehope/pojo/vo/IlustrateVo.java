package net.seehope.pojo.vo;

public class IlustrateVo {
    private String symptomName;
    private String describe;
     private String treatId;
    private String symptomId;

    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTreatId() {
        return treatId;
    }

    public void setTreatId(String treatId) {
        this.treatId = treatId;
    }
}
