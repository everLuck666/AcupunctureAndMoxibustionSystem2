package net.seehope.impl;

import net.seehope.IlustrateService;
import net.seehope.mapper.*;
import net.seehope.pojo.*;
import net.seehope.pojo.bo.IlustrateBo;
import net.seehope.pojo.bo.XueWeiBo;
import net.seehope.pojo.vo.IlustrateTwoVo;
import net.seehope.pojo.vo.IlustrateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IlustrateServiceImpl implements IlustrateService {
    Logger logger = LoggerFactory.getLogger(IlustrateServiceImpl.class);
    @Autowired
    SymptomMapper symptomMapper;
    @Autowired
    TreatProjectMapper treatProjectMapper;
    @Autowired
    TreatMapper treatMapper;
    @Autowired
    XueWeiMapper xueWeiMapper;
    @Autowired
    XueTreatMapper xueTreatMapper;
    @Override
    @Transactional
    public void addIlustrate(IlustrateBo ilustrateBo) {
        Symptom symptom = new Symptom();
        symptom.setSymptomName(ilustrateBo.getSymptomName());
        Symptom symptomTemp = symptomMapper.selectOne(symptom);

        if(symptomTemp != null){
            throw new RuntimeException("这个症状已经存在，无法添加");
        }else{
            symptom.setSymptomId((symptomMapper.countId()+1)+"");
            symptom.setReason(ilustrateBo.getReason());
            symptom.setPath(ilustrateBo.getPath());
            symptomMapper.insert(symptom);
        }


        TreatProject treatProject = new TreatProject();
        treatProject.setTreatName(ilustrateBo.getTreatName());
        TreatProject treatProjectTemp = treatProjectMapper.selectOne(treatProject);
        if(treatProjectTemp != null){
            throw  new RuntimeException("这个诊疗方案已经存在,无法添加");
        }else {
            treatProject.setTreatId((treatProjectMapper.countId()+1)+"");
            treatProject.setEffect(ilustrateBo.getEffect());
            treatProject.setTotalTime(0+"");
            //  treatProject.setTreatDescribe(ilustrateBo);
            treatProjectMapper.insert(treatProject);
        }


        TreatProject treatProject1 = treatProjectMapper.selectOne(treatProject);
        Symptom symptom1 = symptomMapper.selectOne(symptom);

        Treat treat = new Treat();
        treat.setTreatId(treatProject1.getTreatId()+"");
        treat.setSymptomId(symptom1.getSymptomId()+"");
        Treat treatTemp = treatMapper.selectOne(treat);

        if(treatTemp != null){
            throw new RuntimeException("这个症状和诊疗方案的对应已经存在");
        }else{
            treat.setCreateTime(new Date());
            treatMapper.insert(treat);
        }

    }

    @Override
    @Transactional
    public void addXueWei(XueWeiBo weiBo) {
        int id = -1;
        XueWei xueWei = new XueWei();
        xueWei.setPointname(weiBo.getPointName());
        XueWei xueWeiTemp = xueWeiMapper.selectOne(xueWei);
        if(xueWeiTemp != null){
            logger.warn("出现相同穴位名称，执行不插入");

        }else {
            xueWei.setTemperature(weiBo.getTemperature());
            xueWei.setTreattime(Integer.parseInt(weiBo.getTreatTime()));
            id = xueWeiMapper.countId()+1;
            xueWei.setId(id);
            xueWei.setPath(weiBo.getPath());
            xueWeiMapper.insert(xueWei);
        }



        int treatProjectTemp = 0;
        try {
            treatProjectTemp = treatProjectMapper.exists(weiBo.getTreatProjectName());
        }catch (Exception e){
            throw new RuntimeException("诊疗方案不存在");
        }

            XueTreat xueTreat = new XueTreat();
            xueTreat.setTreatId(treatProjectTemp);
            xueTreat.setDay(Integer.parseInt(weiBo.getDay()));
            xueTreat.setXueId(id);
            xueTreatMapper.insert(xueTreat);

            TreatProject treatProject = new TreatProject();
            treatProject.setTreatId(treatProjectTemp+"");
            TreatProject treatProject1 = treatProjectMapper.selectOne(treatProject);
            if(Integer.parseInt(treatProject1.getTotalTime())+1 == Integer.parseInt(weiBo.getDay())){
                treatProjectMapper.delete(treatProject);
                treatProject1.setTotalTime((Integer.parseInt(treatProject1.getTotalTime())+1)+"");
                treatProjectMapper.insert(treatProject1);
            }else if(Integer.parseInt(treatProject1.getTotalTime())>=Integer.parseInt(weiBo.getDay())){
                logger.info("添加的诊疗方案不需要增加新的一天");
            }else{
                throw new RuntimeException("不能跨着增加天数");
            }



    }

    @Override
    public List<IlustrateTwoVo> getAllIlustrate() {
        List<Treat> treats = treatMapper.selectAll();
        List<IlustrateTwoVo> ilustrateVos = new ArrayList<>();
        for(Treat treat:treats){
            Symptom symptom = new Symptom();
            symptom.setSymptomId(treat.getSymptomId());
            Symptom symptom1 = symptomMapper.selectOne(symptom);


            TreatProject treatProject = new TreatProject();
            treatProject.setTreatId(treat.getTreatId());
            TreatProject treatProject1 = treatProjectMapper.selectOne(treatProject);

            IlustrateTwoVo ilustrateTwoVo = new IlustrateTwoVo();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ilustrateTwoVo.setCreate_Time(simpleDateFormat.format(treat.getCreateTime()));
            ilustrateTwoVo.setEffect(treatProject.getEffect());
            ilustrateTwoVo.setPath(symptom1.getPath());
            ilustrateTwoVo.setReason(symptom1.getReason());
            ilustrateTwoVo.setSymptomId(symptom1.getSymptomId());
            ilustrateTwoVo.setTreatName(treatProject1.getTreatName());
            ilustrateTwoVo.setTreateId(treatProject1.getTreatId());
            ilustrateTwoVo.setSymptomName(symptom1.getSymptomName());
            ilustrateVos.add(ilustrateTwoVo);
        }

        return ilustrateVos;
    }

    @Override
    @Transactional
    public void deleteIlustrate(String treatId, String symptomId) {
        Treat treat = new Treat();
        treat.setTreatId(treatId);
        treat.setSymptomId(symptomId);
        Treat treatTemp = treatMapper.selectOne(treat);
        if(treatTemp != null){
            Symptom symptom = new Symptom();
            symptom.setSymptomId(symptomId);
            symptomMapper.delete(symptom);

            TreatProject treatProject = new TreatProject();
            treatProject.setTreatId(treatId);
            treatProjectMapper.delete(treatProject);
            treatMapper.delete(treat);

        }else{
            throw new RuntimeException("不存在这条说明");
        }

    }
}
