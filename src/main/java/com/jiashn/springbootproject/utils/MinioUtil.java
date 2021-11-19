package com.jiashn.springbootproject.utils;

import com.jiashn.springbootproject.config.MinioConfig;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.minio.*;
import io.minio.errors.*;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.security.provider.MD5;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: jiangjs
 * @Description: minio工具类
 * @Date: 2021/11/18 11:15
 **/
@Component
public class MinioUtil {

    private static final Logger log = LoggerFactory.getLogger(MinioUtil.class);

    @Autowired
    private MinioClient minioClient;


    /**
     * 校验当前的桶是否存在
     *    不存在时，新增一个桶
     */
    public void existBucket(String bucketName){
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("验证minio的桶是否存在报错：{}",e.getMessage());
        }
    }

    /**
     * 根据MultipartFile文件上传文件，返回文件名称
     * @param files 上传的文件
     * @param bucketName 桶名称
     * @return 返回名称
     */
    public List<String> upLoadFileBackFileName(MultipartFile[] files,String bucketName){
        List<String> backNames = new ArrayList<>(files.length);
        InputStream ins = null;
        try {
            //验证当前桶是否存在
            existBucket(bucketName);
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                //设置MD5
                String fileCode = MD5Encoder.encode(fileName.getBytes());
                ins = file.getInputStream();
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileCode)
                        .stream(ins,ins.available(),-1)
                        .contentType(file.getContentType())
                        .build()
                );
                backNames.add(fileCode);
            }
        }catch (Exception e){
            log.error("minio上传文件报错：{}",e.getMessage());
            e.printStackTrace();
        }finally {
            if (Objects.nonNull(ins)){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return backNames;
    }

    /**
     * 下载文件地址
     * @param bucketName
     * @param fileName
     * @return
     */
    public InputStream downLoadFile(String bucketName,String fileName){
        InputStream ins = null;
        try {
            ins = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
        } catch (Exception e) {
            log.error("获取文件流报错：{}",e.getMessage());
            e.printStackTrace();
        }
        return ins;
    }


}