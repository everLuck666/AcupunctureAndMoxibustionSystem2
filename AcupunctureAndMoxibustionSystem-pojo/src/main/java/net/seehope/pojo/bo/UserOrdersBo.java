package net.seehope.pojo.bo;

public class UserOrdersBo {
    private String productName;
    private String orderId;
    private String productNumber;
    private Double orderAmout;
    private String status;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public Double getOrderAmout() {
        return orderAmout;
    }

    public void setOrderAmout(Double orderAmout) {
        this.orderAmout = orderAmout;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
