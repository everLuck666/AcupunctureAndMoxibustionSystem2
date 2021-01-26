package net.seehope;

import net.seehope.pojo.Article;
import net.seehope.pojo.vo.ArticleVo;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    List<ArticleVo>  getAllArticle() throws IOException;
    void addArticle(Article article);

    void deleteArticle(String id);
}
