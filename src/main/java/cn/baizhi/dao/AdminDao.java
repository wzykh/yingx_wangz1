package cn.baizhi.dao;

import cn.baizhi.entity.Admin;

public interface AdminDao {
    public Admin selectByName(String username);
}
