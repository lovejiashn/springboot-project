package com.jiashn.springbootproject.useUtil;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.*;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 获取resource目录下的几种方式
 * @date: 2023/6/9 16:04
 **/
@RestController
@RequestMapping("/file")
public class GetFileTypeUtil {
    @Resource
    private ResourceLoader resourceLoader;
    @Resource
    private ApplicationContext applicationContext;

    @GetMapping("/get.do")
    private void getFileContent(){
        try {
            //new File,打包后就找不到文件，因此不用
            File file = new File("src/main/resources/static/file/test.txt");
            FileInputStream fileIs = new FileInputStream(file);
            getFileContent(fileIs);
            //ClassLoader.getResourceAsStream()方法获取
            InputStream loaderIs = GetFileTypeUtil.class.getClassLoader().getResourceAsStream("static/file/test.txt");
            getFileContent(loaderIs);
            //class.getResourceAsStream()方法获取
            InputStream asStream = GetFileTypeUtil.class.getResourceAsStream("/static/file/test.txt");
            getFileContent(asStream);
            //ResourceLoader获取文件
            InputStream rlIs = resourceLoader.getResource("classpath:static/file/test.txt").getInputStream();
            getFileContent(rlIs);
            //ApplicationContext获取文件
            InputStream apcis = applicationContext.getResource("classpath:static/file/test.txt").getInputStream();
            getFileContent(apcis);
            //ResourceUtils获取文件
            File file1 = ResourceUtils.getFile("classpath:static/file/test.txt");
            getFileContent(new FileInputStream(file1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getFileContent(InputStream ism){
        //一次性将文件内容读出
        byte[] bytes;
        try {
            bytes = new byte[ism.available()];
            int item = 0;
            while ((item = ism.read(bytes)) != -1){
                String str = new String(bytes, 0, item);
                System.out.println("读取文件内容："+str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(ism)){
                    ism.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
