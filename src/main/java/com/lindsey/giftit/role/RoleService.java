package com.lindsey.giftit.role;

import com.lindsey.giftit.users.UserEntity;
import com.lindsey.giftit.users.UserService;
import ma.glasnost.orika.MapperFacade;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class RoleService {
    private RoleRepository roleRepository;
    private MapperFacade mapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, MapperFacade mapper){
        this.roleRepository = roleRepository;
        this.mapper = mapper;

    }

    public RoleEntity findByNameOfRole(String nameOfRole){
        RoleEntity roleEntity = roleRepository.findByNameOfRole(nameOfRole);
        return roleEntity;
    }
}
