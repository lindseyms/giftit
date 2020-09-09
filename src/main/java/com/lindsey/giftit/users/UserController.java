package com.lindsey.giftit.users;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity createNewUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(userDTO));
    }

    @PostMapping(value = "/username/{userUsername}/friendUsername/{friendUsername}")
    public ResponseEntity addFriendByUsername(@PathVariable String userUsername, @PathVariable String friendUsername){
        userService.addFriendById(userUsername, friendUsername);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/id/{id}", produces = "application/json")
    public ResponseEntity getUserById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @GetMapping(value = "/username/{username}", produces = "application/json")
    public ResponseEntity getUserByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUsername(username));
    }

    @GetMapping(value = "/friendsOf/{username}", produces = "application/json")
    public ResponseEntity getAllFriendsByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllFriendsByUsername(username));
    }

    @GetMapping(value = "/friendIdsOf/{username}", produces = "application/json")
    public ResponseEntity getAllFriendIdsByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllFriendIds(username));
    }

    @ExceptionHandler(ConstraintViolationException.class) //this will handle the error if the user does not provide a value that was defined in the entity as being not null
    public ResponseEntity constraintViolation(ConstraintViolationException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data provided");
    }


}
