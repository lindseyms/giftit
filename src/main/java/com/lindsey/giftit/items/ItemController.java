package com.lindsey.giftit.items;

import com.lindsey.giftit.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
//@RequestMapping("items")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
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

    @PostMapping("/profile/remove/")
    public String removeItem(@RequestParam("link") String link){
        itemService.removeItem(link);

        return "redirect:/profile";
    }
}
