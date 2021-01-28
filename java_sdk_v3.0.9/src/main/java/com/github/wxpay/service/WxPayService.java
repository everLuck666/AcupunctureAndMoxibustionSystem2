package com.github.wxpay.service;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.bo.UserOrderInformationBo;

import java.util.Map;

public interface WxPayService {
    public Map<String,String>wxPay(String openId, String ipAddress, JSONObject bo) throws Exception;
}
