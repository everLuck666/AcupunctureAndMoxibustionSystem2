package net.seehope.pojo.bo;

import java.io.Serializable;

public class ShoppingCarBo implements Serializable {
    private static final long serialVersionUID = 6141936388841892937L;
    private String productName;
    private String productNumber;
    private String productPrice;//商品金额
    private String order_amout;//订单总额

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getOrder_amout() {
        return order_amout;
    }

    public void setOrder_amout(String order_amout) {
        this.order_amout = order_amout;
    }
}
