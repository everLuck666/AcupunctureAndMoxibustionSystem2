package com.github.wxpay.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.wxpay.bo.UserOrderInformationBo;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.service.WxPayService;
import com.github.wxpay.vo.WxPayNotifyVO;
import net.seehope.jwt.JWTUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("wxPay")
public class WxpayController {

    private static final String APPID = "wx22b4e8dc67f0ea0c";
    private static final String SECRET = "7c1355ff038ca93c0d49106ff367636e";

    @Autowired
    WxPayService wxPayService;



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


        }
        return WXPayUtil.mapToXml(result);
    }

    @RequestMapping("xiao")
    public void sss(){
        System.out.println("lal");
    }






}
