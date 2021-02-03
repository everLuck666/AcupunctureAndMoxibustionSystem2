package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`article`")
public class Article implements Serializable {
    @Column(name = "`id`")
    private Integer id;

    @Column(name = "`title`")
    private String title;

    @Column(name = "`image`")
    private String image;

    @Column(name = "`content`")
    private String content;


    @Column(name = "`createTime`")
    private String createTime;

    @Column(name = "`text`")
    private String text;



    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }





    private static final long serialVersionUID = 1L;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}