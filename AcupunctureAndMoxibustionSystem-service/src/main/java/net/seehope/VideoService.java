package net.seehope;

import net.seehope.pojo.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    List<Video> getAllVideos();

    String updateVideo(List<MultipartFile> files, String path);

    void addVideo(Video video);
}
