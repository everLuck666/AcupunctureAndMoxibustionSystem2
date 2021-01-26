package net.seehope.impl;

import net.seehope.VideoService;
import net.seehope.mapper.VideoMapper;
import net.seehope.pojo.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Autowired
    VideoMapper videoMapper;
    @Override
    public List<Video> getAllVideos() {

        return videoMapper.selectAll();
    }

    @Override
    public String updateVideo(List<MultipartFile> files, String path) {
        File tempFile = new File("AcupunctureAndMoxibustionSystem-controller");
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
                fileName = file.getOriginalFilename()+ time + "." + photo[photo.length-1];
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
    public void addVideo(Video video) {
        videoMapper.insert(video);

    }
}
