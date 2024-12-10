package com.jiashn.springbootproject.useabstract;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jiangjs
 * @description:
 * @date: 2024/8/27 15:58
 **/
@RequestMapping("/testAba")
@RestController
@Slf4j
public class TestController {
    @Autowired
    private TestBase testBase;
    @GetMapping("/retest.do")
    public void retest(){
        try {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    System.out.println("执行事务2121212");
                }
            });
            testBase.test();
        } catch (Exception e) {
            log.error("data-exchange[%s]: [%s] error occurred: ", e);
            throw e;
        } finally {
            log.info("data-exchange[%s]: [%s] transform list size %s spent %s ms...");
        }
    }
}
