package cn.baizhi.service;

import cn.baizhi.entity.Admin;

public interface AdminService {
    //根据姓名查找
    public Admin queryByName(String username);
}
