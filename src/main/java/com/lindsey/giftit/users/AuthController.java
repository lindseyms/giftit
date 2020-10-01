package com.lindsey.giftit.users;

import com.lindsey.giftit.items.ItemDTO;
import com.lindsey.giftit.items.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    @GetMapping("/add_items")
    public String addItems(Model model){
        model.addAttribute("item", new ItemDTO());
        return "auth/add_items";
    }

    @PostMapping("/add_items")
    public String addNewItem(ItemDTO item, RedirectAttributes redirectAttributes){
        itemService.createNewItem(item);
        redirectAttributes.addAttribute("id", item.getId()).addFlashAttribute("success", true);
        return "redirect:/add_items";
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
//        Long userId = itemService.loggedInUser().getId();
//        List<ItemDTO> itemDTOS = itemService.findAllItemsByUserId(userId);
//
//        model.addAttribute("itemDTOS", itemDTOS);
        return "auth/login";
    }

//    @GetMapping("/find_friends")
//    public String findFriends(){
//        return "auth/find_friends";
//    }

    @GetMapping("/search")
    public String findFriends(Model model, @RequestParam("username") String username){
        log.warn("Username is: " + username);
        UserDTO friend = userService.findByUsername(username);
        log.warn("user is!!: " + friend);
        friendId = friend.getId();
        List<ItemDTO> items = itemService.findAllItemsByUserId(friendId);
        model.addAttribute("itemDTOS2", items);

        return "auth/friend_profile";
    }

    @PostMapping("/search")
    public String addFriend(RedirectAttributes redirectAttributes){
//       log.warn("Friend id is: " +friend.getId());
        userService.addFriendById(itemService.loggedInUser().getId(), friendId);
        String friendUsername = userService.findById(friendId).getUsername();
        UserDTO userDTO = userService.findById(friendId);
        log.warn("username is: (coming from postmapping) " + userDTO.getId() + "first" + userDTO.getFirstName() + "last" + userDTO.getLastName() +"usernaem" +userDTO.getUsername() + "email" + userDTO.getEmail() + "pass" + userDTO.getPassword() + "confirm" + userDTO.getConfirmPassword());
        redirectAttributes.addAttribute("username", friendUsername).addFlashAttribute("success", true);
        return "redirect:/search";
    }

}
