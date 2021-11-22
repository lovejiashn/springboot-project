package com.jiashn.springbootproject.minio.service.impl;

import com.jiashn.springbootproject.minio.enums.BucketEnum;
import com.jiashn.springbootproject.minio.service.MinioFileManageService;
import com.jiashn.springbootproject.utils.MinioUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
import com.jiashn.springbootproject.utils.ShortUrlGenerator;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/22 9:57
 **/
@Service
public class MinioFileManageServiceImpl implements MinioFileManageService {

    private static final Logger log = LoggerFactory.getLogger(MinioFileManageServiceImpl.class);

    @Autowired
    private MinioUtil minioUtil;

    @Value("${upload-filePath}")
    private String path;

    @Autowired
    private ShortUrlGenerator shortUrlGenerator;

    @Override
    public ResultUtil<List<Map<String,String>>> upLoadFiles(MultipartFile[] files) {
        List<Map<String,String>> filePaths = minioUtil.upLoadFileBackFileName(files, BucketEnum.EMAIL.getName());
        return ResultUtil.success(filePaths);
    }

    @Override
    public ResultUtil<?> downFile(String filePath) {
        InputStream inputStream = minioUtil.downLoadFile(BucketEnum.EMAIL.getName(), filePath);
        File file = new File(path+"\\测试.png");
        try {
            FileUtils.copyInputStreamToFile(inputStream,file);
        } catch (IOException e) {
            log.error("流转file报错：{}",e.getMessage());
            e.printStackTrace();
            return ResultUtil.error("保存文件报错");
        }
        return ResultUtil.success();
    }

    @Override
    public ResultUtil<?> removeFile(String filePath) {
        boolean b = minioUtil.removeFile("email",filePath);
        return b ? ResultUtil.success() : ResultUtil.error("删除失败");
    }

    @Override
    public ResultUtil<List<String>> upLoadFileBackUrl(MultipartFile[] files) {
        List<String> fileUrls = minioUtil.upLoadFileBackUrl(files, BucketEnum.EMAIL.getName());
        for (String fileUrl : fileUrls) {
            String url = shortUrlGenerator.shortUrl(fileUrl);
            log.info("生成的短连接:{}",url);
        }
        return ResultUtil.success(fileUrls);
    }
}
