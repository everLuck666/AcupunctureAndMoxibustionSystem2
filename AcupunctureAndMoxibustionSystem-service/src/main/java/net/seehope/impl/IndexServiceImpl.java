package net.seehope.impl;


import net.seehope.IndexService;
import net.seehope.common.PlanStatus;
import net.seehope.mapper.*;
import net.seehope.pojo.*;
import net.seehope.pojo.vo.IlustrateVo;
import net.seehope.pojo.vo.MyPlanVo;
import net.seehope.pojo.vo.SymptomInformationVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service

public class IndexServiceImpl implements IndexService {

    @Autowired
    MedicalRecordMapper medicalRecordMapper;

    @Autowired
    TreatProjectMapper treatProjectMapper;

    @Autowired
    TreatMapper treatMapper;
    @Autowired
    SymptomMapper symptomMapper;

    @Autowired
    XueTreatMapper xueTreatMapper;
    @Autowired
    XueWeiMapper xueWeiMapper;





    @Override
    public List<MyPlanVo> getMyPlan(String userId) {
        List<MyPlanVo> myPlanVoList = new ArrayList<>();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(userId);
        List<MedicalRecord> medicalRecordList = medicalRecordMapper.select(medicalRecord);
        for(MedicalRecord medicalRecordTemp: medicalRecordList){
            //得到针灸的总时间
            XueTreat xueTreat = new XueTreat();

            xueTreat.setTreatId(Integer.parseInt(medicalRecordTemp.getProjectId()));

            List<XueTreat> xueTreatList = xueTreatMapper.select(xueTreat);
            int xueTotalTime =  0 ;
            for(XueTreat xueTreat1:xueTreatList){
                XueWei xueWei = new XueWei();
                xueWei.setId(xueTreat1.getXueId());
                XueWei xueWei1 = xueWeiMapper.selectOne(xueWei);
                xueTotalTime += xueWei1.getTreattime();

            }



           MyPlanVo myPlanVo = new MyPlanVo();
           myPlanVo.setDate(medicalRecordTemp.getTotalTime());
           myPlanVo.setName(medicalRecordTemp.getSymptomName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

           myPlanVo.setCreateTime(simpleDateFormat.format(medicalRecordTemp.getCreateTime()));


               myPlanVo.setTotalTime(xueTotalTime+"");//诊疗总时间

           Integer totalTime = Integer.parseInt(medicalRecordTemp.getTotalTime());
           Integer processTime = Integer.parseInt(medicalRecordTemp.getProgress());

            if(totalTime>processTime){
                myPlanVo.setStatus(PlanStatus.UNFINISH.getStatus());
            }else{
                myPlanVo.setStatus(PlanStatus.FINISH.getStatus());
            }

            myPlanVo.setProcess(medicalRecordTemp.getProgress());
            myPlanVoList.add(myPlanVo);
        }

        return myPlanVoList;
    }

    @Override
    public List<IlustrateVo> getIlustrate() {
        List ilustrateVoList = new ArrayList();

        List<Treat> treatsList = treatMapper.selectAll();
        for(Treat treat:treatsList){

            Symptom symptom = new Symptom();
            symptom.setSymptomId(treat.getSymptomId());

            Symptom symptom1 = symptomMapper.selectOne(symptom);
            //查询针灸描述
            TreatProject treatProject = new TreatProject();
            treatProject.setTreatId(treat.getTreatId());
            TreatProject treatProject1 = treatProjectMapper.selectOne(treatProject);


            IlustrateVo ilustrateVo = new IlustrateVo();
            ilustrateVo.setDescribe(treatProject1.getTreatDescribe());
            ilustrateVo.setSymptomName(symptom1.getSymptomName());
            ilustrateVo.setTreatId(treat.getTreatId());
            ilustrateVo.setSymptomId(treat.getSymptomId());

            ilustrateVoList.add(ilustrateVo);
        }



        return ilustrateVoList;
    }

    @Override
    public List<IlustrateVo> getIlustrateBySearch(String text) {

        return symptomMapper.getIlustrateBySearch(text);
    }

    @Override
    public SymptomInformationVo getSymptomInformation(String symptomId, String treatId,String day) {

        SymptomInformationVo symptomInformationVo = new SymptomInformationVo();


        Symptom symptom = new Symptom();
        symptom.setSymptomId(symptomId);
        Symptom symptom1 = symptomMapper.selectOne(symptom);

        symptomInformationVo.setSymptonName(symptom1.getSymptomName());
        symptomInformationVo.setReason(symptom1.getReason());

        TreatProject treatProject = new TreatProject();
        treatProject.setTreatId(treatId);
        TreatProject treatProject1 = treatProjectMapper.selectOne(treatProject);

        symptomInformationVo.setTotalDay(treatProject1.getTotalTime());

        symptomInformationVo.setEffect(treatProject1.getEffect());
        symptomInformationVo.setDescribe(treatProject1.getTreatDescribe());


        XueTreat xueTreat = new XueTreat();
        xueTreat.setDay(Integer.parseInt(day));
        xueTreat.setTreatId(Integer.parseInt(treatId));

        List<XueTreat> xueTreatList = xueTreatMapper.select(xueTreat);
        List<XueWei> xueWeis = new ArrayList<>();
        for(XueTreat xueTreat1 : xueTreatList){
            XueWei xueWei = new XueWei();
            xueWei.setId(xueTreat1.getXueId());
            XueWei xueWei1 = xueWeiMapper.selectOne(xueWei);
            xueWeis.add(xueWei1);
        }

        symptomInformationVo.setXueWeiList(xueWeis);


        return symptomInformationVo;
    }
    @Transactional
    @Override
    public void addMyPlay(String treatId, String userId, String symptomId) {

        Symptom symptom = new Symptom();
        symptom.setSymptomId(symptomId);
        Symptom symptom1 = symptomMapper.selectOne(symptom);

        TreatProject treatProject = new TreatProject();
        treatProject.setTreatId(treatId);
        TreatProject treatProject1 = treatProjectMapper.selectOne(treatProject);

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(userId);
        medicalRecord.setCreateTime(new Date());
        medicalRecord.setProgress(0+"");
        medicalRecord.setProjectId(treatId);
        medicalRecord.setSymptomName(symptom1.getSymptomName());
        medicalRecord.setTotalTime(treatProject1.getTotalTime());

           if(isCanAddPlan(treatId,userId,medicalRecord.getSymptomName())){
               medicalRecordMapper.insert(medicalRecord);
           }else {
               throw new RuntimeException("已经添加过这个方案了");
           }

    }

    @Override
    public boolean isCanAddPlan(String treatId, String userId, String symptomName) {


        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(userId);


        medicalRecord.setProjectId(treatId);
        medicalRecord.setSymptomName(symptomName);

        MedicalRecord medicalRecord1 = medicalRecordMapper.selectOne(medicalRecord);
        if(medicalRecord1 == null){


            return true;
        }


        return false;
    }


}
