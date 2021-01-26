package net.seehope.controller;

import net.seehope.IndexService;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Symptom;
import net.seehope.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("ilustrate")
public class IlustrateController {
    @Autowired
    IndexService indexService;

    @PutMapping("ilustrate")
    public RestfulJson updateIlustrate(HttpServletRequest request){


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
        String path = "/src/main/resources/static/images/";
        String fileName = indexService.update(files, path);


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());

        return RestfulJson.isOk("上传成功");

    }


}
