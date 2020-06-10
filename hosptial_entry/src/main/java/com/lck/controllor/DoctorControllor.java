package com.lck.controllor;

import com.lck.model.User;
import com.lck.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class DoctorControllor {

    @Resource
    UserRepository repository;
    //首页
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    //登录页面
    @GetMapping("/login")
    public String login(
            @RequestParam(name="username") String username,
            @RequestParam(name="password") String password,
            Model model, HttpSession session
    ){
        if(!StringUtils.isEmpty(username)&&"123456".equals(password)){
            session.setAttribute("loginUser",username);
            return "redirect:/list.html";
        }else{
            model.addAttribute("msg","用户名或者密码错误");
            return "index";
        }
    }



    @GetMapping("/login/{userName}")
    public String login(
            @PathVariable String userName
    ){
        User lck = repository.findByUserName(userName);
        return lck.toString();
    }


}
