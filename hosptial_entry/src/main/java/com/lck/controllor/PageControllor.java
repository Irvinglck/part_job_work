package com.lck.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageControllor {
    @GetMapping("/index")
    private String index(){
        return "index";
    }
}
