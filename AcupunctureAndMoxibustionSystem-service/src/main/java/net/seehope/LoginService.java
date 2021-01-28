package net.seehope;

import net.seehope.pojo.Users;
import net.seehope.pojo.bo.ManagerBo;

public interface LoginService {
    void insertUser(Users user);

    boolean isExist(String userId);

    Users managerLogin(ManagerBo bo);
}
