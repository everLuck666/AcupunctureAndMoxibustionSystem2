package net.seehope;

import net.seehope.pojo.Goods;
import net.seehope.pojo.Orders;
import net.seehope.pojo.UserInfo;
import net.seehope.pojo.bo.DeleteAddressBo;
import net.seehope.pojo.bo.ShoppingCarBo;

import java.util.List;

public interface ShoppingService {
    //获取所有商品信息
    List getAllGoods();

    //根据商品关键词搜索商品
    List<Goods> getGoodsByKeyword(String keyword);

    //加入购物车
    void addShoppingCar(ShoppingCarBo bo, String userId);

    //删除购物车里的商品
    void deleteShoppingCar(String key);

    //展示购物车
    List getShoppingCar(String userId);

    //添加地址
    void addAddress(UserInfo userInfo);

    void deleteAddress(String userId, DeleteAddressBo bo);

    //获取用户收货地址
    List getUserAddress(String userId);

    //获取我的订单信息
    List getMyorders(String userId);

    //下单
    void addOrders(Orders orders);

    //判断订单是否存在
    boolean orderIsExits(String orderId,String productName);

    //判断商品是否存在和是否为上架状态
    boolean goodsIsExits(String productName);
}
