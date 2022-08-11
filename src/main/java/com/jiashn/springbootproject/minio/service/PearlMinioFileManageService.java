package com.jiashn.springbootproject.minio.service;

import com.jiashn.springbootproject.utils.ResultUtil;

import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/10 16:07
 **/
public interface PearlMinioFileManageService {
    /**
     * 返回分片上传需要的签名数据URL及 uploadId
     * @param bucketName 桶名称
     * @param fileName 文件名称
     * @param chunkNum 分片数
     * @return 返回值
     */
    ResultUtil<Map<String, String>> createUploadIdAndPresignedUrl(String bucketName,
                                                                  String fileName,
                                                                  Integer chunkNum);

    /**
     * 分片上传完成后，合并分片生成文件
     * @param bucketName 桶名称
     * @param fileName 文件名称
     * @param uploadId 文件的uploadId
     * @return 返回合并结果
     */
    ResultUtil<?> completeMultipartUpload(String bucketName,
                                          String fileName,
                                          String uploadId);
}
