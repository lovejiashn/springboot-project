package com.jiashn.springbootproject.down.service.impl;

import com.jiashn.springbootproject.down.service.DownFileService;
import com.jiashn.springbootproject.down.util.DownLoadFileManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/11/21 10:23
 **/
@Service
public class DownFileServiceImpl implements DownFileService {
    @Resource
    private DownLoadFileManager downLoadFileManager;

    @Override
    public void multiDownFile(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String prefix = StringUtils.isNotBlank(fileName) ? fileName.substring(fileName.lastIndexOf(".")) : ".zip";
        File newFile = null;
        try {
            assert fileName != null;
            newFile = File.createTempFile(fileName, prefix);
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert newFile != null;
        downLoadFileManager.downLoadFile(newFile);
    }
}