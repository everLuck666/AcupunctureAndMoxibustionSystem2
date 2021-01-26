package net.seehope.impl;

import net.seehope.UserService;

import net.seehope.common.UserType;
import net.seehope.mapper.UsersMapper;
import net.seehope.pojo.Users;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.usagetracker.UsageTrackerClient;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UsersMapper usersMapper;


    @Override
    public Users getUserInfo(String sno) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        Users user = new Users();
        user.setUserId(userId);

        if (isExist(user.getUserId())){
            List list = usersMapper.selectAll();
            if(list.size() == 1){
                throw new RuntimeException("还剩一个管理员，禁止删除");
            }
            usersMapper.delete(user);
        }else{
            throw new RuntimeException("账号不存在");
        }


    }

    @Override
    public void insertUser(Users user) {
        if (isExist(user.getUserId())){
            throw new RuntimeException("账号存在");
        }else{
            user.setIdentity(UserType.SUPERMANAGER.getType());
            usersMapper.insert(user);
        }


    }

    @Override
    public Users login(Users user) {
        return null;
    }

    @Override
    public boolean isExist(String userId) {
        Users users = new Users();
        users.setUserId(userId);

        Users users1 =  usersMapper.selectOne(users);
        if(users1 != null){
            return true;
        }

        return false;
    }

    @Override
    public List<Users> getAllManagers() {
        Users users = new Users();
        users.setIdentity(UserType.SUPERMANAGER.getType());
        return usersMapper.select(users);
    }
}
