package com.lindsey.giftit.users;


import com.lindsey.giftit.items.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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
        UserEntity userEntity = new UserEntity(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(), new ArrayList<>(), new ArrayList<>());
        userEntity = userRepository.save(userEntity);
        return new UserDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>(), new ArrayList<>());
    }

    public UserDTO getUserById(Long id){
        UserEntity entity = userRepository.getOne(id);
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getEmail(), entity.getPassword(), itemService.getAllItemsByUsername(entity.getUsername()), new ArrayList<>());
        return userDTO;
    }

    public UserDTO getUserByUsername(String username){
        UserEntity entity = userRepository.getUserByUsername(username);
//        for(UserEntity friend : )
//            List<UserDTO> friendsList = new ArrayList<>();

        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getEmail(), entity.getPassword(), itemService.getAllItemsByUsername(username), new ArrayList<>());
        return userDTO;
    }

    public void addFriendById(String userUsername, String friendUsername){
        UserEntity userUserEntity = userRepository.getUserByUsername(userUsername);
        UserEntity userFriendEntity = userRepository.getUserByUsername(friendUsername);
        userRepository.addFriendById(userUserEntity.getId(), userFriendEntity.getId());
    }

    public List<UserDTO> getAllFriendsByUsername(String username){
        List <UserDTO> friendList = new ArrayList<>();
        List<Long> friendIds = getAllFriendIds(username);
        for(Long friendId : friendIds){
            UserDTO friend = getUserById(friendId);
            friendList.add(friend);
        }
        return friendList;
    }

    public List<Long> getAllFriendIds(String username){
        return userRepository.getAllFriendIds(getUserIdByUsername(username));
    }

    public Long getUserIdByUsername(String username){
        UserEntity entity = userRepository.getUserByUsername(username);
        return entity.getId();

    }

}
