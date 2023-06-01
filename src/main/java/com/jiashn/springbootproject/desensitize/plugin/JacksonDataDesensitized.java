package com.jiashn.springbootproject.desensitize.plugin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.jiashn.springbootproject.desensitize.aop.JsonSensitive;
import com.jiashn.springbootproject.desensitize.enums.DesensitizedStrategy;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * @author: jiangjs
 * @description: 通过jackson数据序列化来实现脱敏
 * @date: 2023/6/1 15:54
 **/
@NoArgsConstructor
@AllArgsConstructor
public class JacksonDataDesensitized extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏数据
     */
    private DesensitizedStrategy strategy;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(strategy.getDesensitize().apply(value));

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (Objects.isNull(property)){
           return prov.findNullValueSerializer(null);
        }
        //校验当前bean是否为String
        if (Objects.equals(property.getType().getRawClass(),String.class)){
            JsonSensitive sensitive = property.getAnnotation(JsonSensitive.class);
            if (Objects.isNull(sensitive)){
                sensitive = property.getContextAnnotation(JsonSensitive.class);
            }
            if (Objects.nonNull(sensitive)){
                return new JacksonDataDesensitized(sensitive.strategy());
            }
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
