package cn.baizhi;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = YingxWangzApplication.class)
@RunWith(SpringRunner.class)
public class test {
    @Autowired
    private AdminDao adminDao;
    @Test
    public void test1(){
        Admin admin = adminDao.selectByName("111");
        System.out.println(admin);
    }

}
