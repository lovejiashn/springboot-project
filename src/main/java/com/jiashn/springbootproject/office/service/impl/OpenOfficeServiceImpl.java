package com.jiashn.springbootproject.office.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiashn.springbootproject.minio.entity.SysFile;
import com.jiashn.springbootproject.minio.enums.BucketEnum;
import com.jiashn.springbootproject.minio.mapper.SysFileMapper;
import com.jiashn.springbootproject.office.service.OpenOfficeService;
import com.jiashn.springbootproject.office.util.AsposeOfficeToPdfUtil;
import com.jiashn.springbootproject.utils.MinioUtil;
import com.jiashn.springbootproject.office.util.JodOfficeToPdfUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    private JodOfficeToPdfUtil jodOfficeToPdfUtil;
    @Autowired
    private AsposeOfficeToPdfUtil asposeOfficeToPdfUtil;

    @Value("${upload-filePath}")
    private String path;

    /**
     * 支持在线查看的文件格式
     */
    public static final List<String> FILE_SUFFIX = Arrays.asList("jpg","png","txt","doc","docx","xls","xlsx","ppt","pptx");
    public static final String HTTP = "http";
    @Override
    public void openOfficeOnlinePreview(String param, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File sourceFile = null;
        String localSecond = String.valueOf(System.currentTimeMillis());
        String destFile = path+"\\"+localSecond+".pdf";
        boolean isDelSourceFile = false;
        InputStream urlStream = null;
        ReadableByteChannel readableByteChannel = null;
        FileChannel fileChannel = null;
        FileOutputStream fileOutputStream = null;
        try{
            //校验当前传递参数是否是url或md5数据
            String suffix = param.substring(param.lastIndexOf(".") + 1);
            if (param.startsWith(HTTP)){
                //获取最后的文件后缀名
                if (StringUtils.isBlank(suffix) || !FILE_SUFFIX.contains(suffix)){
                    log.info("{}:该文件格式不支持在线查看",suffix);
                    return;
                }
                URL url = new URL(param);
                readableByteChannel = Channels.newChannel(url.openStream());
                String fileName = path + "\\" + localSecond + "."+suffix;
                sourceFile = new File(fileName);
                fileOutputStream = new FileOutputStream(sourceFile);
                fileChannel = fileOutputStream.getChannel();
                fileChannel.transferFrom(readableByteChannel,0,Long.MAX_VALUE);
                /*// 试图连接并取得返回状态码
                URLConnection urlConn = url.openConnection();
                urlConn.connect();
                HttpURLConnection httpconn = (HttpURLConnection) urlConn;
                int httpResult = httpconn.getResponseCode();
                if (httpResult == HttpURLConnection.HTTP_OK) {
                    urlStream = urlConn.getInputStream();
                }
                MultipartFile file =

                sourceFile = HttpUrlToFile.getNetHttpToFile(param, fileName);*/
            } else {
                //验证参数中是否带有后缀名
                if (FILE_SUFFIX.contains(suffix)){
                    //默认是本地文件
                    String pathFile = path + "\\" + param;
                    sourceFile = new File(pathFile);
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
                    String filePath = path+"\\"+sysFile.getFileName();
                    isDelSourceFile = true;
                    sourceFile = new File(filePath);
                    FileUtils.copyInputStreamToFile(urlStream,sourceFile);
                }
            }
            inputStream = jodOfficeToPdfUtil.officeToPdf(sourceFile,destFile);
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
        }finally {
            try{
                if (Objects.nonNull(outputStream)){
                    outputStream.close();
                }
                if (Objects.nonNull(inputStream)){
                    inputStream.close();
                }
                if (Objects.nonNull(readableByteChannel)) {
                    readableByteChannel.close();
                }
                if (Objects.nonNull(fileChannel)) {
                    fileChannel.close();
                }
                if (Objects.nonNull(fileOutputStream)) {
                    fileOutputStream.close();
                }

                if (Objects.nonNull(urlStream)) {
                    urlStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            if (StringUtils.isNotBlank(destFile)){
                File file = new File(destFile);
                file.delete();
            }
            if (isDelSourceFile && Objects.nonNull(sourceFile)){
                sourceFile.delete();
            }
        }
    }

    @Override
    public void asposeOfficeOnlinePreview(String param, HttpServletResponse response) {
        String suffix = param.substring(param.lastIndexOf(".") + 1);
        FileInputStream fis = null;
        OutputStream os = null;
        String destFilePath = null;
        try {
            if (FILE_SUFFIX.contains(suffix)){
                String filePath = path + "\\" + param;
                destFilePath = path + "\\" + System.currentTimeMillis() + ".pdf";
                boolean checkedLicense = asposeOfficeToPdfUtil.checkedLicense(suffix);
                if (checkedLicense){
                    boolean officeToPdf = asposeOfficeToPdfUtil.officeToPdf(filePath, destFilePath, suffix);
                    if (officeToPdf){
                        //浏览器显示pdf
                        response.setContentType("application/pdf");
                        fis = new FileInputStream(destFilePath);
                        os = response.getOutputStream();
                        byte[] bytes = new byte[1024];
                        int n;
                        //当没有读取完时,继续读取,循环
                        while((n = fis.read(bytes))!=-1){
                            //将字节数组的数据全部写入到输出流中
                            os.write(bytes,0,n);
                        }
                        //强制将缓存区的数据进行输出
                        os.flush();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (Objects.nonNull(os)){
                    os.close();
                }
                if (Objects.nonNull(fis)){
                    fis.close();
                }
                if (StringUtils.isNotBlank(destFilePath)){
                    File file = new File(destFilePath);
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}