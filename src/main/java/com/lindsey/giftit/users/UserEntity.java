package com.lindsey.giftit.users;

import com.lindsey.giftit.items.ItemEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
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
    //private String confirmPassword;

//    @ManyToMany//learn how to use this
//    private List<UserEntity> friendsList;

    @OneToMany
    @JoinColumn(name = "username")
    private List<ItemEntity> itemsList;
}