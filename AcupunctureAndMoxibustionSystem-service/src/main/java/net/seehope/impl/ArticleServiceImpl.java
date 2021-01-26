package net.seehope.impl;

import net.seehope.ArticleService;
import net.seehope.IndexService;
import net.seehope.mapper.ArticleMapper;
import net.seehope.pojo.Article;
import net.seehope.pojo.Video;
import net.seehope.pojo.vo.ArticleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    IndexService indexService;
    @Override
    public List<ArticleVo> getAllArticle() throws IOException {
        List<ArticleVo> articleVos = new ArrayList<>();
        List<Article> articles = articleMapper.selectAll();
        for (Article article:articles){
            ArticleVo articleVo = new ArticleVo();
            articleVo.setContent(article.getContent());
            articleVo.setImage(article.getImage());
            File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
            articleVo.setText(indexService.readDoc(tempFile.getAbsolutePath() +"/src/main/resources/static/article/"+article.getPath()));
            articleVo.setTitle(article.getTitle());
            articleVo.setId(article.getId());
            articleVo.setCreateTime(article.getCreateTime());

            articleVos.add(articleVo);
        }

        return articleVos;
    }

    @Override
    public void addArticle(Article article) {
        articleMapper.insert(article);
    }

    @Override
    public void deleteArticle(String id) {
        Article article = new Article();
        article.setId(Integer.parseInt(id));

        Article tempItem = articleMapper.selectOne(article);
        if (tempItem != null) {
          articleMapper.delete(article);
            File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
            File dest = new File(tempFile.getAbsolutePath() + "/src/main/resources/static/article/" + tempItem.getPath());
            File dest2 = new File(tempFile.getAbsolutePath() + "/src/main/resources/static/images/" + tempItem.getImage());
            if (dest != null) {
                dest.delete();
            } else {
                logger.warn("请注意要删除的文章不存在");
            }

            if (dest2 != null) {
                dest2.delete();
            } else {
                logger.warn("请注意要删除的文章的图片不存在");
            }

        } else {
            logger.info("文章不存在");
            throw new RuntimeException("这个文章不存在");
        }
    }
}
