package net.seehope.controller;

import net.seehope.ShoppingService;
import net.seehope.common.RestfulJson;
import net.seehope.mapper.UserInfoMapper;
import net.seehope.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("shopping")
public class ShoppingController {
    @Autowired
    ShoppingService shoppingService;

    @GetMapping("getAllGoods")
    public RestfulJson getAllGoods(){
        System.out.println("巴拉巴拉");
        return RestfulJson.isOk(shoppingService.getAllGoods());
    }

    @GetMapping("getGoodsBy")
    public RestfulJson getGoodsByKeyword(String keyword){
        return RestfulJson.isOk(shoppingService.getGoodsByKeyword(keyword));
    }

    @PutMapping("addAddress")
    public RestfulJson addAddress(@RequestBody UserInfo userInfo, HttpServletRequest request){
        String userId = request.getAttribute("openId").toString();
        userInfo.setUserId(userId);
        try {
            shoppingService.addAddress(userInfo);
            return RestfulJson.isOk("添加地址成功！");
        }catch (Exception e){
            return RestfulJson.errorMsg(e.getMessage());
        }

    }

    @GetMapping("getUserAddress")
    public RestfulJson getUserAddress(HttpServletRequest request){
        String userId = request.getAttribute("openId").toString();
        return RestfulJson.isOk(shoppingService.getUserAddress(userId));

    }



}
