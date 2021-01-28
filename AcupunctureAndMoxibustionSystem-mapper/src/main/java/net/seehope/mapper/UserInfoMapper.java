package net.seehope.mapper;

import net.seehope.pojo.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@Repository
public interface UserInfoMapper extends tk.mybatis.mapper.common.Mapper<UserInfo> {
    List queryUserAddress(String userId);

}




