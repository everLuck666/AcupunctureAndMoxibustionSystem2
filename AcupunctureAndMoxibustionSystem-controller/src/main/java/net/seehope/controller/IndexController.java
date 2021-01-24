package net.seehope.controller;

import net.seehope.IndexService;
import net.seehope.common.RestfulJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    IndexService indexService;

    @GetMapping("plan")
    public RestfulJson getMyPlan(HttpServletRequest request){
        String userId = request.getHeader("openId").toString();
        return RestfulJson.isOk(indexService.getMyPlan(userId));

    }
    @GetMapping("ilustrate")
    public RestfulJson getIlustrate(){
        return RestfulJson.isOk(indexService.getIlustrate());
    }

    //搜索症状
    @GetMapping("seachSymptom")
    public RestfulJson getIlustrateBySearch(String text){

        return RestfulJson.isOk(indexService.getIlustrateBySearch(text));
    }
    //得到详细的症状详情
    @GetMapping("symptomInformation")
    public RestfulJson getSymptomInformation(String symptomId,String treatId,String day){
        return RestfulJson.isOk(indexService.getSymptomInformation(symptomId, treatId, day));
    }

    //加入诊疗方案
    @PutMapping("appendPlan")
    public RestfulJson addMyPlay(HttpServletRequest request, @RequestBody  Map map){
        String userId = request.getHeader("openId").toString();
        String treatId = map.get("treatId").toString();
        String symptomId = map.get("symptomId").toString();
        System.out.println("我执行到这里了");
        indexService.addMyPlay(treatId,userId,symptomId);
        return RestfulJson.isOk("成功");
    }








}
