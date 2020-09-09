package com.lindsey.giftit.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getUserByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO friends VALUES (:userId , :friendId)", nativeQuery = true)
    void addFriendById(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "SELECT friend_id FROM friends WHERE user_id = :userId", nativeQuery = true)
    List<Long> getAllFriendIds(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "SELECT friend_id FROM friends WHERE user_id = :userId", nativeQuery = true)
    List<UserEntity> getAllFriendsByUsername(@Param("userId") Long userId);

}
