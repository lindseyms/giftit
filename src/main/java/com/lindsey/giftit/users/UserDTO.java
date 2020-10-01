package com.lindsey.giftit.users;

import com.lindsey.giftit.items.ItemDTO;
import com.lindsey.giftit.role.RoleEntity;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String confirmPassword;

    private List<ItemDTO> itemsList;
    private List<UserDTO> friendsList;

    private Set<RoleEntity> roles = new HashSet<>();



}
