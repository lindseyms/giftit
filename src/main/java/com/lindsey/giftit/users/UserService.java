package com.lindsey.giftit.users;


import com.lindsey.giftit.items.ItemService;
import com.lindsey.giftit.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserService {
    private UserRepository userRepository;
    private ItemService itemService;
    private RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private MapperFacade mapper;

    @Autowired
    public UserService(UserRepository userRepository, ItemService itemService, RoleService roleService, MapperFacade mapper){
        this.userRepository = userRepository;
        this.itemService = itemService;
        this.roleService = roleService;
        this.encoder = new BCryptPasswordEncoder();
        this.mapper = mapper;
    }


    public UserDTO createNewUser(UserDTO userDTO){
        String secret = "{bcrypt}" + encoder.encode(userDTO.getPassword());
        userDTO.setPassword(secret);
        userDTO.setConfirmPassword(secret);
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        userEntity.addRole(roleService.findByNameOfRole("ROLE_USER"));
        userRepository.save(userEntity);
        return userDTO;
    }

    public UserDTO loginUser(UserDTO userDTO){
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        userRepository.findByUsername(userEntity.getUsername());
        return userDTO;
    }

    public UserDTO findById(Long id){
        UserEntity entity = userRepository.getOne(id);
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(),entity.getLastName(),entity.getUsername(),entity.getEmail(),entity.getPassword(),entity.getConfirmPassword(), new ArrayList<>(), new ArrayList<>(), entity.getRoles());
        return userDTO;
    }
//
//    public UserDTO getUserById(Long id){
//        UserEntity entity = userRepository.getOne(id);
//        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(),entity.getLastName(),entity.getUsername(),entity.getEmail(),entity.getPassword(),entity.getConfirmPassword(), new ArrayList<>(), new ArrayList<>(), entity.getRoles());
//        return userDTO;
//    }

    public UserDTO findByUsername(String username){
        UserEntity entity = userRepository.findUserByUsername(username);
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(),entity.getLastName(),entity.getUsername(),entity.getEmail(),entity.getPassword(),entity.getConfirmPassword(),  new ArrayList<>(), new ArrayList<>(), entity.getRoles());

        return userDTO;
    }

    public void addFriendById(Long userId, Long friendId){
        UserEntity userUserEntity = userRepository.findUserById(userId);
        UserEntity userFriendEntity = userRepository.findUserById(friendId);
        userRepository.addFriendById(userUserEntity.getId(), userFriendEntity.getId());
    }

//    public List<UserDTO> getAllFriendsByUsername(String username){
//        List <UserDTO> friendList = new ArrayList<>();
//        List<Long> friendIds = getAllFriendIds(username);
//        for(Long friendId : friendIds){
//            UserDTO friend = getUserById(friendId);
//            friendList.add(friend);
//        }
//        return friendList;
//    }

//    public List<Long> getAllFriendIds(String username){
//        return userRepository.getAllFriendIds(getUserIdByUsername(username));
//    }

//    public Long getUserIdByUsername(String username){
//        UserEntity entity = userRepository.getUserByUsername(username);
//        return entity.getId();
//
//    }

}
