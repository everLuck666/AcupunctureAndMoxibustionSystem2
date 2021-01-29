package net.seehope.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.seehope.UserService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Users;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "管理员管理",value = "UserManagerController")
public class UserManagerController {
    @Autowired
    UserService userService;

    @PutMapping("user")
    @ApiOperation("添加管理员")
    @ApiImplicitParams({@ApiImplicitParam(name ="userName",value = "姓名",dataType = "String"),
            @ApiImplicitParam(name ="userId",value = "账号",dataType = "String"),
            @ApiImplicitParam(name ="password",value = "密码",dataType = "String")
    })
    public RestfulJson addManager(@RequestBody Users users){
        userService.insertUser(users);
        return RestfulJson.isOk("添加管理员成功");
    }

    @DeleteMapping("user")
    @ApiOperation("删除管理员")
    @ApiImplicitParams({@ApiImplicitParam(name ="userId",value = "账号",dataType = "String"),
    })
    public RestfulJson deleteManager(@RequestBody Map map){
        userService.deleteUser(map.get("userId").toString());
        return RestfulJson.isOk("删除管理员成功");
    }
    //得到所有管理员
    @GetMapping("user")
    @ApiOperation("得到所有的管理员信息")
    public RestfulJson getAllManagers(){
     return RestfulJson.isOk(userService.getAllManagers());
    }



}
