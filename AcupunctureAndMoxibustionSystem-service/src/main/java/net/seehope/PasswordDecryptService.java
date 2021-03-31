package net.seehope;

import com.alibaba.fastjson.JSONObject;

public interface PasswordDecryptService {

    String getUserInfo(String encryptedData, String sessionkey, String iv);
   // String decrypt(String data, String key, String iv, String encodingFormat);
}
