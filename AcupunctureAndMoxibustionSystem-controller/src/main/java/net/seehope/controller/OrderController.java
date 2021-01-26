package net.seehope.controller;

import net.seehope.common.RestfulJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    //得到今天收入
    @GetMapping("income")
    public RestfulJson getTodayIncome(){

    }

}
