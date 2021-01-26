package net.seehope;

import com.github.pagehelper.PageInfo;
import net.seehope.pojo.bo.GetOrdersBo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OrdersService {
    //获取待处理订单数量
    Integer getWaitingOrders();

    //获取已完成订单数量
    Integer getFinishedOrders();

    //根据筛选条件获取所有订单信息，分页返回
    PageInfo getAllOrders(GetOrdersBo ordersBo);

    //导出订单信息到Excel
    ResponseEntity<byte[]> exportExcel(HttpServletRequest request, HttpServletResponse response, String excelName);



}
