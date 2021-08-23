package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryContorller {
    @Autowired
    private CategoryService cs;
    @RequestMapping("/queryByLevels")
    public List<Category> queryByLevels(int levels){
       return cs.queryCategory(levels);
    }
    @RequestMapping("/qureyByParentId")
    public List<Category> qureyByParentId(String id){
       return cs.queryByParentId(id);
    }
    @RequestMapping("saveLevels")
    public void saveLevels(@RequestBody Category category){
        cs.saveLevels2(category);
    }
    @RequestMapping("removeLevels")
    public void removeLevels(String id){
        cs.removeLevels2(id);
    }
    @RequestMapping("removeLevels1")
    public Map<String, Object> removeLevels1(String id){
        HashMap<String, Object> map = new HashMap<>();
        List<Category> id1 = cs.queryByParentId(id);
        if (!id1.isEmpty()){
            map.put("msg", "一级类别不为空，不能删除！！！");
        }else{
            cs.removeLevels2(id);
            map.put("msg", "删除成功");
        }
        return map;
    }
}
