package net.seehope;

import net.seehope.pojo.Goods;
import net.seehope.pojo.UserInfo;

import java.util.List;

public interface ShoppingService {
    //获取所有商品信息
    List getAllGoods();

    //根据商品关键词搜索商品
    List<Goods> getGoodsByKeyword(String keyword);

    //加入购物车
    void addShoppingCar();

    //展示购物车
    List getShoppingCar();

    //添加地址
    void addAddress(UserInfo userInfo);

    //获取用户收货地址
    List getUserAddress(String userId);

    //获取我的订单信息
    List getMyorders();
}
