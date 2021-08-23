package cn.baizhi.service;

import cn.baizhi.annotation.DeleteAspect;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("videoService")
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao vd;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryByPage(int page, int size) {
        Map<String, Object> map = new HashMap<String,Object>();
        List<Video> list = vd.qureyPage((page - 1) * size, size);
        Integer integer = vd.countVideo();
        if (integer%size==0){
            map.put("sum", integer/size);
        }else {
            map.put("sum", integer/size+1);
        }
        map.put("data", list);
        return map;
    }

    @DeleteAspect
    @Override
    public void saveVideo(Video video) {
        video.setId(UUID.randomUUID().toString());
        video.setCreateDate(new Date());
        vd.addVideo(video);
    }

    @DeleteAspect
    @Override
    public void deleteVideo(String id) {
        vd.deleteVideo(id);
    }
}
