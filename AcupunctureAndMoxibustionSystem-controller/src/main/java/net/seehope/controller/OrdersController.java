package net.seehope.controller;

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
}
