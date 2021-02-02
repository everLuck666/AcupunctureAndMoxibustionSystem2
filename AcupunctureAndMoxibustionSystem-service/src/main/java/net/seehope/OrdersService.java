package net.seehope;

import com.github.pagehelper.PageInfo;
import net.seehope.pojo.bo.GetOrdersBo;
import net.seehope.pojo.vo.OrderVo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

public interface OrdersService {
    //获取待处理订单数量
    Integer getWaitingOrders();

    //获取已完成订单数量
    Integer getFinishedOrders();

    //根据筛选条件获取所有订单信息，分页返回
    PageInfo getAllOrders(GetOrdersBo ordersBo);

    void updateOrder(String orderId);

    //导出订单信息到Excel
    ResponseEntity<byte[]> exportExcel(HttpServletRequest request, HttpServletResponse response, String excelName);

    //得到今日收入

    String getTodayIncome();



    //得到本月收入
    String getMonthIncome();


    //得到累计收入
    String totalIncome();
    //得到多天的每日收入
    List<OrderVo> getAllIncome();

    //得到这一天的收入
    String getTodayIncome(Date date);



}
