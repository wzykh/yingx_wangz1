package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/login")
    public Map<String,Object> login(@RequestBody Admin admin){
        Map<String, Object> map = new HashMap<String,Object>();
        Admin admin1 = adminService.queryByName(admin.getUsername());
        map.put("msg", false);
        if(admin1 != null){
            if (admin1.getPassword().equals(admin.getPassword())){
                map.put("ok", admin1);
                map.put("msg", true);
            }else{
                map.put("err", "密码错误或不能为空！！！");
            }
        }else{
            map.put("err", "用户名错误！！！");
        }
        return map;
    }
}
