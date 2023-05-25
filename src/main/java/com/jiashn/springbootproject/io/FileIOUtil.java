package com.jiashn.springbootproject.io;

import java.io.*;
import java.util.Objects;

/**
 * @author jiangjs
 * @date 2023-05-23 21:17
 * @description 使用InputStream/OutputStream
 */
public class FileIOUtil {

    public static void main(String[] args) {

        String filePath = "D:\\file\\test.txt";
        FileInputStream fis = null;
        FileOutputStream fout = null;
        FileReader fr = null;
        FileWriter fw = null;
        try {
            //使用FileInputStream读取文件信息
             fis = new FileInputStream(filePath);
             //一次性将文件内容读出
             byte[] bytes = new byte[fis.available()];
             int item = 0;
             while ((item = fis.read(bytes)) != -1){
                 String str = new String(bytes, 0, item);
                 System.out.println("读取文件内容："+str);
            }
             //使用FileReader读取文件数据
            File file = new File(filePath);
            fr = new FileReader(file);
            char[] chars = new char[1024];
            int frLen = 0;
            while ((frLen = fr.read(chars)) != -1){
                String str = new String(chars,0,frLen);
                System.out.println("FileReader读取文件内容："+str);
            }

            //使用FileOutputStream输入内容到文件
            String content = "只是输入的内容";
            fout = new FileOutputStream(filePath,Boolean.FALSE);
            fout.write(content.getBytes());
            //使用FileWriter写入文件内容
            String appendContent = "FileWriter写入的数据";
            fw = new FileWriter(filePath,Boolean.FALSE);
            fw.append(appendContent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(fis)) {
                    fis.close();
                }
                if (Objects.nonNull(fout)) {
                    fout.close();
                }
                if (Objects.nonNull(fr)) {
                    fr.close();
                }
                if (Objects.nonNull(fw)) {
                    fw.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
