package net.seehope.impl;

import net.seehope.ArticleService;
import net.seehope.mapper.ArticleMapper;
import net.seehope.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Override
    public List<Article> getAllArticle() {
        return articleMapper.selectAll();
    }
}
