package cn.baizhi.service;

import cn.baizhi.annotation.DeleteAspect;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao cd;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryCategory(int levels) {
        return cd.qureyCategory(levels);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByParentId(String id) {
        return cd.qureyByParentId(id);
    }

    @DeleteAspect
    @Override
    public void saveLevels2(Category category) {
        category.setId(UUID.randomUUID().toString());
        cd.addLevels2(category);
    }

    @DeleteAspect
    @Override
    public void removeLevels2(String id) {
        cd.deleteLevels2(id);
    }


}
