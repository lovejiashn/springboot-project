package com.jiashn.springbootproject.down.util;


import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.CountDownLatch;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/11/21 11:04
 **/
public class DownloadThreadTask implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(DownloadThreadTask.class);

    private final File file;
    /**
     * 下载文件名称
     */
    private final String downLoadFileName;
    /**
     * 偏移量
     */
    private long offset = 0;
    /**
     * 分配给本线程的下载字节数
     */
    private long length = 0;

    private final CountDownLatch latch;

    DownloadThreadTask(File file,String downLoadFileName,long offset,long length,CountDownLatch latch){
        this.file = file;
        this.downLoadFileName = downLoadFileName;
        this.offset = offset;
        this.length = length;
        this.latch = latch;
    }

    @Override
    public void run() {
        try{
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buff = new byte[1024];
            int bytesRead;
            File newFile = new File(downLoadFileName);
            RandomAccessFile raf = new RandomAccessFile(newFile,"rw");
            while ((bytesRead = bis.read(buff, 0, buff.length)) != -1) {
                raf.seek(this.offset);
                raf.write(buff, 0, bytesRead);
                this.offset = this.offset + bytesRead;
            }
            raf.close();
            bis.close();
        }catch(Exception e){
            log.error("下载文件报错：{}",e.getMessage());
            e.printStackTrace();
        }finally {
            latch.countDown();
        }
    }
}