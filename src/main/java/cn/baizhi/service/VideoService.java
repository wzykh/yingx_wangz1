package cn.baizhi.service;

import cn.baizhi.entity.Video;

import java.util.Map;

public interface VideoService {
    //分页
    public Map<String,Object> queryByPage(int page, int size);
    //添加
    public void saveVideo(Video video);
    //删除
    public void deleteVideo(String id);
}
