package com.jiashn.springbootproject;

import com.jiashn.springbootproject.conditional.DiffSystemLoadBeanService;
import com.jiashn.springbootproject.defaultUse.UseDefaultImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private DiffSystemLoadBeanService loadBeanService;

    @Test
    public void testConditional(){
        loadBeanService.showLoadSystem();
    }

    @Test
    public void testUseDefault(){
        UseDefaultImpl useDefault = new UseDefaultImpl();
        useDefault.sendEmail();
    }

}
