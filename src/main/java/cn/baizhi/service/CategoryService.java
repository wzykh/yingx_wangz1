package cn.baizhi.service;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> queryCategory(int levels);

    public List<Category> queryByParentId(String id);

    public void saveLevels2(Category category);

    public void removeLevels2(String id);

}
