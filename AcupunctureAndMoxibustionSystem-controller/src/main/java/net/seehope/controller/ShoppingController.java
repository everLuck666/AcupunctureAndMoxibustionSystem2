package net.seehope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.seehope.ShoppingService;
import net.seehope.common.RestfulJson;
import net.seehope.mapper.UserInfoMapper;
import net.seehope.pojo.UserInfo;
import net.seehope.pojo.bo.ShoppingCarBo;
import net.seehope.pojo.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags="小程序商城接口",value = "ShoppingController")
@RestController
@RequestMapping("shopping")
public class ShoppingController {
    @Autowired
    ShoppingService shoppingService;

    @ApiOperation("获取所有商品")
    @GetMapping("getAllGoods")
    public RestfulJson getAllGoods(){
        return RestfulJson.isOk(shoppingService.getAllGoods());
    }

    @ApiOperation("根据关键词搜索商品")
    @GetMapping("getGoodsBy")
    public RestfulJson getGoodsByKeyword(String keyword){
        return RestfulJson.isOk(shoppingService.getGoodsByKeyword(keyword));
    }

    @ApiOperation("添加用户收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "收货人姓名", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", dataType = "String"),
            @ApiImplicitParam(name = "address",value = "收货地址", dataType = "String"),
    })
    @PutMapping("addAddress")
    public RestfulJson addAddress(@RequestBody UserInfoVo userInfoVo, HttpServletRequest request){
        String userId = request.getAttribute("openId").toString();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(userInfoVo.getUserName());
        userInfo.setPhone(userInfoVo.getPhone());
        userInfo.setAddress(userInfoVo.getAddress());
        try {
            shoppingService.addAddress(userInfo);
            return RestfulJson.isOk("添加地址成功！");
        }catch (Exception e){
            return RestfulJson.errorMsg(e.getMessage());
        }

    }

    @ApiOperation("获取用户收货地址")
    @GetMapping("getUserAddress")
    public RestfulJson getUserAddress(HttpServletRequest request){
        String userId = request.getAttribute("openId").toString();
        return RestfulJson.isOk(shoppingService.getUserAddress(userId));

    }

    /**
     * 获取用户订单信息
     * @param request
     * @return
     */
    @ApiOperation("获取用户订单")
    @GetMapping("getUserOrders")
    public RestfulJson getUserOrders(HttpServletRequest request){
        String userId = request.getAttribute("openId").toString();
        return RestfulJson.isOk(shoppingService.getMyorders(userId));

    }

    /**
     * 加入购物车
     * @param bo
     * @param request
     * @return
     */
    @ApiOperation("加入购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productName", value = "商品名称", dataType = "String"),
            @ApiImplicitParam(name = "productNumber", value = "商品数量", dataType = "String"),
            @ApiImplicitParam(name = "productPrice",value = "商品价格", dataType = "String"),
            @ApiImplicitParam(name = "order_amout", value = "订单总额", dataType = "String")
    })
    @PutMapping("addShopping")
    public RestfulJson addShoppingCar(@RequestBody ShoppingCarBo bo, HttpServletRequest request){
        String userId = request.getAttribute("openId").toString();
//        String userId = "123";
        try {
            shoppingService.addShoppingCar(bo,userId);
            return RestfulJson.isOk("加入购物车成功！");
        }catch (Exception e){
            return RestfulJson.errorMsg(e.getMessage());
        }

    }

    @ApiOperation("获取用户购物车信息")
    @GetMapping("getShopping")
    public RestfulJson getShoppingCar(HttpServletRequest request){
        String userId = request.getAttribute("openId").toString();
        return RestfulJson.isOk(shoppingService.getShoppingCar(userId));
    }



}
