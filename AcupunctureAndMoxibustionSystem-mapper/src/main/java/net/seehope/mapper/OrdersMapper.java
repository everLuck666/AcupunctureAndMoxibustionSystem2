package net.seehope.mapper;

import net.seehope.pojo.Orders;

import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
public interface OrdersMapper extends tk.mybatis.mapper.common.Mapper<Orders> {
    //返回小程序用户订单信息
    List queryUserOrders(String userId);
}




