package cn.baizhi;

import cn.baizhi.entity.Admin;
import lombok.extern.java.Log;

@Log
public class LombokTest {
    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.setId("123");
        admin.setPassword("123");
        System.out.println(admin.getId());
        System.out.println(admin.getPassword());

        System.out.println("------------------------------");
        Admin admin1 = new Admin("2222", "2222", "22222", 222);
    }
}
