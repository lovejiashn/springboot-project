package com.jiashn.springbootproject.hutool;

import cn.hutool.http.HttpRequest;
import com.jiashn.springbootproject.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/6/28 16:21
 **/
@RestController
@RequestMapping("/file")
public class PostFileUtil {

    @PostMapping("/upload.do")
    public ResultUtil<String> uploadFile(MultipartFile file,
                                         @RequestParam("localCode") @NotBlank String localCode,
                                         @RequestParam("projName") @NotBlank String projName,
                                         @RequestParam("projInfoId") @NotBlank String projInfoId,
                                         @RequestParam("regionId") @NotBlank String regionId) throws IOException {
        Map<String,Object> params = new HashMap<>(4);
        params.put("localCode",localCode);
        params.put("projName",projName);
        params.put("projInfoId",projInfoId);
        params.put("regionId",regionId);
        HttpRequest req = HttpRequest.post("http://localhost:8083/aplanmis-front/monomer/uploadFile.do")
                .contentType("multipart/form-data");
        req.form("file",convertToFile(file));
        params.forEach(req::form);
        String body = req.execute().body();
        return ResultUtil.success(body);
    }

    public static File convertToFile(MultipartFile file) throws IOException {
        File tmpFile = Files.createTempFile("temp", ".tmp").toFile();
        try (FileOutputStream fos = new FileOutputStream(tmpFile)){
            fos.write(file.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmpFile;
    }
}
