package com.lindsey.giftit.role;

import com.lindsey.giftit.users.UserDTO;
import com.lindsey.giftit.users.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.ManyToMany;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Long id;
    private String nameOfRole;
    private Collection<UserEntity> users;
}
