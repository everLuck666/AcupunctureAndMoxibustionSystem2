package net.seehope.common;

public enum OrderType {
    Waiting(0),Finished(1);//NOUSED是表示这个订单没有发货，USED表示已发货

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    OrderType(Integer type){
        this.type = type;
    }
    public Integer type;
}
