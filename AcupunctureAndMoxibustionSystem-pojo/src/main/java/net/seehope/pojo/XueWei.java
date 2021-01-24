package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`xue_wei`")
public class XueWei implements Serializable {
    @Column(name = "`pointName`")
    private String pointname;

    @Column(name = "`temperature`")
    private String temperature;

    @Column(name = "`treatTime`")
    private Integer treattime;

    @Column(name = "`id`")
    private Integer id;

    private static final long serialVersionUID = 1L;

    /**
     * @return pointName
     */
    public String getPointname() {
        return pointname;
    }

    /**
     * @param pointname
     */
    public void setPointname(String pointname) {
        this.pointname = pointname;
    }

    /**
     * @return temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * @param temperature
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * @return treatTime
     */
    public Integer getTreattime() {
        return treattime;
    }

    /**
     * @param treattime
     */
    public void setTreattime(Integer treattime) {
        this.treattime = treattime;
    }

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
}