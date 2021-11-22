package com.jiashn.springbootproject.minio.service;

import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/22 9:57
 **/
public interface MinioFileManageService {

    /**
     * 上传文件
     * @param files 多文件数组
     * @return 返回保存数据信息
     */
    ResultUtil<List<Map<String,String>>> upLoadFiles(MultipartFile[] files);

    /**
     * 获取文件信息
     * @param filePath 文件路径
     * @return 返回保存数据信息
     */
    ResultUtil<?> downFile(String filePath);

    /**
     * 删除文件信息
     * @param filePath 文件路径
     * @return 返回保存数据信息
     */
    ResultUtil<?> removeFile(String filePath);

    /**
     * 上传文件并返回访问文件的URL
     * @param files 文件
     * @return 返回url集合
     */
    ResultUtil<List<String>> upLoadFileBackUrl(MultipartFile[] files);
}
