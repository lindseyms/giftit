package com.lindsey.giftit.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String loginHomePage(){
        return "auth/login";
    }
}
