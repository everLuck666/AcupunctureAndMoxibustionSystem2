package net.seehope;

import net.seehope.pojo.bo.IlustrateBo;
import net.seehope.pojo.bo.XueWeiBo;
import net.seehope.pojo.vo.IlustrateTwoVo;

import java.util.List;

public interface IlustrateService {
    void addIlustrate(IlustrateBo ilustrateBo);

    void addXueWei(XueWeiBo weiBo);

    List<IlustrateTwoVo> getAllIlustrate();
    void deleteIlustrate(String treatId,String symptomId);
}
