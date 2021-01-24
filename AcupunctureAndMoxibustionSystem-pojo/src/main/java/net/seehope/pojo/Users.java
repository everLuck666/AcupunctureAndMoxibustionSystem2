package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`users`")
public class Users implements Serializable {
    @Id
    @Column(name = "`user_id`")
    private String userId;

    @Column(name = "`user_name`")
    private String userName;

    @Column(name = "`password`")
    private String password;

    @Column(name = "`identity`")
    private Integer identity;

    @Column(name = "`version`")
    private String version;

    private static final long serialVersionUID = 1L;

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
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return identity
     */
    public Integer getIdentity() {
        return identity;
    }

    /**
     * @param identity
     */
    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    /**
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }
}