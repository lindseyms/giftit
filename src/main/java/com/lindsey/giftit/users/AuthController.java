package com.lindsey.giftit.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lindsey.giftit.items.ItemDTO;
import com.lindsey.giftit.items.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class AuthController {

    private UserService userService;
    private ItemService itemService;
    private  Long friendId;

    @Autowired
    public AuthController(UserService userService, ItemService itemService){
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/login")
    public String login(){
        return "auth/login";
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


    @GetMapping("/profile")
    public String profile(Model model){
        Long userId = itemService.loggedInUser().getId();
        List<ItemDTO> itemDTOS = itemService.findAllItemsByUserId(userId);
        model.addAttribute("itemDTOS", itemDTOS);
        return "auth/profile";
    }

    @GetMapping("/")
    public String root(Model model){
        return "auth/index";
    }

    @GetMapping("/home")
    public String home(Model model){
        return "auth/index";
    }

    @GetMapping("/search")
    public String findFriends(Model model, @RequestParam("username") String username){
        UserDTO friend = userService.findByUsername(username);
        friendId = friend.getId();
        List<ItemDTO> items = itemService.findAllItemsByUserId(friendId);
        model.addAttribute("itemDTOS2", items);
        model.addAttribute("firstName", friend.getFirstName());

        boolean areUsersFriends = userService.areUsersFriends(itemService.loggedInUser().getId(), friendId);
        if(areUsersFriends){
            model.addAttribute("areUsersFriends", "true");
        }
        else{
            model.addAttribute("areUsersFriends", "false");
        }

        return "auth/friend_profile";
    }

    @PostMapping("/search")
    public String addFriend(RedirectAttributes redirectAttributes){
        boolean areUsersFriends = userService.areUsersFriends(itemService.loggedInUser().getId(), friendId);

        if(areUsersFriends){
            userService.removeAsFriend(itemService.loggedInUser().getId(), friendId);
        }
        else{
            userService.addFriendById(itemService.loggedInUser().getId(), friendId);
        }
        String friendUsername = userService.findById(friendId).getUsername();
        redirectAttributes.addAttribute("username", friendUsername).addFlashAttribute("success", true);

        return "redirect:/search";
    }

    @ExceptionHandler(NullPointerException.class) //this will handle the error if the user does not provide a value that was defined in the entity as being not null
    public String userNotFound(NullPointerException ex){
        return "auth/person_not_found";
    }


}
