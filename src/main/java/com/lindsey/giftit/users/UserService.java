package com.lindsey.giftit.users;


import com.lindsey.giftit.items.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private ItemService itemService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, ItemService itemService){
        this.userRepository = userRepository;
        this.itemService = itemService;
        this.encoder = new BCryptPasswordEncoder();

    }

    public UserDTO createNewUser(UserDTO userDTO){
        String secret = "{bcrypt}" + encoder.encode(userDTO.getPassword());
        userDTO.setPassword(secret);
        userDTO.setConfirmPassword(secret);
        UserEntity userEntity = new UserEntity(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getConfirmPassword());
        userEntity = userRepository.save(userEntity);
        return new UserDTO(userEntity.getId(), userEntity.getFirstName(), userEntity.getLastName(), userEntity.getUsername(), userEntity.getEmail(), userEntity.getPassword(), userDTO.getConfirmPassword());
    }

    public UserDTO getUserById(Long id){
        UserEntity entity = userRepository.getOne(id);
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getEmail(), entity.getPassword(), entity.getConfirmPassword());//, itemService.getAllItemsByUsername(entity.getUsername()), new ArrayList<>());
        return userDTO;
    }

    public UserDTO findByUsername(String username){
        UserEntity entity = userRepository.getUserByUsername(username);
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getUsername(), entity.getEmail(), entity.getPassword(), entity.getConfirmPassword());//, itemService.getAllItemsByUsername(username), new ArrayList<>());
        return userDTO;
    }

//    public UserDTO findByEmail(String email){
//        Optional<UserEntity> entity = userRepository.findByEmail(email);
//        UserDTO userDTO = new UserDTO(entity.get().getId(), entity.get().getFirstName(), entity.get().getLastName(), entity.get().getUsername(), entity.get().getEmail(), entity.get().getPassword(), entity.getConfirmPassword(), new ArrayList<>(), new ArrayList<>());
//        return userDTO;
//    }

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
