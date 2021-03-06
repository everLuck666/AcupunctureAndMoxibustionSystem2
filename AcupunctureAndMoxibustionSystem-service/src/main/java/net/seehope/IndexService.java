package net.seehope;

import net.seehope.pojo.vo.IlustrateVo;
import net.seehope.pojo.vo.MyPlanVo;
import net.seehope.pojo.vo.SymptomInformationVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IndexService {
    //得到我的诊疗计划

    List<MyPlanVo> getMyPlan(String userId,String status);//status 1表示没有删除的，如果为-1就是表示删除的。


    //得到说明页面的信息
    List<IlustrateVo> getIlustrate();

    //得到症状详情


    //搜索症状
    List<IlustrateVo> getIlustrateBySearch(String text);
    //得到症状的详情
    SymptomInformationVo getSymptomInformation(String symptomId,String treatId,String day);

    //增加我的诊疗方案
    void addMyPlay(String treatId,String userId,String symptomId) throws ParseException;

    //判断这个诊疗方案是否已经添加过了
    String isCanAddPlan(String treatId,String userId,String symptomName);
    //上传服务
    String update(List<MultipartFile> files, String path);

    //读文档
    String readDoc(String path) throws IOException;


    //得到今日开始时间
    Long getStartTime() ;


    //得到今日结束时间
    Long getEndTime();

    //文件重命名
    void renameTo(String oldName,String newName,String path) throws IOException;

    //删除文件
    void deleteFile(String fileName,String path);

    //删除我的机会
    void deleteMyPlan(String userId,String treatId);




















}
