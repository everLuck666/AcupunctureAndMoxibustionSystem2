package net.seehope.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import net.seehope.LoginService;
import net.seehope.UserService;
import net.seehope.common.RestfulJson;
import net.seehope.common.UserType;
import net.seehope.exception.PassPortException;
import net.seehope.impl.UserServiceImpl;
import net.seehope.jwt.JWTUtils;
import net.seehope.pojo.Users;
import net.seehope.pojo.bo.ManagerBo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    UserService userService;
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
    @Transactional
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

                String sessionKey  = jsonObject.getString("session_key");
                log.info("用户的openId是"+user.getUserId());
                user.setIdentity(UserType.USER.getType());


                Calendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                calendar.add(Calendar.DAY_OF_MONTH,+7);
                Date cd = calendar.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String time = simpleDateFormat.format(cd);
                log.info("用户的token过期时间是"+time);
                map.put("expired",time);
                if(!userService.isExist(user.getUserId())){
                    user.setVersion("0");
                    userService.insertUser(user,UserType.USER.getType());
                }else{
                    Users usrs = userService.getUserInfo(user.getUserId());
                    int version = 0;
                    if(usrs.getVersion() != null){
                         version = Integer.valueOf(usrs.getVersion());
                    }

                    version++;
                    if(version == 1000){
                        version = 0;
                    }
                    userService.updateVersion(version+"",user.getUserId());
                    log.info("用户在别的地方登陆现在版本号是"+version);
                }
                Users users = userService.getUserInfo(user.getUserId());

                payload.put("openId",user.getUserId());
                payload.put("identity",UserType.USER.getType()+"");
                payload.put("version",users.getVersion());
                payload.put("sessionKey",sessionKey);
                log.info("下发的token中版本号是"+users.getVersion());

                String token = JWTUtils.getToken(payload);
                map.put("token",token);
                log.info("用户的token:"+token);
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
    @Transactional
    @PostMapping(value = "manager",produces="application/json;charset=UTF-8")
    public RestfulJson loginManger(@RequestBody ManagerBo bo){
        Users usrs = userService.getUserInfo(bo.getUsername());
        if(usrs == null){
            throw new RuntimeException("用户不存在");
        }
        int version = Integer.valueOf(usrs.getVersion());
        version++;
        if(version == 1000){
            version = 0;
        }
        usrs.setVersion(version+"");
        userService.updateVersion(version+"",bo.getUsername());//版本号+1

        Map<String,String> map = new HashMap();

        try {
            Users users =  userService.login(bo);
            if(users.getIdentity()==-1){
                return RestfulJson.errorMsg("管理员已经被删除");
            }
            Map<String, String> payload = new HashMap<>();
            payload.put("username", bo.getUsername());

            payload.put("openId", users.getUserId());
            payload.put("version",users.getVersion());
            log.info("管理员版本号"+users.getVersion());
            log.info("下发管理员token" + users.getUserId());
            payload.put("identity", users.getIdentity() + "");
            String token = JWTUtils.getToken(payload);
            map.put("token", token);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, +7);
            Date cd = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(cd);
            log.info("管理员的token过期时间是" + time);
            map.put("expired", time);
            map.put("identity", users.getIdentity() + "");
        } catch (PassPortException e){
            return RestfulJson.errorMsg("密码错误");
        }catch (Exception e){
            return RestfulJson.errorMsg("管理员不存在或者找到了两个用户");
        }
        return RestfulJson.isOk(map);
    }


}


