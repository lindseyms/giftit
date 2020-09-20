package com.lindsey.giftit;

import com.lindsey.giftit.role.RoleDTO;
import com.lindsey.giftit.role.RoleEntity;
import com.lindsey.giftit.users.UserDTO;
import com.lindsey.giftit.users.UserEntity;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public MapperFactory mapperFactory(){
        DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .dumpStateOnException(false)
                .useBuiltinConverters(true)
                .build();

        mapperFactory.classMap(UserDTO.class, UserEntity.class)
                .mapNulls(true)
                .byDefault()
                .register();

        mapperFactory.classMap(UserEntity.class, UserDTO.class)
                .mapNulls(true)
                .byDefault()
                .register();

        mapperFactory.classMap(RoleDTO.class, RoleEntity.class)
                .mapNulls(true)
                .byDefault()
                .register();

        mapperFactory.classMap(RoleEntity.class, RoleDTO.class)
                .mapNulls(true)
                .byDefault()
                .register();

        return mapperFactory;
    }

    @Bean
    MapperFacade mapper(MapperFactory mapperFactory){
        return mapperFactory.getMapperFacade();
    }

}
