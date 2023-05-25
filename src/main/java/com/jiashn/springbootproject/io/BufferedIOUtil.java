package com.jiashn.springbootproject.io;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 缓冲流使用
 * @date: 2023/5/24 15:20
 **/
public class BufferedIOUtil {
    public static void main(String[] args) {
        String filePath ="D:\\file\\test.txt";
        BufferedInputStream bis = null;
        BufferedOutputStream bdos = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try{
            bis = new BufferedInputStream(new FileInputStream(filePath));
            int itm = 0;
            byte[] bytes = new byte[bis.available()];
            while ((itm = bis.read(bytes)) != -1){
                System.out.println("读取文件内容：" + new String(bytes,0,itm));
            }
            //BufferedReader读取文件内容
            br = new BufferedReader(new FileReader(filePath));
            int itm2 = 0;
            char[] chars = new char[1024];
            while ((itm2 = br.read(chars)) != -1){
                System.out.println("BufferedReader读取文件内容：" + new String(chars,0,itm2));
            }
            //BufferedOutputStream写入文件内容
            bdos = new BufferedOutputStream(new FileOutputStream(filePath));
            bdos.write("BufferedOutputStream写入文件内容".getBytes(StandardCharsets.UTF_8));
            bdos.flush();

            //BufferedWriter写入文件内容
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write("BufferedWriter写入文件内容".toCharArray());
            bw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (Objects.nonNull(bis)) {
                    bis.close();
                }if (Objects.nonNull(br)) {
                    br.close();
                }if (Objects.nonNull(bdos)) {
                    bdos.close();
                }if (Objects.nonNull(bw)) {
                    bw.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
