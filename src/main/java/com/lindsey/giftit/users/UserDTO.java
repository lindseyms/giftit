package com.lindsey.giftit.users;

import com.lindsey.giftit.items.ItemDTO;
import com.lindsey.giftit.items.ItemEntity;
import com.lindsey.giftit.role.RoleDTO;
import com.lindsey.giftit.role.RoleEntity;
import lombok.*;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    @NonNull
    private Long id;
    @NonNull

    private String firstName;
    @NonNull

    private String lastName;
    @NonNull

    private String username;
    @NonNull

    private String email;
    @NonNull

    private String password;
    private String confirmPassword;

//    private List<ItemDTO> itemsList;
//    private List<UserDTO> friendsList;

    private Set<RoleEntity> roles = new HashSet<>();



}
