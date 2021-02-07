package net.seehope.controller;

import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.*;
import net.seehope.IlustrateService;
import net.seehope.IndexService;
import net.seehope.common.FilePath;
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
@Api(tags = "说明管理",value = "IlustrateController")
public class IlustrateController {
    @Autowired
    IndexService indexService;
    @Autowired
    IlustrateService ilustrateService;

    @PutMapping(value = "ilustrate",produces="application/json;charset=UTF-8")
    @ApiOperation(value = "上传说明",notes = "file字段是必要,代表图片的名字")
    public RestfulJson updateIlustrate(HttpServletRequest request, IlustrateBo ilustrateBo){


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        File tempFile = new File(FilePath.path);
        String path = FilePath.images;
        String fileName = indexService.update(files, path);
        ilustrateBo.setPath(fileName);
        ilustrateService.addIlustrate(ilustrateBo);

        return RestfulJson.isOk("上传说明成功");

    }
    //添加穴位
    @PutMapping(value = "xuewei",produces="application/json;charset=UTF-8")
    @ApiOperation(value = "增加穴位",notes = "file字段是必要,代表图片的名字，增加的穴位的名字如果一样，但是穴位的其他参数" +
            "不一样的话会导致穴位的信息发生变化")
    @ApiParam(name = "file",value = "图片字段")
    public RestfulJson addXueWei(HttpServletRequest request, XueWeiBo xueWeiBo){

        ilustrateService.addXueWei(xueWeiBo,request);
         return RestfulJson.isOk("添加成功");

    }
    //得到所有说明
    @GetMapping(value = "ilustrateAll",produces="application/json;charset=UTF-8")
    @ApiOperation("得到所有的说明")
    public RestfulJson getAllIlustrate(){
        return RestfulJson.isOk(ilustrateService.getAllIlustrate());

    }
    //删除说明
    @DeleteMapping(value = "ilustrate",produces="application/json;charset=UTF-8")
    @ApiOperation("删除说明")
    @ApiImplicitParams({@ApiImplicitParam(name ="treatId",value = "诊疗方案的id",dataType = "String"),
            @ApiImplicitParam(name = "symptomId",value = "病症的id",dataType = "String")})
    public RestfulJson deleteIlustrate(@RequestBody Map map){
        String treatId = map.get("treatId").toString();
        String symptomId = map.get("symptomId").toString();
        ilustrateService.deleteIlustrate(treatId,symptomId);

        return RestfulJson.isOk("删除说明成功");

    }

    @PutMapping(value = "record",produces="application/json;charset=UTF-8")
    @ApiOperation("记录诊疗数据")
    @ApiImplicitParams({@ApiImplicitParam(name ="openId",value = "用户的id放在header里面",dataType = "String"),
            @ApiImplicitParam(name = "treatId",value = "诊疗方案的id",dataType = "String")})
    public RestfulJson updateRecord(@RequestBody Map map,HttpServletRequest request){

        String userId = request.getAttribute("openId").toString();
        String treatId = map.get("treatId").toString();
        ilustrateService.updateRecord(userId,treatId);
        return RestfulJson.isOk("记录诊疗数据完成");



    }

    @GetMapping(value = "ilustrateInfomation",produces="application/json;charset=UTF-8")
    @ApiOperation("得到这个诊疗方案的所有穴位信息")

    public RestfulJson getIlustrateInfomation(@ApiParam(name = "treatId",value = "诊疗方案的id") String treatId){
        return RestfulJson.isOk(ilustrateService.getIlustrateInfomation(treatId));
    }


    @DeleteMapping(value = "xueWei",produces="application/json;charset=UTF-8")
    @ApiOperation("删除诊疗方案的某一个穴位")
    @ApiImplicitParams({@ApiImplicitParam(name ="treatId",value = "诊疗id",dataType = "String"),
            @ApiImplicitParam(name = "xueWeiId",value = "穴位ID",dataType = "String")})
    public RestfulJson deleteXueWei(@RequestBody Map map){
        String treatId = map.get("treatId").toString();
        String xueWeiId = map.get("xueWeiId").toString();
        ilustrateService.deleteXueWei(treatId,xueWeiId);
        return RestfulJson.isOk("删除穴位成功");




    }








}
