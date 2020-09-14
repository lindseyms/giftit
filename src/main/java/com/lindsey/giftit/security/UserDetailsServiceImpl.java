package com.lindsey.giftit.security;



import com.lindsey.giftit.users.UserEntity;
import com.lindsey.giftit.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Optional<UserEntity> user = userRepository.findByEmail(email);
       if(!user.isPresent()){
           throw new UsernameNotFoundException(email);
       }
       return user.get();
    }


}