package com.jiashn.springbootproject.utils;

import io.minio.*;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
    public List<Map<String,String>> upLoadFileBackFileName(MultipartFile[] files,String bucketName){
        List<Map<String,String>> backNames = new ArrayList<>(files.length);
        InputStream ins = null;
        try {
            //验证当前桶是否存在
            existBucket(bucketName);
            for (MultipartFile file : files) {
                Map<String, String> fileMap = new HashMap<>(2);
                String fileName = file.getOriginalFilename();
                fileMap.put("fileName",fileName);
                //设置MD5
                String fileCode = Md5Util.md5Encode(fileName);
                fileMap.put("fileRecode",fileCode);
                ins = file.getInputStream();
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(ins,ins.available(),-1)
                        .contentType(file.getContentType())
                        .build()
                );
                backNames.add(fileMap);
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
     * @param bucketName 桶名称
     * @param fileName 文件名称
     * @return 返回文件流
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

    public List<String> upLoadFileBackUrl(MultipartFile[] files,String bucketName){
        InputStream ins = null;
        List<String> fileUrls = new ArrayList<>(files.length);
        existBucket(bucketName);
        try {
            for (MultipartFile file : files) {
                ins = file.getInputStream();
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                String contentType = file.getContentType();
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .contentType(contentType)
                        .object(fileName)
                        .stream(ins,ins.available(),-1)
                        .build());
                String objectUrl = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .method(Method.GET)
                        .build());
                fileUrls.add(objectUrl);
            }
        }catch (Exception e){
            log.error("上传文件报错：{}",e.getMessage());
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
        return fileUrls;
    }

    /**
     * 删除上传的文件
     * @param bucketName 桶名称
     * @param filePath 文件信息
     * @return 返回删除结果
     */
    public boolean removeFile(String bucketName,String filePath){
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName).object(filePath)
                    .build());
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("删除文件信息报错：{}",e.getMessage());
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}