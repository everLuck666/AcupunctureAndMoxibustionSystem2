package net.seehope.controller;

import net.seehope.GoodsService;
import net.seehope.IndexService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    IndexService indexService;

    @PutMapping("goods")
    public RestfulJson addGoods(@RequestBody Goods goods, HttpServletRequest request){
//        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
//        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
//        String path = "/src/main/resources/static/images/";
//        String fileName = indexService.update(files, path);
//        goods.setImageUrl(fileName);
        goodsService.addGoods(goods);
        return RestfulJson.isOk("添加商品成功！");
    }

    @DeleteMapping("goods")
    public RestfulJson deleteGoods(String goodsName){
        goodsService.deleteGoods(goodsName);
        return RestfulJson.isOk("删除商品成功！");
    }

    @GetMapping("goods")
    public RestfulJson getGoods(){
        return RestfulJson.isOk(goodsService.getAllGoods());
    }

    @PostMapping("goods")
    public RestfulJson updateGoods(String goodsName){
        goodsService.updateGoods(goodsName);
        return RestfulJson.isOk("修改成功！");
    }
}
