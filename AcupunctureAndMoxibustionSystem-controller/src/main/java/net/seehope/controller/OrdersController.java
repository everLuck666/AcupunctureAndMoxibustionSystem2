package net.seehope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.seehope.OrdersService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.bo.GetOrdersBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("orders")
@Api(tags = "订单管理",value = "OrdersController")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @GetMapping("waiting")

    public RestfulJson getWaitingOrders(){
        return RestfulJson.isOk(ordersService.getWaitingOrders());
    }

    @GetMapping("finished")
    public RestfulJson getFinishedOrders(){
        return RestfulJson.isOk(ordersService.getFinishedOrders());
    }

    @GetMapping("orders")
    public RestfulJson getAllOrders(@RequestBody GetOrdersBo ordersBo){
        return RestfulJson.isOk(ordersService.getAllOrders(ordersBo));
    }

    // 导出excel
    @ApiOperation("导出订单数据接口")
    @GetMapping("/exportExcel")
    public ResponseEntity<byte[]> exportOrdersExcel(HttpServletRequest request, HttpServletResponse response) {
        String excelName = "订单记录表";
        return ordersService.exportExcel(request,response,excelName);
    }

    //得到今天收入
    @GetMapping("income")
    @ApiOperation("得到今天收入")
    public RestfulJson getTodayIncome(){

       return RestfulJson.isOk(ordersService.getTodayIncome());

    }

    //得到本月收入
    @GetMapping("incomeMonth")
    @ApiOperation("得到本月收入")
    public RestfulJson getMonthIncome(){

        return RestfulJson.isOk(ordersService.getMonthIncome());

    }
    //累计收入
    @GetMapping("totalIncome")
    @ApiOperation("得到累计收入")
    public RestfulJson getTotalIncome(){
        return RestfulJson.isOk(ordersService.getTodayIncome());
    }
    //得到多天的每天数据
    @GetMapping("all")
    @ApiOperation("得到所有时间段的收入")
    public RestfulJson getAllOrderIncome(){
        return RestfulJson.isOk(ordersService.getAllIncome());
    }

}
