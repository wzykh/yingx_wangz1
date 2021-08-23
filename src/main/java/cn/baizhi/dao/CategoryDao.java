package cn.baizhi.dao;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryDao {
    //根据级别查询
    public List<Category> qureyCategory(int levels);
    //根据一级查二级
    public List<Category> qureyByParentId(String id);
    //根据一级添加二级
    public void addLevels2(Category category);
    //删除二级
    public void deleteLevels2(String id);
}
