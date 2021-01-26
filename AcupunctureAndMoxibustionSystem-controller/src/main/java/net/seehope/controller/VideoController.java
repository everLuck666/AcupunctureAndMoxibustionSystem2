package net.seehope.controller;

import net.seehope.IndexService;
import net.seehope.VideoService;
import net.seehope.common.NonStaticResourceHttpRequestHandler;
import net.seehope.common.RestfulJson;
import net.seehope.pojo.Video;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")

public class VideoController {
    @Autowired
    VideoService videoService;

    @Autowired
    IndexService indexService;

    @Autowired
    NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;





    @GetMapping("/video")
    public void videoPreview(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //假如我把视频1.mp4放在了static下的video文件夹里面
        //sourcePath 是获取resources文件夹的绝对地址
        //realPath 即是视频所在的磁盘地址
        //String sourcePath = ClassUtils.getDefaultClassLoader().getResource("").getPath().substring(1);
        String realPath = "/Users/everyluck/Downloads/2.mp4";


        Path filePath = Paths.get(realPath);
        if (Files.exists(filePath)) {
            System.out.println("视频播放");
            String mimeType = Files.probeContentType(filePath);
            if (!StringUtils.isEmpty(mimeType)) {
                response.setContentType(mimeType);
                System.out.println("快要播放了");
            }
            request.setAttribute(NonStaticResourceHttpRequestHandler.ATTR_FILE, filePath);
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        }
    }
    @GetMapping("video2")
    public RestfulJson videoShow(){
        return RestfulJson.isOk(videoService.getAllVideos());
    }
    //上传视频
    @PutMapping("video")
    public RestfulJson updateVideo(HttpServletRequest request){


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
        String path = "/src/main/resources/static/video/";
        String fileName = indexService.update(files, path);

        Video video = new Video();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(new Date());
        video.setCreateTime(createTime);
        video.setPath("static/video/"+fileName);
        video.setVideoName(fileName);
        videoService.addVideo(video);
        return RestfulJson.isOk("上传成功");

    }
    @DeleteMapping("video")
    public RestfulJson deleteVideo(@RequestBody Map map){
        String videoName = map.get("videoName").toString();
        videoService.deleteVideo(videoName);
        return RestfulJson.isOk("删除成功");
    }




}
