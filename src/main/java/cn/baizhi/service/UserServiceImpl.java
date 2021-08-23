package cn.baizhi.service;

import cn.baizhi.annotation.DeleteAspect;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.MonthAndCount;
import cn.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryByPage(int page, int size) {
        HashMap<String, Object> map = new HashMap<>();
        List<User> list = userDao.selectRange((page - 1) * size, size);
        Integer integer = userDao.countPage();
        if (integer%size==0){
            map.put("num", integer/size);
        }else{
            map.put("num", integer/size+1);
        }
        map.put("data", list);
        return map;
    }

    @DeleteAspect
    @Override
    public void update(String id, Integer status) {
        userDao.updateStatus(id, status);
    }

    @DeleteAspect
    @Override
    public void addUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreatedate(new Date());
        user.setStatus(0);
        userDao.addUser(user);
    }

    @DeleteAspect
    @Override
    public void deleteUser(String id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> queryAll() {
        return userDao.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> querySexCount() {
        List<String> data = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
        List<Integer> manCount = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<Integer> womanCount = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        HashMap<String, Object> map = new HashMap<>();
        List<MonthAndCount> man = userDao.selectSexCount("男");
        for (MonthAndCount mans : man) {
            Integer count = mans.getCount();
            Integer month = mans.getMonth();
            manCount.set(month-1, count);
        }
        List<MonthAndCount> woMan = userDao.selectSexCount("女");
        for (MonthAndCount woMans : woMan) {
            Integer count = woMans.getCount();
            Integer month = woMans.getMonth();
            womanCount.set(month-1, count);
        }
        map.put("data", data);
        map.put("manCount", manCount);
        map.put("womanCount", womanCount);
        return map;
    }
}
