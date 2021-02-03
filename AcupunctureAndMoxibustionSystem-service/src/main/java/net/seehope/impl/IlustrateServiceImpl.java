package net.seehope.impl;

import net.seehope.IlustrateService;
import net.seehope.IndexService;
import net.seehope.common.RecordStatus;
import net.seehope.mapper.*;
import net.seehope.pojo.*;
import net.seehope.pojo.bo.IlustrateBo;
import net.seehope.pojo.bo.XueWeiBo;
import net.seehope.pojo.vo.IlustrateTwoVo;
import net.seehope.pojo.vo.IlustrateVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    @Autowired
    IndexService indexService;
    @Autowired
    MedicalRecordMapper medicalRecordMapper;
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
             treatProject.setTreatDescribe(ilustrateBo.getDescribe());
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
    public void addXueWei(XueWeiBo weiBo, HttpServletRequest request) {
        int id = -1;

        boolean flag = true;
        boolean flag2 = false;//用来判断是否发生了穴位修改

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");


        XueWei xueWei = new XueWei();
        xueWei.setPointname(weiBo.getPointName());
        XueWei xueWeiTemp = xueWeiMapper.selectOne(xueWei);
        if(xueWeiTemp != null){
            logger.warn("出现相同穴位名称，执行不插入");
            id = Integer.parseInt(xueWeiTemp.getId());
            String fileName = null;
            logger.info("开始检测这个穴位的信息和数据库的存储是否有区别");

            for (int i = 0; i < files.size(); i++) {
                MultipartFile file = files.get(i);
                if (file.isEmpty()) {
                    throw new RuntimeException("上传的文件是空的");
                }
                fileName = file.getOriginalFilename();

            }
            if(fileName != null){
                xueWei.setPath(fileName);
            }
            xueWei.setTemperature(weiBo.getTemperature());
            xueWei.setTreattime(Integer.parseInt(weiBo.getTreatTime()));

            XueWei xueWei1 = xueWeiMapper.selectOne(xueWei);

            if(xueWei1 == null){
                logger.info("开始更改穴位信息");
                xueWeiMapper.delete(xueWeiTemp);
                flag2 = true;

            }else{
                flag = false;
                logger.info("检测完毕，穴位没有变化");
            }
        }

        if(flag) {
            //上传图片

            File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
            String path = "/src/main/resources/static/images/";
            String fileName = indexService.update(files, path);
            weiBo.setPath(fileName);

            xueWei.setTemperature(weiBo.getTemperature());
            xueWei.setTreattime(Integer.parseInt(weiBo.getTreatTime()));
            id = xueWeiMapper.countId()+1;
            if(flag2){
                xueWei.setId(xueWeiTemp.getId());
            }else {
                xueWei.setId(id+"");
            }
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
            xueTreat.setTreatId(treatProjectTemp+"");
            xueTreat.setDay(weiBo.getDay());
            xueTreat.setXueId(id+"");
            XueTreat xueTreat1 = xueTreatMapper.selectOne(xueTreat);
            if(xueTreat1 != null && !flag2){
                throw new RuntimeException("请不要重复提交请求");
            }

            if(!flag2){
                xueTreatMapper.insert(xueTreat);
            }else{
                logger.info("改变了穴位信息，但是不改变穴位和治疗方案的映射");
            }



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
            ilustrateTwoVo.setDescribe(treatProject1.getTreatDescribe());
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

            XueTreat xueTreat = new XueTreat();
            xueTreat.setTreatId(treatId);
            List<XueTreat> xueTreatList = xueTreatMapper.select(xueTreat);
            for (XueTreat xueTreat1:xueTreatList){

                xueTreatMapper.delete(xueTreat1);
            }

        }else{
            throw new RuntimeException("不存在这条说明");
        }

    }

    @Override
    @Transactional
    public void updateRecord(String userId, String treatId) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(userId);
        medicalRecord.setProjectId(treatId);

        MedicalRecord medicalRecordTemp = medicalRecordMapper.selectOne(medicalRecord);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(medicalRecordTemp != null){
            Date date = medicalRecordTemp.getCreateTime();
            String time = simpleDateFormat.format(date);
            String now = simpleDateFormat.format(new Date());
            if(StringUtils.equals(time,now)){
                if(Integer.parseInt(medicalRecordTemp.getProgress())== RecordStatus.OFF.getStatus()){
                    medicalRecordTemp.setCreateTime(new Date());
                        medicalRecordMapper.delete(medicalRecord);
                        medicalRecordTemp.setProgress(RecordStatus.UP.getStatus()+"");
                        medicalRecordMapper.insert(medicalRecordTemp);
                }

                logger.info("诊疗记录已经记录过了");
            }else{

                medicalRecordTemp.setCreateTime(new Date());
                if(Integer.parseInt(medicalRecordTemp.getTotalTime()) >= Integer.parseInt(medicalRecordTemp.getProgress())){
                    medicalRecordMapper.delete(medicalRecord);
                    medicalRecordTemp.setProgress((Integer.parseInt(medicalRecordTemp.getProgress())+1)+"");
                    medicalRecordMapper.insert(medicalRecordTemp);
                }else{
                    logger.info("您已经完成了诊疗计划");
                }

            }

        }


    }
}
