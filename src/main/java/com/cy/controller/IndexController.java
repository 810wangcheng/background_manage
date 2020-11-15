package com.cy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("doIndex")
    public String doIndexUI(){
        return "starter";
    }

    @RequestMapping("doPageUI")
    public String doPageUI(){
        return "common/page";
    }

    @RequestMapping("doLogin")
    public String doLogin(){
        return "login";
    }
}
