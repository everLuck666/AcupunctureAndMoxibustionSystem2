package net.seehope.impl;

import net.seehope.IndexService;
import net.seehope.OrderService;
import net.seehope.mapper.OrdersMapper;
import net.seehope.pojo.Orders;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    IndexService indexService;
    @Override
    public String getTodayIncome() {
        String todays = indexService.getStartTime()+"";
        String todaye = indexService.getEndTime() + "";
        List<Orders> ordersList =  ordersMapper.getTodayIncome(todays,todaye);
        int sum = 0;
        for(Orders orders:ordersList){
            sum += orders.getOrderAmout();
        }

        return sum+"";
    }

    @Override
    public String getMonthIncome() {
        return null;
    }

    @Override
    public String totalIncome() {
        return null;
    }
}
