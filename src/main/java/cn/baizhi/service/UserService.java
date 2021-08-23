package cn.baizhi.service;

import cn.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //分页
    public Map<String,Object> queryByPage(int page,int size);

    //修改状态
    public void update(String id,Integer status);

    //添加用户
    public void addUser(User user);

    //删除用户
    public void deleteUser(String id);

    //查所有
    public List<User> queryAll();

    //查询每月注册人数
    public Map<String,Object> querySexCount();
}
