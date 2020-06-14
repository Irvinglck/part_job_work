package com.lck.controllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/page")
public class PageControllor {

    @GetMapping("/add")
    public String add() {
        return "/patients/filedata";
    }
    @GetMapping("/toDetail")
    public String toDetail() {
        return "redirect:/detail.html";
    }
    @GetMapping("/toDetail1")
    public String toDetail1() {
        return "redirect:/detail1.html";
    }    @GetMapping("/toDetail2")
    public String toDetail2() {
        return "redirect:/detail2.html";
    }
}
