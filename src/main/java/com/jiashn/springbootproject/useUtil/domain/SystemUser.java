package com.jiashn.springbootproject.useUtil.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author: jiangjs
 * @description:
 * @date: 2023/5/8 11:40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemUser {
    private String name;
    private String gender;
    private Integer age;
    private Position position;

    public Optional<Position> optionalPosition(){
        return Optional.ofNullable(position);
    }
}
