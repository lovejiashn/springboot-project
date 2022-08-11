package com.jiashn.springbootproject.config;

import com.google.common.collect.Multimap;
import io.minio.CreateMultipartUploadResponse;
import io.minio.ListPartsResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.errors.*;
import io.minio.messages.Part;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/10 15:02
 **/
public class PearlMinioClient extends MinioClient {

    protected PearlMinioClient(MinioClient client) {
        super(client);
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
    @Override
    public ObjectWriteResponse completeMultipartUpload(String bucketName, String region, String objectName, String uploadId,
                                                          Part[] parts, Multimap<String, String> extraHeaders,
                                                          Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        return super.completeMultipartUpload(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams);
    }

    /**
     * 创建分片上传请求
     * @param bucketName 捅名称
     * @param region 区域
     * @param objectName 对象名
     * @param headers 消息头
     * @param extraQueryParams 额外查询参数
     * @return 返回值
     */
    @Override
    public CreateMultipartUploadResponse createMultipartUpload(String bucketName, String region, String objectName, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        return super.createMultipartUpload(bucketName, region, objectName, headers, extraQueryParams);
    }

    /**
     * 查询分片数据
     * @param bucketName 桶
     * @param region 区域
     * @param objectName 对象名
     * @param maxParts 最大分片
     * @param partNumberMarker 分片号
     * @param uploadId 上传Id
     * @param extraHeaders 额外消息头
     * @param extraQueryParams 额外查询参数
     * @return 返回值
     */
    public ListPartsResponse listMultiParts(String bucketName, String region, String objectName, Integer maxParts,
                                               Integer partNumberMarker, String uploadId, Multimap<String, String> extraHeaders,
                                               Multimap<String, String> extraQueryParams) throws NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, ServerException, XmlParserException, ErrorResponseException, InternalException, InvalidResponseException {
        return super.listParts(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, extraHeaders, extraQueryParams);
    }


}