package net.seehope.impl;

import net.seehope.IlustrateService;
import net.seehope.IndexService;
import net.seehope.common.FilePath;
import net.seehope.common.RecordStatus;
import net.seehope.mapper.*;
import net.seehope.pojo.*;
import net.seehope.pojo.bo.IlustrateBo;
import net.seehope.pojo.bo.XueWeiBo;
import net.seehope.pojo.vo.IlustrateTwoVo;
import net.seehope.pojo.vo.SymptomInformationVo;
import net.seehope.pojo.vo.XueWeiAndDayVo;
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
import java.util.UUID;

@Service
public class IlustrateServiceImpl implements IlustrateService {
    Logger logger = LoggerFactory.getLogger(IlustrateServiceImpl.class);
    @Autowired
    IndexService indexService;
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
    MedicalRecordMapper medicalRecordMapper;
    @Override
    @Transactional
    public void addIlustrate(IlustrateBo ilustrateBo) {

        Symptom symptom = new Symptom();
        symptom.setSymptomName(ilustrateBo.getSymptomName());
        Symptom symptomTemp = symptomMapper.selectOne(symptom);

        if(symptomTemp != null){
            indexService.deleteFile(ilustrateBo.getPath(),FilePath.images);
            throw new RuntimeException("这个症状已经存在，无法添加");
        }else{
            symptom.setSymptomId(UUID.randomUUID().toString());
            symptom.setReason(ilustrateBo.getReason());
            symptom.setPath(ilustrateBo.getPath());
            symptomMapper.insert(symptom);
        }


        TreatProject treatProject = new TreatProject();
        treatProject.setTreatName(ilustrateBo.getTreatName());
        TreatProject treatProjectTemp = treatProjectMapper.selectOne(treatProject);
        if(treatProjectTemp != null){
            indexService.deleteFile(ilustrateBo.getPath(),FilePath.images);
            throw  new RuntimeException("这个诊疗方案已经存在,无法添加");
        }else {
            treatProject.setTreatId(UUID.randomUUID().toString());
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
            indexService.deleteFile(ilustrateBo.getPath(),FilePath.images);
            throw new RuntimeException("这个症状和诊疗方案的对应已经存在");

        }else{
            treat.setCreateTime(new Date());
            treatMapper.insert(treat);
        }

    }

