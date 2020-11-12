package com.lindsey.giftit.security;

import com.lindsey.giftit.items.ItemDTO;
import com.lindsey.giftit.items.ItemService;
import com.lindsey.giftit.users.UserDTO;
import com.lindsey.giftit.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class AuthController {

    final private UserService userService;
    final private ItemService itemService;

    @Autowired
    public AuthController(UserService userService, ItemService itemService){
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/login")
    public String login(){
        return "pages/login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new UserDTO());
        return "pages/register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid UserDTO user, RedirectAttributes redirectAttributes) {
        try{
            userService.createNewUser(user);

        } catch (DataIntegrityViolationException ex){
            redirectAttributes.addAttribute("id", user.getId()).addFlashAttribute("failed", true);
            return "redirect:/register";
        }
        redirectAttributes.addAttribute("id", user.getId()).addFlashAttribute("success", true);
        return "redirect:/register";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal){
        UserDTO loggedInUser = userService.findByUsername(principal.getName());
        List<ItemDTO> itemDTOS = itemService.findAllItemsByUserId(loggedInUser.getId());
        if(itemDTOS.isEmpty()) {
            model.addAttribute("noItemKey", "noItems");
        }

        model.addAttribute("itemDTOS", itemDTOS);
        return "pages/profile";
    }

    @GetMapping("/")
    public String root(){
        return "pages/index";
    }

    @GetMapping("/home")
    public String home(){
        return "pages/index";
    }

}
