package com.jiashn.springbootproject.minio.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiashn.springbootproject.minio.entity.SysFile;
import com.jiashn.springbootproject.minio.enums.BucketEnum;
import com.jiashn.springbootproject.minio.mapper.SysFileMapper;
import com.jiashn.springbootproject.minio.service.MinioFileManageService;
import com.jiashn.springbootproject.shorUrl.ShortUrlServerEnum;
import com.jiashn.springbootproject.utils.MinioUtil;
import com.jiashn.springbootproject.utils.ResultUtil;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    @Autowired
    private SysFileMapper sysFileMapper;

    @Value("${upload-filePath}")
    private String path;

    @Override
    public ResultUtil<?> upLoadFiles(MultipartFile[] files) {
        List<Map<String,String>> filePaths = minioUtil.upLoadFileBackFileName(files, BucketEnum.EMAIL.getName());
        for (Map<String, String> map : filePaths) {
            SysFile sysFile = new SysFile();
            String fileName = map.get("fileName");
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            sysFile.setBusinessType("email")
                    .setCreateTime(LocalDateTime.now())
                    .setFileName(fileName)
                    .setFilePath(map.get("fileRecode"))
                    .setSuffix(suffix)
                    .setCreateTime(LocalDateTime.now());
            sysFileMapper.insert(sysFile);
        }
        return ResultUtil.success("上传成功");
    }

    @Override
    public ResultUtil<?> downFile(String filePath) {
        Wrapper<SysFile> fileWrapper = Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getFilePath,filePath);
        //获取文件原始名称
        SysFile sysFile = sysFileMapper.selectOne(fileWrapper);
        if (Objects.isNull(sysFile)){
            return ResultUtil.error("该文件未找到");
        }
        InputStream inputStream = minioUtil.downLoadFile(BucketEnum.EMAIL.getName(), sysFile.getFileName());
        File file = new File(path+"\\"+sysFile.getFileName());
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
        Wrapper<SysFile> fileWrapper = Wrappers.<SysFile>lambdaQuery()
                .eq(SysFile::getFilePath,filePath);
        //获取文件原始名称
        SysFile sysFile = sysFileMapper.selectOne(fileWrapper);
        if (Objects.isNull(sysFile)){
            return ResultUtil.error("该文件未找到");
        }
        boolean b = minioUtil.removeFile(BucketEnum.EMAIL.getName(),sysFile.getFileName());
        return b ? ResultUtil.success() : ResultUtil.error("删除失败");
    }

    @Override
    public ResultUtil<List<String>> upLoadFileBackUrl(MultipartFile[] files) {
        List<String> fileUrls = minioUtil.upLoadFileBackUrl(files, BucketEnum.EMAIL.getName());
        String shortUrl = ShortUrlServerEnum.SINA.getShortUrlServer().createShortUrl("https://www.baidu.com/", "2021-12-31");
        log.info("生成短连接：{}",shortUrl);
        return ResultUtil.success(fileUrls);
    }
}
