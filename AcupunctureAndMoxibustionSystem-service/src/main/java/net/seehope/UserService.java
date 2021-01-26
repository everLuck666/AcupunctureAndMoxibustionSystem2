package net.seehope;

import net.seehope.pojo.Users;

import java.util.List;

public interface UserService {

    //查询用户的信息
    Users getUserInfo(String sno);


    //删除用户信息
    void deleteUser(String userId);

    //存用户信息
    void insertUser(Users user);

    Users login(Users user);

    //判断用户存不存在
    boolean isExist(String userId);

    //得到所有管理员
    List<Users> getAllManagers();


}
