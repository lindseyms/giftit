package com.lindsey.giftit.users;

import com.lindsey.giftit.items.ItemEntity;
import com.lindsey.giftit.role.RoleEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Getter
@Setter
@Entity
@Component
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String username;

    @NonNull
    @Column(nullable = false, unique = true)//cannot be null, every email needs to be unique
    private String email;

    @NonNull
    @Column(length = 100)
    private String password;

    @Transient
    @NotEmpty(message = "Please enter password confirmation")
    private String confirmPassword;

    @Transient
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<ItemEntity> itemsList;

//    @Transient
//    @JoinTable(name = "users_friends", joinColumns = {
//            @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
//            @JoinColumn(name = "friend_id", referencedColumnName = "id", nullable = false)})
//    @ManyToMany
//    private List<UserEntity> friendsList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public void addRole(RoleEntity roleEntity){ //**changed from role entity
       //RoleEntity roleEntity = new RoleEntity(roleDTO.getId(), roleDTO.getNameOfRole());
        roles.add(roleEntity);
    }

//    public void addRoles(Set<RoleDTO> roleDTOs){
//        for(RoleDTO role : roleDTOs){
//            RoleEntity roleEntity = mapper.map(role, RoleEntity.class);
//            roles.add(roleEntity);
//        }
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getNameOfRole()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}