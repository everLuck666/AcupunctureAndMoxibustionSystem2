package net.seehope.impl;


import net.seehope.IndexService;
import net.seehope.common.FilePath;
import net.seehope.common.PlanStatus;
import net.seehope.common.RecordStatus;
import net.seehope.mapper.*;
import net.seehope.pojo.*;
import net.seehope.pojo.vo.IlustrateVo;
import net.seehope.pojo.vo.MyPlanVo;
import net.seehope.pojo.vo.SymptomInformationVo;

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
    public List<MyPlanVo> getMyPlan(String userId) {
        List<MyPlanVo> myPlanVoList = new ArrayList<>();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setUserId(userId);
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


}
