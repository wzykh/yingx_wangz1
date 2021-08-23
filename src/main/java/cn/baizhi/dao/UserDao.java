package cn.baizhi.dao;

import cn.baizhi.entity.MonthAndCount;
import cn.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //分页查
    public List<User> selectRange(@Param("start") int start,@Param("end") int end);
    //计算总页数
    public Integer countPage();
    //修改状态
    public void updateStatus(@Param("id") String id,@Param("status") Integer status);
    //添加
    public void addUser(User user);
    //删除
    public void deleteUser(String id);
    //查所有
    public List<User> selectAll();
    //查询每月注册人数
    public List<MonthAndCount> selectSexCount(String sex);
}
