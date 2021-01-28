package net.seehope.controller;

import net.seehope.IlustrateService;
import net.seehope.IndexService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Symptom;
import net.seehope.pojo.Video;
import net.seehope.pojo.bo.IlustrateBo;
import net.seehope.pojo.bo.XueWeiBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("ilustrate")
public class IlustrateController {
    @Autowired
    IndexService indexService;
    @Autowired
    IlustrateService ilustrateService;

    @PutMapping("ilustrate")
    public RestfulJson updateIlustrate(HttpServletRequest request, IlustrateBo ilustrateBo){


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
        String path = "/src/main/resources/static/images/";
        String fileName = indexService.update(files, path);
        ilustrateBo.setPath(fileName);
        ilustrateService.addIlustrate(ilustrateBo);

        return RestfulJson.isOk("上传说明成功");

    }
    //添加穴位
    @PutMapping("xuewei")
    public RestfulJson addXueWei(HttpServletRequest request, XueWeiBo xueWeiBo){


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
        String path = "/src/main/resources/static/images/";
        String fileName = indexService.update(files, path);
        xueWeiBo.setPath(fileName);
        ilustrateService.addXueWei(xueWeiBo);
         return RestfulJson.isOk("添加成功");

    }
    //得到所有说明
    @GetMapping("ilustrate")
    public RestfulJson getAllIlustrate(){
        return RestfulJson.isOk(ilustrateService.getAllIlustrate());

    }
    //删除说明
    @DeleteMapping("ilustrate")
    public RestfulJson deleteIlustrate(@RequestBody Map map){
        String treatId = map.get("treatId").toString();
        String symptomId = map.get("symptomId").toString();
        ilustrateService.deleteIlustrate(treatId,symptomId);

        return RestfulJson.isOk("删除说明成功");

    }




}
