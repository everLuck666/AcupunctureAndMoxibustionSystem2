package com.github.wxpay.service;

import com.github.wxpay.bo.UserOrderInformationBo;

import java.util.Map;

public interface WxPayService {
    public Map<String,String>wxPay(String openId, String ipAddress, UserOrderInformationBo bo) throws Exception;
}
