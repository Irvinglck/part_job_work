package com.lck.controllor;

import com.lck.model.User;
import com.lck.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserControllor {

    @Resource
    UserRepository repository;

    @GetMapping("/login/{userName}")
    public String login(
            @PathVariable String userName
    ){
        User lck = repository.findByUserName(userName);
        return lck.toString();
    }
}
