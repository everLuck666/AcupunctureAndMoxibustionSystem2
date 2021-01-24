package net.seehope;

import net.seehope.pojo.vo.IlustrateVo;
import net.seehope.pojo.vo.MyPlanVo;
import net.seehope.pojo.vo.SymptomInformationVo;

import java.util.List;

public interface IndexService {
    //得到我的诊疗计划

    List<MyPlanVo> getMyPlan(String userId);


    //得到说明页面的信息
    List<IlustrateVo> getIlustrate();

    //得到症状详情


    //搜索症状
    List<IlustrateVo> getIlustrateBySearch(String text);
    //得到症状的详情
    SymptomInformationVo getSymptomInformation(String symptomId,String treatId,String day);

    //增加我的诊疗方案
    void addMyPlay(String treatId,String userId,String symptomId);

    //判断这个诊疗方案是否已经添加过了
    boolean isCanAddPlan(String treatId,String userId,String symptomName);


















}
