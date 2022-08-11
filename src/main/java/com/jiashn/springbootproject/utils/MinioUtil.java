package com.jiashn.springbootproject.utils;

import com.google.common.collect.Multimap;
import com.jiashn.springbootproject.config.PearlMinioClient;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Part;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private PearlMinioClient pearlMinioClient;


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
     * 根据MultipartFile文件上传文件，返回文件名称及对应MD5编码
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
     * 根据输入流进行文件上传
     * @param inputStream 文件输入流
     * @param bucketName 桶名称
     * @param originalFileName 文件名称
     * @return 返回文件名称及MD5编码
     */
    public Map<String,String> uploadFileByInputStream(InputStream inputStream,String bucketName,String originalFileName){
        //验证当前桶是否存在
        existBucket(bucketName);
        Map<String, String> fileMap = new HashMap<>(2);
        fileMap.put("fileName",originalFileName);
        fileMap.put("fileRecode","");
        try {
            String fileRecode = Md5Util.md5Encode(inputStream);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(originalFileName)
                    .stream(inputStream,inputStream.available(),-1)
                    .build()
            );
            fileMap.put("fileRecode",fileRecode);
            return fileMap;
        }catch (Exception e){
            log.error("{}:文件流上传文件失败：{}",originalFileName,e.getMessage());
            e.printStackTrace();
            return fileMap;
        }
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

    /**
     * 上传分片上传请求，并为每个分片分配uploadId
     * @param bucketName 桶
     * @param region 区域
     * @param objectName 对象名
     * @param headers 消息头
     * @param extraQueryParams 额外参数
     * @return 返回结果
     */
    public CreateMultipartUploadResponse getUploadId(String bucketName, String region, String objectName, Multimap<String, String> headers, Multimap<String, String> extraQueryParams){
        try {
            return pearlMinioClient.createMultipartUpload(bucketName, region, objectName, headers, extraQueryParams);
        } catch (Exception e) {
            log.error("获取每个分片对应的");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件的预签名地址
     * @param bucketName 桶名
     * @param filePath 文件路径
     * @param paraMap 额外提交参数
     * @return 返回执行结果
     */
    @SneakyThrows
    public String getPresignedObjectUrl(String bucketName, String filePath, Map<String, String> paraMap){
        return pearlMinioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(filePath)
                        .expiry(1, TimeUnit.DAYS)
                        .extraQueryParams(paraMap)
                        .build());
    }

    /**
     * 查询分片数据
     * @param bucketName       存储桶
     * @param region           区域
     * @param objectName       对象名
     * @param uploadId         上传ID
     * @param extraHeaders     额外消息头
     * @param extraQueryParams 额外查询参数
     * @return 返回结果
     */
    public ListPartsResponse listMultiParts(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        return pearlMinioClient.listMultiParts(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, extraHeaders, extraQueryParams);
    }

    /**
     * 完成分片上传，执行合并文件
     * @param bucketName 桶名称
     * @param region 区域
     * @param objectName 对象名
     * @param uploadId 上传Id
     * @param parts 分片
     * @param extraHeaders 额外消息头
     * @param extraQueryParams 额外请求参数
     * @return 返回值
     */
    public ObjectWriteResponse completeMultipartUpload(String bucketName, String region, String objectName, String uploadId,
                                                       Part[] parts, Multimap<String, String> extraHeaders,
                                                       Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        return pearlMinioClient.completeMultipartUpload(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams);
    }
}