package com.lindsey.giftit.users;

import com.lindsey.giftit.items.ItemDTO;
import com.lindsey.giftit.items.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;
    private final ItemService itemService;
    private Long friendId;

    public UserController(UserService userService, ItemService itemService){
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/friends")
    public String friends(Model model, Principal principal) {
        UserDTO loggedInUser = userService.findByUsername(principal.getName());

        List<UserDTO> friends = userService.getAllFriendsByUsername(loggedInUser.getUsername());

        model.addAttribute("friends", friends);
        return "pages/friends";
    }

    @GetMapping("/search")
    public String findFriends(Model model, @RequestParam("username") String username, Principal principal){
        UserDTO loggedInUser = userService.findByUsername(principal.getName());
        UserDTO friend = userService.findByUsername(username);
        friendId = friend.getId();

        List<ItemDTO> items = itemService.findAllItemsByUserId(friendId);
        model.addAttribute("itemDTOS", items);
        model.addAttribute("firstName", friend.getFirstName());

        boolean areUsersFriends = userService.areUsersFriends(loggedInUser.getId(), friendId);
        if(areUsersFriends){
            model.addAttribute("areUsersFriends", "true");
        }
        else{
            model.addAttribute("areUsersFriends", "false");
        }
        return "pages/friend_profile";
    }

    @PostMapping("/search")
    public String updateFriendStatus(RedirectAttributes redirectAttributes, Principal principal){
        UserDTO loggedInUser = userService.findByUsername(principal.getName());

        boolean areUsersFriends = userService.areUsersFriends(loggedInUser.getId(), friendId);

        if(areUsersFriends){
            userService.removeAsFriend(loggedInUser.getId(), friendId);
        }
        else{
            userService.addFriendById(loggedInUser.getId(), friendId);
        }
        String friendUsername = userService.findById(friendId).getUsername();
        redirectAttributes.addAttribute("username", friendUsername).addFlashAttribute("success", true);

        return "redirect:/search";
    }

    @ExceptionHandler(NullPointerException.class)
    public String userNotFound(NullPointerException ex){
        return "pages/person_not_found";
    }


}
