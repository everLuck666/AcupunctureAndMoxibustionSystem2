package net.seehope.impl;

import net.seehope.ArticleService;
import net.seehope.IndexService;
import net.seehope.mapper.ArticleMapper;
import net.seehope.pojo.Article;
import net.seehope.pojo.Video;
import net.seehope.pojo.vo.ArticleVo;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
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
    public List<Article> getAllArticle() throws IOException {
        List<ArticleVo> articleVos = new ArrayList<>();
        List<Article> articles = articleMapper.selectAll();

        List<Article> articles2 = articleMapper.selectAll();
        return articles;
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
            File dest = new File(tempFile.getAbsolutePath() + "/src/main/resources/static/images/" +tempItem.getImage() );

            if (dest != null) {
                dest.delete();
            } else {
                logger.warn("请注意要删除的图片不存在");
            }



        }else{
            throw new RuntimeException("这个要删除的记录不存在");
        }


    }

    @Override
    public String readRDF(String path) {
        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
        String text = "";
        try (PDDocument document = PDDocument.load(new File(tempFile.getAbsolutePath()+"/src/main/resources/static/article/"+path))) {

            document.getClass();

            if (!document.isEncrypted()) {

                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                //System.out.println("Text:" + st);

                // split by whitespace
                String lines[] = pdfFileInText.split("\\r?\\n");
                for (String line : lines) {
                    System.out.println(line);
                    text += line;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    @Override
    public void deleteArticleDoc(String fileName) {


            File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
            File dest = new File(tempFile.getAbsolutePath() + "/src/main/resources/static/article/" + fileName);

            if (dest != null) {
                logger.info("开始删除文章"+fileName);
                dest.delete();
            } else {
                logger.warn("请注意要删除的文章不存在");
            }


    }

    public static void main(String[] args) {
        String text = null;
        text += "123";
        System.out.println(text);
    }
}
