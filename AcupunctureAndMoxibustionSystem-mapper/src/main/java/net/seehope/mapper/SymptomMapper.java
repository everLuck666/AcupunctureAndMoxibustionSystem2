package net.seehope.mapper;

import net.seehope.pojo.Symptom;
import net.seehope.pojo.vo.IlustrateVo;
import org.apache.ibatis.annotations.CacheNamespace;

import java.util.List;

/**
* 通用 Mapper 代码生成器
*
* @author mapper-generator
*/
@CacheNamespace
public interface SymptomMapper extends tk.mybatis.mapper.common.Mapper<Symptom> {
    List<IlustrateVo> getIlustrateBySearch(String text);

    int countId();
}




