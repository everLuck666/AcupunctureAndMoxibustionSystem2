package com.github.wxpay.controller;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.wxpay.bo.UserOrderInformationBo;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.service.WxPayService;
import com.github.wxpay.vo.WxPayNotifyVO;
import io.swagger.annotations.Api;
import net.seehope.ShoppingService;
import net.seehope.common.OrderType;
import net.seehope.jwt.JWTUtils;

import net.seehope.pojo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("wxPay")
public class WxpayController {


    @Autowired
    WxPayService wxPayService;

    @Autowired
    ShoppingService shoppingService;



    @PostMapping(value = "/pay",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, String> pay(HttpServletRequest request, @RequestBody JSONObject jsonObject, @RequestHeader("token") String token) throws Exception {
        System.out.println("我进来了");
        System.out.println("token是"+token);

        DecodedJWT verify = JWTUtils.getTokenInfo(token);
        String userId = verify.getClaim("openId").asString();
        System.out.println("---------userId:"+userId);

        // 获取请求ip地址
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.indexOf(",") != -1) {
            String[] ips = ip.split(",");
            ip = ips[0].trim();
        }
        return wxPayService.wxPay(userId,ip,jsonObject);
    }

    @RequestMapping(value = "/success", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String success(HttpServletRequest request, @RequestBody WxPayNotifyVO param) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        System.out.println("开始下单了");
        if ("SUCCESS".equals(param.getReturn_code())) {
            result.put("return_code", "SUCCESS");
            result.put("return_msg", "OK");
            Orders bo = new Orders();
            bo.setUserId(param.getOpenid());
            bo.setOrderId(param.getTransaction_id());
            System.out.println("attach:"+param.getAttach());
            String[] attach = param.getAttach().split(":");
            String[] address = attach[0].split("#");
            String[] orderInfos = attach[1].split(".");
            for (String orderInfo:orderInfos) {
                String[] info = orderInfo.split("#");
                bo.setProductName(info[0]);
                bo.setProductNumber(attach[1]);
                bo.setRemark(attach[2]);
                bo.setUserName(address[0]);
                bo.setUserPhone(address[1]);
                bo.setUserAddress(address[2]);
                bo.setOrderTime(new Date());
                bo.setStatus(OrderType.Waiting.getType()+"");
                bo.setOrderAmout(Double.valueOf(param.getTotal_fee()));
                shoppingService.addOrders(bo);
            }



        }
        return WXPayUtil.mapToXml(result);
    }
}
