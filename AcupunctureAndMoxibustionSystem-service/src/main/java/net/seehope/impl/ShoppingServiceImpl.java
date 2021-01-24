package net.seehope.impl;

import net.seehope.ShoppingService;
import net.seehope.mapper.GoodsMapper;
import net.seehope.mapper.UserInfoMapper;
import net.seehope.pojo.Goods;

import net.seehope.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

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

    @Override
    public void addShoppingCar() {

    }

    @Override
    public List getShoppingCar() {
        return null;
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
    public List getMyorders() {
        return null;
    }
}
