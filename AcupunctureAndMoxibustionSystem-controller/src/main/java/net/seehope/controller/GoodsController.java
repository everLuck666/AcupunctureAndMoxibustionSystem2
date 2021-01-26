package net.seehope.controller;

import net.seehope.GoodsService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;

    @PutMapping("goods")
    public RestfulJson addGoods(@RequestBody Goods goods){
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
