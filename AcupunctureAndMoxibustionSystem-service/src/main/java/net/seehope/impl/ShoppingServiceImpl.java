package net.seehope.impl;

import net.seehope.ShoppingService;
import net.seehope.common.GoodsStatus;
import net.seehope.mapper.GoodsMapper;
import net.seehope.mapper.OrdersMapper;
import net.seehope.mapper.UserInfoMapper;
import net.seehope.pojo.Goods;

import net.seehope.pojo.Orders;
import net.seehope.pojo.UserInfo;
import net.seehope.pojo.bo.DeleteAddressBo;
import net.seehope.pojo.bo.ShoppingCarBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.Duration;
import java.util.List;
import java.util.Set;


@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /**
     * 获取所有商品信息
     * @return
     */
    @Override
    public List getAllGoods() {
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", GoodsStatus.ON.getStatus()+"");
        return goodsMapper.selectByExample(example);
    }

    /**
     * 根据关键词搜索商品
     * @param keyword
     * @return
     */
    @Override
    public List<Goods> getGoodsByKeyword(String keyword) {
        String key = "%" + keyword + "%";
        Example example = new Example(Goods.class);
        Example.Criteria criteria = example.createCriteria();
        if (keyword != null && keyword != ""){
            criteria.andLike("productName",key);
        }
        criteria.andEqualTo("status", GoodsStatus.ON.getStatus()+"");
        return goodsMapper.selectByExample(example);
    }

    /**
     * 将商品添加进Redis，每次只能添加一个
     * @param bo
     */
    @Override
    public void addShoppingCar(ShoppingCarBo bo, String userId) {
        //序列化字符串方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ShoppingCarBo shoppingCarBo = (ShoppingCarBo) redisTemplate.opsForValue().get(userId+bo.getProductName());
        //判断是否为空
        if (null == shoppingCarBo){
            redisTemplate.opsForValue().set(userId+bo.getProductName(),bo, Duration.ofMinutes(30L));
        }else {
            //不为空则是已经添加过了，数量加一即可
            String num = String.valueOf(Integer.valueOf(shoppingCarBo.getProductNumber()) + 1);
            String orderAmout = String.valueOf(Integer.valueOf(shoppingCarBo.getOrder_amout())+Integer.valueOf(shoppingCarBo.getProductPrice()));
            shoppingCarBo.setProductNumber(num);
            shoppingCarBo.setOrder_amout(orderAmout);
            redisTemplate.opsForValue().set(userId+bo.getProductName(),shoppingCarBo, Duration.ofMinutes(30L));
        }

    }

    @Override
    public void deleteShoppingCar(String key) {
        ShoppingCarBo shoppingCarBo = (ShoppingCarBo) redisTemplate.opsForValue().get(key);
        if (null != shoppingCarBo){
            redisTemplate.delete(key);
        }
    }

    @Override
    public List getShoppingCar(String userId) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Set keys = redisTemplate.keys(userId + "*");
        System.out.println("获取购物车"+keys);
        List result = redisTemplate.opsForValue().multiGet(keys);
        return result;
    }

    /**
     * 添加用户收货地址
     * @param userInfo
     */
    @Override
    public void addAddress(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    /**
     * 删除用户收货地址
     * @param userId
     * @param bo
     */
    @Override
    public void deleteAddress(String userId, DeleteAddressBo bo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserName(bo.getUserName());
        userInfo.setPhone(bo.getPhone());
        userInfo.setAddress(bo.getAddress());
        List<UserInfo> res = userInfoMapper.select(userInfo);
        if(res != null){
            userInfoMapper.deleteAddress(userId,bo.getAddress(),bo.getUserName(),bo.getPhone());
        }
    }

    /**
     * 获取用户收货地址
     * @param userId
     * @return
     */
    @Override
    public List getUserAddress(String userId) {
        return userInfoMapper.queryUserAddress(userId);

    }

    @Override
    public List getMyorders(String userId) {
        return ordersMapper.queryUserOrders(userId);
    }

    @Override
    public synchronized void addOrders(Orders bo) {
        try {
            ordersMapper.insert(bo);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean orderIsExits(String orderId, String productName) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setProductName(productName);
        if (null == ordersMapper.selectOne(orders)){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean goodsIsExits(String productName) {
        Goods goods = new Goods();
        goods.setProductName(productName);
        goods.setStatus("1");
        if (null == goodsMapper.selectOne(goods)){
            return true;
        }else {
            return false;
        }

    }
}
