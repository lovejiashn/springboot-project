package com.jiashn.springbootproject.io;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author: jiangjs
 * @description: io流之间转换
 * @date: 2023/5/24 16:17
 **/
public class IOChangeUtil {
    public static void main(String[] args) {
        String filePath ="D:\\file\\test.txt";
        File file = new File(filePath);
        //file转InputStream
        try {
            InputStream is = new FileInputStream(file);
            OutputStream os = new FileOutputStream(file);
            FileInputStream fis = FileUtils.openInputStream(file);
            FileOutputStream fos = FileUtils.openOutputStream(file);
            byte[] bytes = new byte[1024 * 10];
            os.write(bytes);
            os.close();
            FileReader reader = new FileReader(file);
            char[] chars = new char[1024];
            reader.read(chars);

            byte[] bytes2 = IOUtils.toByteArray(is);
            String content = "231212121212";
            byte[] bytes1 = content.getBytes(StandardCharsets.UTF_8);
            char[] chars1 = content.toCharArray();

            InputStream inputStream = new ByteArrayInputStream(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }
}
