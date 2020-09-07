package com.lindsey.giftit.users;

import com.lindsey.giftit.items.ItemDTO;
import com.lindsey.giftit.items.ItemEntity;
import com.lindsey.giftit.items.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
    private UserRepository userRepository;
    private ItemService itemService;

    @Autowired
    public UserService(UserRepository userRepository, ItemService itemService){
        this.userRepository = userRepository;
        this.itemService = itemService;
    }

    public UserDTO createNewUser(UserDTO userDTO){
        UserEntity userEntity = new UserEntity(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(), new ArrayList<>());
        userEntity = userRepository.save(userEntity);
        return new UserDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
    }

    public UserDTO getUserById(Long id){
        UserEntity entity = userRepository.getOne(id);
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getEmail(), entity.getPassword(), itemService.getAllItemsByUsername(entity.getUsername()));
        return userDTO;
    }

    public UserDTO getUserByUsername(String username){
        UserEntity entity = userRepository.getUserByUsername(username);
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getEmail(), entity.getPassword(), itemService.getAllItemsByUsername(username));
        return userDTO;
    }

}
