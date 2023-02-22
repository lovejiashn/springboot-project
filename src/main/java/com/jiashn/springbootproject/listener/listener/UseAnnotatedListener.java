package com.jiashn.springbootproject.listener.listener;

import com.jiashn.springbootproject.listener.domain.SelfDefineEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author jiangjs
 * @date 2023-02-22 4:50
 */
@Component
@Slf4j
public class UseAnnotatedListener {

    @EventListener
    public void getSelfDefine(SelfDefineEvent event){
        log.info("监听事件：{}",event.getUser());
    }
}
