package cn.baizhi;

import cn.baizhi.dao.CategoryDao;
import cn.baizhi.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class Category {
    @Autowired
    private CategoryService cs;
    @Autowired
    private CategoryDao cd;
    @Test
    public void queryCategory(){
        List<cn.baizhi.entity.Category> list = cs.queryCategory(1);
        for (cn.baizhi.entity.Category category : list) {
            System.out.println(category);
        }
    }
    @Test
    public void test1(){
        List<cn.baizhi.entity.Category> list = cd.qureyCategory(1);
        for (cn.baizhi.entity.Category category : list) {
            System.out.println(category);

        }
    }
    @Test
    public void test2(){
        List<cn.baizhi.entity.Category> list = cs.queryByParentId("1");
        for (cn.baizhi.entity.Category category : list) {
            System.out.println(category);

        }
    }
}
