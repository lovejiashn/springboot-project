package com.jiashn.springbootproject.minio.service.impl;

import com.google.common.collect.HashMultimap;
import com.jiashn.springbootproject.minio.service.PearlMinioFileManageService;
import com.jiashn.springbootproject.utils.MinioUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
import io.minio.CreateMultipartUploadResponse;
import io.minio.ListPartsResponse;
import io.minio.messages.Part;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/8/10 16:08
 **/
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PearlMinioFileManageServiceImpl implements PearlMinioFileManageService {

    private final MinioUtil minioUtil;

    @Override
    public ResultUtil<Map<String, String>> createUploadIdAndPresignedUrl(String bucketName,String fileName, Integer chunkNum) {
        Map<String, String> resMap = new HashMap<>(1);
        String contentType = "application/octet-stream";
        HashMultimap<String, String> headers = HashMultimap.create();
        headers.put("Content-Type", contentType);
        CreateMultipartUploadResponse response = minioUtil.getUploadId(bucketName, null, fileName, headers, null);
        String uploadId = response.result().uploadId();
        resMap.put("uploadId",uploadId);
        Map<String, String> reqMap = new HashMap<>(1);
        reqMap.put("uploadId",uploadId);
        for (int i = 1; i <= chunkNum; i++) {
            reqMap.put("partNumber",String.valueOf(i));
            //获取预签名上传地址
            String url = minioUtil.getPresignedObjectUrl(bucketName, fileName, reqMap);
            resMap.put("chunk_"+(i-1),url);
        }
        return ResultUtil.success(resMap);
    }

    @Override
    public ResultUtil<?> completeMultipartUpload(String bucketName,String fileName, String uploadId) {
        try {
            ListPartsResponse response = minioUtil.listMultiParts(bucketName, null, fileName, 1000, 0, uploadId, null, null);
            int partNum = 1;
            Part[] parts = new Part[10000];
            for (Part part : response.result().partList()) {
                parts[partNum - 1] = new Part(partNum,part.etag());
                partNum++;
            }
            minioUtil.completeMultipartUpload(bucketName, null, fileName, uploadId, parts, null, null);
            return ResultUtil.success("合并文件成功");
        } catch (Exception e){
            log.error("合并文件分片报错：{}",e.getMessage());
            e.printStackTrace();
            return ResultUtil.error("合并文件失败");
        }
    }
}