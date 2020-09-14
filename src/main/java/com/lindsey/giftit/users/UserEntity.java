package com.lindsey.giftit.users;

import com.lindsey.giftit.items.ItemEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String username;

    @NotNull
    @Column(nullable = false, unique = true)//cannot be null, every email needs to be unique
    private String email;

    @NotNull
    @Column(length = 100)
    private String password;

    @Transient
    @NotEmpty(message = "Please enter password confirmation")
    private String confirmPassword;

//    @OneToMany
//    @JoinColumn(name = "username")
//    private List<ItemEntity> itemsList;
//
//    @JoinTable(name = "friends", joinColumns = {
//            @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
//            @JoinColumn(name = "friend_id", referencedColumnName = "id", nullable = false)})
//    @ManyToMany
//    private List<UserEntity> friendsList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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