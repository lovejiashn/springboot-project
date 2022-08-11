package com.jiashn.springbootproject.minio.controller;

import com.jiashn.springbootproject.minio.service.PearlMinioFileManageService;
import com.jiashn.springbootproject.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: jiangjs
 * @description: 使用minio实现大文件分片上传
 * @date: 2022/8/10 16:01
 **/
@RestController
@RequestMapping("/pearl/minio")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Api(tags = "分片上传接口")
public class PearlMinioFileManageController {

    public final PearlMinioFileManageService fileManageService;

    @ApiOperation("获取文件分片预签名链接及uploadId接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bucketName",value = "桶名称",required = true,dataType = "string"),
            @ApiImplicitParam(name = "fileName",value = "文件名称",required = true,dataType = "string"),
            @ApiImplicitParam(name = "chunkNum",value = "分片数量",required = true,dataType = "int")
    })
    @GetMapping("/createUploadIdAndPresignedUrl.do")
    public ResultUtil<Map<String, String>> createUploadIdAndPresignedUrl(@RequestParam("bucketName") String bucketName,
                                                                         @RequestParam("fileName") String fileName,
                                                                         @RequestParam("chunkNum") Integer chunkNum){
       return fileManageService.createUploadIdAndPresignedUrl(bucketName,fileName,chunkNum);
    }

    @ApiOperation("合并分片接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bucketName",value = "桶名称",required = true,dataType = "string"),
            @ApiImplicitParam(name = "fileName",value = "文件名称",required = true,dataType = "string"),
            @ApiImplicitParam(name = "uploadId",value = "文件uploadId",required = true,dataType = "string")
    })
    @GetMapping("/completeMultipartUpload.do")
    public ResultUtil<?> completeMultipartUpload(@RequestParam("bucketName") String bucketName,
                                                 @RequestParam("fileName") String fileName,
                                                 @RequestParam("uploadId") String uploadId){
        return fileManageService.completeMultipartUpload(bucketName,fileName,uploadId);
    }

}