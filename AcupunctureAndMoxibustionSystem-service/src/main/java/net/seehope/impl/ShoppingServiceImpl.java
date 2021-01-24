package net.seehope.impl;

import net.seehope.ShoppingService;
import net.seehope.mapper.GoodsMapper;
import net.seehope.mapper.OrdersMapper;
import net.seehope.mapper.UserInfoMapper;
import net.seehope.pojo.Goods;

import net.seehope.pojo.UserInfo;
import net.seehope.pojo.bo.ShoppingCarBo;
import org.springframework.beans.factory.annotation.Autowired;
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
        return goodsMapper.selectAll();
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
    public List getShoppingCar(String userId) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        Set keys = redisTemplate.keys(userId + "*");
        System.out.println(keys);
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
     * 获取用户收货地址
     * @param userId
     * @return
     */
    @Override
    public List getUserAddress(String userId) {
        return null;

    }

    @Override
    public List getMyorders(String userId) {
        return ordersMapper.queryUserOrders(userId);
    }
}
