package com.github.wxpay.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.bo.UserOrderInformationBo;
import com.github.wxpay.constant.WechatConstant;
import com.github.wxpay.sdk.*;
import com.github.wxpay.service.OrderPriceService;
import com.github.wxpay.service.WxPayService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {

    @Autowired
    OrderPriceService orderPriceService;

    @Override
    @Transactional
    public Map<String, String> wxPay(String openId, String ipAddress, JSONObject bo) throws Exception {

        Map<String, String> paraMap = new HashMap<String, String>();
//        paraMap.put("body", bo.getSpecies()+"*"+bo.getTicketNum()+"张"); // 商家名称-销售商品类⽬、String(128)
        paraMap.put("openid", openId); // openId，通过登录获取
        paraMap.put("out_trade_no", UUID.randomUUID().toString().replaceAll("-", ""));// 订单号,每次都不同 paraMap.put("spbill_create_ip", ipAddress);

        String attach = "";
        double totalFee = 0;
        JSONArray jsonArray = bo.getJSONArray("orders");
        JSONObject jsonObject = bo.getJSONObject("address");
        String userName = jsonObject.getString("userName");
        String userPhone = jsonObject.getString("userPhone");
        String address = jsonObject.getString("address");
        attach = userName + "#" + userPhone + "#" + address + ":";
        for(int i =0;i<jsonArray.size();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            String productName  = object.getString("productName");
            String productNumber = object.getString("productNumber");
            String remark = object.getString("remark");
            attach = attach + productName + "#" + productNumber + "#" + remark + ".";
            totalFee += orderPriceService.getOrderPrice(productName,Integer.valueOf(productNumber));
        }

        paraMap.put("attach",attach);

        paraMap.put("total_fee", String.valueOf(totalFee)); // ⽀付⾦额，单位分，即0.01元
        paraMap.put("trade_type", "JSAPI");
        paraMap.put("spbill_create_ip",ipAddress);

        // 2. 通过MyWXPayConfig创建WXPay对象，⽤于⽀付请求
        final String SUCCESS_NOTIFY = "http://localhost:8080/wxPay/success";
        boolean useSandbox = false; // 是否使⽤沙盒环境⽀付API，是的话不会真正扣钱 WXPayConfig config = new MyWXPayConfig();
        WXPayConfig config = new MyWXPayConfig();
        WXPay wxPay = new WXPay(config, SUCCESS_NOTIFY, false, useSandbox);

        Map<String, String> map = wxPay.unifiedOrder(wxPay.fillRequestData(paraMap), 15000, 15000);//这个方法里面包含一次签名


        // 4. 发送post请求"统⼀下单接⼝"(https://api.mch.weixin.qq.com/pay/unifiedorder), 返回预⽀付id:prepay_id String prepayId = (String) map.get("prepay_id");
        String prepayId = (String) map.get("prepay_id");
        System.out.println("我拿到的预处理id是"+prepayId);
        Map<String, String> payMap = new HashMap<String, String>();
        payMap.put("appId", WechatConstant.APPID);
        payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
        payMap.put("nonceStr", WXPayUtil.generateNonceStr());
        if (useSandbox) {
            payMap.put("signType", WXPayConstants.MD5);

        }else {
            payMap.put("signType", WXPayConstants.HMACSHA256);
        }
        payMap.put("package","prepay_id="+prepayId);
        //通过appid,timeStamp,nonceStr,signType,package及商户密钥进行key=value拼接
        String paySign = null;
        if (useSandbox) {

            paySign = WXPayUtil.generateSignature(payMap, WechatConstant.MCH_KEY, WXPayConstants.SignType.MD5);
        } else {

            paySign = WXPayUtil.generateSignature(payMap, WechatConstant.MCH_KEY, WXPayConstants.SignType.HMACSHA256);
        }
        payMap.put("paySign", paySign);
        return payMap;
    }

}
