package net.seehope.impl;

import net.seehope.LoginService;
import net.seehope.mapper.UsersMapper;
import net.seehope.pojo.Users;
import net.seehope.pojo.bo.ManagerBo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UsersMapper usersMapper;

    @Override
    public void insertUser(Users user) {
        usersMapper.insert(user);
    }

    @Override
    public boolean isExist(String userId) {
        if(StringUtils.isBlank(userId)){
            return false;
        }
        Users users = new Users();
        users.setUserId(userId);
        if(usersMapper.selectOne(users)!=null){
            return true;
        }
        return false;
    }

    @Override
    public Users managerLogin(ManagerBo bo) {
        Users user = null;

        if (!StringUtils.isEmpty(bo.getUsername())) {
            Users temp = new Users();
            temp.setUserId(bo.getUsername());
            user = usersMapper.selectOne(temp);
            if (user == null) {
                throw new RuntimeException("用户不存在");
            }
            if (!StringUtils.equals(bo.getPassword(), user.getPassword())) {
                throw new RuntimeException("密码错误");
            }
        }

        return user;
    }
}
