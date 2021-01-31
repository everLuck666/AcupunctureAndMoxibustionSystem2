package net.seehope;

import net.seehope.pojo.Video;
import net.seehope.pojo.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface VideoService {
    List<VideoVo> getAllVideos() throws ParseException;



    void addVideo(Video video);

    void deleteVideo(String videoName);
}
