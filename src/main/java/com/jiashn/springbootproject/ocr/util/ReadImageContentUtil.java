package com.jiashn.springbootproject.ocr.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/11/5 14:31
 **/
@Component
@Slf4j
public class ReadImageContentUtil {
    public Map<String, String> getImageInfo(String filePath) {
        Map<String, String> info = new HashMap<>();
        try (InputStream is = new FileInputStream(filePath)) {
            Metadata metadata = ImageMetadataReader.readMetadata(is);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    info.put(tag.getTagName(), tag.getDescription());
                }
            }
        } catch (Exception e) {
            log.error("Error", e.getMessage());
        }
        return info;
    }
}
