package cn.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(int page) {
        int size = 3;
        Map<String, Object> map = userService.queryByPage(page, size);
        return map;
    }

    @RequestMapping("/updateStatus")
    public String updateStatusController(String id, Integer status) {
        userService.update(id, status);
        return null;
    }

    @RequestMapping("/saveUser")
    public String saveUser(String username, MultipartFile photo, String phone, String brief,String sex) throws IOException {
        User user = new User();
        user.setUsername(username);
        user.setPhone(phone);
        user.setBrief(brief);
        user.setSex(sex);
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tBakgbPGhR8CwK7ECNf";
        String accessKeySecret = "hWjWaNlNyJE1ZQgQYeV4Zy0hOUzbeN";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //上传时的文件改名
        String randomName = UUID.randomUUID().toString();
        //上传时的文件名
        String p = photo.getOriginalFilename();
        //截取文件后缀名
        String extension = FilenameUtils.getExtension(p);
        String fileName = randomName + "." + extension;
        // 上传Byte数组。
        byte[] content = photo.getBytes();
        ossClient.putObject("ykh2", "photos/" + fileName, new ByteArrayInputStream(content));
        user.setHeadimg("http://ykh2.oss-cn-beijing.aliyuncs.com/photos/" + fileName);
        userService.addUser(user);
        // 关闭OSSClient。
        ossClient.shutdown();

        return null;

    }
        @RequestMapping("/removeUser")
        public String removeUser(String id,String headimg){
            String[] a = headimg.split("/");
            String name1 = a[3];
            String name2 = a[4];
            String fileName = name1+"/"+name2;
            String endpoint = "http://oss-cn-beijing.aliyuncs.com";
            String accessKeyId = "LTAI5tBakgbPGhR8CwK7ECNf";
            String accessKeySecret = "hWjWaNlNyJE1ZQgQYeV4Zy0hOUzbeN";
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.deleteObject("ykh2", fileName);
            userService.deleteUser(id);
            ossClient.shutdown();
            return null;
        }
        @RequestMapping("/export")
        public void export() throws IOException {
            String endpoint = "http://oss-cn-beijing.aliyuncs.com";
            String accessKeyId = "LTAI5tBakgbPGhR8CwK7ECNf";
            String accessKeySecret = "hWjWaNlNyJE1ZQgQYeV4Zy0hOUzbeN";
            List<User> users = userService.queryAll();

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建
            for (User user : users) {
                String[] split = user.getHeadimg().split("/");
                ossClient.getObject(new GetObjectRequest("ykh2", "photos/"+split[4]), new File("D:\\photos\\"+split[4]));
                user.setHeadimg("D:\\photos\\"+split[4]);
            }
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("应学APP", "用户信息"), User.class, users);
            workbook.write(new FileOutputStream(new File("D:/yingxue.xls")));
            // 关闭OSSClient。
            ossClient.shutdown();
            workbook.close();
        }
        @RequestMapping("/selectManAndWomanCount")
        public Map<String, Object> selectManAndWomanCount(){
            Map<String, Object> map = userService.querySexCount();
            return map;
        }
}
