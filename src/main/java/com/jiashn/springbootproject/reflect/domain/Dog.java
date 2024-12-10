package com.jiashn.springbootproject.reflect.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.InputStream;
import java.util.List;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/6/30 9:51
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class Dog extends Animal{
    /**
     * 品种
     */
    @NotBlank(message = "品种不能为空")
    public String breed;

    private String[] params;

    public Dog(String breed){
        this.breed = breed;
    }
    public Dog(){
    }

    @Data
    public static class OtherDog{
        private String other;
    }

    public String getOther(String id,Integer age){
        return "id:"+id+"age:"+age;
    }

    public List<Integer> getInputStream(){
        return null;
    }
}
