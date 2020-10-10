package com.lindsey.giftit.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String email);

    UserEntity findUserByUsername(String username);

    UserEntity findUserById(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "INSERT INTO users_friends VALUES (:userId , :friendId)", nativeQuery = true)
    void addFriendById(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "SELECT friend_id FROM users_friends WHERE user_id = :userId", nativeQuery = true)
    List<Long> getAllFriendIds(@Param("userId") Long userId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "SELECT friend_id FROM users_friends WHERE user_id = :userId", nativeQuery = true)
    List<UserEntity> getAllFriendsById(@Param("userId") Long userId);

    @Transactional
    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM users_friends WHERE user_id = :userId AND friend_id = :friendId) THEN 'TRUE' ELSE 'FALSE' END", nativeQuery = true)
    boolean areUsersFriends(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM users_friends WHERE user_id = :userId AND friend_id = :friendId", nativeQuery = true)
    void removeAsFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);


}
