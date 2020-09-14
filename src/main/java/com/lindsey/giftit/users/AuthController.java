package com.lindsey.giftit.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String root(){
        return "auth/profile";
    }

    @GetMapping("/add_items")
    public String addItems(){
        return "auth/add_items";
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @GetMapping("/home")
    public String home(){
        return "auth/home";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new UserDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerNewUser(UserDTO user, RedirectAttributes redirectAttributes){
        userService.createNewUser(user);
        redirectAttributes.addAttribute("id", user.getId()).addFlashAttribute("success", true);
        return "redirect:/register";
    }

}
