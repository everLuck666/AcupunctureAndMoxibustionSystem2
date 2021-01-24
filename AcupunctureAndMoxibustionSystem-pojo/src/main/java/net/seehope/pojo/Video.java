package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`video`")
public class Video implements Serializable {
    @Column(name = "`id`")
    private String id;

    @Column(name = "`videoName`")
    private String videoname;

    @Column(name = "`path`")
    private String path;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
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