package com.jiashn.springbootproject.redis.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: jiangjs
 * @description: redis配置类
 * @date: 2023/4/7 11:16
 **/
@Configuration
public class RedisConfig {
    
    @Value("${redis.channel}")
    private String channel;

    @Bean
    public MessageListenerAdapter adapter(MessageReceiver receiver){
        return new MessageListenerAdapter(receiver, "receive");
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory,
                                                  // RedisMessageListener listener,
                                                   MessageListenerAdapter adapter){
        List<String> channels = Arrays.asList(StringUtils.split(channel, ","));
        List<ChannelTopic> topics = new ArrayList<>(channels.size());
        channels.forEach(cha -> topics.add(new ChannelTopic(cha)));
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        //订阅频道
      //  container.addMessageListener(listener,topics);
        container.addMessageListener(adapter,topics);
        return container;
    }

    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        StringRedisSerializer serializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer redisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(serializer);
        template.setValueSerializer(redisSerializer);
        template.setHashKeySerializer(serializer);
        template.setHashValueSerializer(redisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory factory){
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(factory);
        return listenerContainer;
    }
}
