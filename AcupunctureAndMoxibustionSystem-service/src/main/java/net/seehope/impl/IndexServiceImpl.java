package net.seehope.impl;


import net.seehope.IndexService;
import net.seehope.common.FilePath;
import net.seehope.common.MedicalRecordType;
import net.seehope.common.PlanStatus;
import net.seehope.common.RecordStatus;
import net.seehope.mapper.*;
import net.seehope.pojo.*;
import net.seehope.pojo.vo.IlustrateVo;
import net.seehope.pojo.vo.MyPlanVo;
import net.seehope.pojo.vo.SymptomInformationVo;

import org.apache.commons.lang.StringUtils;
import org.omg.SendingContext.RunTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service

public class IndexServiceImpl implements IndexService {
    Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

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
    public List<MyPlanVo> getMyPlan(String userId,String status) {//status 1表示没有删除的，如果为-1就是表示删除的。
        List<MyPlanVo> myPlanVoList = new ArrayList<>();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(userId);
        if(!StringUtils.equals(status, MedicalRecordType.ALL.getStatus())){
            medicalRecord.setStatus(status);
        }
        List<MedicalRecord> medicalRecordList = medicalRecordMapper.select(medicalRecord);
        for(MedicalRecord medicalRecordTemp: medicalRecordList){
            //得到针灸的总时间
            XueTreat xueTreat = new XueTreat();

            xueTreat.setTreatId(medicalRecordTemp.getProjectId());

            List<XueTreat> xueTreatList = xueTreatMapper.select(xueTreat);
            int xueTotalTime =  0 ;
            for(XueTreat xueTreat1:xueTreatList){
                XueWei xueWei = new XueWei();
                xueWei.setId(xueTreat1.getXueId());
                XueWei xueWei1 = xueWeiMapper.selectOne(xueWei);
                xueTotalTime += xueWei1.getTreattime();

            }



           MyPlanVo myPlanVo = new MyPlanVo();
            myPlanVo.setTreatId(medicalRecordTemp.getProjectId());
           myPlanVo.setDate(medicalRecordTemp.getTotalTime());
           myPlanVo.setName(medicalRecordTemp.getSymptomName());

           Symptom symptom = new Symptom();
           symptom.setSymptomName(medicalRecordTemp.getSymptomName());
           Symptom symptomValue = symptomMapper.selectOne(symptom);
           if(symptomValue != null){
               System.out.println("放入图片的路径");
               myPlanVo.setImage(symptomValue.getPath());
           }

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
            ilustrateVo.setPath(symptom1.getPath());


            ilustrateVo.setTreatNum(treatProject1.getTotalTime());
            ilustrateVo.setXueWeiNum(xueTreatMapper.countXueWei(treatProject1.getTreatId())+"");

            ilustrateVoList.add(ilustrateVo);
        }



        return ilustrateVoList;
    }

