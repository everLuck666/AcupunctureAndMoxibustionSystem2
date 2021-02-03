package net.seehope;

import net.seehope.pojo.bo.IlustrateBo;
import net.seehope.pojo.bo.XueWeiBo;
import net.seehope.pojo.vo.IlustrateTwoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IlustrateService {
    void addIlustrate(IlustrateBo ilustrateBo);

    void addXueWei(XueWeiBo weiBo, HttpServletRequest request);

    List<IlustrateTwoVo> getAllIlustrate();
    void deleteIlustrate(String treatId,String symptomId);

    //记录诊疗方案
    void updateRecord(String userId,String treatId);

}
