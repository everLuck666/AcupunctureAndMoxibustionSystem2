package net.seehope.mapper;

import net.seehope.pojo.TreatProject;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@CacheNamespace
public interface TreatProjectMapper extends tk.mybatis.mapper.common.Mapper<TreatProject> {

    String exists(@Param("treatName") String treatName);

}




