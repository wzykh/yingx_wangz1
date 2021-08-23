package cn.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.baizhi.dao")
public class YingxWangzApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxWangzApplication.class, args);
    }

}
