package com.github.wxpay.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.wxpay.bo.UserOrderInformationBo;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.service.WxPayService;
import com.github.wxpay.vo.WxPayNotifyVO;
import net.seehope.ShoppingService;
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

    private static final String APPID = "wx22b4e8dc67f0ea0c";
    private static final String SECRET = "7c1355ff038ca93c0d49106ff367636e";

    @Autowired
    WxPayService wxPayService;

    @Autowired
    ShoppingService shoppingService;



    @PostMapping(value = "/pay",produces="application/json;charset=UTF-8")
    @ResponseBody
    public Map<String, String> pay(HttpServletRequest request, @RequestBody UserOrderInformationBo userOrderInformationBo,@RequestHeader("token") String token) throws Exception {
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
        return wxPayService.wxPay(userId,ip,userOrderInformationBo);
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
            String[] attach = param.getAttach().split("#");
            bo.setProductName(attach[0]);
            bo.setProductNumber(attach[1]);
            bo.setRemark(attach[2]);
            bo.setUserName(attach[3]);
            bo.setUserPhone(attach[4]);
            bo.setUserAddress(attach[5]);
            bo.setOrderTime(new Date());
            bo.setStatus("0");
            bo.setOrderAmout(Double.valueOf(param.getTotal_fee()));
            shoppingService.addOrders(bo);


        }
        return WXPayUtil.mapToXml(result);
    }
}
