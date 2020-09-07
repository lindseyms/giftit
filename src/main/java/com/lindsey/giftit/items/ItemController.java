package com.lindsey.giftit.items;

import lombok.Data;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {
    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService){
        this.itemService = itemService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity createNewItem(@RequestBody ItemDTO itemDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.createNewItem(itemDTO));
    }

    @GetMapping(value = "username/{username}", produces = "application/json")
    public ResponseEntity getAllItemsByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getAllItemsByUsername(username));
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ResponseEntity getItemById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(itemService.getItemById(id));
    }

    @DeleteMapping(value = "remove/{id}") //figure out if this needs to go here or in the userservice
    public ResponseEntity removeItem(@PathVariable Long id){
        itemService.removeItem(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(ConstraintViolationException.class) //this will handle the error if the user does not provide a value that was defined in the entity as being not null
    public ResponseEntity constraintViolation(ConstraintViolationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data provided");
    }

}
