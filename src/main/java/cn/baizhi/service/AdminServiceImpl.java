package cn.baizhi.service;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("AdminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin queryByName(String username) {
       return adminDao.selectByName(username);
    }
}