    @Override
    public List<IlustrateVo> getIlustrateBySearch(String text) {

        List<IlustrateVo> ilustrateVoList = symptomMapper.getIlustrateBySearch(text);
        for(IlustrateVo ilustrateVo:ilustrateVoList){
            TreatProject treatProject = new TreatProject();
            treatProject.setTreatId(ilustrateVo.getTreatId());
            TreatProject treatProjectValue = treatProjectMapper.selectOne(treatProject);
            ilustrateVo.setTreatNum(treatProjectValue.getTotalTime());
            ilustrateVo.setXueWeiNum(xueTreatMapper.countXueWei(treatProject.getTreatId())+"");
        }

        return ilustrateVoList;


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
        if(treatProject1 != null){
            symptomInformationVo.setTotalDay(treatProject1.getTotalTime());

            symptomInformationVo.setEffect(treatProject1.getEffect());
            symptomInformationVo.setDescribe(treatProject1.getTreatDescribe());
        }

        XueTreat xueTreat = new XueTreat();
        xueTreat.setDay(day);
        xueTreat.setTreatId(treatId);

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
    public void addMyPlay(String treatId, String userId, String symptomId) throws ParseException {

        Symptom symptom = new Symptom();
        symptom.setSymptomId(symptomId);
        Symptom symptom1 = symptomMapper.selectOne(symptom);

        if(symptom1 == null){
            throw new RuntimeException("这个症状不存在");
        }

        TreatProject treatProject = new TreatProject();
        treatProject.setTreatId(treatId);
        TreatProject treatProject1 = treatProjectMapper.selectOne(treatProject);

        if(treatProject1 == null){
            throw new RuntimeException("诊疗方案不存在");
        }

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(userId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        medicalRecord.setCreateTime(simpleDateFormat.parse(createTime));
        medicalRecord.setProgress(RecordStatus.OFF.getStatus() +"");
        medicalRecord.setProjectId(treatId);
        medicalRecord.setSymptomName(symptom1.getSymptomName());
        medicalRecord.setTotalTime(treatProject1.getTotalTime());
        medicalRecord.setStatus(MedicalRecordType.UNDELETE.getStatus());
        int medicalRecordStatus = Integer.parseInt(isCanAddPlan(treatId,userId,medicalRecord.getSymptomName()));
        logger.info("我是添加方案的状态"+medicalRecordStatus);

           if(medicalRecordStatus == Integer.parseInt(MedicalRecordType.SPACE.getStatus())){//如果是被删除的，这个方法会在内部把被删除的添加回来
               medicalRecordMapper.insert(medicalRecord);
           }else if(medicalRecordStatus == Integer.parseInt(MedicalRecordType.DELETE.getStatus())){
               MedicalRecord medicalRecordTemp = new MedicalRecord();
               medicalRecordTemp.setUserId(userId);

               medicalRecordTemp.setProjectId(treatId);
               medicalRecordTemp.setSymptomName(medicalRecord.getSymptomName());

               MedicalRecord medicalRecord1 = medicalRecordMapper.selectOne(medicalRecordTemp);
               medicalRecordMapper.delete(medicalRecord1);
               medicalRecord1.setStatus(MedicalRecordType.UNDELETE.getStatus());
               medicalRecordMapper.insert(medicalRecord1);
           }else{
               throw new RuntimeException("这个方案已经添加过了");
           }

    }

    @Override
    @Transactional
    public String isCanAddPlan(String treatId, String userId, String symptomName) {


        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(userId);


        medicalRecord.setProjectId(treatId);
        medicalRecord.setSymptomName(symptomName);

        MedicalRecord medicalRecord1 = medicalRecordMapper.selectOne(medicalRecord);
        if(medicalRecord1 == null){
            return MedicalRecordType.SPACE.getStatus();
        }else if(StringUtils.equals(medicalRecord1.getStatus(),MedicalRecordType.DELETE.getStatus())){
            return MedicalRecordType.DELETE.getStatus();
        }else{
            return MedicalRecordType.UNDELETE.getStatus();
        }



    }


    @Override
    public String update(List<MultipartFile> files, String path) {
        File tempFile = new File(FilePath.path);
        String fileName = null;
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                throw new RuntimeException("上传的文件是空的");
            }
            fileName = file.getOriginalFilename();

            File dest = new File(tempFile.getAbsolutePath() + path + fileName);
            if (dest.exists()) {
                String[] photo = fileName.split("\\.");
                Date d = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String time = dateFormat.format(new Date());
                fileName = file.getOriginalFilename().replace("."+photo[photo.length-1],"")+ time + "." + photo[photo.length-1];
                dest = new File(tempFile.getAbsolutePath() + path + fileName);
            }
            try {
                logger.info(tempFile.getAbsolutePath() + path + fileName);
                file.transferTo(dest);
                logger.info("第" + (i + 1) + "个文件上传成功");//因为是从第0个开始算的，所以显示的时候要从1开始算

            } catch (IOException e) {
                logger.error(e.toString(), e);
                //File dest = new File(tempFile.getAbsolutePath()+"/src/main/resources/static/images/");
                throw new RuntimeException("第" + (i++) + "个文件上传失败");
            }
        }

        return fileName;
    }

    @Override
    public String readDoc(String path) throws IOException {


        File file = new File(path);
        if(file.exists()){
            System.out.println("文件存在");
        }else{
            System.out.println("文件不存在");
        }
        FileReader fileReader = new FileReader(file);
        char[] buf = new char[1024];

        int content = 0;
        String chunk = null;
        int i = 0;
        while ((content = fileReader.read(buf))!= -1){
            chunk += new String(buf,0,content);
        }

        return chunk+"";
    }

    @Override
    public Long getStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime().getTime();
    }

    @Override
    public Long getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime().getTime();
    }

    @Override
    public void renameTo(String oldName, String newName,String path) throws IOException {

        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");


        File oldFile = new File(tempFile.getAbsolutePath()+path+"/"+oldName);

        File newFile = new File(tempFile.getAbsolutePath()+path+"/"+newName);

        if(newFile.exists()){
            oldFile.delete();
            logger.info("开始删除刚刚上传的文件");
            throw new java.io.IOException("文件已经存在");
        }

        if(oldFile.renameTo(newFile)){
            logger.warn("已经重新命名");

        }else{
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void deleteFile(String fileName, String path) {
        File tempFile = new File(FilePath.path);
        File dest = new File(tempFile.getAbsolutePath() + path + fileName);

        if (dest != null) {
            logger.info("开始删除"+fileName);
            dest.delete();
        } else {
            logger.warn("请注意要删除的文件不存在");
        }
    }

    @Override
    @Transactional
    public void deleteMyPlan(String userId, String treatId) {
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setProjectId(treatId);
        logger.info("开始删除我的计划");
        logger.info("userId:"+userId);
        logger.info("treatId:"+treatId);


        medicalRecord.setUserId(userId);

        MedicalRecord medicalRecordValue = medicalRecordMapper.selectOne(medicalRecord);
        if(medicalRecordValue != null){
            medicalRecordMapper.delete(medicalRecordValue);
            medicalRecordValue.setStatus(MedicalRecordType.DELETE.getStatus());
            medicalRecordMapper.insert(medicalRecordValue);
        }else{
             throw new RuntimeException("没有这条记录，无法删除");
        }
    }


}
