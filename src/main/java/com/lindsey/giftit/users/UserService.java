package com.lindsey.giftit.users;


import com.lindsey.giftit.items.ItemService;
import com.lindsey.giftit.role.RoleDTO;
import com.lindsey.giftit.role.RoleEntity;
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
        log.warn("userEntity + " +userEntity.getFirstName());
        userEntity.addRole(roleService.findByNameOfRole("ROLE_USER"));
        log.warn("Role has been added: " + userEntity.getRoles());
        userRepository.save(userEntity);
        return userDTO;
    }

    public UserDTO loginUser(UserDTO userDTO){
        userRepository.findByEmail(userDTO.getEmail());
        log.warn("logged in user is: " + userDTO.getEmail());
        log.warn("the user " + userDTO.getUsername() + " has this role: " + userDTO.getRoles());
        return userDTO;
    }

    public UserDTO getUserById(Long id){
        UserEntity entity = userRepository.getOne(id);
        UserDTO userDTO = mapper.map(entity, UserDTO.class);
        return userDTO;
    }

    public UserDTO findByUsername(String username){
        UserEntity entity = userRepository.getUserByUsername(username);
        UserDTO userDTO = mapper.map(entity, UserDTO.class);
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
