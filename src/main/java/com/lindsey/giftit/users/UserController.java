package com.lindsey.giftit.users;

import com.lindsey.giftit.items.ItemDTO;
import com.lindsey.giftit.items.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
//@RequestMapping("users")
public class UserController {
    private UserService userService;
    private ItemService itemService;

    public UserController(UserService userService, ItemService itemService){
        this.userService = userService;
        this.itemService = itemService;
    }



    @GetMapping("/friends")
    public String friends(Model model) {
        String username = itemService.loggedInUser().getUsername();
        List<UserDTO> friends = userService.getAllFriendsByUsername(username);

        model.addAttribute("friends", friends);
        return "auth/friends";
    }


}
