package net.seehope;

import net.seehope.pojo.Symptom;
import net.seehope.pojo.bo.IlustrateBo;
import net.seehope.pojo.bo.XueWeiBo;
import net.seehope.pojo.vo.IlustrateTwoVo;
import net.seehope.pojo.vo.SymptomInformationVo;
import net.seehope.pojo.vo.XueWeiAndDayVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IlustrateService {
    void addIlustrate(IlustrateBo ilustrateBo);

    void addXueWei(XueWeiBo weiBo, HttpServletRequest request);

    List<IlustrateTwoVo> getAllIlustrate();
    void deleteIlustrate(String treatId,String symptomId);

    //记录诊疗方案
    void updateRecord(String userId,String treatId);

    //返回详细的说明信息
    List<XueWeiAndDayVo> getIlustrateInfomation(String treatId);

    //得到病症的详细信息
    Symptom getSymptomInfomation(String symptomId);

    //删除指定项目的某个穴位
    void deleteXueWei(String treatId,String xueWeiId);

}
