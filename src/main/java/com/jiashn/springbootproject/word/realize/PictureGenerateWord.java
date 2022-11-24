package com.jiashn.springbootproject.word.realize;

import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.util.BytePictureUtils;
import com.jiashn.springbootproject.word.domain.LabelData;
import com.jiashn.springbootproject.word.domain.PictureContentData;
import com.jiashn.springbootproject.word.enums.WordContentTypeEnum;
import com.jiashn.springbootproject.word.factory.GenerateWordFactory;
import com.jiashn.springbootproject.word.service.GenerateWord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: jiangjs
 * @description:
 * @date: 2022/11/24 16:46
 **/
@Component
public class PictureGenerateWord implements GenerateWord {

    @PostConstruct
    private void init(){
        GenerateWordFactory.register(WordContentTypeEnum.PICTURE,this);
    }

    @Override
    public Object generateWord(LabelData data) {
        PictureContentData picture = (PictureContentData) data;
        String picUrl = picture.getPicUrl();
        if (StringUtils.isNotBlank(picUrl)){
            return new PictureRenderData(picture.getWidth(),picture.getHeight(),picture.getPicType().getPicName(),
                    BytePictureUtils.getUrlBufferedImage(picUrl));
        } else {
            return new PictureRenderData(picture.getWidth(),picture.getHeight(),picture.getFile());
        }
    }
}