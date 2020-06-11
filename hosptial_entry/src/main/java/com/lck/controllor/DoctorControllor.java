package com.lck.controllor;

import com.lck.model.Patient;
import com.lck.model.User;
import com.lck.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
            model.addAttribute("patients",mockData());
            return "redirect:/list.html";
        }else{
            model.addAttribute("msg","用户名或者密码错误");
            return "index";
        }
    }

    List<Patient> mockData(){
        List<Patient> resultList=new ArrayList<>();
        resultList.add(new Patient().setNumber("DS001").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("3").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS002").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS003").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("8").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS004").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("2").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS005").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("20").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("0").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        resultList.add(new Patient().setNumber("DS007").setUsername("特朗普").setGender("1").setAge("250").setIsDiabetes("1").setDiaAge("14").setHighBlood("1").setBoolFat("1").setOpraType("人工").setReverseOpra("机器矫正").setIsFirstOpra("1").setOpraTime("2020-09-12 23:12:12").setInhosptialNo("23423423"));
        return resultList;
    }

    @GetMapping("/login/{userName}")
    public String login(
            @PathVariable String userName
    ){
        User lck = repository.findByUserName(userName);
        return lck.toString();
    }


}
