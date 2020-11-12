package com.lindsey.giftit.users;

import com.lindsey.giftit.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserService{
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;
    private final MapperFacade mapper;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService, MapperFacade mapper){
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = new BCryptPasswordEncoder();
        this.mapper = mapper;
    }

    public UserDTO createNewUser(UserDTO userDTO) {
        //encrypt passwords
        String secret = "{bcrypt}" + encoder.encode(userDTO.getPassword());
        userDTO.setPassword(secret);
        userDTO.setConfirmPassword(secret);

        //capitalize the first letter of the user's first and last names, and lowercase remaining letters
        userDTO.setFirstName(userDTO.getFirstName().substring(0, 1).toUpperCase() + userDTO.getFirstName().substring(1).toLowerCase());
        userDTO.setLastName(userDTO.getLastName().substring(0, 1).toUpperCase() + userDTO.getLastName().substring(1).toLowerCase());

        //lowercase email and username
        userDTO.setEmail(userDTO.getEmail().toLowerCase());
        userDTO.setUsername(userDTO.getUsername().toLowerCase());

        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        userEntity.addRole(roleService.findByNameOfRole("ROLE_USER"));
        userRepository.save(userEntity);
        return userDTO;
    }

    public UserDTO loginUser(UserDTO userDTO) {
        UserEntity userEntity = mapper.map(userDTO, UserEntity.class);
        userRepository.findByUsername(userEntity.getUsername());
        return userDTO;
    }

    public UserDTO findById(Long id){
        UserEntity entity = userRepository.getOne(id);
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(),entity.getLastName(),entity.getUsername(),entity.getEmail(),entity.getPassword(),entity.getConfirmPassword(), new ArrayList<>(), new ArrayList<>(), entity.getRoles());
        return userDTO;
    }

    public UserDTO findByUsername(String username){
        UserEntity entity = userRepository.findUserByUsername(username.toLowerCase());
        UserDTO userDTO = new UserDTO(entity.getId(), entity.getFirstName(),entity.getLastName(),entity.getUsername(),entity.getEmail(),entity.getPassword(),entity.getConfirmPassword(),  new ArrayList<>(), new ArrayList<>(), entity.getRoles());
        return userDTO;
    }

    public void addFriendById(Long userId, Long friendId){
        UserEntity userUserEntity = userRepository.findUserById(userId);
        UserEntity userFriendEntity = userRepository.findUserById(friendId);
        userRepository.addFriendById(userUserEntity.getId(), userFriendEntity.getId());
    }

    public List<UserDTO> getAllFriendsByUsername(String username){
        List <UserDTO> friendList = new ArrayList<>();
        List<Long> friendIds = getAllFriendIds(username);
        for(Long friendId : friendIds){
            UserDTO friend = findById(friendId);
            friendList.add(friend);
        }
        return friendList;
    }

    public List<Long> getAllFriendIds(String username){
        return userRepository.getAllFriendIds(findByUsername(username).getId());
    }

    public boolean areUsersFriends(Long userId, Long friendId){
        return userRepository.areUsersFriends(userId, friendId);
    }

    public void removeAsFriend(Long userId, Long friendId){
        userRepository.removeAsFriend(userId, friendId);
    }

}
