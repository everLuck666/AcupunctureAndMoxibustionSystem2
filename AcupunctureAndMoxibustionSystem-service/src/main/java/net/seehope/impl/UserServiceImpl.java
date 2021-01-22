package net.seehope.impl;

import net.seehope.UserService;

import net.seehope.pojo.Users;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public Users getUserInfo(String sno) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public void insertUser(Users user) {

    }

    @Override
    public Users login(Users user) {
        return null;
    }
}
