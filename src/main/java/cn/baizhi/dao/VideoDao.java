package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
    //分页查
    public List<Video> qureyPage(@Param("start") int start,@Param("end") int end);
    //计算总页
    public Integer countVideo();
    //添加
    public void addVideo(Video video);
    //删除
    public void deleteVideo(String id);
}
