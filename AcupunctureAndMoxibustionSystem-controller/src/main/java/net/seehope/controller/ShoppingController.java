package net.seehope.controller;

import net.seehope.ShoppingService;
import net.seehope.common.RestfulJson;
import net.seehope.mapper.UserInfoMapper;
import net.seehope.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("shopping")
public class ShoppingController {
    @Autowired
    ShoppingService shoppingService;





}
