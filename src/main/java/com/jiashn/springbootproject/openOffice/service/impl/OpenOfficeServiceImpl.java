package com.jiashn.springbootproject.openOffice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiashn.springbootproject.minio.entity.SysFile;
import com.jiashn.springbootproject.minio.enums.BucketEnum;
import com.jiashn.springbootproject.minio.mapper.SysFileMapper;
import com.jiashn.springbootproject.openOffice.service.OpenOfficeService;
import com.jiashn.springbootproject.utils.MinioUtil;
import com.jiashn.springbootproject.utils.OfficeToPdfUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author: jiangjs
 * @Description:
 * @Date: 2021/11/25 10:07
 **/
@Service
public class OpenOfficeServiceImpl implements OpenOfficeService {
    private static final Logger log = LoggerFactory.getLogger(OpenOfficeServiceImpl.class);

    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private OfficeToPdfUtil officeToPdfUtil;

    @Value("${upload-filePath}")
    private String path;

    /**
     * 支持在线查看的文件格式
     */
    public static final List<String> FILE_SUFFIX = Arrays.asList("txt","doc","docx","xls","xlsx","ppt","pptx");
    public static final String HTTP = "http";
    @Override
    public void openOfficeOnlinePreview(String param, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String sourceFile = "";
        String destFile = "";
        boolean isDelSourceFile = false;
        InputStream urlStream = null;
        String localSecond = String.valueOf(System.currentTimeMillis());
        try{
            //校验当前传递参数是否是url或md5数据
            String suffix = param.substring(param.lastIndexOf(".") + 1);
            if (param.startsWith(HTTP)){
                //获取最后的文件后缀名
                if (StringUtils.isBlank(suffix) || !FILE_SUFFIX.contains(suffix)){
                    log.info("{}:该文件格式不支持在线查看",suffix);
                    return;
                }
                //将URL文件转换成pdf
                URL url = new URL(param);
                // 试图连接并取得返回状态码
                URLConnection urlConn = url.openConnection();
                urlConn.connect();
                HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                int httpResult = httpConn.getResponseCode();
                if (httpResult == HttpURLConnection.HTTP_OK) {
                    urlStream = urlConn.getInputStream();
                }
                if (Objects.nonNull(urlStream)){
                    String second = String.valueOf(System.currentTimeMillis());
                    sourceFile = path+"\\"+second+"."+suffix;
                    File file = new File(sourceFile);
                    FileUtils.copyInputStreamToFile(urlStream,file);
                }
            } else {
                //验证参数中是否带有后缀名
                if (FILE_SUFFIX.contains(suffix)){
                    //默认是本地文件
                    sourceFile = path + "\\" + param;
                } else {
                    Wrapper<SysFile> fileWrapper = Wrappers.<SysFile>lambdaQuery()
                            .eq(SysFile::getFilePath,param);
                    //获取文件原始名称
                    SysFile sysFile = sysFileMapper.selectOne(fileWrapper);
                    if (Objects.isNull(sysFile)){
                        log.info("未找到该文件");
                        return;
                    }
                    if (StringUtils.isBlank(sysFile.getSuffix()) || !FILE_SUFFIX.contains(sysFile.getSuffix())){
                        log.info("{}:该文件格式不支持在线查看",sysFile.getSuffix());
                        return;
                    }
                    //通过minio获取文件流
                    urlStream = minioUtil.downLoadFile(BucketEnum.EMAIL.getName(), sysFile.getFileName());
                    sourceFile = path+"\\"+sysFile.getFileName();
                    isDelSourceFile = true;
                    File file = new File(sourceFile);
                    FileUtils.copyInputStreamToFile(urlStream,file);
                }
            }
            destFile = path+"\\"+localSecond+".pdf";
            inputStream = officeToPdfUtil.officeToPdf(sourceFile,destFile);
            outputStream = response.getOutputStream();
            byte[] buff = new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while((n = inputStream.read(buff)) != -1){
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff,0,n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
        }catch (Exception e){
            log.error("在线查看文件报错：{}",e.getMessage());
            e.printStackTrace();
            return;
        }finally {
            if (Objects.nonNull(outputStream)){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (Objects.nonNull(inputStream)){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotBlank(destFile)){
                File file = new File(destFile);
                file.delete();
            }
            if (isDelSourceFile && StringUtils.isNotBlank(sourceFile)){
                File file = new File(sourceFile);
                file.delete();
            }
        }
    }
}