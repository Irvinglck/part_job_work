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
}
