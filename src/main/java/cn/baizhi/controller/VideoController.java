package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService vs;
    @RequestMapping("/qureyPage")
    public Map<String, Object> qureyPage(int page){
        int size = 3;
        Map<String, Object> map = vs.queryByPage(page, size);
        return map;
    }
    @RequestMapping("/save")
    public String save(String title, MultipartFile video,String brief,String id){
        Video video1 = new Video();
        video1.setId(id);
        video1.setBrief(brief);
        video1.setTitle(title);
        Category category = new Category();
        category.setId(id);
        video1.setCategory(category);
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tBakgbPGhR8CwK7ECNf";
        String accessKeySecret = "hWjWaNlNyJE1ZQgQYeV4Zy0hOUzbeN";
        String fileName = new Date().getTime() + video.getOriginalFilename();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        byte[] content = null;
        try {
            content = video.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ossClient.putObject("ykh2", "video/" + fileName, new ByteArrayInputStream(content));
        String style = "video/snapshot,t_50000,f_jpg,w_800,h_600";
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest("ykh2", "video/"+fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL url = ossClient.generatePresignedUrl(req);
        InputStream inputStream = null;
        try {
            inputStream = new URL(url.toString()).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] split = fileName.split("\\.");
        ossClient.putObject("ykh2", "video/" + split[0]+".jpg", inputStream);
        video1.setVideoPath("http://ykh2.oss-cn-beijing.aliyuncs.com/video/" + fileName);
        vs.saveVideo(video1);
        ossClient.shutdown();
        return null;
    }
    @RequestMapping("/remove")
    public String remove(String id,String videoPath){
        String[] a = videoPath.split("/");
        //http://ykh2.oss-cn-beijing.aliyuncs.com/video/1629122392655share_1efc514b65fa3edb08904b2d2a633d72.jpg
        String name1 = a[3];
        String name2 = a[4];
        String fileName = name1+"/"+name2;
        String replace = name2.replace(".mp4", ".jpg");
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tBakgbPGhR8CwK7ECNf";
        String accessKeySecret = "hWjWaNlNyJE1ZQgQYeV4Zy0hOUzbeN";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.deleteObject("ykh2", fileName);
        ossClient.deleteObject("ykh2", name1+"/"+replace);
        vs.deleteVideo(id);
        ossClient.shutdown();
        return null;
    }
}
