package com.lindsey.giftit.role;

import com.lindsey.giftit.users.UserDTO;
import com.lindsey.giftit.users.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@RequiredArgsConstructor  //***we changed this from allArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String nameOfRole;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

}
