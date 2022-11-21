package com.jiashn.springbootproject.down.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.concurrent.*;

/**
 * @author: jiangjs
 * @description: 下载文件管理
 * @date: 2022/11/21 10:30
 **/
@Component
public class DownLoadFileManager {

    private final static Logger log = LoggerFactory.getLogger(DownLoadFileManager.class);

    private final static long THREAD_FILE_SIZE = 10000 * 1024;
    private final static String LOCAL_PATH = "D://down//";
    private final ExecutorService taskExecutor = Executors.newFixedThreadPool(20);

    public void downLoadFile(File file){
        String fileName = file.getName();
        long size = file.length();
        long offset = 0;
        String downFileName = LOCAL_PATH + System.currentTimeMillis() + "-" + fileName;
        //计算线程数
        long threadCount = (size / THREAD_FILE_SIZE) + (size % THREAD_FILE_SIZE == 0 ? 0 : 1);
        CountDownLatch latch = new CountDownLatch((int)threadCount);
        // 如果远程文件尺寸小于等于unitSize
        if (size <= THREAD_FILE_SIZE) {
            DownloadThreadTask downloadThread = new DownloadThreadTask(file, downFileName, offset,size,latch);
            taskExecutor.execute(downloadThread);
        } else {
            // 如果远程文件尺寸大于unitSize
            for (int i = 1; i < threadCount; i++) {
                DownloadThreadTask downloadThread = new DownloadThreadTask(file, downFileName, offset, THREAD_FILE_SIZE,latch);
                taskExecutor.execute(downloadThread);
                offset = offset + THREAD_FILE_SIZE;
            }
            // 如果不能整除，则需要再创建一个线程下载剩余字节
            if (size % THREAD_FILE_SIZE != 0) {
                DownloadThreadTask downloadThread = new DownloadThreadTask(file, downFileName, offset, size - THREAD_FILE_SIZE * (threadCount - 1), latch);
                taskExecutor.execute(downloadThread);
            }
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("多线程下载文件报错:"+e.getMessage());
            e.printStackTrace();
        }
        taskExecutor.shutdown();
        log.info("下载完成！{}",downFileName);
    }
}