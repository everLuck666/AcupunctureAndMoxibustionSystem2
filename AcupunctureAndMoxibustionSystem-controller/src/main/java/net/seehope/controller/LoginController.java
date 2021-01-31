package net.seehope.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.seehope.LoginService;
import net.seehope.common.RestfulJson;
import net.seehope.common.UserType;
import net.seehope.exception.PassPortException;
import net.seehope.jwt.JWTUtils;
import net.seehope.pojo.Users;
import net.seehope.pojo.bo.ManagerBo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags="微信登录获取token接口",value = "LoginController")
@RequestMapping("user")
@RestController
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class LoginController  {
    @Autowired
    LoginService loginService;
    private static final long  serialVersionUID=1L;

    private static final String APPID = "wxd5dc9647089cd5bf";
    private static final String SECRET = "c4f4acaa0a2015ae3083fd8dda3b34b6";
    private String code;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }


    //
    @RequestMapping(value = "/login",produces="application/json;charset=UTF-8")
    @ApiOperation("获取token接口")
    @ApiModelProperty("code是必须的")
    public RestfulJson login(@ApiParam(name="code",value="微信授权登录成功后返回的code",required=true)String code){
        log.info("code:"+code);
        System.out.println("------------------------------------");
        //微信那边的接口，grant_type=authorization_code是固定的
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+
                "&secret="+SECRET+"&js_code="+ code +"&grant_type=authorization_code";
        //发送请求给微信后端
        CloseableHttpClient httpClient= HttpClients.createDefault();
        HttpGet httpGet=new HttpGet(url);
        InputStream inputStream=null;
        CloseableHttpResponse httpResponse=null;
        StringBuilder result=new StringBuilder();

        Users user=new Users();

        Map<String,String> payload = new HashMap<>();
        Map map = new HashMap();

        try {
            httpResponse=httpClient.execute(httpGet);
            HttpEntity entity=httpResponse.getEntity();
            inputStream=entity.getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=bufferedReader.readLine())!=null){
                // System.out.println(line); //这里需要使用fastjson来提取一下内容
                JSONObject jsonObject= JSON.parseObject(line);

                user.setUserId(jsonObject.getString("openid"));
                log.info("用户的openId是"+user.getUserId());
                user.setIdentity(UserType.USER.getType());


                payload.put("openId",user.getUserId());
                payload.put("identity",UserType.USER.getType()+"");

                String token = JWTUtils.getToken(payload);
                map.put("token",token);

                log.info("用户的token:"+token);

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_MONTH,+7);
                Date cd = calendar.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String time = simpleDateFormat.format(cd);
                log.info("用户的token过期时间是"+time);
                map.put("expired",time);
                map.put("openId",user.getUserId());
                if(!loginService.isExist(user.getUserId())){
                    loginService.insertUser(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return RestfulJson.isOk(map);
    }

    @ApiOperation("管理员登录获取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String")
    })
    @PostMapping(value = "manager",produces="application/json;charset=UTF-8")
    public RestfulJson loginManger(@RequestBody ManagerBo bo){
        Map<String,String> map = new HashMap();
        try {
            Users users =  loginService.managerLogin(bo);
            Map<String,String> payload = new HashMap<>();
            payload.put("username",bo.getUsername());

            payload.put("userId",users.getUserId());
            log.info("下发管理员token"+users.getUserId());
            payload.put("identity",users.getIdentity()+"");
            String token = JWTUtils.getToken(payload);
            map.put("token",token);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH,+7);
            Date cd = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(cd);
            log.info("管理员的token过期时间是"+time);
            map.put("expired",time);
            map.put("openId",users.getUserId());
        } catch (PassPortException e){
            return RestfulJson.errorMsg("密码错误");
        } catch (Exception e){
            return RestfulJson.errorMsg("管理员不存在");

        }
        return RestfulJson.isOk(map);
    }


}


