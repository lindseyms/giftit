package com.lindsey.giftit.role;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;

    }

    public RoleEntity findByNameOfRole(String nameOfRole){
        RoleEntity roleEntity = roleRepository.findByNameOfRole(nameOfRole);
        return roleEntity;
    }
}
