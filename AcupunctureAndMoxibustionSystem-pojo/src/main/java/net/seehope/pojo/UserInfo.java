package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`user_info`")
public class UserInfo implements Serializable {
    @Id
    @Column(name = "`id`")
    private Integer id;

    @Column(name = "`user_id`")
    private String userId;

    @Column(name = "`user_name`")
    private String userName;

    @Column(name = "`phone`")
    private String phone;

    @Column(name = "`address`")
    private String address;



    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}