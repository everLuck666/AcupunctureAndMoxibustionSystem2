package net.seehope.impl;

import net.seehope.VideoService;
import net.seehope.mapper.VideoMapper;
import net.seehope.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;
    @Override
    public List<Video> getAllVideos() {

        return videoMapper.selectAll();
    }
}
