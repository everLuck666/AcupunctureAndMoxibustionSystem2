package net.seehope.mapper;

import net.seehope.pojo.Orders;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@Repository
public interface OrdersMapper extends tk.mybatis.mapper.common.Mapper<Orders> {
    //返回小程序用户订单信息
    List queryUserOrders(String userId);


    List<Orders> getTodayIncome(@Param("todays") String todays, @Param("todaye")String todaye);

    String getMonthIncome();


    String totalIncome();

    //返回待处理订单数量
    Integer queryWaitingOrders();

    //返回已完成订单数量
    Integer queryFinishedOrders();

}




