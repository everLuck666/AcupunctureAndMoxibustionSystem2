package net.seehope.controller;

import net.seehope.ArticleService;
import net.seehope.IndexService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Article;
import net.seehope.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    IndexService indexService;

    @GetMapping("article")
    public RestfulJson getAllArticle() throws IOException {

        return RestfulJson.isOk(articleService.getAllArticle());

    }

    //上传文章
    @PutMapping("article")
    public RestfulJson updateVideo(HttpServletRequest request,String content,String title){


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String path = "/src/main/resources/static/article/";
        String fileName = indexService.update(files, path);


        List<MultipartFile> photosFiles = ((MultipartHttpServletRequest) request).getFiles("photos");
        String photoPath = "/src/main/resources/static/images/";
        String photoFileName = indexService.update(photosFiles, photoPath);

        Article article = new Article();
        article.setContent(content);
        article.setImage(fileName);
        article.setTitle(title);
        article.setImage(photoFileName);
        article.setPath(fileName);//文档路径

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        article.setCreateTime(createTime);
        articleService.addArticle(article);

        return RestfulJson.isOk("上传成功");

    }

    //删除文章
    @DeleteMapping("article")
    public RestfulJson deleteArticle(@RequestBody Map map){
        String id = map.get("id").toString();
        articleService.deleteArticle(id);
        return RestfulJson.isOk("文章删除成功");

    }

}
