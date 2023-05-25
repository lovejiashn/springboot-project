package com.jiashn.springbootproject.io;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 数组流操作文件
 * @date: 2023/5/24 11:19
 **/
public class ByteArrayIOUtil {

    public static void main(String[] args) {
        String filePath ="D:\\file\\test.txt";
        ByteArrayInputStream bais = null;
        CharArrayReader car = null;
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        CharArrayWriter caw = null;
        FileWriter fw = null;
        try {
            //使用FileInputStream读取文件信息
            //一次性将文件内容读出
            byte[] bytes = new byte[1024*10];
            int item = 0;
            bais = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File(filePath)));
            while ((item = bais.read(bytes)) != -1){
                String str = new String(bytes, 0, item);
                System.out.println("读取文件内容："+str);
            }
            //CharArrayReader读取文件内容
            char[] chars = fileToChar(filePath);
            assert chars != null;
            car = new CharArrayReader(chars);
            int charNum = 0;
            while ((charNum = car.read(chars)) != -1){
                String str = new String(chars, 0, charNum);
                System.out.println("CharArrayReader:读取文件内容："+str);
            }

            //ByteArrayOutputStream写入文件内容
            String content = "ByteArrayOutputStream写入的文件内容";
            baos = new ByteArrayOutputStream();
            baos.write(content.getBytes(StandardCharsets.UTF_8));
            fos = new FileOutputStream(filePath);
            baos.writeTo(fos);

            //CharArrayWriter写入文件内容
            caw = new CharArrayWriter();
            caw.write("这是测试CharArrayWriter");
            fw = new FileWriter(filePath);
            caw.writeTo(fw);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(bais)) {
                    bais.close();
                }
                if (Objects.nonNull(car)) {
                    car.close();
                }
                if (Objects.nonNull(baos)) {
                    baos.close();
                }
                if (Objects.nonNull(fos)) {
                    fos.close();
                }if (Objects.nonNull(caw)) {
                    caw.close();
                }if (Objects.nonNull(fw)) {
                    fw.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static char[] fileToChar(String filePath){
        FileReader fr = null;
        try {
            fr = new FileReader(filePath);
            char[] chars = new char[1024];
            int frLen = 0;
            StringBuilder sb = new StringBuilder();
            while ((frLen = fr.read(chars)) != -1){
                sb.append(new String(chars,0,frLen));
            }
            return sb.toString().toCharArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(fr)) {
                    fr.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
