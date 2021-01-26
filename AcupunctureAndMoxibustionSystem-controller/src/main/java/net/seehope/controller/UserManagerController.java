package net.seehope.controller;

import net.seehope.UserService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserManagerController {
    @Autowired
    UserService userService;

    @PutMapping("user")
    public RestfulJson addManager(@RequestBody Users users){
        userService.insertUser(users);
        return RestfulJson.isOk("添加管理员成功");
    }

    @DeleteMapping("user")
    public RestfulJson deleteManager(@RequestBody Map map){
        userService.deleteUser(map.get("userId").toString());
        return RestfulJson.isOk("删除管理员成功");
    }
    //得到所有管理员
    @GetMapping("user")
    public RestfulJson getAllManagers(){
     return RestfulJson.isOk(userService.getAllManagers());
    }



}
