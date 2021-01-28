package com.github.wxpay.service.impl;

import com.github.wxpay.service.OrderPriceService;
import net.seehope.mapper.GoodsMapper;
import net.seehope.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPriceServiceImpl implements OrderPriceService {
    @Autowired
    GoodsMapper goodsMapper;
    public double getOrderPrice(String name, Integer number){
        Goods good = new Goods();
        good.setProductName(name);
        Goods goods = goodsMapper.selectOne(good);
        double price = number * goods.getProductPrice();
        return price;
    }
}
