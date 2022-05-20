package com.jiashn.springbootproject.word.controller;

import com.jiashn.springbootproject.word.service.HutoolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangjs
 * @date 2022-04-03 20:13
 */
@RestController
@RequestMapping("/hutool")
public class HutoolController {

    @Resource
    private HutoolService hutoolService;

    @GetMapping("/getWordByHutool.do")
    public void getWordByHutool(){
        hutoolService.getWordByHutool();
    }

    @GetMapping("/downLoadWord.do")
    public void downLoadWord(HttpServletResponse response){
        hutoolService.downLoadWord(response);
    }
    @GetMapping("/showElectronlicense.do")
    public ModelAndView showElectronlicense(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/electronlicense");
        return modelAndView;
    }
}
