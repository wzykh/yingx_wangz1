package cn.baizhi;

import cn.baizhi.dao.UserDao;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.entity.MonthAndCount;
import cn.baizhi.entity.User;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private VideoDao vd;
    @Autowired
    private VideoService vs;
    @Test
    public void test(){
        List<User> users = userDao.selectRange(0, 3);
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void test1(){
        List<Video> videos = vd.qureyPage(0, 1);
        for (Video video : videos) {
            System.out.println(video);
        }

    }
    @Test
    public void test2(){
        Map<String, Object> map = vs.queryByPage(1, 3);
        Object data = map.get("data");
        System.out.println(data);

    }
    @Test
    public void test3(){
        List<MonthAndCount> counts = userDao.selectSexCount("男");
        for (MonthAndCount count : counts) {
            System.out.println(count);
        }
    }
}
