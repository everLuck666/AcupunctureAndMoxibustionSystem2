package net.seehope.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`orders`")
public class Orders implements Serializable {
    @Id
    @Column(name = "`order_id`")
    private String orderId;

    @Column(name = "`product_name`")
    private String productName;

    @Column(name = "`product_number`")
    private String productNumber;

    @Column(name = "`user_name`")
    private String userName;

    @Column(name = "`user_phone`")
    private String userPhone;

    @Column(name = "`user_address`")
    private String userAddress;

    @Column(name = "`order_amout`")
    private Double orderAmout;

    @Column(name = "`order_time`")
    private Date orderTime;

    @Column(name = "`remark`")
    private String remark;

    @Column(name = "`status`")
    private String status;

    private static final long serialVersionUID = 1L;

    /**
     * @return order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return product_name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return product_number
     */
    public String getProductNumber() {
        return productNumber;
    }

    /**
     * @param productNumber
     */
    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return user_phone
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * @param userPhone
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * @return user_address
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * @param userAddress
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * @return order_amout
     */
    public Double getOrderAmout() {
        return orderAmout;
    }

    /**
     * @param orderAmout
     */
    public void setOrderAmout(Double orderAmout) {
        this.orderAmout = orderAmout;
    }

    /**
     * @return order_time
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}