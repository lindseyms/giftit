package com.lindsey.giftit.security;



import com.lindsey.giftit.role.RoleDTO;
import com.lindsey.giftit.role.RoleEntity;
import com.lindsey.giftit.users.UserDTO;
import com.lindsey.giftit.users.UserEntity;
import com.lindsey.giftit.users.UserRepository;
import com.lindsey.giftit.users.UserService;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;
    private UserService userService;
    private MapperFacade mapper;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, UserService userService, MapperFacade mapper) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        log.warn("userEntity coming from find by email method in repository: " + userEntity.get().getEmail());

        if(!userEntity.isPresent()){
            throw new UsernameNotFoundException(email);
        }
        return userEntity.get();
    }

}