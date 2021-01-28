package net.seehope.common;

public enum GoodsStatus {
    ON(1),OFF(0);  //ON表示商品上架状态，OFF表示商品已下架
    GoodsStatus(Integer status){
        this.status = status;
    }
   private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
