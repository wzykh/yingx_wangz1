package cn.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Excel(name = "用户ID",width = 30,height = 20)
    private String id;
    @Excel(name = "姓名",width = 30,height = 20)
    private String username;
    @Excel(name = "电话",width = 30,height = 20)
    private String phone;
    @Excel(name = "头像",width = 20,height = 20,type = 2)
    private String headimg;
    @Excel(name = "描述",width = 30,height = 20)
    private String brief;
    private String wechat;
    @Excel(name = "注册时间",width = 30,height = 20,format = "yyyy年MM月dd日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdate;
    @Excel(name = "状态",width = 30,height = 20)
    private Integer status;
    @Excel(name = "性别",width = 30,height = 20)
    private String sex;
}