    @Override
    @Transactional
    public void addXueWei(XueWeiBo weiBo, HttpServletRequest request) {
        String id = "-1";

        boolean flag = true;
        boolean flag2 = false;//用来判断是否发生了穴位修改


        boolean flag3 = false;//当出现相同的穴位就会变成true,接下来很可能出现重复
        boolean flag4 = false;//当诊疗方案出现重复的时候，也很可能出问题

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");


        String fileName = "";

        if(flag) {
            //上传图片

            XueWei xueWei = new XueWei();//用于检测是否发生穴位的事情
            xueWei.setPointname(weiBo.getPointName());

            xueWei.setTemperature(weiBo.getTemperature());
            xueWei.setTreattime(Integer.parseInt(weiBo.getTreatTime()));

            File tempFile = new File(FilePath.path);
            String path = FilePath.images;
            fileName = indexService.update(files, path);
            weiBo.setPath(fileName);
            //xueWei.setPath(fileName);



            id = UUID.randomUUID().toString();

            XueWei xueWeiValue = xueWeiMapper.selectOne(xueWei);
            if(xueWeiValue != null){
                flag3 = true;
                id = xueWeiValue.getId();
                logger.info("出现了一样的穴位");

            }else{
                xueWei.setId(id+"");
                xueWei.setPath(weiBo.getPath());
                xueWeiMapper.insert(xueWei);
            }



        }



         String treatProjectTemp = null;

            treatProjectTemp = treatProjectMapper.exists(weiBo.getTreatProjectName());
            if(treatProjectTemp == null){
                indexService.deleteFile(fileName,FilePath.images);
                throw new RuntimeException("诊疗方案不存在");
            }

            XueTreat xueTreat = new XueTreat();
            xueTreat.setTreatId(treatProjectTemp+"");
            xueTreat.setDay(weiBo.getDay());
            xueTreat.setXueId(id+"");
            XueTreat xueTreat1 = xueTreatMapper.selectOne(xueTreat);
            if(xueTreat1 != null){
                indexService.deleteFile(fileName,FilePath.images);
                throw new RuntimeException("请不要重复提交请求");
            }
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
                indexService.deleteFile(fileName,FilePath.images);
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
            ilustrateTwoVo.setEffect(treatProject1.getEffect());
            ilustrateTwoVo.setPath(symptom1.getPath());
            ilustrateTwoVo.setReason(symptom1.getReason());
            ilustrateTwoVo.setSymptomId(symptom1.getSymptomId());
            ilustrateTwoVo.setTreatName(treatProject1.getTreatName());
            ilustrateTwoVo.setTreatId(treatProject1.getTreatId());
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
                //查询这个穴位除了本诊疗方案外是否还有其他使用
                int countExcludeXueWei = xueTreatMapper.countXueWei2(xueTreat1.getTreatId(),xueTreat1.getXueId());
                if(countExcludeXueWei ==0){
                    XueWei xueWei = new XueWei();
                    xueWei.setId(xueTreat1.getXueId());
                    xueWeiMapper.delete(xueWei);
                }
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

    @Override
    public List<XueWeiAndDayVo> getIlustrateInfomation(String treatId) {
        List<XueWeiAndDayVo> XueWeiAndDayVoList = new ArrayList<>();

        TreatProject treatProject = new TreatProject();
        treatProject.setTreatId(treatId);

        TreatProject treatProjectValue = treatProjectMapper.selectOne(treatProject);

        if(treatProjectValue != null){
            int i = Integer.parseInt(treatProjectValue.getTotalTime());

            for(int j = 1;j<=i;j++){//第一天，第二天
                XueTreat xueTreat = new XueTreat();
                xueTreat.setTreatId(treatId);
                xueTreat.setDay(j+"");//设置这个天数
                List<XueTreat> xueTreatValueList = xueTreatMapper.select(xueTreat);
                List<XueWei> xueWeiList = new ArrayList<>();
                XueWeiAndDayVo xueWeiBo = new XueWeiAndDayVo();
                xueWeiBo.setDay(j+"");//先设置一下这个的时间
                for(XueTreat xueTreat1:xueTreatValueList){//添加这一天的穴位
                    XueWei xueWei = new XueWei();
                    xueWei.setId(xueTreat1.getXueId());
                    XueWei xueWeiValue = xueWeiMapper.selectOne(xueWei);
                    if(xueWeiValue != null){
                        xueWeiList.add(xueWeiValue);//设置一下这一天的穴位
                    }

                }
                xueWeiBo.setXueWeiList(xueWeiList);
                XueWeiAndDayVoList.add(xueWeiBo);
            }

        }else{
            throw new RuntimeException("不存在这个诊疗方案");
        }
        return XueWeiAndDayVoList ;
    }

    @Override
    public Symptom getSymptomInfomation(String symptomId) {
        Symptom symptom = new Symptom();
        symptom.setSymptomId(symptomId);
        return symptomMapper.selectOne(symptom);
    }

    @Override
    @Transactional
    public void deleteXueWei(String treatId, String xueWeiId) {
        XueTreat xueTreat2 = new XueTreat();
        xueTreat2.setXueId(xueWeiId);
        xueTreat2.setTreatId(treatId);
        xueTreatMapper.delete(xueTreat2);
        //查询这个穴位除了本诊疗方案外是否还有其他使用
        int countExcludeXueWei = xueTreatMapper.countXueWei2(treatId,xueWeiId);
        if(countExcludeXueWei ==0){
            XueWei xueWei = new XueWei();
            xueWei.setId(xueWeiId);
            xueWeiMapper.delete(xueWei);
        }
    }


}
