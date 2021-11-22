package com.jiashn.springbootproject.minio.enums;

/**
 * @author jiangjs
 * @Description: minio桶名称枚举
 */

public enum BucketEnum {
    /**
     * email桶
     */
    EMAIL("email");

    BucketEnum(String name){
        this.name = name;
    }
    private final String name;

    public String getName() {
        return name;
    }
}
