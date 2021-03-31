package net.seehope.impl;

import com.alibaba.fastjson.JSONObject;

import net.seehope.PasswordDecryptService;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
@Service
public class PasswordDecryptServiceImpl implements PasswordDecryptService {

    Logger log = LoggerFactory.getLogger(PasswordDecryptService.class);


    @Override
    public String getUserInfo(String encryptedData, String sessionkey, String iv){
        log.info("开始解密：encryptedData="+encryptedData+", sessionkey="+sessionkey+",iv="+iv);
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionkey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                System.out.println("===================");
                System.out.println(result);
                System.out.println("==================");
                return result;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidParameterSpecException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//        public  String decrypt(String data, String key, String iv, String encodingFormat) {
//            byte[] dataByte = Base64.decodeBase64(data.getBytes());
//            byte[] keyByte = Base64.decodeBase64(key.getBytes());
//            byte[] ivByte = Base64.decodeBase64(iv.getBytes());
//            try {
//                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//                SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
//                AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
//                parameters.init(new IvParameterSpec(ivByte));
//                cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
//                byte[] resultByte = cipher.doFinal(dataByte);
//                if (null != resultByte && resultByte.length > 0) {
//                    return new String(resultByte, encodingFormat);
//                }
//                return null;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }



}






