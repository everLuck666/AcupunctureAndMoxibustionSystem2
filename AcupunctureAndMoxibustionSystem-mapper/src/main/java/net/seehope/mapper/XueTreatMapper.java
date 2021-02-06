package net.seehope.mapper;

import net.seehope.pojo.XueTreat;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@CacheNamespace
public interface XueTreatMapper extends tk.mybatis.mapper.common.Mapper<XueTreat> {

    //查看这个诊疗方案有多少个穴位

    int countXueWei(@Param("treatId") String treatId);




}




