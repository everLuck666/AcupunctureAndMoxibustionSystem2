package net.seehope.controller;

import io.swagger.annotations.*;
import net.seehope.IndexService;
import net.seehope.common.RestfulJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("index")
@Api(tags = "小程序端",value ="IndexController" )
public class IndexController {
    @Autowired
    IndexService indexService;

    @GetMapping("plan")
    @ApiOperation(value = "得到我的诊疗计划",notes = "token是必须的，放在header中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId",value = "就是token,但是字段是openId")
    })

    public RestfulJson getMyPlan(HttpServletRequest request){
        String userId = request.getHeader("openId").toString();
        return RestfulJson.isOk(indexService.getMyPlan(userId));
    }
    @GetMapping("ilustrate")
    @ApiOperation("得到说明信息")
    public RestfulJson getIlustrate(){
        return RestfulJson.isOk(indexService.getIlustrate());
    }

    //搜索症状
    @GetMapping("seachSymptom")
    @ApiOperation("根据搜索返回说明")
    public RestfulJson getIlustrateBySearch(@ApiParam(name = "text",value = "搜索的文本") String text){

        return RestfulJson.isOk(indexService.getIlustrateBySearch(text));
    }
    //得到详细的症状详情
    @GetMapping("symptomInformation")
    @ApiOperation("得到详细的病症情况")
    @ApiImplicitParams({@ApiImplicitParam(name ="symptomId",value = "症状id",dataType = "String"),
            @ApiImplicitParam(name ="treatId",value = "治疗方案的id",dataType = "String"),
            @ApiImplicitParam(name ="day",value = "第几天的说明",dataType = "String")
    })
    public RestfulJson getSymptomInformation(String symptomId,String treatId,String day){
        return RestfulJson.isOk(indexService.getSymptomInformation(symptomId, treatId, day));
    }

    //加入诊疗方案
    @PutMapping("appendPlan")
    @ApiOperation(value = "加入诊疗方案",notes = "token放在header中,这个token字段先用openId来代替")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="treatId",value = "治疗方案的id",dataType = "String"),
            @ApiImplicitParam(name ="symptomId",value = "症状的id",dataType = "String"),
            @ApiImplicitParam(name ="openId",value = "登录得到的openId放到请求头里面",dataType = "String")

    })
    public RestfulJson addMyPlay(HttpServletRequest request, @RequestBody  Map map) throws ParseException {
        String userId = request.getHeader("openId").toString();
        String treatId = map.get("treatId").toString();
        String symptomId = map.get("symptomId").toString();
        System.out.println("我执行到这里了");
        indexService.addMyPlay(treatId,userId,symptomId);
        return RestfulJson.isOk("成功");
    }



}
