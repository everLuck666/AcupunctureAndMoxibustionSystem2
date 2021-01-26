package net.seehope.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`video`")
public class Video implements Serializable {
    @Column(name = "`id`")
    private int id;

    @Column(name = "`videoName`")
    private String videoname;

    @Column(name = "`path`")
    private String path;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "`createTime`")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return videoName
     */
    public String getVideoname() {
        return videoname;
    }

    /**
     * @param videoname
     */
    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
    }
}