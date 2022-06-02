package com.wb.reggie.controller;

import com.wb.reggie.common.RetObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @Description: 上传下载的控制层处理
 * @Title: CommonController
 * @Package com.wb.reggie.controller
 * @Author: 吴某人
 * @Copyright:个人学习
 * @CreateTime: 2022/5/30 21:11
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public RetObj<String> upload(MultipartFile file){
        log.info("准备上传图片");
        //获取文件名
        String name = file.getOriginalFilename();
        //设置文件存储路径及文件名
        String filename = UUID.randomUUID() + name.substring(name.lastIndexOf("."));
        //判断路径是否存在
        File file1 = new File(basePath);
        if (!file1.exists()){
            file1.mkdir();
        }
        //直接保存文件
        try {
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RetObj.success(filename);
    }

    /**
     * 文件下载(显示到浏览器页面)
     * @param name
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        log.info("准备下载图片");
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            //文件输入流
            inputStream = new FileInputStream(basePath + name);
            //文件输出流
            outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            //创建一个字节数组
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,length);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
