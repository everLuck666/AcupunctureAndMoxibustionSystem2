package net.seehope.controller;

import net.seehope.ArticleService;
import net.seehope.common.RestfulJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("article")
    public RestfulJson getAllArticle(){

        return RestfulJson.isOk(articleService.getAllArticle());

    }
}
